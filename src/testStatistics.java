
public class testStatistics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		int populationSize = 500;		
		double percentage;
		Subject sub = new Subject(null, 0);
		
		
		int maxHeight = 2;		
		
		FnImplement myFunctions = new FnImplement();
		SyntaxTree st = myFunctions.setSyntaxTree(FnInterface.terminals, FnInterface.functions, FnInterface.arities);
		Fitness f = myFunctions.setFitnessFunction(FnInterface.terminals);
		Population population = new Population(populationSize, st,f,maxHeight,0);
		
		
		System.out.println(population);
		Statistics stats = new Statistics();
		stats.setPopulation(population);
		
		percentage = stats.getMostFrequentSubject(sub);
		System.out.println("Elegido = " +  sub.getIndividual() + "\t% = " + percentage);
		System.out.println("Average Fitness = " +  stats.getPopulationAvgFitness());
		System.out.println("Best individual Fitness = " +  stats.getBestIndividualFitness());
	}

}
