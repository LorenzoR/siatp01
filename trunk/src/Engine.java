import java.util.ArrayList;


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
	private int populationSize;
	private double pMut;
	private int currentGeneration;
	private int selectionSize;
	private int maxHeight;
	private FnImplement myFunctions;
	private SyntaxTree st;
	private Fitness f;
	private Statistics stats;
	private ArrayList<Node>results;
		
	public Engine( int populationSize, int maxHeight, double pMut, int selectionSize, FnImplement functions, Statistics stats ){
		
		this.populationSize = populationSize;		
		this.pMut = pMut;
		//SelectionSize debe ser par y menor a populationSize
		if( selectionSize >= populationSize-1  )
			selectionSize = populationSize / 2;
		if( selectionSize % 2 != 0 )
			selectionSize++;
		this.selectionSize = selectionSize;
		
		myFunctions = functions;
		st = myFunctions.setSyntaxTree(FnInterface.terminals, FnInterface.functions, FnInterface.arities);
		f = myFunctions.setFitnessFunction(FnInterface.terminals);
		this.stats = stats; 
		this.maxHeight = maxHeight;
		results = new ArrayList<Node>();
		currentGeneration = 0;
	}
	
	public int getCurrentGeneration(){
		return currentGeneration;
	}
	
	public void incrementCurrentGeneration(){
		currentGeneration++;
		return;
	}
	
	public ArrayList<Population> populationInit(){
		ArrayList<Population>populations = new ArrayList<Population>(FnInterface.OUTPUT_BITS);
		for( int i=0 ; i< FnInterface.OUTPUT_BITS; i++ )
			populations.add(new Population(this.populationSize,st, f, maxHeight, i ) );
		return populations;
	}
	
	public boolean EndCondition(Population population){		
		double percentage;
		Subject resultSubject = new Subject(null, 0);		
		stats.setPopulation(population);
		int bestFitness = stats.getBestIndividualFitness();
		percentage = stats.getMostFrequentSubject(resultSubject);
		System.out.println("\tAptitud del Mejor Individuo = " + bestFitness);
		if( FnInterface.SHOW_AVG_FITNESS )
			System.out.println("\tAptitud Promedio = " + stats.getPopulationAvgFitness());
		if( FnInterface.SHOW_AVG_COUNT_NODES )
			System.out.println("\tCantidad de nodos promedio = " + stats.getPopulationAvgCountNodes());
		if( FnInterface.SHOW_MOST_FREQUENT_INDIVIDUAL )
			System.out.println("\tFrecuencia del Mejor Individuo = " + percentage);
		
		int maxFitness = 1 << FnInterface.INPUT_BITS;
		
		if( FnInterface.NEVER_CUT ){
			if(  bestFitness == maxFitness )
				showResult(population, FnInterface.BIT_TO_RESOLVE);
			return false;
		}
		if( FnInterface.CUT_AT_FIRST_BEST ){			 
			 
			 return bestFitness == maxFitness;			
		}else{
			return this.currentGeneration >= FnInterface.MAX_GENERATIONS;			
		}
		
	}
	public ArrayList<Individual> selection( Population population){
		ArrayList<Individual> resp = population.selection(selectionSize);
		//System.out.println("\tSelected parents = " + resp);
		return resp;
	}
	
	public ArrayList<Individual> reproduction( ArrayList<Individual> parents, int outputBit ){
		ArrayList<Individual> offspring;		
		ArrayList<Individual> finalOffspring = new ArrayList<Individual>(parents.size());		
		
		for( int i=0; i<parents.size(); i++ ){
			offspring = parents.get(i).crossOver(parents.get(i+1), outputBit);
			//System.out.println("\tCrossOver result = " + offspring );			
			//System.out.println("\t** Offspring " + i + " Mutation");
			offspring.get(0).mutate(pMut, outputBit);
			//System.out.println("\t** Offspring " + (i+1) + " Mutation");
			offspring.get(1).mutate(pMut, outputBit);
			//System.out.println("\tMutation result  = " + offspring );			
			finalOffspring.add(offspring.get(0));
			finalOffspring.add(offspring.get(1));
			i++;
		}				
		//System.out.println("\tFinal offspring result  = " + finalOffspring );
		return finalOffspring;
	}
	
	public Population replacement( Population population, ArrayList<Individual> offspring){		
		for( int i=0 ; i < offspring.size() ; i++ ){
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
							"\n\tAltura maxima del arbol = " + FnInterface.MAX_HEIGHT +
							"\n\tSeleccion por Ruleta \n\tReproduccion por Crossover y Mutacion con prob="+FnInterface.MUTATION_PROBABILITY+
							"\n\tCorte cuando un individuo alcanza el " + FnInterface.HIGHEST_PERCENTAGE*100 + "%, o en su defecto al llegar a " + FnInterface.MAX_GENERATIONS + " generaciones");
	}
	
	public void showResult(Population population, int outputBit){
		//System.out.println("\nPoblacion Inicial\n" + originalPopulation);		
		//System.out.println("Poblacion Final luego de " + engine.getCurrentGeneration() + " generaciones\n" + population);
		/*
		Individual selectedIndividual =  new Individual(st, FnInterface.MAX_HEIGHT, f, outputBit);
		double percentage = getSelectedIndividual(population, selectedIndividual);
		Node selectedNode = selectedIndividual.getChromosome();
		*/
		System.out.println("\nResultados para el BIT " + outputBit + " ,  CANT NODE = "+stats.getMaxFitnessNode().size()+" ,  FITNESS = "+stats.getBestIndividualFitness());
		//System.out.println("Porcentaje en la Poblacion = " + percentage*100 + "% , luego de " + getCurrentGeneration() + " generaciones");
		System.out.println("Individuo seleccionado = ");
		stats.getMaxFitnessNode().printPreorder();	
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
		Engine engine = new Engine( FnInterface.POPULATION_SIZE, FnInterface.MAX_HEIGHT, 
						FnInterface.MUTATION_PROBABILITY, FnInterface.SELECTION_SIZE, myFunctions,
						stats);
			
		
		//Inicializo la poblacion, el paramero algType define si voy a minimizar o maximizar
		ArrayList<Population> populations = engine.populationInit();		
		
		int i = FnInterface.BIT_TO_RESOLVE;
		//for( int i=0 ; i<FnInterface.OUTPUT_BITS ; i++ ){			
			Population population = populations.get(i);
			ArrayList<Individual> parents;
			ArrayList<Individual> offspring;
			
			while( !engine.EndCondition(population) ){
				//population.print();
				parents = engine.selection(population);			
				offspring = engine.reproduction(parents, i);			
				population = (Population)engine.replacement(population, offspring);
				engine.incrementCurrentGeneration();
				System.out.println("Iteracion " + engine.getCurrentGeneration());
			}	
			engine.showEngineParams();
			engine.showResult(population, i);
			//engine.currentGeneration = 0;
			
		//}
		//engine.showAllResults();	
	}

	@SuppressWarnings("unused")
	private void showAllResults() {
		// TODO Auto-generated method stub		
		for( int i=0 ; i<results.size() ; i++ ){
			System.out.println("=====> BIT " + i + " ,  SIZE = "+results.get(i).size());
			results.get(i).printPreorder();
		}
		
	}
	
}
