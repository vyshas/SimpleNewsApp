package com.vyshas.newsapp.core.di.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vyshas.newsapp.BuildConfig
import com.vyshas.newsapp.core.AppConstants
import com.vyshas.newsapp.core.data.model.ApiResponseCallAdapterFactory
import com.vyshas.newsapp.core.data.repository.remote.NewsApiService
import com.vyshas.newsapp.core.data.repository.remote.NewsApiService.Companion.BASE_API_URL
import com.vyshas.newsapp.core.data.utils.InstantJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.datetime.Instant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().header("X-Api-Key", AppConstants.API.API_KEY).build())
            }.addInterceptor(
                httpLoggingInterceptor
            ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(BASE_API_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(
                            KotlinJsonAdapterFactory()
                        ).add(
                            Instant::class.java, InstantJsonAdapter()
                        ).build()
                )
            ).addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): NewsApiService = retrofit.create(NewsApiService::class.java)
}
