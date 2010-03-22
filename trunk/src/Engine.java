import java.util.ArrayList;
import java.util.Random;

//Clase que implementa un engine que usa algoritmos geneticos
//Las definiciones que necesita el engine estan en la interface FnInterface
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
		
		//Creo una poblacion con CERO individuos, y defino si voy
		//a minimizar o maximizar la funcion
		if( type == FnInterface.ALG_TYPE.MINIMIZE )			
			population = new PopulationMin(0, chromosomeSize);
		else
			population = new PopulationMax(0, chromosomeSize);
		
		//Genero los individuos que formaran la poblacion
		for( int i=0 ; i< this.populationSize ; i++ ){
			//Genero un FENOTIPO: numero real entre 0 y 1 ...
			Random number = new Random();
			double fenotype = number.nextDouble();
			
			//Y lo codifico en un GENOTIPO : estructura que guarda cromosomas en determinados locus
			//En nuestro caso, hay un solo locus: 1 sola posicion
			//Un cromosoma: string de valores booleanos			
			ArrayList<Boolean> chromosome = myFunctions.encode(fenotype, FnInterface.CHROMOSOME_SIZE );
			Individual genotype = new Individual( chromosome );			
			population.addIndividual(genotype);			
		}
		return population;
	}
	//Para borrar ... ya no se usa esta funcion
	public ArrayList<Boolean> Encode( double fenotype ){
		//Falta completar la codificacion	
		//Esta codificacion sigue el ejemplo de Lore		
		Random number = new Random();
		ArrayList<Boolean> chromosome = new ArrayList<Boolean>(this.chromosomeSize);
		for (int i = 0; i < this.chromosomeSize; i++)
			chromosome.add(i, number.nextBoolean());		
		
		return chromosome;		
	}
	
	public boolean EndCondition(Population population){
		//Corta por un numero maximo de generaciones
		//return this.currentGeneration >= FnInterface.MAX_GENERATIONS;
		
		double percentage;
		Subject resultSubject = new Subject(null, 0);
		stats.setPopulation(population);
		percentage = stats.getMostFrequentSubject(resultSubject);
		//return percentage >= FnInterface.HIGHEST_PERCENTAGE;
		
		if( this.currentGeneration >= FnInterface.MAX_GENERATIONS )
			return true;
		if( percentage >= FnInterface.HIGHEST_PERCENTAGE ){
			System.out.println("The ONE = " +  resultSubject.getIndividual() + "\t% = " + percentage);
			return true;
		}
		else
			System.out.println("NOT YET, the best until now = " +  resultSubject.getIndividual() + "\t% = " + percentage);
			return false;
	}
	public ArrayList<Individual> selection( Population population ){
		//Solo implementa RULETA
		ArrayList<Individual> resp = population.selection(selectionSize);
		System.out.println("\tSelected parents = " + resp);
		return resp;
	}
	
	public ArrayList<Individual> reproduction( ArrayList<Individual> parents ){
		ArrayList<Individual> offspring;	
		ArrayList<Individual> finalOffspring = new ArrayList<Individual>(parents.size());
		
		//offspring = parents.get(0).crossOver(parents.get(1));
		for( int i=0; i<parents.size(); i++ ){
			offspring = parents.get(i).crossOver(parents.get(i+1));
			System.out.println("\tCrossOver result = " + offspring );
			
			System.out.println("\t** Offspring " + i + " Mutation");
			offspring.get(0).mutate(pMut);
			System.out.println("\t** Offspring " + (i+1) + " Mutation");
			offspring.get(1).mutate(pMut);
			System.out.println("\tMutation result  = " + offspring );			
			finalOffspring.add(offspring.get(0));
			finalOffspring.add(offspring.get(1));
			i++;
		}				
		System.out.println("\tFinal offspring result  = " + finalOffspring );
		return finalOffspring;
	}
	
	public Population replacement( Population population, ArrayList<Individual> offspring){
		//population.addIndividual(offspring.get(0));
		//population.addIndividual(offspring.get(1));

		for( int i=0 ; i<offspring.size() ; i++ ){
			population.addIndividual(offspring.get(i));
		}
		population.replacement(populationSize);
		return population;
	}
	public Individual getSelectedIndividual(Population population) {
		double percentage;
		Subject resultSubject = new Subject(null, 0);
		percentage = stats.getMostFrequentSubject(resultSubject);
		System.out.println("PERCENTAGE = " + percentage);
		return resultSubject.getIndividual();
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
		Population originalPopulation = (Population)population.clone();
		
		ArrayList<Individual> parents;
		ArrayList<Individual> offspring;
		
		while( !engine.EndCondition(population) ){
			parents = engine.selection(population);
			offspring = engine.reproduction(parents);
			population = (Population)engine.replacement(population, offspring);
			engine.incrementCurrentGeneration();
			System.out.println("\nIteracion " + engine.getCurrentGeneration());
		}		
		System.out.println("\nPoblacion Inicial\n" + originalPopulation);		
		System.out.println("Poblacion Final luego de " + engine.getCurrentGeneration() + " generaciones\n" + population);
		Individual selectedIndividual = engine.getSelectedIndividual(population);
		System.out.println("Individuo selectionado = " + selectedIndividual + "\nFenotipo = " + myFunctions.decode( selectedIndividual.getChromosome() ) );
				
	}
	
}
