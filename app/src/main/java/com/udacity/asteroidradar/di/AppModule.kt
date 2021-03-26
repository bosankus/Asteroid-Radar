package com.udacity.asteroidradar.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.WorkManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.data.api.ApiService
import com.udacity.asteroidradar.data.room.AsteroidDao
import com.udacity.asteroidradar.data.room.AsteroidDatabase
import com.udacity.asteroidradar.data.room.PicOfDayDao
import com.udacity.asteroidradar.data.room.PicOfDayDatabase
import com.udacity.asteroidradar.repository.MainRepository
import com.udacity.asteroidradar.util.Constants.ASTEROID_DATABASE_NAME
import com.udacity.asteroidradar.util.Constants.BASE_URL
import com.udacity.asteroidradar.util.Constants.PICTURE_DATABASE_NAME
import com.udacity.asteroidradar.util.Constants.SHARED_PREFERENCE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**Created by
Author: Ankush Bose
Date: 22,March,2021
 **/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideRunDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            AsteroidDatabase::class.java,
            ASTEROID_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providePictureDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            PicOfDayDatabase::class.java,
            PICTURE_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideAsteroidDao(db: AsteroidDatabase) = db.asteroidDao()

    @Singleton
    @Provides
    fun providePictureDao(db: PicOfDayDatabase) = db.pictureDao()

    @Singleton
    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun getMainRepository(
        apiService: ApiService,
        asteroidDao: AsteroidDao,
        picOfDayDao: PicOfDayDao
    ): MainRepository {
        return MainRepository(apiService, asteroidDao, picOfDayDao)
    }

    @Singleton
    @Provides
    fun providesWorkManager(@ApplicationContext app: Context) =
        WorkManager.getInstance(app)
}