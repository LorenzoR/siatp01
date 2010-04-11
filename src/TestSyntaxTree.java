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
		
		/*TerminalNode auxNode = new TerminalNode("X", true);
		FunctionNode auxNode2 = new FunctionNode("WHY", null, null);
		
		System.out.println("AuxNode es " + auxNode);
		auxNode.setAnotherValue(auxNode2);
		System.out.println("AuxNode ahora es " + auxNode);
		
		
		System.out.print("auxNode es ");
		auxNode.printValue();
		
		Node n9 = st.getRandomTree(2, 0);
		System.out.println("***** N9 es: ******");
		n9.printPreorder();
		
		//Node n10 = st.getRandomTree(2,0);
		//System.out.println("***** N10 es: ******");
		//n10.printPreorder();
		//System.out.println("***** N10 left es: ******");
		//n10.getLeft().printPreorder();
		
		//Node n11;
		st.doMutate(n9, 0.2);
		System.out.println("****** N9 es: ******");
		n9.printPreorder();
		
		//n9.setValue(auxNode);
	//	System.out.println("******N9 mutado es: *******");
		//n9.printPreorder();
		
		//System.out.println(n9.value());*/
		
		Node n11 = st.getRandomTree(2, 0);
		//Node n12 = st.doMutate2(0.5, n11);
		//System.out.println("Cantidad nodos: " + n11.size());
		
		int maxH = 4;
		System.out.println("*****n11 es *******");
		n11.printPreorder();
		
		Node n12;
		n12 = st.mutate(0.5, maxH, n11);
		System.out.println("*****n12 ahora es *******");
		n12.printPreorder();
		
		
	}

}
