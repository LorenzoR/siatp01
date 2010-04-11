public abstract class Node implements Cloneable{

	public Node tree;
	public int marca;
	
	public abstract boolean value();
	
	public abstract void printValue();
	
	public abstract String toString();
	
	public abstract int size();
	
	public abstract boolean isTerminalNode();
	
	public abstract boolean isFunctionNode();
	
	public void setValue(Node tree) {
		this.tree = tree;
	}
	
	public Node getLeft() {
		return tree.getLeft();
	}

	public Node getRight() {
		return tree.getRight();
	}
	
	public void printPreorder() {
		//this.printValue();
		printPreorderAux(0);
	}
		
	private void printPreorderAux(int depth) {
		for( int i=0; i < depth; i++ )
			System.out.print("    ");
		System.out.println(this);
		
		if (this.getLeft() != null) {
			this.getLeft().printPreorderAux(depth+1);
		}

		if (this.getRight() != null) {
			this.getRight().printPreorderAux(depth+1);
		}
	}
	
	public Object clone() throws CloneNotSupportedException{
        Node obj=null;
        try{
            obj=(Node)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(ex.getMessage());
        }       
        return obj;
    }
	public boolean equals( Object obj){		
		if( obj == null || !(obj instanceof Node ) )
			return false;
		
		Node n = (Node)obj;
		
		if( n instanceof TerminalNode )
			return ((TerminalNode)n).equals(this);
		else
			return ((FunctionNode)n).equals(this); 
	}
}




