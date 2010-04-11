import java.util.ArrayList;
import java.util.Random;

public class Individual implements Cloneable{
	private Node chromosome;	
	private SyntaxTree stGenerator;
	private int maxHeight;
	
	public Individual(SyntaxTree st, int maxHeight) {
		Random random = new Random();
		stGenerator = st;
		this.maxHeight = maxHeight;
		this.chromosome = st.getRandomTree(random.nextInt(maxHeight), random.nextInt(2));
	}

	public Individual(SyntaxTree st, Node otherChromosome) {
		stGenerator = st;
		maxHeight = FnInterface.MAX_HEIGHT;
		this.setChromosome(otherChromosome);
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
	
	public void mutate(double pMut) {
		chromosome = stGenerator.mutate(pMut, maxHeight, chromosome);
	}

	public boolean equals( Object obj){		
		if( obj == null || !(obj instanceof Individual ) )
			return false;
		return true;
	}
	
	public ArrayList<Individual> crossOver(Individual individual) {				
		ArrayList<Individual> children = new ArrayList<Individual>(2);
		
		ArrayList<Node> nodesOffspring = stGenerator.crossOver(this.chromosome, individual.getChromosome() );
		children.add(0, new Individual(stGenerator, nodesOffspring.get(0)));
		children.add(1, new Individual(stGenerator, nodesOffspring.get(1)));
		return children;
	}
}
