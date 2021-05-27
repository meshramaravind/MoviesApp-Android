package com.arvind.moviesapp.di

import android.app.Application
import com.arvind.moviesapp.repository.MostPopularTVShowsRepository
import com.arvind.moviesapp.repository.SearchTvShowsRepository
import com.arvind.moviesapp.repository.TvShowsDetailsRepository
import com.arvind.moviesapp.utils.BASE_URL
import com.arvind.moviesapp.webapi.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providesBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun providesRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    fun providesRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    fun providesTvShowsRepository(apiService: ApiService): MostPopularTVShowsRepository {
        return MostPopularTVShowsRepository(apiService)
    }

    @Provides
    fun providesSearchTvShowsRepository(
        apiService: ApiService,
        query: String
    ): SearchTvShowsRepository {
        return SearchTvShowsRepository(apiService, query)
    }

    @Provides
    fun providesTvShowsDetailsRepository(
        apiService: ApiService,
        application: Application,
        id: String
    ): TvShowsDetailsRepository {
        return TvShowsDetailsRepository(apiService, application, id)
    }

}