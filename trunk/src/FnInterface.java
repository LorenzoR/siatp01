
public interface FnInterface {	
	
	/*Metodos que implementa el engine*/
	public enum SELECT_TYPE{UNIVERSAL, ELITE, ROULETTE, ELITE_ROULETTE, ELITE_UNIVERSAL};
	
	/*Defino que metodo se usara para la seleccion y el reemplazo*/
	public final SELECT_TYPE CONFIG_SELECTION = FnInterface.SELECT_TYPE.UNIVERSAL;
	public final SELECT_TYPE CONFIG_REPLACEMENT = FnInterface.SELECT_TYPE.UNIVERSAL;
	
	
	/* Definiciones que caracterizan a la poblacion y su evolucion */	 
	public int POPULATION_SIZE = 10;//600;
	public double MUTATION_PROBABILITY = 0.01;	
	public int SELECTION_SIZE = 2;//300;
	public int MAX_GENERATIONS = 10;//8;
	public double HIGHEST_PERCENTAGE = 0.6;
		
	/* Altura maxima de los arboles que se generan para la poblacion o 
	 * para la mutacion
	 */ 
	public static final int MAX_HEIGHT = 2;
	
	/* Tenemos CANT_INPUTS palabras de entrada, cada una con BITS_PER_INPUR 
	 * bit. Y en la salida
	 * Si la cantidad de entradas son 2 y es un bit por entrada=> A0 y B0
	 * Si son 2 entradas con 2 bits por entrada =>  A0 A1 y B0 B1 ...etc
	 * La salida queda definida con BITS_PER_OUTPUT bits
	 */
	public static final int CANT_INPUTS = 2;
	public static final int BITS_PER_INPUT = 4;
	public static final int BITS_PER_OUTPUT = 1;
	
	public static final String[] terminals = {"B0", "B1", "A0", "A1"};
	public static final String[] functions = {"AND", "OR", "NOT"};
	public static final int[] arities = {2, 2, 1};
	
	/*Me permite crear las clases que hacen posible el cruce, la mutacion 
	 * y el calculo de la aptitud
	 */
	public SyntaxTree setSyntaxTree(String[] terminals, String[] functions, int[]arities);
	public Fitness setFitnessFunction(String[] terminals);
	
}
