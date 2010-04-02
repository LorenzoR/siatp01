public abstract class Node {

	public Node tree;

	public abstract boolean value();
	
	public abstract void printValue();
	
	public Node getLeft() {
		return tree.getLeft();
	}

	public Node getRight() {
		return tree.getRight();
	}

	public void printPreorder() {
		this.printValue();
		
		if (this.getLeft() != null) {
			this.getLeft().printPreorder();
		}

		if (this.getRight() != null) {
			this.getRight().printPreorder();
		}

	}
	
	
}




