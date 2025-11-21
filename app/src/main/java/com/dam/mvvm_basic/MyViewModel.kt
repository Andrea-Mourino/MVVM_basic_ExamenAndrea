package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel private constructor() : ViewModel() {

    companion object {
        @Volatile
        private var INSTANCE: MyViewModel? = null

        fun getInstance(): MyViewModel {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MyViewModel().also { INSTANCE = it }
            }
        }
    }

    private val TAG_LOG = "miDebug"

    val estadoLiveData = MutableLiveData(Estados.INICIO)
    var cuentaAtras = mutableStateOf(0)
    var _numbers = mutableStateOf(0)
    private var jobCuentaAtras: Job? = null

    /**
     * START crea random  inicia cuenta atrás
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

                Log.d(TAG_LOG, "Cuenta atrás: ${estadoAux.procesarString("Cuenta")} | Valor: ${cuentaAtras.value}")

                delay(1000)

                cuentaAtras.value--
            }

            if (estadoLiveData.value == Estados.ADIVINANDO) {
                Log.d(TAG_LOG, "Tiempo agotado → Volver a INICIO")
                estadoLiveData.value = Estados.INICIO
            }
        }
    }

    /**
     * Comprobar si el botón pulsado es correcto
     */
    fun comprobar(ordinal: Int): Boolean {
        if (estadoLiveData.value != Estados.ADIVINANDO) return false

        return if (ordinal == Datos.numero) {
            Log.d(TAG_LOG, "¡Correcto!")
            estadoLiveData.value = Estados.INICIO
            jobCuentaAtras?.cancel()
            cuentaAtras.value = 0
            true
        } else {
            Log.d(TAG_LOG, "Incorrecto")
            false
        }
    }
}
