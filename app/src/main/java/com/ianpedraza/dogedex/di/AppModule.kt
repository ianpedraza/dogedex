package com.ianpedraza.dogedex.di

import android.content.Context
import com.ianpedraza.dogedex.BuildConfig
import com.ianpedraza.dogedex.data.datasource.AuthDataSource
import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.data.repository.auth.AuthRepository
import com.ianpedraza.dogedex.data.repository.auth.DefaultAuthRepository
import com.ianpedraza.dogedex.data.repository.dogs.DefaultDogsRepository
import com.ianpedraza.dogedex.data.repository.dogs.DogsRepository
import com.ianpedraza.dogedex.domain.mappers.DogDTOMapper
import com.ianpedraza.dogedex.domain.mappers.UserDTOMapper
import com.ianpedraza.dogedex.framework.api.DogsApi
import com.ianpedraza.dogedex.framework.api.auth.AuthRemoteDataSource
import com.ianpedraza.dogedex.framework.api.dogs.DogsRemoteDataSource
import com.ianpedraza.dogedex.ui.auth.managers.AuthResourcesDataManager
import com.ianpedraza.dogedex.ui.auth.managers.AuthResourcesManager
import com.ianpedraza.dogedex.usecases.GetAllDogsUseCase
import com.ianpedraza.dogedex.usecases.LoginUseCase
import com.ianpedraza.dogedex.usecases.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    ): DogsApi = retrofit.create(DogsApi::class.java)

    /* Dogs */

    @Singleton
    @Provides
    fun provideDogDTOMapper(): DogDTOMapper = DogDTOMapper()

    @Singleton
    @Provides
    fun provideRemoteDogsDataSource(
        service: DogsApi,
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

    /* Signup */

    @Singleton
    @Provides
    fun provideSignUpResourcesManager(
        @ApplicationContext
        context: Context
    ): AuthResourcesManager = AuthResourcesDataManager(context.resources)

    @Singleton
    @Provides
    fun provideUserDTOMapper(): UserDTOMapper = UserDTOMapper()

    @Singleton
    @Provides
    fun provideAuthDataSource(
        service: DogsApi,
        mapper: UserDTOMapper
    ): AuthDataSource = AuthRemoteDataSource(service, mapper)

    @Singleton
    @Provides
    fun provideAuthRepository(
        dataSource: AuthDataSource
    ): AuthRepository = DefaultAuthRepository(dataSource)

    @Singleton
    @Provides
    fun provideSignupUseCase(
        repository: AuthRepository
    ): SignUpUseCase = SignUpUseCase(repository)

    /* Login */
    @Singleton
    @Provides
    fun provideLoginUseCase(
        repository: AuthRepository
    ): LoginUseCase = LoginUseCase(repository)
}
