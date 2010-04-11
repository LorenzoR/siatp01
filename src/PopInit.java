
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static java.lang.Math.*;

public class PopInit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Aqui se cargan todas las caracteristicas del sistema para
		// construir arboles que representan individuos.
		SyntaxTree st = SetearFabricaDeArboles();
		
		int maxCant = 20;
		int iniD = 2;
		int finD = 2;
		int cantD = 10;
		List<Node> pop = genPop1(st, iniD, finD, cantD, maxCant);
		//List<Node> pop = genPop2(st, cantD, maxCant);
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
				//Node t = st.getRandomTree(i, 0);
				// Ojo: se cambio a method=1 para testear metodo getRandomTree()
				Node t = st.getRandomTree(i, 1);
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
	
	
	public static List<Node> genPop2(SyntaxTree st, int cantD, int maxCant) {
		int cant = 0;
		List<Node> pop = new ArrayList<Node>();
		// Este arreglo tiene la cantidad maxima de arboles que se van a generar
		// para cada altura distinta
		int cantArboles = 100;
		int[] maxCantD = new int[cantArboles];
		// Formula que me devuelve la cantidad maxima de arboles distintos para
		// cada altura h: 3^( (2^h)-1 )*8^(2^h)
		// Para h=0 da 4
		// Para h=1 da 96
		// Para h=2 da 55.296
		// Para h=3 da 36.691.771.392 sobrepasa el contenido de un int
		
		// Creo el contenido del arreglo
		@SuppressWarnings("unused")
		int aux, auxCant = 0;
		for( int h=0; h < 3; h++ ) {
			// Tomo la mitad del maximo posible para generar arboles por h
			aux = (int) ( pow(3, pow(2,h)-1) * pow(8, pow(2,h)) ) / 2;
			maxCantD[h] = Math.min(cantD, aux);
			//auxCant += maxCantD[h];
			//System.out.println( "Para h = " + h + ": " + maxCantD[h]);
		}
		for( int h=3; h < cantArboles; h++ ) {
			maxCantD[h] = cantD;
			//auxCant += maxCantD[h];
			//System.out.println( "Para h = " + h + ": " + maxCantD[h]);
		}
		//System.out.println("Cantidad total de arboles de altura 0-99: " + auxCant);
		
		// Empiezo a generar la poblacion
		for( int i=0; i <= 9; i++ ) {
			//System.out.println("\nAltura: " + i + "\n" + "---------" + "\n");
			//System.out.println("\nMetodo: 0\n" + "---------" + "\n");
			for( int j=0; j < maxCantD[i]/2; j++ ) {
				if( cant == maxCant ) break;
				Node t = st.getRandomTree(i, 0);
				//t.printPreorder();
				cant++;
				pop.add(t);
			}
			//System.out.println("\nMetodo: 1\n" + "---------" + "\n");
			for( int j = maxCantD[i]/2; j < maxCantD[i]; j++ ) {
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
	
}
