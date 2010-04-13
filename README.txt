- Configurar los parametros iniciales en el archivo FnInterface.java

	CONFIG_SELECTION : Método de selección.
	CONFIG_REPLACEMENT : Método de reemplazo
	
	POPULATION_SIZE : Tamaño de la población.
	MUTATION_PROBABILITY : Probabilidad de mutación.
	SELECTION_SIZE : Tamaño de la selección
	MAX_GENERATIONS : Cantidad máxima de generaciones.
	
	NEVER_CUT : true para que la aplicación no finalice al encontrar un individuo de aptitud optima, false en caso contrario.
	CUT_AT_FIRST_BEST : true para que la aplicación finalice al encontrar un individuo de aptitud optima, false en caso contrario.

	MAX_HEIGHT : Altura máxima de los árboles de la población inicial y de los subarboles creados en la mutación.
	
	BIT_TO_RESOLVE : Bit a resolver (de 0 a 6).
	
	SHOW_MOST_FREQUENT_INDIVIDUAL : true para mostrar el individuo con mayor frecuencia;
	SHOW_AVG_FITNESS : true para mostrar la aptitud promedio;
	SHOW_AVG_COUNT_NODES : true para mostrar la cantidad de nodos promedio;


- Ejecutar ant build para compilar el codigo fuente.

- Ejecutar java Engine.