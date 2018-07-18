package com.wappster.fitbook.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wappster.fitbook.network.FitBookApiService
import com.wappster.fitbook.network.URL_BASE
import com.wappster.fitbook.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.Nullable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkModule {

    /**
     * Provides the Post service implementation.
     * @param httpLoggingInterceptor the Retrofit object used to instantiate the service
     * @param urlAndHeaderInterceptor the Retrofit object used to instantiate the service
     * @return OkHttpClient used to create Retrofit instance
     */
    @Provides
    @Reusable
    @JvmStatic
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                             @Named(Constants.urlAndHeaderInterceptor) urlAndHeaderInterceptor: Interceptor): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(urlAndHeaderInterceptor)
                .build()


    /**
     * Provides the Gson object.
     * @return the Gson object
     */
    @Provides
    @Reusable
    @JvmStatic
    fun provideGson(): Gson =
            GsonBuilder()
                    .setDateFormat("dd/MM/yyyy HH:mm:ss")
                    .create()


    /**
     * Provides the Retrofit object.
     * @param httpClient the OkHttpClient which contains the logger interceptor
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()


    /**
     * Provides the api service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the api service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    fun providesApiService(retrofit: Retrofit): FitBookApiService {
        return retrofit.create(FitBookApiService::class.java)
    }
}