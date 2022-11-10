package com.ianpedraza.dogedex.di

import android.content.Context
import com.ianpedraza.dogedex.BuildConfig
import com.ianpedraza.dogedex.framework.api.ApiServiceInterceptor
import com.ianpedraza.dogedex.framework.api.DogsApi
import com.ianpedraza.dogedex.ml.Classifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.tensorflow.lite.support.common.FileUtil
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.nio.MappedByteBuffer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(ApiServiceInterceptor)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Factory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        converterFactory: Factory,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(converterFactory)
            .client(client).build()

    @Singleton
    @Provides
    fun provideDogsApi(
        retrofit: Retrofit
    ): DogsApi = retrofit.create(DogsApi::class.java)

    /* Recognize Image */

    @Singleton
    @Provides
    fun provideTfLiteModel(
        @ApplicationContext
        context: Context
    ): MappedByteBuffer = FileUtil.loadMappedFile(context, "model.tflite")

    @Singleton
    @Provides
    fun provideLabels(
        @ApplicationContext
        context: Context
    ): List<String> = FileUtil.loadLabels(context, "labels.txt")

    @Singleton
    @Provides
    fun provideClassifier(
        tfLiteModel: MappedByteBuffer,
        labels: List<String>
    ) = Classifier(tfLiteModel, labels)
}
