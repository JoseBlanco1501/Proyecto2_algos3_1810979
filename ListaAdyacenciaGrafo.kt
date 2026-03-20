/**
 * Implementacion de un grafo dirigido utilizando listas de adyacencia.
 * Cada vertice almacena una lista de sus arcos de salida.
 *
 * @param T tipo generico de los vertices almacenados en el grafo
 */
class ListaAdyacenciaGrafo<T> : Grafo<T> {

    /**
     * Mapa interno que almacena cada vertice junto con su lista
     * de vertices adyacentes (arcos de salida).
     */
    private val adyacencia = mutableMapOf<T, MutableList<T>>()

    /**
     * Agrega un nuevo vertice al grafo si no existe previamente.
     *
     * @param v vertice a agregar
     * @return true si el vertice fue agregado, false si ya existia
     */
    override fun agregarVertice(v: T): Boolean {
        if (v in adyacencia) return false
        adyacencia[v] = mutableListOf()
        return true
    }

    /**
     * Elimina un vertice del grafo junto con todos los arcos
     * que lo referencian.
     *
     * @param v vertice a eliminar
     * @return true si el vertice existia y fue eliminado
     */
    override fun eliminarVertice(v: T): Boolean {
        if (v !in adyacencia) return false

        // 1. Eliminar el vertice del mapa
        adyacencia.remove(v)

        // 2. Eliminarlo de todas las listas de adyacencia
        for ((_, lista) in adyacencia) {
            lista.remove(v)
        }

        return true
    }

    /**
     * Crea un arco dirigido desde un vertice hacia otro.
     *
     * @param desde vertice origen
     * @param hasta vertice destino
     * @return true si ambos vertices existen y el arco fue creado
     */
    override fun conectar(desde: T, hasta: T): Boolean {
        if (desde !in adyacencia || hasta !in adyacencia) return false
        adyacencia[desde]!!.add(hasta)
        return true
    }

    /**
     * Verifica si un vertice existe en el grafo.
     *
     * @param v vertice a verificar
     * @return true si el vertice existe
     */
    override fun contiene(v: T): Boolean = v in adyacencia

    /**
     * Devuelve la lista de arcos de salida de un vertice.
     * Se retorna una copia inmutable para evitar modificaciones externas.
     *
     * @param v vertice a consultar
     * @return lista de vertices adyacentes o una lista vacia
     */
    override fun obtenerArcosSalida(v: T): List<T> {
        return adyacencia[v]?.toList() ?: emptyList()
    }

    /**
     * Devuelve todos los vertices que tienen un arco dirigido hacia v.
     *
     * @param v vertice destino
     * @return lista de vertices que apuntan a v
     */
    override fun obtenerArcosEntrada(v: T): List<T> {

        // Si el vertice no existe en el grafo, no puede tener arcos de entrada
        if (v !in adyacencia) return emptyList()

        // Lista donde se almacenaran todos los vertices que apuntan a v
        val resultado = mutableListOf<T>()

        // Recorrer todos los vertices del grafo
        // u representa un vertice y lista representa sus arcos de salida
        for ((u, lista) in adyacencia) {

            // Si v aparece en la lista de adyacencia de u,
            // significa que existe un arco dirigido u -> v
            if (v in lista) resultado.add(u)
        }

        // Retornar todos los vertices que tienen un arco hacia v
        return resultado
    }


    /**
     * Devuelve la cantidad total de vertices almacenados en el grafo.
     *
     * @return numero de vertices
     */
    override fun tamano(): Int = adyacencia.size

    /**
     * Construye un subgrafo que contiene solo los vertices indicados
     * y los arcos existentes entre ellos.
     *
     * Se utiliza una lista de vertices validos para simplificar la logica.
     *
     * @param vertices coleccion de vertices solicitados
     * @return un nuevo grafo con los vertices y arcos correspondientes
     */
    override fun subgrafo(vertices: Collection<T>): Grafo<T> {
        
        val nuevo = ListaAdyacenciaGrafo<T>()
        val verticesValidos = vertices.filter { contiene(it) }

        // Agregar solo los vertices que existen en el grafo original
        for (v in verticesValidos) {
            nuevo.agregarVertice(v)
        }
    
        // Copiar arcos entre los vertices seleccionados
        for (u in vertices) {
            for (v in obtenerArcosSalida(u)) {
                if (v in verticesValidos) {
                    nuevo.conectar(u, v)
                }
            }
        }

        return nuevo
    }

    override fun obtenerVertices(): Collection<T> {
        return adyacencia.keys
    }

}