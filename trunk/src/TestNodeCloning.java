import java.util.HashMap;
import java.util.Map;
import java.util.Random;




public class TestNodeCloning {
	
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
		Node cloneParent1 = null;
		try {
			cloneParent1 = (Node)parent1.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			System.out.println("Test Cloning failed");
		}
		
		System.out.println("*****cloneParent1 es *******");
		cloneParent1.printPreorder();
		
		FunctionNode clone = (FunctionNode)cloneParent1;
		clone.setLeft(st.getRandomTree(2, 0));
		
		System.out.println("*****cloneParent1 MODIFICADO es *******");
		cloneParent1.printPreorder();
		
		System.out.println("*****parent1 AHORA es *******");
		parent1.printPreorder();
		
		
		
	}

}
