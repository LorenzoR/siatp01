import java.util.ArrayList;


public interface FnInterface {
	
	/* Definiciones para la funcion que se quiere maximizar o minimizar */
	public static final int GROUP_NUMBER = 8;
	public static final double A = 0.1 * GROUP_NUMBER;
	public static final double B = 5 * GROUP_NUMBER;
	public static final double C = 0.2 * GROUP_NUMBER;
	
	/* Enumerativo que define si se minimiza o maximiza */
	public enum ALG_TYPE { MINIMIZE, MAXIMIZE };
	public ALG_TYPE algType = FnInterface.ALG_TYPE.MINIMIZE; 
	
	/* Definiciones que caracterizan a la poblacion y su evolucion */	 
	public int POPULATION_SIZE = 1000;
	public double MUTATION_PROBABILITY = 0.1;	
	public int SELECTION_SIZE = 2;
	public int MAX_GENERATIONS = 1000;//CAMBIAR POR 10000
	public double HIGHEST_PERCENTAGE = 0.6;
	
	/* Definiciones que caracterizan al individuo y su representacion */ 
	public int CHROMOSOME_SIZE = 10; 
	
	/* Funciones para codificar y decodificar a los individuos */
	public ArrayList<Boolean> encode( double fenotype, int chromosomeSize );
	public double decode( ArrayList<Boolean> chromosome );
		
	/* Definiciones auxiliares para las funciones de aptitud
	 * Upper Bound se usa para definir correctamente una funcion
	 * de aptitud que minimize la funcion analizada
	 */ 
	public static final double UPPER_BOUND = 4;	
	
	/* Funciones que determinan la aptitud de un individuo, es decir
	 * incorporan informacion del medio ambiente.
	 * En nuestro trabajo, queremos maximizar y minimizar una funcion,
	 * luego necesitamos 2 metodos.
	 * Si queremos evolucionar otra poblacion analizando otra caracteristica, 
	 * basta definir aqui un nuevo metodo, e indicarle al engine que evolucione
	 * una poblacion distinta (especializando la clase Population)
	 */
	public double minFn(ArrayList<Boolean> chromosome );
	public double maxFn(ArrayList<Boolean> chromosome);
	
}
