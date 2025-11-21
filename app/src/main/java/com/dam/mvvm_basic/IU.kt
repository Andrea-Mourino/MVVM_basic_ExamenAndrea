package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Interfaz de usuario principal
 */
@Composable
fun IU(miViewModel: MyViewModel) {

    // Estado observable de la cuenta atrás
    val numero by remember { miViewModel.cuentaAtras }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Mostrar cuenta atrás
        Text(text = numero.toString(), fontSize = 32.sp)

        // Botones de colores
        Column {
            Row {
                Boton(miViewModel, Colores.CLASE_ROJO)
                Boton(miViewModel, Colores.CLASE_VERDE)
            }
            Row {
                Boton(miViewModel, Colores.CLASE_AZUL)
                Boton(miViewModel, Colores.CLASE_AMARILLO)
            }
        }

        // Botón Start
        Boton_Start(miViewModel, Colores.CLASE_START)
    }
}

/**
 * Botón de color individual
 */
@Composable
fun Boton(miViewModel: MyViewModel, enum_color: Colores) {
    val TAG_LOG = "miDebug"
    var _activo by remember { mutableStateOf(miViewModel.estadoLiveData.value!!.boton_activo) }

    // Observa cambios de estado para habilitar/deshabilitar botones
    miViewModel.estadoLiveData.observe(LocalLifecycleOwner.current) {
        _activo = it!!.boton_activo
    }

    Spacer(modifier = Modifier.size(10.dp))

    Button(
        enabled = _activo,
        colors = ButtonDefaults.buttonColors(enum_color.color),
        onClick = {
            Log.d(TAG_LOG, "Botón pulsado: ${enum_color.txt}")
            miViewModel.comprobar(enum_color.ordinal)
        },
        modifier = Modifier.size(80.dp, 40.dp)
    ) {
        Text(text = enum_color.txt, fontSize = 10.sp)
    }
}

/**
 * Botón Start con efecto de parpadeo
 */
@Composable
fun Boton_Start(miViewModel: MyViewModel, enum_color: Colores) {
    val TAG_LOG = "miDebug"
    var _activo by remember { mutableStateOf(miViewModel.estadoLiveData.value!!.start_activo) }
    var _color by remember { mutableStateOf(enum_color.color) }

    // Observa cambios de estado para habilitar/deshabilitar Start
    miViewModel.estadoLiveData.observe(LocalLifecycleOwner.current) {
        _activo = it!!.start_activo
    }

    // Parpadeo del botón Start mientras está activo
    LaunchedEffect(_activo) {
        while (_activo) {
            _color = enum_color.color_suave
            delay(100)
            _color = enum_color.color
            delay(500)
        }
    }

    Spacer(modifier = Modifier.size(40.dp))
    Button(
        enabled = _activo,
        colors = ButtonDefaults.buttonColors(_color),
        onClick = {
            Log.d(TAG_LOG, "Start pulsado")
            miViewModel.crearRandom()
        },
        modifier = Modifier.size(100.dp, 40.dp)
    ) {
        Text(text = enum_color.txt, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun IUPreview() {
    IU(MyViewModel.getInstance())
}
