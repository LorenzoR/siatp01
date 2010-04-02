public class FunctionNode extends Node {

	public String function;
	public Node left;
	public Node right;

	public FunctionNode(String function, Node left, Node right) {
		this.function = function;
		this.left = left;
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
	
}
