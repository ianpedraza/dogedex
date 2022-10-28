package com.ianpedraza.dogedex.di

import android.content.Context
import com.ianpedraza.dogedex.BuildConfig
import com.ianpedraza.dogedex.data.datasource.AuthDataSource
import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.data.datasource.UserDataSource
import com.ianpedraza.dogedex.data.repository.auth.AuthRepository
import com.ianpedraza.dogedex.data.repository.auth.DefaultAuthRepository
import com.ianpedraza.dogedex.data.repository.dogs.DefaultDogsRepository
import com.ianpedraza.dogedex.data.repository.dogs.DogsRepository
import com.ianpedraza.dogedex.data.repository.user.DefaultUserRepository
import com.ianpedraza.dogedex.data.repository.user.UserRepository
import com.ianpedraza.dogedex.domain.mappers.DogDTOMapper
import com.ianpedraza.dogedex.domain.mappers.UserDTOMapper
import com.ianpedraza.dogedex.framework.api.ApiServiceInterceptor
import com.ianpedraza.dogedex.framework.api.DogsApi
import com.ianpedraza.dogedex.framework.api.auth.AuthRemoteDataSource
import com.ianpedraza.dogedex.framework.api.dogs.DogsRemoteDataSource
import com.ianpedraza.dogedex.framework.api.user.UserRemoteDataSource
import com.ianpedraza.dogedex.usecases.AddDogToUserUseCase
import com.ianpedraza.dogedex.usecases.GetAllDogsUseCase
import com.ianpedraza.dogedex.usecases.GetDogsCollectionUseCase
import com.ianpedraza.dogedex.usecases.GetUserDogs
import com.ianpedraza.dogedex.usecases.LoginUseCase
import com.ianpedraza.dogedex.usecases.SignUpUseCase
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferencesUtils = SharedPreferencesUtils(context)

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

    /* Users */

    @Singleton
    @Provides
    fun provideUserDataSource(
        service: DogsApi,
        mapper: DogDTOMapper
    ): UserDataSource = UserRemoteDataSource(service, mapper)

    @Singleton
    @Provides
    fun provideUserRepository(
        dataSource: UserDataSource,
        dogsDataSource: DogsDataSource
    ): UserRepository = DefaultUserRepository(dataSource, dogsDataSource)

    /* Add dogs to user */

    @Singleton
    @Provides
    fun provideAddDogToUserUseCase(
        repository: UserRepository
    ): AddDogToUserUseCase = AddDogToUserUseCase(repository)

    /* Get User Dogs */

    @Singleton
    @Provides
    fun provideGetUserDogs(
        repository: UserRepository
    ): GetUserDogs = GetUserDogs(repository)

    /* Get Dogs Collection */

    @Singleton
    @Provides
    fun provideGetDogsCollectionUseCase(
        repository: UserRepository
    ): GetDogsCollectionUseCase = GetDogsCollectionUseCase(repository)
}
