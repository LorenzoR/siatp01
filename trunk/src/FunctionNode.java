public class FunctionNode extends Node implements Cloneable {

	public String function;
	public Node left;
	public Node right;

	public FunctionNode(String function, Node left, Node right) {
		this.function = function;
		this.left = left;
		this.right = right;
	}

	public boolean isFunctionNode() {
		return true;
	}
	
	public boolean isTerminalNode() {
		return false;
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
	public Object clone(){
	       FunctionNode obj=null;
	        try{
	            obj=(FunctionNode)super.clone();
	        }catch(CloneNotSupportedException ex){
	            System.out.println("No se puede duplicar la superclase de Function Node");
	        }    
	        obj.function = new String(function);
	        try {
				obj.left = (Node)left.clone();
				if( right != null )
		        	obj.right = (Node)right.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				System.out.println("No se puede duplicar Function Node interamente");
			}
	        
	        return obj;
	    }
	@Override
	public String toString() {
		return this.function;
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
	
	public boolean equals( Object obj){
		boolean nodeResult, leftResult, rightResult;
		if( obj == null || !(obj instanceof FunctionNode ) )
			return false;
		
		FunctionNode n = (FunctionNode)obj;
		nodeResult = ( function.compareTo( n.getFunction() ) == 0 )?true:false;
		
		if( nodeResult == false)
			return false;
		
		if( this.getLeft() != null )
			leftResult = this.getLeft().equals(n.getLeft());
		else
			leftResult = (n.getLeft() == null)?true: false;
		
		if( leftResult == false )
			return false;
		
		if( this.getRight() != null )
			rightResult = this.getRight().equals(n.getRight());
		else
			rightResult = (n.getRight() == null)?true: false;
		
		return rightResult;
		 
	}

	
}
