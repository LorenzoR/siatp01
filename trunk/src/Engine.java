import java.util.ArrayList;
import java.util.Random;

/*
 * CLASE: ENGINE
 * =============
 * Clase que implementa el engine desarrollado. 
 * Usa algoritmos geneticos para hacer evolucionar una poblacion
 * Las definiciones que caracterizan la evolucion estan en la interface FnInterface
 * Statistics stats, es una instancia de la libreria de calculos estadisticos
 * que se aplican sobre la poblacion
*/
public class Engine {
	private int chromosomeSize; 
	private int populationSize;
	private double pMut;
	private int currentGeneration;
	private int selectionSize;
	private FnImplement myFunctions;
	private Statistics stats;
		
	public Engine( int populationSize, int chromosomeSize, double pMut, int selectionSize, FnImplement functions, Statistics stats ){
		this.chromosomeSize = chromosomeSize;
		this.populationSize = populationSize;		
		this.pMut = pMut;
		//SelectionSize debe ser par y menor a populationSize
		if( selectionSize >= populationSize-1  )
			selectionSize = populationSize / 2;
		if( selectionSize % 2 != 0 )
			selectionSize++;
		this.selectionSize = selectionSize;		
		
		myFunctions = functions;
		this.stats = stats; 
		currentGeneration = 0;
	}
	
	public int getCurrentGeneration(){
		return currentGeneration;
	}
	
	public void incrementCurrentGeneration(){
		currentGeneration++;
		return;
	}
	
	public Population populationInit(FnInterface.ALG_TYPE type ){
		Population population;		
		/* Creo una poblacion con CERO individuos. Esta poblacion es una
		 * especializacion de la clase Population. La subclase a instanciar
		 * depende del tipo de evolucion que analizamos (Maximizar o minimizar)
		 */
		if( type == FnInterface.ALG_TYPE.MINIMIZE )			
			population = new PopulationMin(0, chromosomeSize);
		else
			population = new PopulationMax(0, chromosomeSize);
		
		//Genero los individuos que formaran la poblacion
		for( int i=0 ; i< this.populationSize ; i++ ){
			//Genero un FENOTIPO: numero real entre 0 y 1 ...
			Random number = new Random();
			double fenotype = number.nextDouble();
			
			/* Y lo codifico en un GENOTIPO : estructura que guarda cromosomas
			 *  en determinados locus. En nuestro caso, hay un solo locus
			 *  Un cromosoma : es un ArrayList de valores booleanos
			 */  			
			ArrayList<Boolean> chromosome = myFunctions.encode(fenotype, FnInterface.CHROMOSOME_SIZE );
			Individual genotype = new Individual( chromosome );			
			population.addIndividual(genotype);			
		}
		return population;
	}
	
	public boolean EndCondition(Population population){		
		double percentage;
		Subject resultSubject = new Subject(null, 0);
		
		stats.setPopulation(population);
		percentage = stats.getMostFrequentSubject(resultSubject);		
		
		/* El criterio de corte se alcanza cuando un individuo ocupa
		 * un porcentaje dado de la poblacion. Esto se determina 
		 * mediante la constante HIGHEST_PERFORMANCE
		 * En caso la convergencia sea muy lenta y no se alcanze dicho
		 * porcentaje, se corta al llegar a MAX_GENERATIONS 
		 */
		if( this.currentGeneration >= FnInterface.MAX_GENERATIONS )
			return true;
		if( percentage >= FnInterface.HIGHEST_PERCENTAGE ){
			//System.out.println("The ONE = " +  resultSubject.getIndividual() + "\t% = " + percentage);
			return true;
		}
		else{
			//System.out.println("NOT YET, the best until now = " +  resultSubject.getIndividual() + "\t% = " + percentage);
			return false;
		}
	}
	public ArrayList<Individual> selection( Population population ){
		ArrayList<Individual> resp = population.selection(selectionSize);
		//System.out.println("\tSelected parents = " + resp);
		return resp;
	}
	
