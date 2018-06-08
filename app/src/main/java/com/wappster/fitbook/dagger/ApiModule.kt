package com.wappster.fitbook.dagger

import com.wappster.fitbook.network.FitBookApiService
import com.wappster.fitbook.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.Nullable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(@Nullable cache: Cache,
                             httpLoggingInterceptor: HttpLoggingInterceptor,
                             @Named(Constants.cacheInterceptor) cacheInterceptor: Interceptor,
                             @Named(Constants.urlAndHeaderInterceptor) urlAndHeaderInterceptor: Interceptor,
                             @Named(Constants.offlineCacheInterceptor) offlineCacheInterceptor: Interceptor): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(urlAndHeaderInterceptor)
                .addInterceptor(offlineCacheInterceptor)
                .cache(cache)
                .addNetworkInterceptor(cacheInterceptor)
                .build()



    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()


    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): FitBookApiService = retrofit.create(FitBookApiService::class.java)
}