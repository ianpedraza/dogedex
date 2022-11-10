package com.ianpedraza.dogedex.di

import com.ianpedraza.dogedex.data.datasource.AuthDataSource
import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.data.datasource.UserDataSource
import com.ianpedraza.dogedex.data.repository.auth.AuthRepository
import com.ianpedraza.dogedex.data.repository.auth.DefaultAuthRepository
import com.ianpedraza.dogedex.data.repository.dogs.DefaultDogsRepository
import com.ianpedraza.dogedex.data.repository.dogs.DogsRepository
import com.ianpedraza.dogedex.data.repository.user.DefaultUserRepository
import com.ianpedraza.dogedex.data.repository.user.UserRepository
import com.ianpedraza.dogedex.framework.api.auth.AuthRemoteDataSource
import com.ianpedraza.dogedex.framework.api.dogs.DogsRemoteDataSource
import com.ianpedraza.dogedex.framework.api.user.UserRemoteDataSource
import com.ianpedraza.dogedex.ml.ClassifierRepository
import com.ianpedraza.dogedex.ml.DefaultClassifierRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    /* Dogs */

    @Singleton
    @Binds
    abstract fun provideDogsDataSource(
        remoteDogsDataSource: DogsRemoteDataSource
    ): DogsDataSource

    @Singleton
    @Binds
    abstract fun provideDogsRepository(
        dogsRepository: DefaultDogsRepository
    ): DogsRepository

    /* Signup */

    @Singleton
    @Binds
    abstract fun provideAuthDataSource(
        authDataSource: AuthRemoteDataSource
    ): AuthDataSource

    @Singleton
    @Binds
    abstract fun provideAuthRepository(
        authRepository: DefaultAuthRepository
    ): AuthRepository

    /* Users */

    @Singleton
    @Binds
    abstract fun provideUserDataSource(
        userDataSource: UserRemoteDataSource
    ): UserDataSource

    @Singleton
    @Binds
    abstract fun provideUserRepository(
        userRepository: DefaultUserRepository
    ): UserRepository

    @Singleton
    @Binds
    abstract fun provideClassifierRepository(
        defaultClassifierRepository: DefaultClassifierRepository
    ): ClassifierRepository
}
