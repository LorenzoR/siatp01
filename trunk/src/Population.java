import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {

	private ArrayList<Individual> population;
	private int size;
	private int individualSize;
	
	public Population(int size, int individualSize) {
		this.size = size;
		this.population = new ArrayList<Individual>(size);
		this.individualSize = this.individualSize;
		
		for (int i = 0; i < this.size; i++) {
			this.population.add(i, new Individual(individualSize));
		}

	}

	public void setPopulation (ArrayList<Individual> population) {
		this.population = population;
		this.size = population.size();
	}
	
	public ArrayList<Individual> selection(int cant) {
		ArrayList<Individual> resp = new ArrayList<Individual>(cant);
		ArrayList<Double> r = new ArrayList<Double>(cant);
		Random randomGenerator = new Random();
		Double randomNumber = randomGenerator.nextDouble();
		ArrayList<Double> aptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> relativeAptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> accumulatedAptitud = new ArrayList<Double>(this.size);
		double totalAptitud = 0;
		
		// Computo de Ri
		for (int i = 1; i <= cant; i++) {
			r.add(i-1, ( randomNumber + i - 1 ) / cant);
		}

		// Computo de aptitudes y aptitud total
		for ( int i = 0; i < this.size; i++ ) {
			aptitud.add(i, population.get(i).getFitness());
			totalAptitud += aptitud.get(i);
		}
		
		// Computo de aptitudes relativas
		for ( int i = 0; i < this.size; i++ ) {
			relativeAptitud.add(i, aptitud.get(i) / totalAptitud);
		}
		
		// Computo de aptitudes acumuladas
		accumulatedAptitud.add(0, relativeAptitud.get(0));
		for ( int i = 1; i < this.size; i++ ) {
			accumulatedAptitud.add(i, accumulatedAptitud.get(i - 1) + relativeAptitud.get(i));
		}
		/*
		System.out.print("Ri\t");
		System.out.println(r);
		
		System.out.print("Aptitudes\t");
		System.out.println(aptitud);
		
		System.out.print("Aptitud Total\t");
		System.out.println(totalAptitud);
		
		System.out.print("Aptitudes Relativas\t");
		System.out.println(relativeAptitud);
		
		System.out.print("Aptitudes Acumuladas\t");
		System.out.println(accumulatedAptitud);
		*/
		int j = 0;
		int i = 0;
		
		if ( r.get(j) < accumulatedAptitud.get(i)) {
			//System.out.println("Selecciono a " + i);
			resp.add(j, population.get(i));
			j++;
		}
		
		i = 1;
		
		while ( j < cant ) {
			
			if ( r.get(j) > accumulatedAptitud.get(i-1) && r.get(j) < accumulatedAptitud.get(i) ) {
				//System.out.println("Selecciono a " + i);
				resp.add(j, population.get(i));
				j++;
			}
			
			if ( j < cant && accumulatedAptitud.get(i) < r.get(j) ) {
				i++;
			}
			
		}
		
		/*for ( int i = 1; i < this.size && j < cant; i++ ) {
			if ( r.get(j) > accumulatedAptitud.get(i-1) && r.get(j) < accumulatedAptitud.get(i) ) {
				System.out.println("Selecciono a " + i);
				resp.add(j, population.get(i));
				j++;
			}
		}*/
		
		//System.out.println("Individuos Seleccionados");
		//System.out.println(resp);
		
		return resp;
	}

	public ArrayList<Individual> replacement ( int cant ) {
		ArrayList<Individual> newPopulation = new ArrayList<Individual>(cant);
		Random randomGenerator = new Random();
		ArrayList<Double> r = new ArrayList<Double>(cant);
		ArrayList<Double> aptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> relativeAptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> accumulatedAptitud = new ArrayList<Double>(this.size);
		double totalAptitud = 0;
		
		// Computo de Ri
		for ( int i = 0; i < cant; i++ ) {
			r.add(i, randomGenerator.nextDouble());
		}
		
		Collections.sort(r);
		

		// Computo de aptitudes y aptitud total
		for ( int i = 0; i < this.size; i++ ) {
			aptitud.add(i, population.get(i).getFitness());
			totalAptitud += aptitud.get(i);
		}
		
		// Computo de aptitudes relativas
		for ( int i = 0; i < this.size; i++ ) {
			relativeAptitud.add(i, aptitud.get(i) / totalAptitud);
		}
		
		// Computo de aptitudes acumuladas
		accumulatedAptitud.add(0, relativeAptitud.get(0));
		for ( int i = 1; i < this.size; i++ ) {
			accumulatedAptitud.add(i, accumulatedAptitud.get(i - 1) + relativeAptitud.get(i));
		}
		
		/*System.out.print("Ri\t");
		System.out.println(r);
		
		System.out.print("Aptitudes\t");
		System.out.println(aptitud);
		
		System.out.print("Aptitud Total\t");
		System.out.println(totalAptitud);
		
		System.out.print("Aptitudes Relativas\t");
		System.out.println(relativeAptitud);
		
		System.out.print("Aptitudes Acumuladas\t");
		System.out.println(accumulatedAptitud);
		*/
		int j = 0;
		int i = 0;
		
		if ( r.get(j) < accumulatedAptitud.get(i)) {
			//System.out.println("Selecciono a " + i);
			newPopulation.add(j, population.get(i));
			j++;
		}
		
		i = 1;
		
		while ( j < cant ) {
			
			if ( r.get(j) > accumulatedAptitud.get(i-1) && r.get(j) < accumulatedAptitud.get(i) ) {
				//System.out.println("Selecciono a " + i);
				newPopulation.add(j, population.get(i));
				j++;
			}
			
			if ( j < cant && accumulatedAptitud.get(i) < r.get(j) ) {
				i++;
			}
			
		}
		
		this.setPopulation(newPopulation);
		
		return newPopulation;
	}
	
	public void addIndividual (Individual individual) {
		this.population.add(individual);
		this.size++;
	}
	
	public void print() {
		for (int i = 0; i < this.size; i++) {
			System.out.print("Individuo " + i + ": ");
			population.get(i).print();
		}
	}

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
		int individualSize = 5;
		double pMut = 0.5;
		
		Population population = new Population(populationSize, individualSize);
		ArrayList<Individual> parents;
		ArrayList<Individual> offspring;
		
		//System.out.println("Poblacion Inicial");
		//population.print();
		
		while ( currentGeneration < 100 ) {
			//System.out.println("Poblacion inicial");
			//population.print();
			parents = population.selection(2);
			//System.out.println("Parents");
			//System.out.println(parents);
			offspring = parents.get(0).crossOver(parents.get(1));
			//offspring = parents.get(0).reproduction(parents.get(1));
			//System.out.println("Offspring");
			//offspring.get(0).print();
			//offspring.get(1).print();
			//System.out.print("Antes de mutar\t\t");
			//offspring.get(0).print();
			offspring.get(0).mutate(pMut);
			offspring.get(1).mutate(pMut);
			//System.out.println("Despuse de mutar");
			//offspring.get(0).print();
			//offspring.get(1).print();
			
			population.addIndividual(offspring.get(0));
			population.addIndividual(offspring.get(1));
			population.replacement(populationSize);
			
			//population.setPopulation(population.selection(populationSize));
			//System.out.println("Poblacion Final");
			//population.print();
			System.out.println("Iteracion " + currentGeneration);
			//System.out.println("Population size " + populationSize);
			currentGeneration++;
		}
		
		System.out.println("Poblacion Final luego de " + currentGeneration + " generaciones");
		population.print();
		
		//population.replacement(4);
		
		//population.selection(2);
	}
}
