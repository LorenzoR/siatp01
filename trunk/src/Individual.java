import java.util.ArrayList;
import java.util.Random;

public class Individual {

	private ArrayList<Boolean> chromosome;
	private int size;

	public Individual(int size) {

		this.chromosome = new ArrayList<Boolean>(size);
		this.size = size;
		Random number = new Random();

		for (int i = 0; i < size; i++)
			this.chromosome.add(i, number.nextBoolean());
	}

	public Individual(ArrayList<Boolean> chromosome) {
		this.size = chromosome.size();
		this.chromosome = chromosome;
	}

	public boolean getChromosome(int index) {
		return this.chromosome.get(index);
	}

	public ArrayList<Boolean> getChromosome() {
		return this.chromosome;
	}

	public void setChromosome(int index, boolean info) {
		this.chromosome.set(index, info);
	}

	public void print() {

		//for (int i = 0; i < this.size; i++)
		//	System.out.print(this.chromosome.get(i).toString() + "\t");

		for (int i = 0; i < this.size; i++) {
			if ( this.chromosome.get(i) ) {
				System.out.print("1");
			}
			else {
				System.out.print("0");
			}
		}
		
		System.out.print("\n");

	}

	public void mutate(double pMut) {

		Random randomGenerator = new Random();

		for (int i = 0; i < this.size; i++) {
			if (randomGenerator.nextDouble() < pMut) {
				this.chromosome.set(i, !this.chromosome.get(i));
			}

		}

	}

	public ArrayList<Individual> crossOver(Individual individual) {

		Random randomGenerator = new Random();
		int crossPoint = (int) Math.round(randomGenerator.nextDouble()
				* (this.size - 1));
		ArrayList<Individual> children = new ArrayList<Individual>(2);
		children.add(0, new Individual(this.getChromosome()));
		children.add(1, new Individual(individual.getChromosome()));
		boolean aux;

		//System.out.println("Cross Point: " + crossPoint);

		for (int i = crossPoint + 1; i < this.size; i++) {
			aux = children.get(0).getChromosome(i);
			children.get(0).setChromosome(i, children.get(1).getChromosome(i));
			children.get(1).setChromosome(i, aux);
		}

		return children;

	}

	//public ArrayList<Individual> reproduction (Individual individual) {
	//	ArrayList<Individual> offspring = this.crossOver(individual);
	//	offspring.get(0).mutate
	//}
	
	public double getFitness() {

		double sum = 0;
		
		for ( int i = 0; i < this.size; i++ ) {
			if ( this.chromosome.get(i))
				sum++;
		}
		
		return sum;
	}

}
