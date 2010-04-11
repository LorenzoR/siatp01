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
		int populationSize = 4;		
		double pMut = 0.1;
		
		
		int maxHeight = 2;		
		
		FnImplement myFunctions = new FnImplement();
		SyntaxTree st = myFunctions.setSyntaxTree(FnInterface.terminals, FnInterface.functions, FnInterface.arities);
		Fitness f = myFunctions.setFitnessFunction(FnInterface.terminals);
		Population population = new Population(populationSize, st,f,maxHeight,0);
		Population originalPopulation = (Population)population.clone();
		ArrayList<Individual> parents;
		ArrayList<Individual> offspring;
		
		//System.out.println("Poblacion Inicial");
		//population.print();
	
		
		while ( currentGeneration < 2 ) {
			//System.out.println("Poblacion inicial");
			//population.print();
			
			parents = population.selection(2);
			//System.out.println("Parents");
			//System.out.println(parents);
			//parents.get(0).print();
			//parents.get(1).print();
			//System.out.println("\tSelected parents = " + parents);
			offspring = parents.get(0).crossOver(parents.get(1), 0);
			//System.out.println("\tCrossOver result = " + offspring );
			//offspring = parents.get(0).reproduction(parents.get(1));
			//System.out.println("Offspring");
			//offspring.get(0).print();
			//offspring.get(1).print();
			//System.out.print("Antes de mutar\t\t");
			//offspring.get(0).print();
			
			//System.out.println("\t****Parent Mutation");
			offspring.get(0).mutate(pMut, 0);
			
			//System.out.println("\t****Parent Mutation");
			offspring.get(1).mutate(pMut, 0);
			
			//System.out.println("\tMutation result  = " + offspring );
			
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
		
		System.out.println("\nPoblacion Inicial\n" + originalPopulation);
		originalPopulation.print();
		System.out.println("Poblacion Final luego de " + currentGeneration + " generaciones");
		population.print();
		
	
	}

}
