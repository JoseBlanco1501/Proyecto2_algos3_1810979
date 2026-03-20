import java.io.File
import kotlin.system.exitProcess

/**
 * Verifica si dos cartas comparten exactamente una caracteristica:
 * nivel, poder o atributo. Solo se considera valido si coinciden en una sola.
 *
 * @param a primera carta
 * @param b segunda carta
 * @return true si comparten exactamente una caracteristica
 */
fun compartenExactamenteUna(a: CartaMostro, b: CartaMostro): Boolean {
    var coincidencias = 0

    if (a.nivel() == b.nivel()) coincidencias++
    if (a.poder() == b.poder()) coincidencias++
    if (a.atributo() == b.atributo()) coincidencias++

    return coincidencias == 1
}

/**
 * Carga un archivo CSV y construye una lista de cartas mostro.
 * Si alguna carta es invalida, se muestra el error y el programa termina.
 *
 * @param path ruta del archivo CSV
 * @return lista de cartas validas
 */
fun cargarDeck(path: String): List<CartaMostro> {
    return File(path).readLines()          // Lee todas las lineas del archivo
        .drop(1)                           // Omite la primera linea (encabezado del CSV)
        .mapNotNull { linea: String ->     // Procesa cada linea y descarta las que sean invalidas
            val partes = linea.split(",")  // Separa los campos por coma

            try {
                // Intenta construir una carta con los valores del CSV
                CartaMostro(
                    partes[0],
                    partes[1].toInt(),
                    partes[2],
                    partes[3].toInt()
                )
            } catch (e: IllegalArgumentException) {
                // Si la carta es invalida, muestra el error y termina el programa
                println("Error en carta '${partes[0]}': ${e.message}")
                exitProcess(1)
            }
        }
}

/**
 * Construye un grafo dirigido donde cada carta es un vertice.
 * Se agrega un arco A -> B si comparten exactamente una caracteristica.
 *
 * @param cartas lista de cartas del deck
 * @return grafo construido
 */
fun construirGrafo(cartas: List<CartaMostro>): Grafo<CartaMostro> {
    val g = ListaAdyacenciaGrafo<CartaMostro>()   // Crea un grafo vacio
    cartas.forEach { g.agregarVertice(it) }       // Agrega cada carta como vertice del grafo

    // Recorre todas las parejas de cartas para determinar si deben conectarse
    for (a in cartas) {
        for (b in cartas) {
            // Se conecta A -> B solo si son distintas y comparten exactamente una caracteristica
            if (a != b && compartenExactamenteUna(a, b)) {
                g.conectar(a, b)
            }
        }
    }
    return g   
}


/**
 * Imprime todas las ternas A B C tales que:
 * A -> B y B -> C existen en el grafo.
 *
 * @param g grafo de cartas mostro
 */
fun imprimirTernas(g: Grafo<CartaMostro>) {
    for (a in g.obtenerVertices()) {
        for (b in g.obtenerArcosSalida(a)) {
            for (c in g.obtenerArcosSalida(b)) {
                println("${a.nombre()} ${b.nombre()} ${c.nombre()}")
            }
        }
    }
}

/**
 * Funcion principal del programa.
 * Carga el deck, construye el grafo e imprime las ternas validas.
 */
fun main() {
    val deck = cargarDeck("deck.csv")
    val grafo = construirGrafo(deck)
    imprimirTernas(grafo)
}

