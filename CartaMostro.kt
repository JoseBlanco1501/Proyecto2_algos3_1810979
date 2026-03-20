/**
 * Representa una carta mostro del juego Duelo de cartas de mostro.
 *
 * Cada carta posee un nombre unico, un nivel entre 1 y 12, un atributo valido
 * y un poder que debe ser multiplo de 50. Todas las validaciones se realizan
 * en el bloque init para garantizar que ninguna instancia invalida pueda ser creada.
 *
 * @property nombre nombre unico de la carta
 * @property nivel nivel del mostro (1 a 12)
 * @property atributo atributo elemental del mostro
 * @property poder de ataque del mostro (multiplo de 50)
 *
 * @throws IllegalArgumentException si alguna propiedad no cumple las restricciones
 */
class CartaMostro(
    private val nombre: String,
    private val nivel: Int,
    private val atributo: String,
    private val poder: Int
) {

    /**
     * Bloque de inicializacion donde se validan todas las restricciones
     * establecidas por las reglas del juego.
     *
     * - El nivel debe estar entre 1 y 12.
     * - El poder debe ser multiplo de 50.
     * - El atributo debe pertenecer al conjunto de atributos validos.
     *
     * Si alguna condicion no se cumple, se lanza una IllegalArgumentException.
     */
    init {
        require(nivel in 1..12) {
            "Nivel invalido: $nivel. Debe estar entre 1 y 12."
        }

        require(poder % 50 == 0) {
            "Poder debe ser multiplo de 50. Valor recibido: $poder"
        }

        val atributosValidos = setOf(
            "AGUA", "FUEGO", "VIENTO", "TIERRA",
            "LUZ", "OSCURIDAD", "DIVINO"
        )

        require(atributo in atributosValidos) {
            "Atributo invalido: $atributo"
        }
    }

    fun nombre() = nombre
    fun nivel() = nivel
    fun atributo() = atributo
    fun poder() = poder

    override fun toString(): String = nombre
}

