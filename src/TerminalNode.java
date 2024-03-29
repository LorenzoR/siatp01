
public class TerminalNode extends Node implements Cloneable {

		private boolean value;
		private String name;
		
		public TerminalNode(String name, boolean value) {
			this.value = value;
			this.name = name;
		}

		public boolean isTerminalNode() {
			return true;
		}
		
		public boolean isFunctionNode() {
			return false;
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

		public Object clone(){
	       TerminalNode obj=null;
	        try{
	            obj=(TerminalNode)super.clone();
	        }catch(CloneNotSupportedException ex){
	            System.out.println("No se puede duplicar el Terminal Node");
	        }    
	        obj.name = new String(name);
	        return obj;
	    }
		@Override
		public String toString() {
			return name + " (" + value + ")";
		}
		
		public void setAnotherValue (FunctionNode node) {
			super.tree = node;
		}

		public int size() {
			return 1;
		}
		
		public boolean equals( Object obj){		
			if( obj == null || !(obj instanceof TerminalNode ) )
				return false;
			TerminalNode n = (TerminalNode)obj;
			return value == n.value()  &&  name.compareTo(n.getName()) == 0 ; 
		}
	}