
public class PopulationMin extends Population {
	
	public PopulationMin(int size, int individualSize){
		super( size, individualSize);	
	}
	
	public double fitness( Individual individual ){		
		//System.out.println(" ======> Population MIN fitness function !!!!");
		return myFunctions.minFn(individual.getChromosome());		
	}
}
