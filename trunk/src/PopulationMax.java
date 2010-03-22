
public class PopulationMax extends Population {
	public PopulationMax(int size, int individualSize){
		super( size, individualSize);	
	}
	
	public double fitness( Individual individual ){		
		//System.out.println(" ======> Population MAX fitness function !!!!");
		return myFunctions.maxFn(individual.getChromosome());		
	}
}
