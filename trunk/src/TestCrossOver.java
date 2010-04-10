import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;




public class TestCrossOver {
	
	public static void main(String[] args) {
		
		Map<String, Integer> functionSet = new HashMap<String, Integer>();
		functionSet.put("AND", 2);
		functionSet.put("OR", 2);
		functionSet.put("NOT", 1);
		
		Map<String, Boolean> terminalSet = new HashMap<String, Boolean>();
		Random randomGenerator = new Random();
		terminalSet.put("A0", randomGenerator.nextBoolean());
		terminalSet.put("A1", randomGenerator.nextBoolean());
		terminalSet.put("A2", randomGenerator.nextBoolean());
		terminalSet.put("A3", randomGenerator.nextBoolean());
		terminalSet.put("B0", randomGenerator.nextBoolean());
		terminalSet.put("B1", randomGenerator.nextBoolean());
		terminalSet.put("B2", randomGenerator.nextBoolean());
		terminalSet.put("B3", randomGenerator.nextBoolean());
		
		SyntaxTree st = new SyntaxTree(terminalSet, functionSet);
		
		Node parent1 = st.getRandomTree(2, 0);				
		System.out.println("*****parent1 es *******");
		parent1.printPreorder();		
		
		Node parent2 = st.getRandomTree(2, 0);	
		System.out.println("*****parent2 es *******");
		parent2.printPreorder();
		
		ArrayList<Node> offspring = st.crossOver(parent1, parent2);		
		for( int i=0 ; i<offspring.size(); i++){
			System.out.println("*****Offspring "+i+" es *******");
			Node child = offspring.get(i);
			child.printPreorder();
		}
		System.out.println("*****AFTER CROSSOVER parent1 es *******");
		parent1.printPreorder();
		
		System.out.println("*****AFTER CROSSOVER parent2 es *******");
		parent2.printPreorder();
		
	}

}
