import java.util.ArrayList;
//import java.util.Collections;
import java.util.Random;

public class Population implements Cloneable {

	private ArrayList<Individual> population;
	private int size;
	private int individualSize;
	
	public Population(int size, int individualSize) {
		this.size = size;
		this.population = new ArrayList<Individual>(size);
		
		this.individualSize = individualSize;
		if( this.individualSize < 1 )
			this.individualSize = 0;
		
		for (int i = 0; i < this.size; i++) {
			this.population.add(i, new Individual(individualSize));
		}

	}

	public void setPopulation (ArrayList<Individual> population) {
		this.population = population;
		this.size = population.size();
	}
	/*Para poder hacer copias de poblaciones */
	@SuppressWarnings("unchecked")
	public Object clone(){
        Population obj=null;
        try{
            obj=(Population)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println("No se puede duplicar");
        }        
        obj.population = (ArrayList<Individual>)obj.population.clone();
        return obj;
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
		
		for( int i=0 ; i < r.size() ; i++ ){
			for( int j=0; j< accumulatedAptitud.size() ; j++ )
				if( r.get(i) < accumulatedAptitud.get(j) ){					
					resp.add( i, (Individual)population.get(j).clone() );
					break;
				}					
		}
		/*
		 
		int j = 0;
		int i = 0;
		
		if ( r.get(j) < accumulatedAptitud.get(i)) {
			System.out.println("Selecciono a " + i);
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
		*/
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
		ArrayList<Individual> newPopulation = this.selection(cant);
		this.setPopulation(newPopulation);
		
		return newPopulation;
		/*
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
		*/
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
	public String toString(){
		StringBuffer resp = new StringBuffer();
		for (int i = 0; i < this.size; i++) {
			resp.append("Individuo " + i + ": " + population.get(i).toString() + "\n");
		}
		return resp.toString();
	}		
	
}
