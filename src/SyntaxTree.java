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
	
	public ArrayList<Node> crossOver(double pMut, Node parentT1, Node parentT2){
		ArrayList<Node>offspring = new ArrayList<Node>(2);
		ArrayList<NodeReference> nodesReferenceArrayPT1 = new ArrayList<NodeReference>();
		ArrayList<NodeReference> nodesReferenceArrayPT2 = new ArrayList<NodeReference>();
		//Si uno de los padres es un TerminalNode no lo puedo cruzar porque
		//no tengo la referencia a la referencia del objeto			
		if (parentT1 instanceof TerminalNode || parentT2 instanceof TerminalNode) {
			offspring.add(parentT2);
			offspring.add(parentT1);			
		}else{
			//El primer elemento corresponde a la raiz, que no se puede cambiar
			nodesReferenceArrayPT1.add(null);
			nodesReferenceArrayPT2.add(null);
			
			//Tengo que clonar a los padres, ya que los hijos seran individuos
			//diferentes
			Node parentClone1=null;
			Node parentClone2=null;
			try {
				parentClone1 = (Node) parentT1.clone();
				parentClone2 = (Node) parentT2.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			
			
			//Obtengo las referencias a todos los nodos (excepto el raiz). 
			//Estas referencias estan indexadas por orden del ArrayList
			getNodesReference( parentClone1, nodesReferenceArrayPT1);
			getNodesReference( parentClone2, nodesReferenceArrayPT2);
			
			//Elijo al azar un nodo en base a los indices posibles
			int randomNodeNumberParentT1 = 0;
			while( randomNodeNumberParentT1 == 0){
				randomNodeNumberParentT1 = randomGenerator.nextInt(nodesReferenceArrayPT1.size());
			}
			int randomNodeNumberParentT2 = 0;
			while( randomNodeNumberParentT2 == 0){
				randomNodeNumberParentT2 = randomGenerator.nextInt(nodesReferenceArrayPT2.size());
			}
			
			System.out.println("===> random number for p1 = " + randomNodeNumberParentT1);
			System.out.println("===> random number for p2 = " + randomNodeNumberParentT2);
			
			System.out.println("Size del arr ref 1 = " + nodesReferenceArrayPT1.size());
			if( nodesReferenceArrayPT1.size() > 0 ){
				for(int i=0 ; i<nodesReferenceArrayPT1.size();i++)
					System.out.println("index="+i+", Node = "+nodesReferenceArrayPT1.get(i).node);
			}
			
			System.out.println("=============> Selected node P1 =" + nodesReferenceArrayPT1.get(randomNodeNumberParentT1).node);
			System.out.println("=============> Selected node P2 =" + nodesReferenceArrayPT2.get(randomNodeNumberParentT2).node);
			//Intercambio los subarboles segun los nodos elegidos
			int dir1 = nodesReferenceArrayPT1.get(randomNodeNumberParentT1).getChildDirection();
			Node n1 = nodesReferenceArrayPT1.get(randomNodeNumberParentT1).getNode();
			int dir2 = nodesReferenceArrayPT1.get(randomNodeNumberParentT2).getChildDirection();
			Node n2 = nodesReferenceArrayPT1.get(randomNodeNumberParentT2).getNode();
			
			Node auxNode1 = (dir1==0)?n1.getLeft():n1.getRight();
			Node auxNode2 = (dir2==0)?n2.getLeft():n2.getRight();
			
			FunctionNode fn1 = (FunctionNode)n1;
			FunctionNode fn2 = (FunctionNode)n2;
			if( dir1 == 0)
				fn1.setLeft(auxNode2);
			else
				fn1.setRight(auxNode2);
			
			if( dir2 == 0 )
				fn2.setLeft(auxNode1);
			else
				fn2.setRight(auxNode1);
		} 		
		return offspring;
	}
	
	private void getNodesReference( Node node, ArrayList<NodeReference>refArray ){
		if (node instanceof TerminalNode) {			
			return;
		}
		else{
			if( node.getLeft() != null ){
				refArray.add(new NodeReference(node,0));
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
