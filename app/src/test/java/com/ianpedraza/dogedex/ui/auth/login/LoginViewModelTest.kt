package com.ianpedraza.dogedex.ui.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ianpedraza.dogedex.FakeAuthValidator
import com.ianpedraza.dogedex.MainCoroutineRule
import com.ianpedraza.dogedex.core.domain.models.User
import com.ianpedraza.dogedex.usecases.LoginUseCase
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    private val loginUseCase: LoginUseCase = mock()

    private lateinit var viewModel: LoginViewModel

    private lateinit var fakeUser: com.ianpedraza.dogedex.core.domain.models.User

    @Before
    fun setup() {
        val authValidator = FakeAuthValidator()
        viewModel = LoginViewModel(loginUseCase, authValidator)

        fakeUser = com.ianpedraza.dogedex.core.domain.models.User(
            id = 0L,
            email = "",
            authenticationToken = ""
        )
    }

    @Test
    fun wrongEmailFormat_returnsErrorTest() {
        val email = "examplemail.com"
        val password = "Password123"

        viewModel.login(email, password)

        val result = viewModel.uiState.value
        assertThat(result.emailError, Is(notNullValue()))
    }

    @Test
    fun wrongPasswordFormat_returnsErrorTest() {
        val email = "example@mail.com"
        val password = "pas"

        viewModel.login(email, password)

        val result = viewModel.uiState.value
        assertThat(result.passwordError, Is(notNullValue()))
    }

    @Test
    fun correctLogin_loginSuccessTest() {
        val email = "example@mail.com"
        val password = "Password123"

        val expected = flow<DataState<com.ianpedraza.dogedex.core.domain.models.User>> { emit(DataState.Success(fakeUser)) }
        whenever(loginUseCase(any(), any())).thenReturn(expected)

        viewModel.login(email, password)

        val result = viewModel.uiState.value
        assertThat(result.loginStatus, Is(notNullValue()))
        assertThat(result.loginStatus, instanceOf(DataState.Success::class.java))
    }
}
