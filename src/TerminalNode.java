
public class TerminalNode extends Node {

		private boolean value;
		private String name;
		
		public TerminalNode(String name, boolean value) {
			this.value = value;
			this.name = name;
			super.marca = -1;
		}

		public void setValue(String name, boolean value) {
			this.value = value;
			this.name = name;
		}

		public Node getRight() {
			return null;
		}

		public Node getLeft() {
			return null;
		}
		
		public boolean value() {
			return this.value;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void printValue() {
			System.out.println(name + ": " + value);
		}

		/*public void setValue(Node tree) {
			//super.tree = tree;
			//System.out.println("El nuevo valor es " + tree);
			this.value = ((TerminalNode) tree).value();
			this.name = ((TerminalNode) tree).getName();			
		}*/

		@Override
		public String toString() {
			return name + " (" + value + ")" + "(" + super.marca + ")";
		}
		
		public void setAnotherValue (FunctionNode node) {
			super.tree = node;
		}

		public int size() {
			return 1;
		}
	}