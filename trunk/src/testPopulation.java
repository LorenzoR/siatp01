import java.util.ArrayList;


public class testPopulation {

	/**
	 * @param args
	 */
	public static void main(String args[]) {

		/*
		 * Individual individuo1 = new Individual(6); Individual individuo2 =
		 * new Individual(6); ArrayList<Individual> children = new
		 * ArrayList<Individual>(2);
		 * 
		 * individuo1.print(); individuo2.print();
		 * 
		 * System.out.println("Cross Over");
		 * 
		 * children = individuo1.crossOver(individuo2); children.get(0).print();
		 * children.get(1).print();
		 * 
		 * //System.out.println("Mutate..."); //individuo.mutate(0.1);
		 * //individuo.print();
		 */
		int currentGeneration = 0;
		int populationSize = 10;
		int individualSize = 31;
		double pMut = 0.3;
		
		Population population = new Population(populationSize, individualSize);
		Population originalPopulation = (Population)population.clone();
		ArrayList<Individual> parents;
		ArrayList<Individual> offspring;
		
		//System.out.println("Poblacion Inicial");
		//population.print();
		
		while ( currentGeneration < 10000 ) {
			//System.out.println("Poblacion inicial");
			//population.print();
			
			parents = population.selection(2);
			//System.out.println("Parents");
			//System.out.println(parents);
			System.out.println("\tSelected parents = " + parents);
			offspring = parents.get(0).crossOver(parents.get(1));
			System.out.println("\tCrossOver result = " + offspring );
			//offspring = parents.get(0).reproduction(parents.get(1));
			//System.out.println("Offspring");
			//offspring.get(0).print();
			//offspring.get(1).print();
			//System.out.print("Antes de mutar\t\t");
			//offspring.get(0).print();
			
			System.out.println("\t****Parent Mutation");
			offspring.get(0).mutate(pMut);
			System.out.println("\t****Parent Mutation");
			offspring.get(1).mutate(pMut);
			System.out.println("\tMutation result  = " + offspring );
			
			//System.out.println("Despuse de mutar");
			//offspring.get(0).print();
			//offspring.get(1).print();
			
			population.addIndividual(offspring.get(0));
			population.addIndividual(offspring.get(1));
			population.replacement(populationSize);
			
			//population.setPopulation(population.selection(populationSize));
			//System.out.println("Poblacion Final");
			//population.print();			
			//System.out.println("Population size " + populationSize);
			System.out.println("\nIteracion " + currentGeneration);
			currentGeneration++;
		}
		
		//System.out.println("\nPoblacion Inicial\n" + originalPopulation);
		System.out.println("\nPoblacion Inicial\n");
		originalPopulation.print();
		System.out.println("Poblacion Final luego de " + currentGeneration + " generaciones");
		population.print();
		
		/*ArrayList<Boolean> auxBool = new ArrayList<Boolean>(21);
		
		for ( int i = 0; i < 31; i++ )
			auxBool.add(i, true);
		
		auxBool.set(30, false);
		
		Individual ind = new Individual(auxBool);
		
		ind.print();
		ind.decode();
		
		for ( int i = 0; i < 31; i++ )
			auxBool.set(i, true);
		
		Individual ind2 = new Individual(auxBool);
		
		ind2.print();
		ind2.decode();
		
		for ( int i = 0; i < 31; i++ )
			auxBool.set(i, true);
		
		auxBool.set(0, false);
		
		Individual ind3 = new Individual(auxBool);
		
		ind3.print();
		ind3.decode();
	*/
	}

}
