public abstract class Node{

	public Node tree;
	public int marca;
	
	public abstract boolean value();
	
	public abstract void printValue();
	
	public abstract String toString();
	
	public abstract int size();
	
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
	
}




