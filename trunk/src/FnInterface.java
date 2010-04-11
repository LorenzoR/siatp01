
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
	public int POPULATION_SIZE = 40;//600;
	public double MUTATION_PROBABILITY = 0.01;	
	public int SELECTION_SIZE = 4;//300;
	public int MAX_GENERATIONS = 10;//8;
	public double HIGHEST_PERCENTAGE = 0.6;
		
	public static final int MAX_HEIGHT = 2;
	public static final int CANT_INPUTS = 2;
	public static final int BITS_PER_INPUT = 2;
	public static final int BITS_PER_OUTPUT = 1;
	public SyntaxTree setearFabricaDeArboles();
	public Fitness setearFuncionDeFitness();
	
}
