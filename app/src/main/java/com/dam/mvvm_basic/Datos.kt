package com.dam.mvvm_basic

import androidx.compose.ui.graphics.Color

/**
 * Clase para almacenar los datos del juego
 */
object Datos {
    var numero = 0
    var cuentaAtras = 5 // valor inicial de la cuenta atrás
}

/**
 * Colores utilizados
 */
enum class Colores(val color: Color, val color_suave: Color = Color.Transparent, val txt: String) {
    CLASE_ROJO(color = Color.Red, txt = "roxo"),
    CLASE_VERDE(color = Color.Green, txt = "verde"),
    CLASE_AZUL(color = Color.Blue, txt = "azul"),
    CLASE_AMARILLO(color = Color.Yellow, txt = "melo"),
    CLASE_START(color = Color.Magenta, color_suave = Color.Red, txt = "Start")
}

/**
 * Estados del juego
 */
enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false),
    GENERANDO(start_activo = false, boton_activo = false),
    ADIVINANDO(start_activo = false, boton_activo = true)
}

/**
 * Estados auxiliares para la cuenta atrás
 */
enum class EstadosAuxiliares(val txt: String) {
    AUX1("5"),
    AUX2("4"),
    AUX3("3"),
    AUX4("2"),
    AUX5("1");
}
