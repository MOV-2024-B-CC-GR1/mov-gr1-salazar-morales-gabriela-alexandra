package org.example

import java.util.*
/*
   // Variable inmutable, no puede reasignarse
    val nombre: String = "Gabi"
    println("Nombre: $nombre")

    // Variable mutable, puede reasignarse
    var edad: Int = 30
    println("Edad inicial: $edad")

    // Reasignación de la variable mutable
    edad = 31
    println("Edad actualizada: $edad")

    // Ejemplo de Duck Typing, Kotlin reconoce el tipo de dato automáticamente
    val ejemploVariable = "Este es un String" // No es necesario especificar el tipo
    println("Ejemplo de variable: $ejemploVariable")

    // Uso de la sentencia 'when', equivalente al switch en otros lenguajes
    val estadoCivil = "C" // C para Casado, S para Soltero

    when (estadoCivil) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("Estado civil desconocido")
        }
    }

    // Sin operador ternario, usamos if/else en una línea
    val estadoCoqueteo = if (estadoCivil == "S") "Si" else "No"
    println("Estado de coqueteo: $estadoCoqueteo")

    // Clases en Java - Fecha de nacimiento usando la clase Date
    val fechaNacimiento: Date = Date()
    println("Fecha de nacimiento: $fechaNacimiento")

    // Llamada a la función
    imprimirNombre("Gabi")

    // Uso de template strings
    val salario: Int = 1500
    println("El salario de $nombre es de $$salario")

    // Uso de template strings con operaciones dentro de las llaves
    println("El salario anual de $nombre es de $${salario * 12}")
}

// Función que imprime el nombre, no retorna ningún valor (Unit es opcional)
fun imprimirNombre(nombre: String) {
    // Función interna (scope local)
    fun funcionInterna() {
        println("Este es un mensaje desde la función interna")
    }

    // Llamamos a la función interna
    funcionInterna()

    println("El nombre ingresado es: $nombre")
}


fun imprimirNombreUnit(nombre: String): Unit {
    // Función interna dentro de imprimirNombre
    fun funcionInterna() {
        println("Función interna")
    }

    // Llamamos a la función interna
    funcionInterna()

    // Uso de template strings para imprimir el nombre
    //println("Nombre: $nombre")
    println("Nombre: ${nombre}")

 */

/*

fun calcularSueldo(
        sueldo: Double,  // Requerido
        tasa: Double = 12.00,  // Opcional (defecto)
        bonoEspecial: Double? = null  // Opcional (nullable)
    ): Double {
        // Variable? -> "?" Es Nullable (osea que puede en algún momento ser nulo)
        // Int → Int? (nullable)
        // String → String? (nullable)
        // Date → Date? (nullable)

        if (bonoEspecial == null) {
            return sueldo * (100/tasa)
        } else {
            return sueldo * (100/tasa) + bonoEspecial
        }
    }


    calcularSueldo(10.00)  // solo parámetro requerido

    calcularSueldo(10.00, 15.00, 20.00)  // parámetro requerido y sobrescribir parámetros opcionales
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)

    calcularSueldo(10.00, bonoEspecial = 20.00)  // usando el parámetro bonoEspecial en 2da posición
    // gracias a los parámetros nombrados

    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
    // usando el parámetro bonoEspecial en 1ra posición
    // usando el parámetro sueldo en 2da posición
    // usando el parámetro tasa en 3ra posición
    // gracias a los parámetros nombrados

    abstract class NumerosJava {
        protected val numeroUno: Int
        private val numeroDos: Int

        constructor(uno: Int, dos: Int) {
            this.numeroUno = uno
            this.numeroDos = dos
            println("Inicializando")
        }
    }

    abstract class Numeros( // Constructor Primario
        // Caso 1) Parámetro normal
        // uno: Int (parámetro sin modificador de acceso)

        // Caso 2) Parámetro y propiedad (atributo) (protected)
        protected val numeroUno: Int,  // instancia.numeroUno
        protected val numeroDos: Int   // instancia.numeroDos
    ) {
        var parametroNoUsadoNoPropiedadDeLaClase: Int? = null

        init { // bloque constructor primario OPCIONAL
            this.numeroUno
            this.numeroDos
            println("Inicializando")
        }
    }

    class Suma( // Constructor primario
        unoParametro: Int,  // Parámetro
        dosParametro: Int   // Parámetro
    ) : Numeros( // Clase padre, Numeros (extendiendo)
        unoParametro,
        dosParametro
    ) {
        // Implementación adicional de la clase Suma
    }



 */
