# Proyecto2_algos3_1810979

## Integrante
- Jose Alejandro Blanco Rojas - Carnet 18-10979

# Mundo Chiquito – Implementacion

Este proyecto modela el efecto de la carta *Mundo Chiquito* del juego “Duelo de cartas de mostro”. A partir de un archivo CSV con cartas, se construye un grafo dirigido donde cada carta es un vertice y se agregan arcos entre cartas que comparten exactamente una caracteristica. Finalmente, se imprimen todas las ternas A B C tales que existen los arcos A → B y B → C.

El proyecto ya incluia la estructura base del grafo. Las siguientes partes fueron agregadas para completar la funcionalidad requerida.

---

## Cambios realizados

### 1. Metodo `obtenerVertices()` en `ListaAdyacenciaGrafo`
Se implemento el metodo necesario para obtener todos los vertices del grafo:

```kotlin
override fun obtenerVertices(): Collection<T> {
    return adyacencia.keys
}
```

Esto permite recorrer el grafo desde el exterior sin exponer la estructura interna.

---

## Archivos agregados

### 2. `CartaMostro.kt`
Se agrego la clase que representa una carta del juego. Incluye:

- nombre  
- nivel (1 a 12)  
- atributo (AGUA, FUEGO, VIENTO, TIERRA, LUZ, OSCURIDAD, DIVINO)  
- poder (multiplo de 50)

Todas las validaciones se realizan en el bloque `init` usando `require`.  
Si una carta no cumple las reglas, se lanza una `IllegalArgumentException`.

---

### 3. `MundoChiquito.kt`
Se agrego toda la logica principal del programa:

#### • Comparacion entre cartas  
La funcion `compartenExactamenteUna` determina si dos cartas comparten exactamente una caracteristica (nivel, poder o atributo).

#### • Carga del deck  
La funcion `cargarDeck` lee el archivo CSV, crea las cartas y valida sus datos.  
Si una carta es invalida, se muestra el error y el programa termina.

#### • Construccion del grafo  
La funcion `construirGrafo` agrega cada carta como vertice y crea arcos dirigidos entre cartas que comparten exactamente una caracteristica.

#### • Impresion de ternas  
La funcion `imprimirTernas` recorre el grafo e imprime todas las ternas A B C donde existan los arcos correspondientes.

#### • Funcion `main`  
Coordina todo el proceso:

1. Cargar el deck  
2. Construir el grafo  
3. Imprimir las ternas validas  

---

## Ejecucion

Compilar:

```
kotlinc *.kt -include-runtime -d main.jar
```

Ejecutar:

```
java -jar main.jar
```

El archivo `deck.csv` debe estar en el mismo directorio.

---

## Manejo de errores

- Las validaciones de las cartas se realizan en `CartaMostro`.
- Si una carta del CSV es invalida, se muestra un mensaje claro y el programa finaliza.
- Esto garantiza que el grafo solo se construya con datos correctos.

---