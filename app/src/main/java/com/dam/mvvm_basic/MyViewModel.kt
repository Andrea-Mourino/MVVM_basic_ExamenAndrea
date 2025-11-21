package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel como singleton para mantener un estado compartido en toda la app
 */
class MyViewModel private constructor() : ViewModel() {

    companion object {
        @Volatile
        private var INSTANCE: MyViewModel? = null

        // Método para obtener la instancia singleton del ViewModel
        fun getInstance(): MyViewModel {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MyViewModel().also { INSTANCE = it }
            }
        }
    }

    private val TAG_LOG = "miDebug"

    // Estado principal del juego observado por la UI
    val estadoLiveData = MutableLiveData(Estados.INICIO)

    // Valor actual de la cuenta atrás que se muestra en pantalla
    var cuentaAtras = mutableStateOf(0)

    // Número aleatorio generado (0-3)
    var _numbers = mutableStateOf(0)

    // Job para controlar la corutina de la cuenta atrás
    private var jobCuentaAtras: Job? = null

    /**
     * Se llama al pulsar Start: genera número aleatorio y comienza la cuenta atrás
     */
    fun crearRandom() {
        estadoLiveData.value = Estados.GENERANDO

        _numbers.value = (0..3).random()
        Datos.numero = _numbers.value
        Log.d(TAG_LOG, "Random generado: ${Datos.numero}")

        estadoLiveData.value = Estados.ADIVINANDO

        // Inicia la corutina de cuenta atrás
        iniciarCuentaAtras()
    }

    /**
     * Inicia la cuenta atrás usando corutina y estados auxiliares
     * Actualiza el valor de cuentaAtras cada segundo
     * Cambia el estado a INICIO si se agota el tiempo sin acertar
     */
    private fun iniciarCuentaAtras() {
        // Cancelar corutina previa si existe
        jobCuentaAtras?.cancel()

        cuentaAtras.value = Datos.cuentaAtras

        val estados = listOf(
            EstadosAuxiliares.AUX1,
            EstadosAuxiliares.AUX2,
            EstadosAuxiliares.AUX3,
            EstadosAuxiliares.AUX4,
            EstadosAuxiliares.AUX5
        )

        jobCuentaAtras = viewModelScope.launch {
            for (estadoAux in estados) {
                // Si el usuario ya acertó o el estado cambió, se detiene
                if (estadoLiveData.value != Estados.ADIVINANDO) break

                // Log usando la función de cada estado auxiliar
                Log.d(TAG_LOG, "Cuenta atrás: ${estadoAux.procesarString("Cuenta")} | Valor: ${cuentaAtras.value}")

                delay(1000) // espera 1 segundo

                // Decrementa la cuenta atrás
                cuentaAtras.value--
            }

            // Si el tiempo terminó y el usuario no acertó, vuelve al inicio
            if (estadoLiveData.value == Estados.ADIVINANDO) {
                Log.d(TAG_LOG, "Tiempo agotado → Volver a INICIO")
                estadoLiveData.value = Estados.INICIO
            }
        }
    }

    /**
     * Comprobar si el botón pulsado es el correcto
     * @return true si el usuario acierta
     */
    fun comprobar(ordinal: Int): Boolean {
        if (estadoLiveData.value != Estados.ADIVINANDO) return false

        return if (ordinal == Datos.numero) {
            Log.d(TAG_LOG, "¡Correcto!")
            estadoLiveData.value = Estados.INICIO
            jobCuentaAtras?.cancel() // parar cuenta atrás
            cuentaAtras.value = 0
            true
        } else {
            Log.d(TAG_LOG, "Incorrecto")
            false
        }
    }
}
