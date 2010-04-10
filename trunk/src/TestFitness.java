import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestFitness {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestFitness tf = new TestFitness();
		// Aqui se cargan todas las caracteristicas del sistema para
		// construir arboles que representan individuos.
		SyntaxTree st = tf.SetearFabricaDeArboles();
		
		//Random r = new Random();
		//int maxD = r.nextInt(6) + 1;
		int maxD = 2;
		int method = 0;
		Node t = st.getRandomTree(maxD, method);
		int bitsPorEntrada = 1;		// 1 bit para A y 1 bit para B
		int colSalida = 0;			// Salida bit 0
		st.fitness(t, bitsPorEntrada, colSalida);
		
		/*
		TestFitness tf = new TestFitness();
		tf.test2(maxD, method);
		*/
		
		/*
		TestFitness tf = new TestFitness();
		tf.test1("xor");
		*/
		
		/*
		Node t = tf.construirArbol("AND", false, false);
		t.printPreorder();
		System.out.println("Evaluacion: " + t.value());
		*/
	}
	
	public SyntaxTree SetearFabricaDeArboles() {
		Map<String, Integer> functionSet = new HashMap<String, Integer>();
		functionSet.put("AND", 2);
		functionSet.put("OR", 2);
		functionSet.put("NOT", 1);

		Map<String, Boolean> terminalSet = new HashMap<String, Boolean>();
		Random r = new Random();
		terminalSet.put("A0", r.nextBoolean());
		//terminalSet.put("A1", r.nextBoolean());
		//terminalSet.put("A2", r.nextBoolean());
		//terminalSet.put("A3", r.nextBoolean());
		terminalSet.put("B0", r.nextBoolean());
		//terminalSet.put("B1", r.nextBoolean());
		//terminalSet.put("B2", r.nextBoolean());
		//terminalSet.put("B3", r.nextBoolean());

		SyntaxTree st = new SyntaxTree(terminalSet, functionSet);
		// Seteo las mascaras de las entradas (posiciones de los bits de entrada)
		Map<String, Integer> terminalMask = new HashMap<String, Integer>();
		/*
		terminalMask.put("A3", 128);	// bit7: 2^7
		terminalMask.put("A2", 64);
		terminalMask.put("A1", 32);
		terminalMask.put("A0", 16);
		terminalMask.put("B3", 8);
		terminalMask.put("B2", 4);
		terminalMask.put("B1", 2);
		*/
		terminalMask.put("A0", 2);
		terminalMask.put("B0", 1);		// bit0: 2^0
		st.setTerminalMask(terminalMask);
		
		return st;
	}
	
	/*
	public Node construirArbol(String fun, boolean a0, boolean b0) {
		Node n1 = new TerminalNode("A0", a0);
		Node n2 = new TerminalNode("B0", b0);
		Node n3 = new FunctionNode(fun, n1, n2);
		return n3;
	}
	*/
	
	public Node construirArbol(String fun, boolean a0, boolean b0) {
		if( fun.equalsIgnoreCase("XOR") ) {
			Node n1 = new TerminalNode("A0", a0);
			Node n2 = new TerminalNode("B0", b0);
			Node n3 = new FunctionNode("NOT", n1, null);
			Node n4 = new FunctionNode("NOT", n2, null);
			Node n5 = new FunctionNode("AND", n3, n2);
			Node n6 = new FunctionNode("AND", n1, n4);
			Node n7 = new FunctionNode("OR", n5, n6);
			return n7;
		}
		else if( fun.equalsIgnoreCase("NOT") ) {
			Node n1 = new TerminalNode("A0", a0);
			Node n2 = new FunctionNode(fun, n1, null);
			return n2;
		}
		else {
			Node n1 = new TerminalNode("A0", a0);
			Node n2 = new TerminalNode("B0", b0);
			Node n3 = new FunctionNode(fun, n1, n2);
			return n3;
		}
	}
	
	// usa tabla cableada
	public int test1(String fun) { 
		int valorAptitud = 0;
		for( int a=0; a < 2; a++ ) {
			for( int b=0; b < 2; b++ ) {
				Node t = construirArbol(fun, Util.toBoolean(a), Util.toBoolean(b));
				t.printPreorder();
				boolean valorCalculado = t.value();
				System.out.println("Evaluacion arbol " + a + b + ": " + valorCalculado);
				if( valorCalculado == (Util.toBoolean(a) | Util.toBoolean(b)) ) {
					valorAptitud++;
					System.out.println("Coincidencia");
				}
			}
		}
		System.out.println("Aptitud arbol: " + valorAptitud);
		return valorAptitud;
	}
	
	// usa tabla cableada
	public int test2(int maxD, int method) { 
		int valorAptitud = 0;
		SyntaxTree st = SetearFabricaDeArboles();
		Node t = st.getRandomTree(maxD, method);
		System.out.println("Arbol original (random):");
		t.printPreorder();
		for( int i=0; i < 4; i++ ) {
			//Node t = new TerminalNode("B0", toBoolean(i%2));
			// Seteo valores de los terminales del arbol construido
			st.setAllTerminalValues(t, i);
			System.out.println("Arbol con valores seteados:");
			t.printPreorder();
			boolean valorCalculado = t.value();
			System.out.println("Evaluacion arbol con entrada " + i + ": " + valorCalculado);
			if( valorCalculado == valorTabla(i, 1, 0) ) {
				valorAptitud++;
				System.out.println("******Coincidencia******");
			}
			else
				System.out.println("************************");
		}
		System.out.println("Aptitud arbol: " + valorAptitud);
		return valorAptitud;
	}
	
	// entradaTabla = A3 A2 A1 A0 B3 B2 B1 B0 (de 0 a 255)
	// 4 bits por entrada (A y B)
	// colsalida elige una de las 7 salidas: 0..6
	public boolean valorTabla(int entradaTabla, int bitsPorEntrada, int colSalida) {
		//return true;
		int a, b, rpta;
		int divisor = 1 << bitsPorEntrada;
		
		a = entradaTabla / divisor;
		b = entradaTabla % divisor;
		// Obtengo el bit menos significativo de B: B0
		//rpta = (a*10 + b) % 2;
		
		// Muevo el bit que me interesa a la posicion colSalida
		rpta = (a*10 + b) >> colSalida;
		// Obtengo dicho bit en rpta
		rpta %= 2;
		System.out.println("De tabla: B0 = " + Util.toBoolean(rpta));
		return Util.toBoolean(rpta);
	}

}
