
public class testStatistics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		int populationSize = 10;
		int individualSize = 4;
		double percentage;
		Subject sub = new Subject(null, 0);
		
		Population population = new Population(populationSize, individualSize);
		System.out.println(population);
		Statistics stats = new Statistics();
		stats.setPopulation(population);
		
		percentage = stats.getMostFrequentSubject(sub);
		System.out.println("Elegido = " +  sub.getIndividual() + "\t% = " + percentage);
	}

}
