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
		System.out.println(this);
		
		if (this.getLeft() != null) {
			this.getLeft().printPreorder();
		}

		if (this.getRight() != null) {
			this.getRight().printPreorder();
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
	
}




