package com.wappster.fitbook.ui.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.wappster.fitbook.R
import com.wappster.fitbook.models.User
import com.wappster.fitbook.network.FitBookApiService
import com.wappster.fitbook.ui.base.BasePresenter
import com.wappster.fitbook.ui.register.RegisterActivity
import com.wappster.fitbook.utils.Constants.Companion.RC_SIGN_IN
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class LoginPresenter(loginView: LoginView) : BasePresenter<LoginView>(loginView) {

    @Inject
    lateinit var apiService: FitBookApiService

    // Google Sign In Variables
    private var gso: GoogleSignInOptions? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mAuth: FirebaseAuth? = null

    // Facebook Sign In Variables
    private var callbackManager : CallbackManager? = null

    private var subscription: Disposable? = null

    override fun onViewCreated() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(view.getContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(view.getContext(), gso!!)
        mAuth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAccessToken(result!!.accessToken)
            }

            override fun onCancel() {
                view.showError(R.string.request_canceled)
            }

            override fun onError(error: FacebookException?) {
                view.showError(error!!.localizedMessage)
            }

        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener(view.getContext() as Activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success")
                        val user = mAuth?.currentUser
                        Log.d("TAG", "signInWithCredential:${user?.displayName}")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.getException())
                        Toast.makeText(view.getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun onLoginClicked(email: String, password: String) {
        view.showLoading()
        subscription = apiService
                .login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { response -> view.updateUser(response.data) },
                        { t -> view.showError(t.localizedMessage) }
                )
    }

    fun onRegisterClicked() {
        view.getContext().startActivity(Intent(view.getContext(), RegisterActivity::class.java))
    }

    fun onGoogleSignInClicked() {
        view.showLoading()
        val intent = mGoogleSignInClient?.signInIntent
        if (view.getContext() is LoginActivity) {
            (view.getContext() as LoginActivity).startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    fun onFacebookSignInClicked() {
        view.showLoading()
        LoginManager.getInstance().logInWithReadPermissions(view.getContext() as Activity, listOf("email", "public_profile"))
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                view.showError(e.localizedMessage)
            }

        } else {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener((view.getContext() as Activity)) { task ->
                    if (task.isSuccessful) {
                        val user = User(0,
                                task.result.user.email,
                                null,
                                task.result.user.providerId,
                                Date(task.result.user.metadata!!.creationTimestamp),
                                Date(task.result.user.metadata!!.lastSignInTimestamp))
                        view.updateUser(user)
                    } else {
                        view.showError(task.exception!!.localizedMessage)
                    }
                }
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
        mGoogleSignInClient = null
    }
}