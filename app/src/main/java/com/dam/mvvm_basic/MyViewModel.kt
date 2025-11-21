package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val TAG_LOG = "miDebug"

    // estado actual del juego
    val estadoLiveData = MutableLiveData(Estados.INICIO)

    // valor visible en UI
    var cuentaAtras = mutableStateOf(0)

    // número aleatorio a adivinar
    var _numbers = mutableStateOf(0)

    // corutina de cuenta atrás
    private var jobCuentaAtras: Job? = null

    /**
     * START → crea random → inicia cuenta atrás
     */
    fun crearRandom() {
        estadoLiveData.value = Estados.GENERANDO

        _numbers.value = (0..3).random()
        Datos.numero = _numbers.value
        Log.d(TAG_LOG, "Random generado: ${Datos.numero}")

        estadoLiveData.value = Estados.ADIVINANDO

        iniciarCuentaAtras()
    }

    /**
     * Inicia la cuenta atrás usando los estados auxiliares
     */
    private fun iniciarCuentaAtras() {
        // cancelar cualquier cuenta atrás previa
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
                if (estadoLiveData.value != Estados.ADIVINANDO) break

                // mostrar primero el valor actual antes de decrementar
                Log.d(TAG_LOG, "Cuenta atrás: ${estadoAux.txt} | Valor actual: ${cuentaAtras.value}")

                delay(1000) // esperar 1 segundo

                // luego decrementamos para la siguiente iteración
                cuentaAtras.value--
            }

            // si la cuenta llega a 0 y el usuario no acertó
            if (estadoLiveData.value == Estados.ADIVINANDO) {
                Log.d(TAG_LOG, "Tiempo agotado → Volver a INICIO")
                estadoLiveData.value = Estados.INICIO
            }
        }
    }


    /**
     * Comprobar si el boton pulsado es el correcto
     */
    fun comprobar(ordinal: Int): Boolean {
        if (estadoLiveData.value != Estados.ADIVINANDO) return false

        return if (ordinal == Datos.numero) {
            Log.d(TAG_LOG, "¡Correcto!")
            estadoLiveData.value = Estados.INICIO
            // parar la cuenta atrás
            jobCuentaAtras?.cancel()
            cuentaAtras.value = 0
            true
        } else {
            Log.d(TAG_LOG, "Incorrecto")
            false
        }
    }
}
