package com.ianpedraza.dogedex.di

import com.ianpedraza.dogedex.BuildConfig
import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.data.repository.DefaultDogsRepository
import com.ianpedraza.dogedex.data.repository.DogsRepository
import com.ianpedraza.dogedex.domain.mappers.DogDTOMapper
import com.ianpedraza.dogedex.framework.api.DogsApiService
import com.ianpedraza.dogedex.framework.api.DogsRemoteDataSource
import com.ianpedraza.dogedex.usecases.GetAllDogsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideDogsApi(
        retrofit: Retrofit
    ): DogsApiService = retrofit.create(DogsApiService::class.java)

    @Singleton
    @Provides
    fun provideDogDTOMapper(): DogDTOMapper = DogDTOMapper()

    @Singleton
    @Provides
    fun provideRemoteDogsDataSource(
        service: DogsApiService,
        mapper: DogDTOMapper
    ): DogsDataSource = DogsRemoteDataSource(service, mapper)

    /*
    @Singleton
    @Provides
    fun provideLocalDogsDataSource(): DogsDataSource = DogsLocalDataSource()
     */

    @Singleton
    @Provides
    fun provideDogsRepository(
        dataSource: DogsDataSource
    ): DogsRepository = DefaultDogsRepository(dataSource)

    @Singleton
    @Provides
    fun provideGetAllDogsUseCase(
        repository: DogsRepository
    ): GetAllDogsUseCase = GetAllDogsUseCase(repository)
}