/*
fun main() {


}
*/


/*
// Clase abstracta Numeros con dos parámetros
abstract class Numeros(val unoParametro: Int, val dosParametro: Int)

// Clase Suma que hereda de Numeros
class Suma(unoParametro: Int, dosParametro: Int) : Numeros(unoParametro, dosParametro) {

    // Propiedades locales de la clase Suma, que reciben los mismos valores que los parámetros del constructor
    val numeroUno: Int = unoParametro
    val numeroDos: Int = dosParametro

    // Propiedades adicionales de la clase Suma
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito: String = "Publico implicito"

    init {
        // Usando "this" para referirse a las propiedades de la clase Suma (propiedades locales)
        println("Número Uno (propiedad local): ${this.numeroUno}")  // Accede a numeroUno de Suma
        println("Número Dos (propiedad local): ${this.numeroDos}")  // Accede a numeroDos de Suma

        // Usando "this" para referirse a las propiedades heredadas de la clase Numeros
        println("Uno Parametro (heredado): ${this.unoParametro}")   // Accede a unoParametro de Numeros
        println("Dos Parametro (heredado): ${this.dosParametro}")   // Accede a dosParametro de Numeros

        // Accediendo a las propiedades públicas de Suma
        println("Propiedad Pública Explícita: ${this.soyPublicoExplicito}")  // Accede a soyPublicoExplicito
        println("Propiedad Pública Implícita: ${this.soyPublicoImplicito}")  // Accede a soyPublicoImplicito
    }
}

fun main() {
    // Instanciando la clase Suma con valores para los parámetros del constructor
    val suma = Suma(10, 20)
}
*/

/*
// Clase Numeros base
abstract class Numeros(val unoParametro: Int, val dosParametro: Int)

// Clase Suma que hereda de Numeros
class Suma(unoParametro: Int = 0, dosParametro: Int = 0) : Numeros(
    unoParametro,
    dosParametro
) {
    // Propiedades adicionales de la clase Suma
    val numeroUno: Int = unoParametro
    val numeroDos: Int = dosParametro

    // Constructor secundario que recibe dos parámetros Int? (nullable)
    constructor(uno: Int?, dos: Int?) : this(
        uno ?: 0,  // Si uno es null, asignamos 0
        dos ?: 0   // Si dos es null, asignamos 0
    ) {
        println("Primer constructor secundario: uno = $uno, dos = $dos")
    }

    // Segundo constructor secundario que recibe un parámetro Int y otro Int? (nullable)
    constructor(uno: Int, dos: Int?) : this(
        uno,
        dos ?: 0
    ) {
        println("Segundo constructor secundario: uno = $uno, dos = $dos")
    }

    // Tercer constructor secundario que recibe un parámetro Int? y otro Int
    constructor(uno: Int?, dos: Int) : this(
        uno ?: 0,
        dos
    ) {
        println("Tercer constructor secundario: uno = $uno, dos = $dos")
    }

    // Bloque de inicialización para imprimir los valores
    init {
        println("Número Uno (propiedad local): $numeroUno")
        println("Número Dos (propiedad local): $numeroDos")
    }
}

// Función main donde se crean instancias de la clase Suma
fun main() {
    // Usando el primer constructor secundario (Int? y Int?)
    val sumaA = Suma(10, 20)
    println("SumaA: ${sumaA.numeroUno} + ${sumaA.numeroDos} = ${sumaA.numeroUno + sumaA.numeroDos}\n")

    // Usando el segundo constructor secundario (Int y Int?)
    val sumaB = Suma(15, null)
    println("SumaB: ${sumaB.numeroUno} + ${sumaB.numeroDos} = ${sumaB.numeroUno + sumaB.numeroDos}\n")

    // Usando el tercer constructor secundario (Int? y Int)
    val sumaC = Suma(null, 5)
    println("SumaC: ${sumaC.numeroUno} + ${sumaC.numeroDos} = ${sumaC.numeroUno + sumaC.numeroDos}")
}
*/

