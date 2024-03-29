import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class SyntaxTree {

	/* Nodos terminales y su valor */
	private Map<String, Boolean> terminalSet;
	/* Nombres de nodos terminales */
	private ArrayList<String> auxTerminalSet;
	/* Nodos funcion y su aridad */
	private Map<String, Integer> functionSet;
	/* Nombres de nodos funcion */
	private ArrayList<String> auxFunctionSet;
	/* Aridades de funciones */
	private ArrayList<Integer> arity;
	/* Valores de nodos terminales */
	private ArrayList<Boolean> terminalValues;
	/* Generador de numeros aleatorios */
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

	// Metodos usados para generar als arboles: 0 = full, 1 = grow
	public Node getRandomTree(int maxD, int method) {
		//double terminalProb = (double)terminalSet.size() / (terminalSet.size() + functionSet.size());
		double terminalProb = 0.5;
		if ( maxD == 0 || ( method == 1 && randomGenerator.nextFloat() < terminalProb ) ) {
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

	private String chooseRandomElement(ArrayList<String> elements) {
		return elements.get(randomGenerator.nextInt(elements.size()));
	}

	private int getArity(String function) {
		return functionSet.get(function);
	}
	
	public boolean getTerminalValue(String terminal) {
		return terminalSet.get(terminal);
	}
	
	
	public Node mutate(double pMut, int maxH, Node originalTree) {
		
		if ( pMut > randomGenerator.nextDouble() ) {
			return getRandomTree(randomGenerator.nextInt(maxH+1),randomGenerator.nextInt(2));

		}
		else {
			if ( originalTree.getLeft() != null ) {
				((FunctionNode)originalTree).left = mutate(pMut, maxH, originalTree.getLeft());
			}
			
			if ( originalTree.getRight() != null ) {
				((FunctionNode)originalTree).right = mutate(pMut, maxH, originalTree.getRight());
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
			/*
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
			*/
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
			/*
			System.out.println("===> random number for p1 = " + randomNodeNumberParentT1);
			System.out.println("===> random number for p2 = " + randomNodeNumberParentT2);
			*/
			//END DEBUG
			
			//Intercambio los subarboles segun los nodos elegidos
			int dir1 = nodesReferenceArrayPT1.get(randomNodeNumberParentT1).getChildDirection();
			Node n1 = nodesReferenceArrayPT1.get(randomNodeNumberParentT1).getNode();
			int dir2 = nodesReferenceArrayPT2.get(randomNodeNumberParentT2).getChildDirection();
			Node n2 = nodesReferenceArrayPT2.get(randomNodeNumberParentT2).getNode();
			//System.out.println("*** SELECTED NODES IN REF ARRAY: <1>=("+n1+", "+dir1+")\t<2>=("+n2+", "+dir2+")");//DEBUG
			
			Node auxNode1 = (dir1==0)?n1.getLeft():n1.getRight();
			Node auxNode2 = (dir2==0)?n2.getLeft():n2.getRight();
			//System.out.println("NODES TO SWAP:"+"\t1="+auxNode1+", 2="+auxNode2);//DEBUG
			
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
	
}
