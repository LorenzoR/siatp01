
public interface FnInterface {	
	
	/*Metodos que implementa el engine*/
	public enum SELECT_TYPE{UNIVERSAL, ELITE, ROULETTE, ELITE_ROULETTE, ELITE_UNIVERSAL};
	
	/*Defino que metodo se usara para la seleccion y el reemplazo*/
	public static final SELECT_TYPE CONFIG_SELECTION = SELECT_TYPE.ELITE_ROULETTE;
	public static final SELECT_TYPE CONFIG_REPLACEMENT = SELECT_TYPE.ELITE_ROULETTE;
	
	
	/* Definiciones que caracterizan a la poblacion y su evolucion */	 
	public static final int POPULATION_SIZE = 1000;//600;
	public static final double MUTATION_PROBABILITY = 0.1;	
	public static final int SELECTION_SIZE = 500;//300;

	public static int MAX_GENERATIONS = 20;//8;
	public static double HIGHEST_PERCENTAGE = 1;
	
	//Con este seteo, el algoritmo nunca termina. Al encontrar un individuo
	//maximo, muestra el resultado, pero sigue indefinidamente
	//Permita seguir evolucionando y ver si todo la poblacion camina hacia	
	//alguno de los individuos maximos hallados
	public static final boolean NEVER_CUT = false;
	
	//Permite cortar tan pronto se encuentra un individuo con aptitud maxima
	//Este seteo tiene mas prioridad que MAX_GENERATIONS
	public static final boolean CUT_AT_FIRST_BEST = true;
	
	
	
	/* Altura maxima de los arboles que se generan para la poblacion o 
	 * para la mutacion
	 */ 
	public static final int MAX_HEIGHT = 4;
	
	//Elegimos el bit a resolver
	public static final int BIT_TO_RESOLVE = 3;
	
	//Habilita info estadistica que se muestra al correr el engine
	public static final boolean SHOW_MOST_FREQUENT_INDIVIDUAL = false;
	public static final boolean SHOW_AVG_FITNESS = true;
	public static final boolean SHOW_AVG_COUNT_NODES = true;	
	//public static final boolean SHOW_AVG_HEIGHT = false;
	
	/* Tenemos CANT_INPUTS palabras de entrada, cada una con BITS_PER_INPUR 
	 * bit. Y en la salida
	 * Si la cantidad de entradas son 2 y es un bit por entrada=> A0 y B0
	 * Si son 2 entradas con 2 bits por entrada =>  A0 A1 y B0 B1 ...etc
	 * La salida queda definida con BITS_PER_OUTPUT bits
	 */
	public static final int CANT_INPUTS = 2;	// A y B
	public static final int INPUT_BITS = 8;		// cantidad de terminales
	public static final int OUTPUT_BITS = 7;
	
	public static final String[] terminals = {"B0", "B1", "B2", "B3", "A0", "A1", "A2", "A3"};
	//public static final String[] terminals = {"B0", "A0"};
	public static final String[] functions = {"AND", "OR", "NOT", "XOR", "NAND", "NOR", "XNOR"};
	public static final int[] arities = {2, 2, 1, 2, 2, 2, 2};
	
	/*Me permite crear las clases que hacen posible el cruce, la mutacion 
	 * y el calculo de la aptitud
	 */
	public SyntaxTree setSyntaxTree(String[] terminals, String[] functions, int[]arities);
	public Fitness setFitnessFunction(String[] terminals);
	
}