/*
// Clase Numeros base
abstract class Numeros(val unoParametro: Int, val dosParametro: Int)

// Clase Suma que hereda de Numeros
class Suma(unoParametro: Int = 0, dosParametro: Int = 0) : Numeros(
    unoParametro,
    dosParametro
) {
    // Propiedades adicionales de la clase Suma
    val numeroUno: Int = unoParametro
    val numeroDos: Int = dosParametro

    // Constructor secundario que recibe dos parámetros Int?
    constructor(uno: Int?, dos: Int?) : this(
        uno ?: 0,
        dos ?: 0
    )

    // Historial de sumas
    companion object {
        val historialSumas = arrayListOf<Int>() // Historial compartido
        val pi = 3.14 // Constante Pi compartida

        // Método para agregar al historial
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)
        }

        // Función para elevar al cuadrado
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
    }

    // Método para realizar la suma y agregarla al historial
    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)  // Llamada a la función en el companion object
        return total
    }

    // Bloque de inicialización
    init {
        println("Número Uno (propiedad local): $numeroUno")
        println("Número Dos (propiedad local): $numeroDos")
    }
}

// Función main donde se crean instancias de la clase Suma
fun main() {
    // Usando el constructor con valores no nulos
    val sumaA = Suma(10, 20)
    println("SumaA: ${sumaA.numeroUno} + ${sumaA.numeroDos} = ${sumaA.sumar()}\n")

    // Usando el constructor con un valor nulo en 'dos'
    val sumaB = Suma(15, null)
    println("SumaB: ${sumaB.numeroUno} + ${sumaB.numeroDos} = ${sumaB.sumar()}\n")

    // Usando el constructor con un valor nulo en 'uno'
    val sumaC = Suma(null, 5)
    println("SumaC: ${sumaC.numeroUno} + ${sumaC.numeroDos} = ${sumaC.sumar()}\n")

    // Accediendo al valor de Pi
    println("Valor de Pi: ${Suma.pi}")

    // Usando la función elevarAlCuadrado en el companion object
    val numeroElevado = Suma.elevarAlCuadrado(4)
    println("El cuadrado de 4 es: $numeroElevado")

    // Imprimiendo el historial de sumas
    println("Historial de sumas: ${Suma.historialSumas}")
}
*/

