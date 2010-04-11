import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class TestIndividual {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
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
		*/
		FnImplement myFunctions = new FnImplement();
		
		SyntaxTree st = myFunctions.setSyntaxTree(FnInterface.terminals, FnInterface.functions, FnInterface.arities);
		Fitness f = myFunctions.setFitnessFunction(FnInterface.terminals);
		Individual individual = new Individual(st, 0, f, 0);
		System.out.println(individual);		
		System.out.println("=====>ARBOL:");
		individual.getChromosome().printPreorder();

	}

}
