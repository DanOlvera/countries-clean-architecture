package com.example.countriescleanarchitecture.presentation.viewmodel

import com.example.countriescleanarchitecture.data.repository.CountryRepository
import com.example.countriescleanarchitecture.domain.model.Country
import com.example.countriescleanarchitecture.domain.usecase.GetCountriesUseCase
import com.example.countriescleanarchitecture.presentation.state.CountryState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CountryViewModelTest {

    private val mockRepository: CountryRepository = mock()
    private val useCase = GetCountriesUseCase(mockRepository)
    private val viewModel = CountryViewModel(useCase)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify ViewModel emits Loading and Success states`() = runTest {
        // Given
        val mockCountries = listOf(
            Country("Algiers", "AF", null, null, null, "Algeria", null),
            Country("Buenos Aires", "AR", null, null, null, "Argentina", null)
        )

        // When
        `when`(mockRepository.getCountries()).thenReturn(
            flow {
                emit(CountryState.Loading)
                emit(CountryState.Success(mockCountries))
            }
        )

        val collectedStates = mutableListOf<CountryState>()

        val job = launch {
            viewModel.state.take(2).collect { state ->
                collectedStates.add(state)
            }
        }

        viewModel.fetchCountries()

        job.join()

        // Assert
        2 shouldBeEqualTo collectedStates.size
        CountryState.Loading shouldBeEqualTo collectedStates[0]
        collectedStates[1].shouldBeInstanceOf<CountryState.Success>()
        mockCountries shouldBeEqualTo (collectedStates[1] as CountryState.Success).countries

        job.cancel()
    }

    @Test
    fun `verify ViewModel emits Loading and Error state`() = runTest {
        //Given
        val errorMessage = "Failed to load countries"

        //When
        `when`(mockRepository.getCountries()).thenReturn(
            flow {
                emit(CountryState.Loading)
                emit(CountryState.Error(errorMessage))
            }
        )

        val collectedStates = mutableListOf<CountryState>()

        val job=  launch {
            viewModel.state.take(2).collect { state ->
                collectedStates.add(state)
            }
        }

        viewModel.fetchCountries()

        job.join()

        //Assert
        2 shouldBeEqualTo collectedStates.size
        CountryState.Loading shouldBeEqualTo collectedStates[0]
        collectedStates[1].shouldBeInstanceOf<CountryState.Error>()
        errorMessage shouldBeEqualTo (collectedStates[1] as CountryState.Error).errorMessage

        job.cancel()
    }
}