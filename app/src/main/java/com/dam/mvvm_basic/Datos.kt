package com.dam.mvvm_basic

import androidx.compose.ui.graphics.Color

/**
 * Clase para almacenar los datos del juego de manera global
 */
object Datos {
    var numero = 0 // Número aleatorio generado por el juego (0-3)
    var cuentaAtras = 5 // Valor inicial de la cuenta atrás
}

/**
 * Colores utilizados en los botones del juego
 * color_suave: color que se usa para efecto de parpadeo en el botón Start
 * txt: nombre del color que se mostrará en el botón
 */
enum class Colores(val color: Color, val color_suave: Color = Color.Transparent, val txt: String) {
    CLASE_ROJO(color = Color.Red, txt = "roxo"),
    CLASE_VERDE(color = Color.Green, txt = "verde"),
    CLASE_AZUL(color = Color.Blue, txt = "azul"),
    CLASE_AMARILLO(color = Color.Yellow, txt = "melo"),
    CLASE_START(color = Color.Magenta, color_suave = Color.Red, txt = "Start")
}

/**
 * Estados principales del juego
 * start_activo: indica si el botón Start está habilitado
 * boton_activo: indica si los botones de colores están habilitados
 */
enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false),
    GENERANDO(start_activo = false, boton_activo = false),
    ADIVINANDO(start_activo = false, boton_activo = true)
}

/**
 * Estados auxiliares usados para la cuenta atrás y sus efectos de log
 * Cada estado tiene su propio método 'procesarString' que transforma un mensaje según la lógica:
 *   - AUX1: sin cambios
 *   - AUX2: minúsculas
 *   - AUX3: mayúsculas
 *   - AUX4: minúsculas
 *   - AUX5: mayúsculas
 */
enum class EstadosAuxiliares(val txt: String) {
    AUX1("5") { override fun procesarString(msg: String) = msg },
    AUX2("4") { override fun procesarString(msg: String) = msg.lowercase() },
    AUX3("3") { override fun procesarString(msg: String) = msg.uppercase() },
    AUX4("2") { override fun procesarString(msg: String) = msg.lowercase() },
    AUX5("1") { override fun procesarString(msg: String) = msg.uppercase() };

    // Método abstracto que se implementa en cada estado auxiliar
    abstract fun procesarString(msg: String): String
}
