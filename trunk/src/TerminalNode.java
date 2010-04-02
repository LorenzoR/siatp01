
public class TerminalNode extends Node {

		private boolean value;
		private String name;
		
		public TerminalNode(String name, boolean value) {
			this.value = value;
			this.name = name;
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
	}