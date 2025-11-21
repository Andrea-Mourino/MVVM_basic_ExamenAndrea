## Introducción

El objetivo de esta app es describir las diferentes clases y como se interrelacionan para el [modelo MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=es-419)

## Escenario
Tenemos nuestra aplicación diseñada y codificada y queremos transformarla a la arquitectura MVVC, separar el manejo de datos de la activity principal.

Además utilizar el patrón de diseño [Observer](https://es.wikipedia.org/wiki/Observer_(patr%C3%B3n_de_dise%C3%B1o))

En este caso, el único dato que vamos a manejar son enteros aleatorios. 

## Corrutinas
En esta rama vamos a usar corrutinas:

- En el ViewModel con la función `estadosAuxiliares` utilizando `viewModelScope.launch { }`
- En la IU con `LaunchedEffect(_activo)` en el botón start




-----------------------------

## Ejercicio1

Implementa una cuenta atrás en el código (5...4...3...2...1)
Utiliza los Estados auxiliares para la cuenta atrás
Configura un cuadro de texto para mostrar la cuenta atrás
Cuando el usuario le da al "Start" empieza la cuenta atrás
Si la cuenta atrás llega a uno y el usuario aun no acertó, la app vuelve al estado INICIO



## Ejercicio2

Modifica la aplicación para que el ViewModel sea un singleton



## Ejercicio3

Añade a los estados auxiliares (sin cambiar el enum) una función própia que tendrá como parámetro una String y que devuelva una String

    En el estado AUX1 devolverá la string sin modificarla
    En el estado AUX2 devolverá la string en minúsculas
    En el estado AUX3 devolverá la string en mayúscula

En la función estadosAuxiliares(msg: String = ""):

    El logcat (mensaje (corutina))debe usar lo que devuelve las funciones de los estados para imprimir el mensaje (msg)





# Solucion

## README – Juego de Colores con Cuenta Atrás en Android Studio (Kotlin)

### El juego implementa:

- MVVM (Model-View-ViewModel) para separar la lógica de negocio de la UI.

- Estados del juego (INICIO, GENERANDO, ADIVINANDO) para controlar qué botones están activos.

- Estados auxiliares (AUX1 a AUX5) para la cuenta atrás y para generar logs con distintas transformaciones de texto.

- Botón Start con parpadeo para indicar al usuario que puede iniciar el juego.

- ViewModel como singleton, garantizando un estado global persistente.


### Funcionamiento del Juego

Al iniciar la app, el botón Start está habilitado y los botones de colores deshabilitados. Al pulsar Start se genera un número aleatorio entre 0 y 3 y se guarda en Datos.numero.

Se cambia el estado a ADIVINANDO. Se inicia una cuenta atrás de 5 segundos usando estados auxiliares (AUX1 a AUX5).

La UI muestra la cuenta atrás en pantalla. Durante la cuenta atrás el usuario puede pulsar uno de los cuatro botones de colores.

Si acierta se muestra un log indicando que ha ganado y se detiene la cuenta atrás.

Se vuelve al estado INICIO.

Si no acierta la cuenta atrás sigue corriendo hasta agotarse.

Si la cuenta atrás llega a 0 sin acertar el juego vuelve automáticamente al estado INICIO y se deshabilitan los botones de colores.

Se vuelve a habilitar el botón Start.



### Pros del Proyecto

- Separación de responsabilidades (MVVM):

- La lógica del juego está separada de la UI.

- Facilita mantenimiento y pruebas unitarias.

- Cuenta atrás interactiva con estados auxiliares:

- Permite mostrar visualmente y en logs cómo cambia la secuencia de la cuenta atrás.

- Implementación flexible que se puede extender a más estados.

- ViewModel singleton: Garantiza que el estado del juego persista si se reconstruye la UI.

- Ideal para juegos simples o aplicaciones con varios fragments/composables.

- Jetpack Compose:

- UI declarativa y reactiva.

- Manejo de estados con mutableStateOf para la cuenta atrás y LiveData para botones.

- Botón Start parpadea cuando está activo.

- Cuenta atrás visible en pantalla.


### Contras / Limitaciones

- Uso de LiveData y observe dentro de Compose:

- Cuenta atrás rígida:

- Se usa una lista fija de estados auxiliares (AUX1 a AUX5) con delays fijos.

- Control de corutina manual:

- No hay dificultad progresiva ni secuencias de colores, es solo un número aleatorio entre 0-3.

- Singleton ViewModel:

- Conveniente para este ejemplo, pero en apps más grandes puede generar problemas de testeo y acoplamiento.

- Normalmente se recomienda ViewModel por Activity/Fragment usando by viewModels().

- UI no escalable:
