

public class NodeReference {
	Node node;
	int childDirection;
	public NodeReference( Node node, int childDirection){
		this.node = node;
		this.childDirection = childDirection;
	}
	public Node getNode(){
		return node;
	}
	public int getChildDirection(){
		return childDirection;
	}
	public String ToString(){
		StringBuffer resp = new StringBuffer();
		resp.append("Child dir = " + childDirection + "Stored Node =\n" );
		//node.printPreorder();
			
		return resp.toString();		
	}
}
