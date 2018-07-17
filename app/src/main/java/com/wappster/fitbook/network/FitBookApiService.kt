package com.wappster.fitbook.network

import com.wappster.fitbook.models.User
import com.wappster.fitbook.ui.base.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FitBookApiService {

    @FormUrlEncoded
    @POST(LOGIN)
    fun login(
            @Field("email") email : String,
            @Field("password") password : String) :
            Observable<BaseResponse<User>>

    @FormUrlEncoded
    @POST(REGISTER)
    fun register(
            @Field("email") email : String,
            @Field("password") password : String) :
            Observable<BaseResponse<User>>

}