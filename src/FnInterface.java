import java.util.ArrayList;


public interface FnInterface {
	
	//Definiciones para la funcion que se quiere maximizar o minimizar
	public static final int GROUP_NUMBER = 8;
	public static final double A = 0.1 * GROUP_NUMBER;
	public static final double B = 5 * GROUP_NUMBER;
	public static final double C = 0.2 * GROUP_NUMBER;
	
	//Enumerativo que define si se minimiza o maximiza
	public enum ALG_TYPE { MINIMIZE, MAXIMIZE };
	public ALG_TYPE algType = FnInterface.ALG_TYPE.MINIMIZE; 
	
	//Definiciones que caracterizan a la poblacion y su evolucion	 
	public int POPULATION_SIZE = 100;//50;
	public double MUTATION_PROBABILITY = 0.1;	
	public int SELECTION_SIZE = 20;//4;
	public int MAX_GENERATIONS = 1000;//100;
	public double HIGHEST_PERCENTAGE = 0.7;
	
	//Definiciones que caracterizan al individuo y su representacion
	public int CHROMOSOME_SIZE = 6; //4;
	
	//Funciones para codificar y decodificar a los individuos
	public ArrayList<Boolean> encode( double fenotype, int chromosomeSize );
	public double decode( ArrayList<Boolean> chromosome );
		
	//Definiciones auxiliares para las funciones de aptitud
	//Upper Bound se usa para definir correctamente una funcion que minimize
	public static final double UPPER_BOUND = 4;	
	
	//Funciones publicas que determinan la aptitud de un individuo
	//Cada poblacion usa una de ellas, y de esta forma incorpora
	//informacion del medio ambiente.
	public double minFn(ArrayList<Boolean> chromosome );
	public double maxFn(ArrayList<Boolean> chromosome);
	
}
