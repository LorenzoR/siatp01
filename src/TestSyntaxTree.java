import java.util.HashMap;
import java.util.Map;
import java.util.Random;




public class TestSyntaxTree {
	
	public static void main(String[] args) {
		
		/*Node n1 = new TerminalNode(false);
		Node n2 = new TerminalNode(false);
		Node n3 = new FunctionNode("OR", n1, n2);
		Node n4 = new TerminalNode(false);
		Node n5 = new TerminalNode(true);
		Node n6 = new FunctionNode("AND", n4, n5);
		Node n7 = new FunctionNode("OR", n6, n3);
		
		n7.printPreorder();
		
		System.out.println("Evaluacion: " + n7.value());
		*/
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
		
		Node n9 = st.getRandomTree(3, 1);
		
		n9.printPreorder();
		
		System.out.println(n9.value());
		
	}

}
