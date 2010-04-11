import java.util.ArrayList;
import java.util.Random;

public class Individual implements Cloneable{
	private Node chromosome;	
	private SyntaxTree stGenerator;
	private int maxHeight;
	private int fitnessValue;
	private Fitness f;
	
	public Individual(SyntaxTree st, int maxHeight, Fitness f, int outputBit) {
		Random random = new Random();
		stGenerator = st;
		this.maxHeight = maxHeight;
		this.chromosome = st.getRandomTree(random.nextInt(maxHeight+1), random.nextInt(2));
		this.fitnessValue = f.fitnessValue(chromosome, outputBit);
		this.f = f;
	}

	public Individual(SyntaxTree st, Node otherChromosome, Fitness f, int outputBit) {
		stGenerator = st;
		maxHeight = FnInterface.MAX_HEIGHT;
		this.f = f;
		this.setChromosome(otherChromosome);
		this.fitnessValue = f.fitnessValue(chromosome, outputBit);
	}
	
	public void setChromosome(Node otherChromosome) {			
		try {
			chromosome = (Node)otherChromosome.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			System.out.println("Individual: "+e.getMessage());
		}
	}
		
	public Node getChromosome() {
		return this.chromosome;
	}
	
	public void print() {
		chromosome.printPreorder();
		System.out.print("\n");

	}
	/*Para poder usar println() en forma transparente*/
	public String toString() {
		StringBuffer resp = new StringBuffer();		
		resp.append("Node\n" + chromosome.toString());
		return resp.toString();		
	}
	
	/*Para poder hacer copias de los individuos en caso un individuo sea seleccionado 2 veces
	 * Es decir en ese caso deben ser DOS INDIVIDUOS DIFERENTES */
	@SuppressWarnings("unchecked")
	public Object clone(){
        Individual obj=null;
        try{
            obj=(Individual)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println("No se puede duplicar");
        }        
        try {
			obj.chromosome = (Node)obj.chromosome.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return obj;
    }
	
	public void mutate(double pMut, int outputBit) {
		chromosome = stGenerator.mutate(pMut, maxHeight, chromosome);
		fitnessValue = f.fitnessValue(chromosome, outputBit);
	}

	public boolean equals( Object obj){		
		if( obj == null || !(obj instanceof Individual ) )
			return false;
		return true;
	}
	
	public ArrayList<Individual> crossOver(Individual individual, int outputBit) {				
		ArrayList<Individual> children = new ArrayList<Individual>(2);
		
		ArrayList<Node> nodesOffspring = stGenerator.crossOver(this.chromosome, individual.getChromosome() );
		children.add(0, new Individual(stGenerator, nodesOffspring.get(0), f, outputBit));
		children.add(1, new Individual(stGenerator, nodesOffspring.get(1), f, outputBit));
		return children;
	}
	
	public int fitnessValue (){
		return fitnessValue;
	}
	
}
