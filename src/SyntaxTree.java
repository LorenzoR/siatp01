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
	private Map<String, Integer> terminalMask;
	
	
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
	
	
	public Node mutate(double pMut, Node originalTree) {
		
		if ( pMut > randomGenerator.nextDouble() ) {
			//System.out.println("------Muto- en " + originalTree);
			return getRandomTree(2,0);

		}
		else {
			if ( originalTree.getLeft() != null ) {
				((FunctionNode)originalTree).left = mutate(pMut, originalTree.getLeft());
			}
			
			if ( originalTree.getRight() != null ) {
				((FunctionNode)originalTree).right = mutate(pMut, originalTree.getRight());
			}		
		}
		
		return originalTree;
	}
	
	public ArrayList<Node> crossOver(Node parentT1, Node parentT2){
		ArrayList<Node>offspring = new ArrayList<Node>(2);
		ArrayList<NodeReference> nodesReferenceArrayPT1 = new ArrayList<NodeReference>();
		ArrayList<NodeReference> nodesReferenceArrayPT2 = new ArrayList<NodeReference>();
		
		//Si uno de los padres es un TerminalNode no lo puedo cruzar porque
		//no tengo la referencia, a la referencia del objeto			
		if (parentT1 instanceof TerminalNode || parentT2 instanceof TerminalNode) {
			try {
				offspring.add((Node) parentT2.clone());
				offspring.add((Node) parentT1.clone());	
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}					
		}else{
			//El primer elemento corresponde a la raiz, que no se puede cambiar
			nodesReferenceArrayPT1.add(null);
			nodesReferenceArrayPT2.add(null);
			
			//Los hijos deben ser individuos diferentes
			Node parentClone1=null;
			Node parentClone2=null;
			try {
				parentClone1 = (Node) parentT1.clone();
				parentClone2 = (Node) parentT2.clone();
				offspring.add(parentClone1);
				offspring.add(parentClone2);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}			
			
			//Obtengo las referencias a todos los nodos (excepto el raiz). 
			//Estas referencias estan indexadas por orden del ArrayList
			getNodesReference( parentClone1, nodesReferenceArrayPT1);
			getNodesReference( parentClone2, nodesReferenceArrayPT2);
			
			//DEBUG
			System.out.println("Reference array 1 SIZE = " + nodesReferenceArrayPT1.size());			
			for(int i=1 ; i<nodesReferenceArrayPT1.size();i++){
				System.out.println("index="+i+", Node = "+nodesReferenceArrayPT1.get(i).node + ", Dir = " + nodesReferenceArrayPT1.get(i).childDirection);
			}			
			System.out.println("END");						
			System.out.println("Reference array 2 SIZE = " + nodesReferenceArrayPT2.size());
			if( nodesReferenceArrayPT2.size() > 0 ){
				for(int i=1 ; i<nodesReferenceArrayPT2.size();i++){
					System.out.println("index="+i+", Node = "+nodesReferenceArrayPT2.get(i).node + ", Dir = " + nodesReferenceArrayPT2.get(i).childDirection);
				}
			}
			System.out.println("END");
			//END DEBUG
			
			//Elijo al azar un nodo en base a la cantidad de nodos del arbol
			int randomNodeNumberParentT1 = 0;
			while( randomNodeNumberParentT1 == 0){
				randomNodeNumberParentT1 = randomGenerator.nextInt(nodesReferenceArrayPT1.size());
			}
			int randomNodeNumberParentT2 = 0;
			while( randomNodeNumberParentT2 == 0){
				randomNodeNumberParentT2 = randomGenerator.nextInt(nodesReferenceArrayPT2.size());
			}
			//DEBUG
			System.out.println("===> random number for p1 = " + randomNodeNumberParentT1);
			System.out.println("===> random number for p2 = " + randomNodeNumberParentT2);
			//END DEBUG
			
			//Intercambio los subarboles segun los nodos elegidos
			int dir1 = nodesReferenceArrayPT1.get(randomNodeNumberParentT1).getChildDirection();
			Node n1 = nodesReferenceArrayPT1.get(randomNodeNumberParentT1).getNode();
			int dir2 = nodesReferenceArrayPT2.get(randomNodeNumberParentT2).getChildDirection();
			Node n2 = nodesReferenceArrayPT2.get(randomNodeNumberParentT2).getNode();
			System.out.println("*** SELECTED NODES IN REF ARRAY: <1>=("+n1+", "+dir1+")\t<2>=("+n2+", "+dir2+")");//DEBUG
			
			Node auxNode1 = (dir1==0)?n1.getLeft():n1.getRight();
			Node auxNode2 = (dir2==0)?n2.getLeft():n2.getRight();
			System.out.println("NODES TO SWAP:"+"\t1="+auxNode1+", 2="+auxNode2);//DEBUG
			
			if( dir1 == 0)
				((FunctionNode)n1).setLeft(auxNode2);
			else
				((FunctionNode)n1).setRight(auxNode2);
			
			if( dir2 == 0 )
				((FunctionNode)n2).setLeft(auxNode1);
			else
				((FunctionNode)n2).setRight(auxNode1);
				
		}		
		return offspring;
	}
	
	private void getNodesReference( Node node, ArrayList<NodeReference>refArray ){
		if (node instanceof TerminalNode) {			
			return;
		}
		else{
			if( node.getLeft() != null ){
				refArray.add(new NodeReference(node, 0));				
				getNodesReference(node.getLeft(), refArray);
			}
			if( node.getRight() != null ){
				refArray.add( new NodeReference(node, 1));
				getNodesReference(node.getRight(), refArray);
			}		 
		}
	}

	
	// Setea las mascaras de las entradas (posiciones de los bits de entrada).
	public void setTerminalMask(Map<String, Integer> terminalMask) {
		this.terminalMask = terminalMask;
	}
	
	// Setea valores de los terminales del arbol usando num. Se usa en la fun de fitness.
	// Necesita que se haya usado previamente setTerminalMask.
	public void setAllTerminalValues(Node t, int num) {
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

	// Calcula los valores de salida del circuito buscado, para cada entrada de la tabla.
	// entradaTabla = A3 A2 A1 A0 B3 B2 B1 B0 (de 0 a 255)
	// 4 bits por entrada (A y B)
	// colsalida elige una de las 7 salidas: 0..6
	public boolean valorTabla(int entradaTabla, int bitsPorEntrada, int colSalida) {
		int a, b, rpta;
		int divisor = 1 << bitsPorEntrada;
		
		a = entradaTabla / divisor;
		b = entradaTabla % divisor;
		
		// Muevo el bit que me interesa a la posicion colSalida
		rpta = (a*10 + b) >> colSalida;
		// Obtengo dicho bit en rpta
		rpta %= 2;
		System.out.println("De tabla: " + Util.toBoolean(rpta));
		return Util.toBoolean(rpta);
	}
	
	// Funcion de fitness.
	public int fitness(Node t, int bitsPorEntrada, int colSalida) { 
		int valorAptitud = 0;
		System.out.println("Arbol original (random):");
		t.printPreorder();
		
		int cantEntradasTabla = 1 << (bitsPorEntrada*2);
		for( int i=0; i < cantEntradasTabla; i++ ) {
			// Seteo valores de los terminales del arbol construido para cada i
			setAllTerminalValues(t, i);
			System.out.println("Arbol con valores seteados:");
			t.printPreorder();
			boolean evaluacionArbol = t.value();
			System.out.println("Evaluacion arbol con entrada " + i + ": " + evaluacionArbol);
			if( evaluacionArbol == valorTabla(i, bitsPorEntrada, colSalida) ) {
				valorAptitud++;
				System.out.println("******Coincidencia******");
			}
			else
				System.out.println("************************");
		}
		System.out.println("Aptitud arbol: " + valorAptitud);
		return valorAptitud;
	}
	
}
