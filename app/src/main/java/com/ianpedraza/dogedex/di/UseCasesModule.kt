package com.ianpedraza.dogedex.di

import com.ianpedraza.dogedex.data.repository.auth.AuthRepository
import com.ianpedraza.dogedex.data.repository.dogs.DogsRepository
import com.ianpedraza.dogedex.data.repository.user.UserRepository
import com.ianpedraza.dogedex.ml.ClassifierRepository
import com.ianpedraza.dogedex.ml.DefaultClassifierRepository
import com.ianpedraza.dogedex.usecases.AddDogToUserUseCase
import com.ianpedraza.dogedex.usecases.GetAllDogsUseCase
import com.ianpedraza.dogedex.usecases.GetDogByMlIdUseCase
import com.ianpedraza.dogedex.usecases.GetDogsCollectionUseCase
import com.ianpedraza.dogedex.usecases.GetUserDogsUseCase
import com.ianpedraza.dogedex.usecases.LoginUseCase
import com.ianpedraza.dogedex.usecases.RecognizeImageUseCase
import com.ianpedraza.dogedex.usecases.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Singleton
    @Provides
    fun provideGetAllDogsUseCase(
        repository: DogsRepository
    ): GetAllDogsUseCase = GetAllDogsUseCase(repository)

    @Singleton
    @Provides
    fun provideSignupUseCase(
        repository: AuthRepository
    ): SignUpUseCase = SignUpUseCase(repository)

    @Singleton
    @Provides
    fun provideLoginUseCase(
        repository: AuthRepository
    ): LoginUseCase = LoginUseCase(repository)

    @Singleton
    @Provides
    fun provideAddDogToUserUseCase(
        repository: UserRepository
    ): AddDogToUserUseCase = AddDogToUserUseCase(repository)

    @Singleton
    @Provides
    fun provideGetUserDogs(
        repository: UserRepository
    ): GetUserDogsUseCase = GetUserDogsUseCase(repository)

    @Singleton
    @Provides
    fun provideGetDogsCollectionUseCase(
        repository: UserRepository
    ): GetDogsCollectionUseCase = GetDogsCollectionUseCase(repository)

    @Singleton
    @Provides
    fun provideGetDogByMlIdUseCase(
        repository: DogsRepository
    ): GetDogByMlIdUseCase = GetDogByMlIdUseCase(repository)

    @Singleton
    @Provides
    fun provideRecognizeImageUseCase(
        repository: ClassifierRepository
    ): RecognizeImageUseCase = RecognizeImageUseCase(repository)
}