/*
// Clase Numeros base
abstract class Numeros(val unoParametro: Int, val dosParametro: Int)

// Clase Suma que hereda de Numeros
class Suma(unoParametro: Int = 0, dosParametro: Int = 0) : Numeros(
    unoParametro,
    dosParametro
) {
    // Propiedades adicionales de la clase Suma
    val numeroUno: Int = unoParametro
    val numeroDos: Int = dosParametro

    // Constructor secundario que recibe dos parámetros Int? (nullable)
    constructor(uno: Int?, dos: Int?) : this(
        uno ?: 0,
        dos ?: 0
    )

    // Historial de sumas
    companion object {
        val historialSumas = arrayListOf<Int>() // Historial compartido
        val pi = 3.14 // Constante Pi compartida

        // Método para agregar al historial
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)
        }

        // Función para elevar al cuadrado
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
    }

    // Método para realizar la suma y agregarla al historial
    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)  // Llamada a la función en el companion object
        return total
    }

    // Bloque de inicialización
    init {
        println("Número Uno (propiedad local): $numeroUno")
        println("Número Dos (propiedad local): $numeroDos")
    }
}

// Función para calcular el sueldo con parámetros nombrados
fun calcularSueldo(bonoEspecial: Double = 0.0, sueldo: Double = 0.0, tasa: Double = 0.0): Double {
    val sueldoFinal = sueldo + bonoEspecial - (sueldo * tasa / 100)
    println("Sueldo calculado con bonoEspecial = $bonoEspecial, sueldo = $sueldo, tasa = $tasa")
    return sueldoFinal
}

// Función main donde se crean instancias de la clase Suma
fun main() {
    // Calcular el sueldo usando parámetros nombrados
    val sueldoFinal = calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
    println("Sueldo final: $sueldoFinal\n")

    // Usando los constructores de la clase Suma
    val sumaA = Suma(1, 1)
    val sumaB = Suma(null, 1)
    val sumaC = Suma(1, null)
    val sumaD = Suma(null, null)

    println("SumaA: ${sumaA.numeroUno} + ${sumaA.numeroDos} = ${sumaA.sumar()}")
    println("SumaB: ${sumaB.numeroUno} + ${sumaB.numeroDos} = ${sumaB.sumar()}")
    println("SumaC: ${sumaC.numeroUno} + ${sumaC.numeroDos} = ${sumaC.sumar()}")
    println("SumaD: ${sumaD.numeroUno} + ${sumaD.numeroDos} = ${sumaD.sumar()}")

    // Imprimiendo el valor de Pi
    println("Valor de Pi: ${Suma.pi}")

    // Usando la función elevarAlCuadrado en el companion object
    val numeroElevado = Suma.elevarAlCuadrado(2)
    println("El cuadrado de 2 es: $numeroElevado")

    // Imprimiendo el historial de sumas
    println("Historial de sumas: ${Suma.historialSumas}")
}
*/


//14-11-2024 arreglos y operadores

/*
    // Ejemplo de Arreglos en Kotlin

    // Arreglo Estático: tamaño fijo, no se puede modificar después de su creación
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println("Arreglo Estático: ${arregloEstatico.joinToString(", ")}")

    // Arreglo Dinámico: tamaño variable, se pueden añadir elementos
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println("Arreglo Dinámico antes: ${arregloDinamico.joinToString(", ")}")
    arregloDinamico.addAll(listOf(11, 12)) // Añadir múltiples elementos
    println("Arreglo Dinámico después: ${arregloDinamico.joinToString(", ")}")
 */

/*
    // Declaración del arreglo dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // Uso de forEach para iterar sobre un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: $valorActual")
        }

    // Usando "it" para referirse al elemento iterado en forEach
    arregloDinamico.forEach { println("Valor Actual (it): $it") }

    // Uso de map para transformar el arreglo
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            valorActual.toDouble() + 100.0
        }
    println("Resultado de map: $respuestaMap")

 */


/*

    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // Uso de filter para filtrar el arreglo por valores mayores a 5
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
            mayoresACinco
        }
    println("Valores mayores a 5: $respuestaFilter")

    // Otra forma de usar filter para valores menores a 5
    val respuestaFilterDos = arregloDinamico.filter { it < 5 }
    println("Valores menores a 5: $respuestaFilterDos")

    // Uso de any para verificar si algún valor es mayor a 5
    val respuestaAny: Boolean = arregloDinamico.any { valorActual: Int ->
        valorActual > 5
    }
    println("¿Algún valor es mayor a 5?: $respuestaAny") // True

    // Uso de all para verificar si todos los valores son mayores a 5
    val respuestaAll: Boolean = arregloDinamico.all { valorActual: Int ->
        valorActual > 5
    }
    println("¿Todos los valores son mayores a 5?: $respuestaAll") // False
 */

/*
    // Declaración del arreglo dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5)

    // Uso de reduce para acumular el valor sumando los elementos del arreglo
    val respuestaReduce: Int = arregloDinamico
        .reduce { acumulado: Int, valorActual: Int ->
            acumulado + valorActual
        }
    println("Resultado de reduce (suma de todos los elementos): $respuestaReduce")
 */

fun main() {




}
