import java.util.ArrayList;
import java.util.Random;

public class Individual implements Cloneable{

	private ArrayList<Boolean> chromosome;
	private int size;
	private Random randomGenerator = new Random();

	public Individual(int size) {

		this.chromosome = new ArrayList<Boolean>(size);
		this.size = size;
		//Random randomGenerator = new Random();

		for (int i = 0; i < size; i++)
			this.chromosome.add(i, randomGenerator.nextBoolean());
	}

	public Individual(ArrayList<Boolean> chromosome) {
		this.size = chromosome.size();
		this.chromosome = chromosome;
	}

	public void setChromosome(ArrayList<Boolean> chromosome) {
		this.chromosome = chromosome;
		this.size = chromosome.size();
	}
	
	public boolean getChromosome(int index) {
		return this.chromosome.get(index);
	}
	public int getSize(){
		return this.size;
	}
	public ArrayList<Boolean> getChromosome() {
		return this.chromosome;
	}

	public void setChromosome(int index, boolean info) {
		this.chromosome.set(index, info);
	}

	public void print() {
		for (int i = 0; i < this.size; i++) {
			if ( this.chromosome.get(i) ) {
				System.out.print("1");
			}
			else {
				System.out.print("0");
			}
		}
		
		System.out.print("\n");

	}
	/*Para poder usar println() en forma transparente*/
	public String toString() {
		StringBuffer resp = new StringBuffer();
		
		for (int i = 0; i < this.size; i++) {
			if ( this.chromosome.get(i) ) {
				resp.append('1');
			}
			else {
				resp.append('0');
			}
		}
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
        obj.chromosome = (ArrayList<Boolean>)obj.chromosome.clone();
        return obj;
    }
	
	public void mutate(double pMut) {

		//Random randomGenerator = new Random();
		
		for (int i = 0; i < this.size; i++) {			
			if (randomGenerator.nextDouble() < pMut) {
				//System.out.println("\t  --> Mutation in bit " + i);
				this.chromosome.set(i, !this.chromosome.get(i));
			}

		}

	}

	public boolean equals( Object obj){		
		if( obj == null || !(obj instanceof Individual ) )
			return false;
		
		Individual aux = (Individual)obj;
				
		return size == aux.getSize() && chromosome.equals(aux.getChromosome());		
	}
	
	public ArrayList<Individual> crossOver(Individual individual) {

		//Random randomGenerator = new Random();
		int crossPoint = (int) Math.round(randomGenerator.nextDouble()
				* (this.size - 1));
		
		
		ArrayList<Individual> children = new ArrayList<Individual>(2);
		children.add(0, new Individual(this.getChromosome()));
		children.add(1, new Individual(individual.getChromosome()));
		boolean aux;

		//System.out.println("\t** Cross Point: " + crossPoint);

		for (int i = crossPoint + 1; i < this.size; i++) {
			aux = children.get(0).getChromosome(i);
			children.get(0).setChromosome(i, children.get(1).getChromosome(i));
			children.get(1).setChromosome(i, aux);
		}

		return children;

	}


}
