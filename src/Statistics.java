
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Statistics {
	private ArrayList<Subject> subjects;
	private int total;	
	private double avgFitness;
	private double avgCountNodes;
	//private double avgHeight;
	private int maxFitness;
	private Node maxNode;
	
	public void setPopulation( Population population){
		subjects = new ArrayList<Subject>();
		total = population.size();
		for( int i=0 ; i<population.size() ; i++ ){
			Individual ind = population.getIndividualsArray().get(i);
			Subject sub = new Subject(ind , 1 );
			int index  = subjects.indexOf(sub);
			if( index == -1 )
				subjects.add(sub);
			else
				subjects.get(index).incrementFrequency();	
		}
		
		avgFitness = 0;
		maxFitness = 0;
		avgCountNodes = 0;
		int dim = subjects.size();
		for( int i=0 ; i< dim ; i++ ){
			int currentFitness = subjects.get(i).getIndividual().fitnessValue(); 
			if( currentFitness > maxFitness ){
				maxFitness = currentFitness;
				maxNode = subjects.get(i).getIndividual().getChromosome();
			}				
			if( FnInterface.SHOW_AVG_FITNESS )
				avgFitness += currentFitness;
			if( FnInterface.SHOW_AVG_COUNT_NODES )
				avgCountNodes += subjects.get(i).getIndividual().getChromosome().size();
		}
		avgFitness = avgFitness/dim;
		avgCountNodes = avgCountNodes/dim;
			
			
	}
	@SuppressWarnings("unchecked")
	public double getMostFrequentSubject(Subject resultSubject){
		double percentage; 
		List list = Collections.synchronizedList(subjects); 
        Collections.sort(list);
        //System.out.println("Ordered List = \n" + list);
        Subject sub = (Subject)list.get(list.size()-1);
        percentage =  ((double)sub.getFrequency())/(double)total ;
        //System.out.println(percentage);
        
        resultSubject.setFrequency(sub.getFrequency());
        resultSubject.setIndividual(sub.getIndividual());			
		
        return percentage;
	}
	
	public double getPopulationAvgFitness(){
		return avgFitness;
		/*
		double sum = 0;
		for( int i=0 ; i< subjects.size() ; i++ ){
			sum += subjects.get(i).getIndividual().fitnessValue();
		}
		return sum/subjects.size();
		*/
	}
	
	public int getBestIndividualFitness(){
		return maxFitness;
		/*
		int maxFitness = 0;	
		for( int i=0 ; i< subjects.size() ; i++ ){
			int currentFitness = subjects.get(i).getIndividual().fitnessValue(); 
			if( currentFitness > maxFitness )
				maxFitness = currentFitness;
		}
		return maxFitness;
		*/
	}
	public double getPopulationAvgCountNodes(){
		return avgCountNodes;
		/*
		double sum = 0;
		for( int i=0 ; i< subjects.size() ; i++ ){
			sum += subjects.get(i).getIndividual().getChromosome().size();
		}
		return sum/subjects.size();
		*/
	}
	public Node getMaxFitnessNode(){
		return maxNode; 
	}
	
}
