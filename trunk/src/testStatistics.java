
public class testStatistics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		int populationSize = 500;		
		double percentage;
		Subject sub = new Subject(null, 0);
		
		FnImplement fn = new FnImplement();
		int maxHeight = 2;		
		
		Population population = new Population(populationSize,fn.setearFabricaDeArboles(),fn.setearFuncionDeFitness(),maxHeight );
		System.out.println(population);
		Statistics stats = new Statistics();
		stats.setPopulation(population);
		
		percentage = stats.getMostFrequentSubject(sub);
		System.out.println("Elegido = " +  sub.getIndividual() + "\t% = " + percentage);
	}

}
