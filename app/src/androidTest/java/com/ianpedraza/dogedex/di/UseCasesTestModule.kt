package com.ianpedraza.dogedex.di

import com.ianpedraza.dogedex.fakes.FakeAuthRepository
import com.ianpedraza.dogedex.fakes.FakeClassifierRepository
import com.ianpedraza.dogedex.fakes.FakeDogsRepository
import com.ianpedraza.dogedex.usecases.GetDogByMlIdUseCase
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
object UseCasesTestModule {
    @Singleton
    @Provides
    fun provideGetDogByMlIdUseCase(
        repository: FakeDogsRepository
    ): GetDogByMlIdUseCase = GetDogByMlIdUseCase(repository)

    @Singleton
    @Provides
    fun provideRecognizeImageUseCase(
        repository: FakeClassifierRepository
    ): RecognizeImageUseCase = RecognizeImageUseCase(repository)

    @Singleton
    @Provides
    fun provideSignupUseCase(
        repository: FakeAuthRepository
    ): SignUpUseCase = SignUpUseCase(repository)

    @Singleton
    @Provides
    fun provideLoginUseCase(
        repository: FakeAuthRepository
    ): LoginUseCase = LoginUseCase(repository)
}
