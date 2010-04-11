
public class TestNode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TerminalNode n1 = new TerminalNode("B1", false);
		TerminalNode n2 = new TerminalNode("A3", true);
		TerminalNode n3 = new TerminalNode("C0", true);
		TerminalNode n4 = new TerminalNode("B1", false);
		
		
		System.out.println("Terminal node n1 = " +  n1);
		System.out.println("Terminal node n2 = " +  n2);
		System.out.println("Terminal node n3 = " +  n3);
		FunctionNode fun2 = new FunctionNode("AND", n1, n2);
		FunctionNode fn1 = new FunctionNode("OR", n3, fun2);
		//System.out.println("Function node = \n" + fn1);
		
		System.out.println("=====> printpreorder fn1");
		fn1.printPreorder();
		
		
		FunctionNode fun3 = new FunctionNode("AND", n1, n2);
		FunctionNode fn2 = new FunctionNode("OR", n3, fun3);
		System.out.println("=====> printpreorder fn2");
		fn2.printPreorder();
		
		FunctionNode fun4 = new FunctionNode("OR", n1, n2);
		FunctionNode fn3 = new FunctionNode("OR", n3, fun4);
		System.out.println("=====> printpreorder fn3");
		fn3.printPreorder();
		
		FunctionNode fun5 = new FunctionNode("OR", n1, n2);
		FunctionNode fn4 = new FunctionNode("NOT", n3, null);
		FunctionNode fn5 = new FunctionNode("NOT", fun5, null);
		
		
		
		System.out.println("n1 eq n2? = " + n1.equals(n2));
		System.out.println("n1 eq n1? = " + n1.equals(n1));
		System.out.println("n1 eq n4? = " + n1.equals(n4));
		System.out.println("fn1 eq fn1? = " + fn1.equals(fn1));
		System.out.println("fn1 eq fn2? = " + fn1.equals(fn2));
		System.out.println("fn1 eq fun2? = " + fn1.equals(fun2));
		System.out.println("fn1 eq fn3? = " + fn1.equals(fn3));
		System.out.println("fn4 eq fn5? = " + fn4.equals(fn5));
		System.out.println("fn2 eq fn3? = " + fn2.equals(fn3));
		
		
	}

}
