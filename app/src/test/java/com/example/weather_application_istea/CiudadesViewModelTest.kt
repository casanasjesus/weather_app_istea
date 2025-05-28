package com.example.weather_application_istea

import androidx.navigation.NavController
import com.example.weather_application_istea.ciudades.CiudadesEstado
import com.example.weather_application_istea.ciudades.CiudadesIntencion
import com.example.weather_application_istea.ciudades.CiudadesViewModel
import com.example.weather_application_istea.models.Ciudad
import com.example.weather_application_istea.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.mockk.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CiudadesViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: Repository
    private lateinit var viewModel: CiudadesViewModel
    private val fakeNavController = mockk<NavController>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
        viewModel = CiudadesViewModel(
            navController = fakeNavController,
            listaDeCiudades = emptyList(),
            repository = repository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando buscarCiudad devuelve resultados, estado es Resultado`() = runTest {
        val ciudad = Ciudad("Buenos Aires", -34.6f, -58.4f, "AR", "Buenos Aires")
        coEvery { repository.buscarCiudad("buenos aires") } returns listOf(ciudad)

        viewModel.ejecutar(CiudadesIntencion.BuscarCiudades("buenos aires"))
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.estado is CiudadesEstado.Resultado)
    }

    @Test
    fun `cuando buscarCiudad devuelve vacío, estado es Vacio`() = runTest {
        coEvery { repository.buscarCiudad("nada") } returns emptyList()

        viewModel.ejecutar(CiudadesIntencion.BuscarCiudades("nada"))
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.estado is CiudadesEstado.Vacio)
    }

    @Test
    fun `cuando buscarCiudad lanza error, estado es Error`() = runTest {
        coEvery { repository.buscarCiudad(any()) } throws Exception("fallo")

        viewModel.ejecutar(CiudadesIntencion.BuscarCiudades("fallo"))
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.estado is CiudadesEstado.Error)
    }

    @Test
    fun `cuando ciudad es seleccionada, navega correctamente`() {
        val ciudad = Ciudad("Rosario", -32.9f, -60.7f, "AR")
        viewModel.ejecutar(CiudadesIntencion.CiudadSeleccionada(ciudad))

        verify { fakeNavController.navigate("clima/${ciudad.lat}/${ciudad.lon}") }
    }
}