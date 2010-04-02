import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class SyntaxTree {

	private Map<String, Boolean> terminalSet;
	private ArrayList<String> auxTerminalSet;
	private Map<String, Integer> functionSet;
	private ArrayList<String> auxFunctionSet;
	private ArrayList<Integer> arity;
	private ArrayList<Boolean> terminalValues;
	private Random randomGenerator = new Random();
	
	public SyntaxTree(Map<String, Boolean> terminalSet, Map<String, Integer> functionSet) {
		Iterator<Entry<String, Integer>> iterator = functionSet.entrySet().iterator();
		this.auxFunctionSet = new ArrayList<String>();
		this.arity = new ArrayList<Integer>();

		while (iterator.hasNext()) {
			Entry<String, Integer> aux = iterator.next();
			this.auxFunctionSet.add(aux.getKey());
			this.arity.add(aux.getValue());
		}

		Iterator<Entry<String, Boolean>> iterator2 = terminalSet.entrySet().iterator();
		this.auxTerminalSet = new ArrayList<String>();
		this.terminalValues = new ArrayList<Boolean>();

		while (iterator2.hasNext()) {
			Entry<String, Boolean> aux2 = iterator2.next();
			this.auxTerminalSet.add(aux2.getKey());
			this.terminalValues.add(aux2.getValue());
		}
		
		this.terminalSet = terminalSet;
		this.functionSet = functionSet;
	}

	public Node getRandomTree(int maxD, int method) {

		if (maxD == 0 || ( method == 1 && randomGenerator.nextFloat() < (terminalSet.size() / (terminalSet.size() + functionSet.size()))) ) {
			String terminalString = chooseRandomElement(auxTerminalSet);
			TerminalNode nodo = new TerminalNode(terminalString, getTerminalValue(terminalString));
			return nodo;
		} else {
			String functionString = chooseRandomElement(auxFunctionSet);
			int arity = getArity(functionString);
			FunctionNode function = null;

			if (arity == 1) {
				function = new FunctionNode(functionString, getRandomTree(maxD - 1, method), null);
			} else if (arity == 2) {
				function = new FunctionNode(functionString, getRandomTree(maxD - 1, method), getRandomTree(maxD - 1, method));
			}

			return function;
		}

	}

	public String chooseRandomElement(ArrayList<String> elements) {
		return elements.get(randomGenerator.nextInt(elements.size()));
	}

	public int getArity(String function) {
		return functionSet.get(function);
	}
	
	public boolean getTerminalValue(String terminal) {
		return terminalSet.get(terminal);
	}

}
