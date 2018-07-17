package com.wappster.fitbook.dagger

import com.wappster.fitbook.BuildConfig
import com.wappster.fitbook.app.FitBookApplication
import com.wappster.fitbook.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object InterceptorModule {

  /**
   * Provides the Post service implementation.
   * @return OkHttpClient used to create Retrofit instance
   */
  @Provides
  @Reusable
  @JvmStatic
  @Named(Constants.urlAndHeaderInterceptor)
  fun provideUrlAndHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
      val request = chain.request()
      val builder = request.newBuilder()
          .addHeader(Constants.USER_AGENT, Constants.ADEPT_ANDROID_APP)

      val url = request.url()
          .newBuilder()
          .addQueryParameter(Constants.VERSION, BuildConfig.VERSION_NAME)
          .build()

      builder.url(url)

      chain.proceed(builder.build())
    }
  }

  /**
   * Provides the Post service implementation.
   * @return OkHttpClient used to create Retrofit instance
   */
  @Provides
  @Reusable
  @JvmStatic
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
    httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return httpLoggingInterceptor
  }
}