
@SuppressWarnings("unchecked")
public class Subject implements Comparable {
	private Individual individual;
	private int frequency;
	
	public Subject(Individual individual, int frequency){
		if( frequency < 0)
			this.frequency = 0;
		else
			this.frequency = frequency;
		this.individual = individual;
	}
	public Individual getIndividual(){
		return individual; 
	}
	public void setIndividual(Individual sub){
		individual = sub;
		return;
	}
	public int getFrequency(){
		return frequency;
	}
	
	public void setFrequency(int newFreq){
		if( newFreq < 0)
			frequency = 0;
		else
			frequency = newFreq;
		return;
	}
	public void incrementFrequency(){
		frequency++;
	}
	public boolean equals( Object obj){		
		if( obj == null || !(obj instanceof Subject ) )
			return false;
		
		Subject aux = (Subject)obj;
		Individual auxIndividual = aux.getIndividual();
		Node auxNode = auxIndividual.getChromosome();
		
		if( auxNode instanceof TerminalNode && individual.getChromosome() instanceof FunctionNode || auxNode instanceof FunctionNode && individual.getChromosome() instanceof TerminalNode )
			return false;
		
		return auxNode.equals(individual.getChromosome());		
	}
	
	public int compareTo(Object obj) {
		if( obj == null || !(obj instanceof Subject ) ){
			System.out.println("Object is not comparable to a Subject!!");
			return 0;
		}			
		Subject aux = (Subject)obj;
		int objFrequency = aux.getFrequency();
		if( frequency < objFrequency )
			return -1;
		else if( frequency > objFrequency )
			return 1;
		else
			return 0;
	}
	
	public String toString(){
		return "Individual = [" + individual.toString() + "]\tFrequency = " + frequency;
	}
}
