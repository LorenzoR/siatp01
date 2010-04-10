

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
	public String toString(){
		StringBuffer resp = new StringBuffer();
		resp.append("Child dir = " + childDirection + "\tStored Node = " + node );
					
		return resp.toString();		
	}
}
