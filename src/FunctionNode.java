public class FunctionNode extends Node implements Cloneable {

	public String function;
	public Node left;
	public Node right;

	public FunctionNode(String function, Node left, Node right) {
		this.function = function;
		this.left = left;
		this.right = right;
		super.marca = -1;
	}

	public void setLeft(Node left) {
		this.left = left;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public Node getRight() {
		return this.right;
	}

	public Node getLeft() {
		return this.left;
	}

	public void setValue(String function) {
		this.function = function;
		super.tree = this;
	}

	public String getFunction() {
		return this.function;
	}
	
	public boolean value() {

		if (function.equalsIgnoreCase("AND")) {
			return left.value() & right.value();
		} else if (function.equalsIgnoreCase("OR")) {
			return left.value() | right.value();
		} else if (function.equalsIgnoreCase("NOT")) {
			if (left == null) {
				return !right.value();
			} else if (right == null) {
				return !left.value();
			}
		}

		return false;
	}
	
	public void printValue() {
		System.out.println(function);
	}

	/*public void setValue(Node tree) {
		//super.tree = tree;
		this.function = ((FunctionNode) tree).getFunction();
		this.left = tree.getLeft();
		this.right = tree.getRight();
		
	}*/

	@Override
	public String toString() {
		return this.function + "(" + super.marca + ")";
	}
	
	public int size() {

		if ( right == null && left == null ) {
			return 0;
		}
		else {
			if ( this.getRight() != null && this.getLeft() != null ) {
				return this.getRight().size() + 1 + this.getLeft().size();
			}
			else if ( this.getRight() != null ){
				return this.getRight().size() + 1;
			}
			else {
				return this.getLeft().size() + 1;
			}
		}

	}
	
}
