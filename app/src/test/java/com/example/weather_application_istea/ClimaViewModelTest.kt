package com.example.weather_application_istea.clima

import com.example.weather_application_istea.models.*
import com.example.weather_application_istea.repository.RepositoryApi
import com.example.weather_application_istea.repository.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClimaViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: Repository
    private lateinit var viewModel: ClimaViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando getClimaActual devuelve datos, estado es Resultado`() = runTest {
        val climaFalso = ClimaActual(
            name = "Buenos Aires",
            main = MainInfo(20f, 19f, 18f, 22f, 1013, 60),
            weather = listOf(WeatherDescription("Clear", "cielo despejado", "01d")),
            wind = WindInfo(3.5f, 180),
            sys = SysInfo("AR")
        )

        coEvery { repository.getClimaActual(any(), any()) } returns climaFalso

        viewModel = ClimaViewModel(0f, 0f, repository)
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.estado is ClimaEstado.Resultado)
        val resultado = viewModel.estado as ClimaEstado.Resultado
        assertEquals("Buenos Aires", resultado.clima.name)
    }

    @Test
    fun `cuando getClimaActual lanza error, estado es Error`() = runTest {
        coEvery { repository.getClimaActual(any(), any()) } throws Exception("fallo")

        viewModel = ClimaViewModel(0f, 0f, repository)
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.estado is ClimaEstado.Error)
    }
}
