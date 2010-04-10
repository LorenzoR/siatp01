import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import java.util.Collections;
import java.util.Random;

public class Population implements Cloneable {

	private ArrayList<Individual> population;
	private int size;
	private int individualSize;
	private Random randomGenerator = new Random();
	
	//Inteface donde se encuentran las funciones que impone el ambiente 
	//particular para el problema. Como se determinara la funcion segun
	//se especialize la poblacion, suprimimos los warnings	
	protected FnImplement myFunctions;
	
	public Population(int size, int individualSize) {
		this.size = size;
		this.population = new ArrayList<Individual>(size);
		
		this.individualSize = individualSize;
		if( this.individualSize < 1 )
			this.individualSize = 0;
		
		myFunctions = new FnImplement();
		
		for (int i = 0; i < this.size; i++) {
			this.population.add(i, new Individual(individualSize));
		}

	}
	public int size(){
		return size;
	}
	public ArrayList<Individual>getIndividualsArray(){
		return population;
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
	
	public ArrayList<Individual> eliteUniversalSelection(int cantElite, int cantUniversal) {
		ArrayList<Individual> eliteSelection = new ArrayList<Individual>(0);
		ArrayList<Individual> universalSelection = new ArrayList<Individual>(0);
		
		eliteSelection = eliteSelection(cantElite);
		universalSelection = universalSelection(cantUniversal);
		
		eliteSelection.addAll(universalSelection);
		
		return eliteSelection;
	}
	
	public ArrayList<Individual> eliteRouletteSelection(int cantElite, int cantRoulette) {
		ArrayList<Individual> eliteSelection = new ArrayList<Individual>(0);
		ArrayList<Individual> rouletteSelection = new ArrayList<Individual>(0);
		
		eliteSelection = eliteSelection(cantElite);
		rouletteSelection = rouletteSelection(cantRoulette);
		
		eliteSelection.addAll(rouletteSelection);
		
		return eliteSelection;
	}
	
	public ArrayList<Individual> eliteSelection(int cant) {
		ArrayList<Individual> resp = new ArrayList<Individual>(0);
		ArrayList<Double> aptitud = new ArrayList<Double>(this.size);
		
		// Computo de aptitudes y aptitud total
		for ( int i = 0; i < this.size; i++ ) {
			aptitud.add(i, this.fitness(population.get(i)) );
		}
		
		Collections.sort(aptitud, Collections.reverseOrder());
		
		List<Double> best = aptitud.subList(0, cant);
		
		//System.out.println("Population es " + population);
		//System.out.println("Aptitudes es " + aptitud);
		//System.out.println("Best es " + best);
		
		for ( int i = 0, j = 0; i < this.size() && j < cant; i++ ) {
			if ( best.contains(this.fitness(population.get(i)))) {
				//System.out.println("Best contiene a " + this.fitness(population.get(i)));
				//System.out.println("Elijo " + i);
				resp.add( j++, (Individual)population.get(i).clone() );
			}
		}
		
		//System.out.println("Elite Selection (elijo " + cant + " ) " + best);
		//System.out.println("Finalmente, me quedo con " + resp);
		
		return resp;
	}
	
	public ArrayList<Individual> rouletteSelection(int cant) {
		ArrayList<Individual> resp = new ArrayList<Individual>(0);
		ArrayList<Double> r = new ArrayList<Double>(cant);
		ArrayList<Double> aptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> relativeAptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> accumulatedAptitud = new ArrayList<Double>(this.size);
		double totalAptitud = 0;

		if ( cant >= this.size || cant < 0) {
			return this.population;
		}
		
		// Computo de Ri
		for (int i = 1; i <= cant; i++) {
			r.add(i-1, randomGenerator.nextDouble());
		}
		
		// Computo de aptitudes y aptitud total
		for ( int i = 0; i < this.size; i++ ) {
			aptitud.add(i, this.fitness(population.get(i)) );
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
		
		
		for( int i=0 ; i < r.size() ; i++ ){
			for( int j=0; j< accumulatedAptitud.size() ; j++ )
				if( r.get(i) < accumulatedAptitud.get(j) ){					
					resp.add( i, (Individual)population.get(j).clone() );
					break;
				}					
		}
		return resp;
	}
	
	public ArrayList<Individual> universalSelection(int cant) {

		ArrayList<Individual> selection;
		ArrayList<Double> r;
		Double randomNumber = randomGenerator.nextDouble();
		ArrayList<Double> aptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> relativeAptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> accumulatedAptitud = new ArrayList<Double>(this.size);
		double totalAptitud = 0;
	
		if ( cant >= this.size || cant < 0) {
			return this.population;
		}
		
		selection = new ArrayList<Individual>(cant);
		r = new ArrayList<Double>(cant);
		
		// Computo de Ri
		for (int i = 1; i <= cant; i++) {
			r.add(i-1, ( randomNumber + i - 1 ) / cant);
		}
		
		// Computo de aptitudes y aptitud total
		for ( int i = 0; i < this.size; i++ ) {
			aptitud.add(i, this.fitness(population.get(i)) );
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
		
		
		for( int i=0 ; i < r.size() ; i++ ){
			for( int j=0; j< accumulatedAptitud.size() ; j++ )
				if( r.get(i) < accumulatedAptitud.get(j) ){					
					selection.add( i, (Individual)population.get(j).clone() );
					break;
				}					
		}
		return selection;
	}
	
	public ArrayList<Individual> selection(int cant) {
		
		return universalSelection(cant);
		/*//ArrayList<Individual> resp = new ArrayList<Individual>(cant);
		ArrayList<Individual> resp = new ArrayList<Individual>(0);
		ArrayList<Double> r = new ArrayList<Double>(cant);
		//Random randomGenerator = new Random();
		Double randomNumber = randomGenerator.nextDouble();
		ArrayList<Double> aptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> relativeAptitud = new ArrayList<Double>(this.size);
		ArrayList<Double> accumulatedAptitud = new ArrayList<Double>(this.size);
		double totalAptitud = 0;
		
		//Detecto si me piden seleccionar una cantidad negativa
		if( cant < 0 )
			cant = 0;		
		//Si me piden seleccionar una cantidad igual a la poblacion, entonces
		//devuelvo directamente la poblacion
		if( cant >= FnInterface.POPULATION_SIZE )
			return this.population;
		
		// Computo de Ri
		for (int i = 1; i <= cant; i++) {
			r.add(i-1, ( randomNumber + i - 1 ) / cant);
		}
		
		// Computo de aptitudes y aptitud total
		for ( int i = 0; i < this.size; i++ ) {
			//aptitud.add(i, population.get(i).getFitness());
			aptitud.add(i, this.fitness(population.get(i)) );
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
		
		
		for( int i=0 ; i < r.size() ; i++ ){
			for( int j=0; j< accumulatedAptitud.size() ; j++ )
				if( r.get(i) < accumulatedAptitud.get(j) ){					
					resp.add( i, (Individual)population.get(j).clone() );
					break;
				}					
		}
		return resp;*/
	}

	public ArrayList<Individual> replacement ( int cant ) {
		ArrayList<Individual> newPopulation = this.selection(cant);
		this.setPopulation(newPopulation);
		
		return newPopulation;		
	}
	
	public void addIndividual (Individual individual) {
		this.population.add(individual);
		this.size++;
	}
	public double fitness( Individual individual){
		double sum = 0;
		//System.out.println("POPULATION , COMMON FITHNESS");
		for ( int i = 0; i < individual.getSize(); i++ ) {
			if ( individual.getChromosome().get(i) )
				sum++;
		}
		
		return sum;
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
