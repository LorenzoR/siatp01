
import java.util.Map;

public class Fitness {
	private int cantBitsEntrada;
	private int cantBitsSalida;
	private int cantEntradas;
	// Datos internos calculados
	private int cantEntradasTabla;
	private int divisor;
	private boolean[] tablaFitness;
	private Map<String, Integer> terminalMask;
	
	public Fitness(int cantBitsEntrada, int cantBitsSalida, int cantEntradas, Map<String, Integer> terminalMask) {
		this.cantBitsEntrada = cantBitsEntrada;
		this.cantBitsSalida = cantBitsSalida;
		this.cantEntradas = cantEntradas;
		cantEntradasTabla = 1 << cantBitsEntrada;
		divisor = 1 << (cantBitsEntrada/cantEntradas);
		setFitnessTable();
		// Setea la posicion de cada Terminal en una entrada de la tabla.
		// entradaTabla = A3 A2 A1 A0 B3 B2 B1 B0 (de 0 a 255)
		// mascara A0 =    0  0  0  1  0  0  0  0 (2^4 = 16)
		// Ej de Mapa para A0: ... <"A0", 16> ... 
		this.terminalMask = terminalMask;
	}
	
	// Crea la tabla de verdad del circuito: su salida para cada entrada posible
	// cantBitsEntrada = 8 (genera entradas 0..255)
	// cantBitsSalida = 7 (0..6)
	private void setFitnessTable() {
		tablaFitness = new boolean[cantEntradasTabla*cantBitsSalida];
		int a, b, salidaCircuito;
		// cantEntradasTabla = 256 (0..255), i tiene una entrada de la tabla
		// cantBitsSalida = 7 (0..6), j tiene un bit de salida
		for( int i=0; i < cantEntradasTabla; i++ ) {
			a = i / divisor;	// Entrada de A
			b = i % divisor;	// Entrada de B
			salidaCircuito = a*10 + b;
			//System.out.println("Valor de la tabla para entrada " + i + ": " + salidaCircuito);
			//System.out.print("Valor de la tabla para entrada " + i + " (binario al reves): ");
			for( int j=0, aux=salidaCircuito; j < cantBitsSalida; j++, aux >>= 1 ) {
				// De cada salida del circuito muevo el bit que me interesa a la
				// posicion 0 del entero aux para luego ingresarlo a la posicion
				// correcta del arreglo
				tablaFitness[i*cantBitsSalida+j] = Util.toBoolean(aux % 2);
				// Para debug
				//System.out.print(aux % 2);
			}
			// Para debug
			//System.out.println();
		}
	}	
	
	// Calcula los valores de salida del circuito buscado, para cada entrada de la tabla.
	// entradaTabla = A3 A2 A1 A0 B3 B2 B1 B0 (de 0 a 255)
	// 4 bits por entrada (A y B)
	// colsalida elige una de las 7 salidas: 0..6
	private boolean getTableValue(int entradaTabla, int colSalida) {
		return tablaFitness[entradaTabla*cantBitsSalida + colSalida];
	}
	
	// Funcion de fitness.
	// Requiere que se haya usado previamente setTerminalMask.
	public int fitnessValue(Node t, int colSalida) { 
		int valorAptitud = 0;
		//System.out.println("Arbol original (random):");
		//t.printPreorder();

		for( int i=0; i < cantEntradasTabla; i++ ) {
			// Seteo valores de los terminales del arbol construido para cada i
			setAllTerminalValues(t, i);
			//System.out.println("Arbol con valores seteados:");
			//t.printPreorder();
			boolean evaluacionArbol = t.value();
			//System.out.println("Evaluacion arbol con entrada " + i + ": " + evaluacionArbol);
			if( evaluacionArbol == getTableValue(i, colSalida) ) {
				valorAptitud++;
				//System.out.println("******Coincidencia******");
			}
			//else
				//System.out.println("************************");
		}
		//System.out.println("Aptitud arbol: " + valorAptitud);
		return valorAptitud;
	}
	
	// Setea valores de los terminales del arbol usando num.
	// Requiere que se haya usado previamente setTerminalMask.
	private void setAllTerminalValues(Node t, int num) {
		if( t == null)
			return;
		if ( t.isTerminalNode() ) {
			TerminalNode aux = (TerminalNode) t;
			String name = aux.getName();
			int mask = terminalMask.get(name);
			// Uso la mascara asociada al TerminalNode para elegir
			// el nuevo valor a setear en dicho nodo
			boolean value = Util.toBoolean(num & mask);
			aux.setValue(name, value);
		}
		else {
			setAllTerminalValues( t.getLeft(), num);
			setAllTerminalValues( t.getRight(), num);
		}
	}
	
}
