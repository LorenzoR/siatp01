- Configurar los parametros iniciales en el archivo FnInterface.java

	CONFIG_SELECTION : M�todo de selecci�n.
	CONFIG_REPLACEMENT : M�todo de reemplazo
	
	POPULATION_SIZE : Tama�o de la poblaci�n.
	MUTATION_PROBABILITY : Probabilidad de mutaci�n.
	SELECTION_SIZE : Tama�o de la selecci�n
	MAX_GENERATIONS : Cantidad m�xima de generaciones.
	
	NEVER_CUT : true para que la aplicaci�n no finalice al encontrar un individuo de aptitud optima, false en caso contrario.
	CUT_AT_FIRST_BEST : true para que la aplicaci�n finalice al encontrar un individuo de aptitud optima, false en caso contrario.

	MAX_HEIGHT : Altura m�xima de los �rboles de la poblaci�n inicial y de los subarboles creados en la mutaci�n.
	
	BIT_TO_RESOLVE : Bit a resolver (de 0 a 6).
	
	SHOW_MOST_FREQUENT_INDIVIDUAL : true para mostrar el individuo con mayor frecuencia;
	SHOW_AVG_FITNESS : true para mostrar la aptitud promedio;
	SHOW_AVG_COUNT_NODES : true para mostrar la cantidad de nodos promedio;


- Ejecutar ant build para compilar el codigo fuente.

- Ejecutar java Engine.