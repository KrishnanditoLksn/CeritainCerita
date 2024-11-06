package app.ditodev.ceritain.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.ditodev.ceritain.data.repository.StoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class RegisterViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = RegisterViewModel(repository)
    }

    @Test
    fun `handleRegistration should call repository`() {
        val name = "Dito"
        val email = "emanuel@gmail.com"
        val password = "coki321"
        viewModel.handleRegistration(name, email, password)
        verify(repository).handleRegistration(name, email, password)
    }

    @Test
    fun `must failed  when password must less 8 `() {
        val password = "coki321"
        val result = viewModel.isPasswordValid(password)
        assert(result)
    }

    @Test
    fun `must success when password more than 8 `() {
        val password = "fdsfsadf123"
        val result = viewModel.isPasswordValid(password)
        assert(result)
    }

    @Test
    fun `name must not empty`() {
        val uName = "Emmanuel Dito"
        val res = viewModel.isNameValid(uName)
        assert(res) { "Name must not empty done " }
    }

    @Test
    fun `email must be valid`(){
        val email = "ishowspied@gmail.com"
        val res = viewModel.isEmailValid(email)
        assert(res)
    }

    @Test
    fun `form must valid with input from name password email`(){
        val name = "Dito"
        val email = "emanuel@gmail.com"
        val password = "coki321"
        val res = viewModel.isFormValid(name, email, password)
        assert(res)
    }
}