	public ArrayList<Individual> reproduction( ArrayList<Individual> parents ){
		ArrayList<Individual> offspring;		
		ArrayList<Individual> finalOffspring = new ArrayList<Individual>(parents.size());		
		
		for( int i=0; i<parents.size(); i++ ){
			offspring = parents.get(i).crossOver(parents.get(i+1));
			//System.out.println("\tCrossOver result = " + offspring );			
			//System.out.println("\t** Offspring " + i + " Mutation");
			offspring.get(0).mutate(pMut);
			//System.out.println("\t** Offspring " + (i+1) + " Mutation");
			offspring.get(1).mutate(pMut);
			//System.out.println("\tMutation result  = " + offspring );			
			finalOffspring.add(offspring.get(0));
			finalOffspring.add(offspring.get(1));
			i++;
		}				
		//System.out.println("\tFinal offspring result  = " + finalOffspring );
		return finalOffspring;
	}
	
	public Population replacement( Population population, ArrayList<Individual> offspring){		
		for( int i=0 ; i<offspring.size() ; i++ ){
			population.addIndividual(offspring.get(i));
		}
		population.replacement(populationSize);
		return population;
	}
	
	public double getSelectedIndividual(Population population, Individual selectedIndividual) {		
		double percentage;
		Subject resultSubject = new Subject(null, 0);
		
		percentage = stats.getMostFrequentSubject(resultSubject);		
		//System.out.println("PERCENTAGE = " + percentage);
		selectedIndividual.setChromosome(resultSubject.getIndividual().getChromosome());
		return percentage;
	}
	
	public void showEngineParams(){
		System.out.println( "Parametros del Engine:");
		System.out.println( "\tPoblacion Total = " + FnInterface.POPULATION_SIZE + " individuos" + 
							"\n\tSe seleccionan = " + FnInterface.SELECTION_SIZE + " individuos por generacion" + 
							"\n\tLong de cromosoma = " + FnInterface.CHROMOSOME_SIZE +
							"\n\tSeleccion por Ruleta \n\tReproduccion por Crossover y Mutacion con prob="+FnInterface.MUTATION_PROBABILITY+
							"\n\tCorte cuando un individuo alcanza el " + FnInterface.HIGHEST_PERCENTAGE*100 + "%, o en su defecto al llegar a " + FnInterface.MAX_GENERATIONS + " generaciones");
	}
	
	public void showResults(Population population){
		//System.out.println("\nPoblacion Inicial\n" + originalPopulation);		
		//System.out.println("Poblacion Final luego de " + engine.getCurrentGeneration() + " generaciones\n" + population);
		Individual selectedIndividual =  new Individual(FnInterface.CHROMOSOME_SIZE);
		double percentage = getSelectedIndividual(population, selectedIndividual);
		System.out.println("\nResultados:");
		System.out.println("\tIndividuo seleccionado = " + selectedIndividual + "\n\tFenotipo = " + myFunctions.decode( selectedIndividual.getChromosome() ) );
		System.out.println("\tPorcentaje en la Poblacion = " + percentage*100 + "% , luego de " + getCurrentGeneration() + " generaciones");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		//Inicializo la interface que tiene todas las constantes y funciones especificas
		FnImplement myFunctions = new FnImplement();
		
		//Libreria para calcular propiedades a la poblacion, como el individuo mas frecuente
		Statistics stats = new Statistics(); 
		
		//Inicializo el engine del AG, SELECTION_SIZE debe ser par, y menor que POPULATION_SIZE
		Engine engine = new Engine( FnInterface.POPULATION_SIZE, FnInterface.CHROMOSOME_SIZE, 
						FnInterface.MUTATION_PROBABILITY, FnInterface.SELECTION_SIZE, myFunctions,
						stats);
		
		//Inicializo la poblacion, el paramero algType define si voy a minimizar o maximizar
		Population population = engine.populationInit(FnInterface.algType);
		@SuppressWarnings("unused")
		Population originalPopulation = (Population)population.clone();
		
		ArrayList<Individual> parents;
		ArrayList<Individual> offspring;
		
		while( !engine.EndCondition(population) ){
			parents = engine.selection(population);			
			offspring = engine.reproduction(parents);			
			population = (Population)engine.replacement(population, offspring);
			engine.incrementCurrentGeneration();
			System.out.println("Iteracion " + engine.getCurrentGeneration());
		}	
		engine.showEngineParams();
		engine.showResults(population);				
	}
	
}
