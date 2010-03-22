
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Statistics {
	private ArrayList<Subject> subjects;
	private int total;	
	
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
}
