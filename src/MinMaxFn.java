import java.util.ArrayList;
import java.util.Random;


public class MinMaxFn {
	private int chromosomeSize; 
	private int populationSize;
	double pMut;
	private FnInterface myInterface;
	
	public MinMaxFn( int populationSize, int chromosomeSize, double pMut ){
		this.chromosomeSize = chromosomeSize;
		this.populationSize = populationSize;
		this.pMut = pMut;
		myInterface = new FnImplement();
	}
	
	public Population populationInit(){
		//Creo una poblacion con CERO individuos
		Population population = new Population(chromosomeSize, 0);
		
		for( int i=0 ; i< this.populationSize ; i++ ){
			//Genero un FENOTIPO: numero real entre 0 y 1 ...
			Random number = new Random();
			double fenotype = number.nextDouble();
			
			//Y lo codifico en un GENOTIPO : estructura que guarda cromosomas en determinados locus
			//En nuestro caso, hay un solo locus: 1 sola posicion
			//Un cromosoma: string de valores booleanos
			ArrayList<Boolean> chromosome = Encode( fenotype );
			Individual genotype = new Individual( chromosome );
			population.addIndividual(genotype);
		}
		return population;
	}
	
	public ArrayList<Boolean> Encode( double fenotype ){
		//Falta completar la codificacion	
		return new ArrayList<Boolean>(this.chromosomeSize);		
	}
	public boolean EndCondition(){
		//Falta completar
		return false;
	}
	public ArrayList<Individual> selection( Population population ){
		//Solo implementa RULETA
		return population.selection(2);
	}
	public ArrayList<Individual> reproduction( ArrayList<Individual> parents ){
		ArrayList<Individual> offspring;
		
		System.out.println("\tSelected parents = " + parents);
		offspring = parents.get(0).crossOver(parents.get(1));
		System.out.println("\tCrossOver result = " + offspring );
				
		System.out.println("\t****Parent Mutation");
		offspring.get(0).mutate(pMut);
		System.out.println("\t****Parent Mutation");
		offspring.get(1).mutate(pMut);
		System.out.println("\tMutation result  = " + offspring );
		
		return offspring;		
		
	}
	
	public Population replacement( Population population, ArrayList<Individual> offspring){
		population.addIndividual(offspring.get(0));
		population.addIndividual(offspring.get(1));
		population.replacement(populationSize);
		return population;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MinMaxFn engine = new MinMaxFn( 4, 5, 0.1);
		Population population = engine.populationInit();
		Population originalPopulation = (Population)population.clone();
		int currentGeneration = 0;		
		ArrayList<Individual> parents;
		ArrayList<Individual> offspring;
		
		while( !engine.EndCondition() ){
			parents = engine.selection(population);
			offspring = engine.reproduction(parents);
			population = engine.replacement(population, offspring);
			currentGeneration++;
			System.out.println("\nIteracion " + currentGeneration);
		}		
		System.out.println("\nPoblacion Inicial\n" + originalPopulation);		//
		System.out.println("Poblacion Final luego de " + currentGeneration + " generaciones\n" + population);
	}

}
