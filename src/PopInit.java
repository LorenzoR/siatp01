
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PopInit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Aqui se cargan todas las caracteristicas del sistema para
		// construir arboles que representan individuos.
		SyntaxTree st = SetearFabricaDeArboles();
		
		int maxCant = 5;
		int iniD = 0;
		int finD = 9;
		int cantD = 2;
		List<Node> pop = genPop1(st, iniD, finD,cantD, maxCant);
		printPop(pop);
	}
	
	public static SyntaxTree SetearFabricaDeArboles() {
		Map<String, Integer> functionSet = new HashMap<String, Integer>();
		functionSet.put("AND", 2);
		functionSet.put("OR", 2);
		functionSet.put("NOT", 1);

		Map<String, Boolean> terminalSet = new HashMap<String, Boolean>();
		Random r = new Random();
		terminalSet.put("A0", r.nextBoolean());
		terminalSet.put("A1", r.nextBoolean());
		terminalSet.put("A2", r.nextBoolean());
		terminalSet.put("A3", r.nextBoolean());
		terminalSet.put("B0", r.nextBoolean());
		terminalSet.put("B1", r.nextBoolean());
		terminalSet.put("B2", r.nextBoolean());
		terminalSet.put("B3", r.nextBoolean());

		SyntaxTree st = new SyntaxTree(terminalSet, functionSet);
		
		return st;
	}
	public static void printPop(List<Node> pop) {
		//Iterator<Node> it = pop.iterator();
		for(Node t : pop) {
			t.printPreorder();
		}
	}
	
	// Construccion de una poblacion usando las alturas iniD a finD, para una
	// cantidad maxima de la poblacion de maxCant.  cantD indica la cantidad
	// de arboles que se genera por cada altura. Internamente usa metodos 0 y 1.
	public static List<Node> genPop1(SyntaxTree st, int iniD, int finD, int cantD, int maxCant) {
		int cant = 0;
		List<Node> pop = new ArrayList<Node>();
		for( int i=iniD; i <= finD; i++ ) {
			//System.out.println("\nAltura: " + i + "\n" + "---------" + "\n");
			//System.out.println("\nMetodo: 0\n" + "---------" + "\n");
			for( int j=0; j < cantD/2; j++ ) {
				if( cant == maxCant ) break;
				Node t = st.getRandomTree(i, 0);
				//t.printPreorder();
				cant++;
				pop.add(t);
			}
			//System.out.println("\nMetodo: 1\n" + "---------" + "\n");
			for( int j = cantD/2; j < cantD; j++ ) {
				if( cant == maxCant ) break;
				Node t = st.getRandomTree(i, 1);
				//t.printPreorder();
				cant++;
				pop.add(t);
			}
			if( cant == maxCant ) break;
		}
		return pop;
	}
	
	/*
	public static List<Node> genPop2(SyntaxTree st, int iniD, int finD, int cantD, int maxCant) {
		int cant = 0;
		List<Node> pop = new ArrayList<Node>();
		for( int i=iniD; i <= finD; i++ ) {
			//System.out.println("\nAltura: " + i + "\n" + "---------" + "\n");
			//System.out.println("\nMetodo: 0\n" + "---------" + "\n");
			for( int j=0; j < cantD/2; j++ ) {
				if( cant == maxCant ) break;
				Node t = st.getRandomTree(i, 0);
				//t.printPreorder();
				cant++;
				pop.add(t);
			}
			//System.out.println("\nMetodo: 1\n" + "---------" + "\n");
			for( int j = cantD/2; j < cantD; j++ ) {
				if( cant == maxCant ) break;
				Node t = st.getRandomTree(i, 1);
				//t.printPreorder();
				cant++;
				pop.add(t);
			}
			if( cant == maxCant ) break;
		}
		return pop;
	}
	*/
}
