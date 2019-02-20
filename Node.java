public class Node {
	int key; // the key value//
	int degree;  //  the degree of the node//
	Node left;    //left brother node//
	Node right;   //right brother node//
	Node child;   //child node//
	Node Parent;  //parent node//
	boolean childcut; //Whether this node is marked//
	String hashtag;//hashtag the node store//
	
	public Node(int key,String hashtag){
		this.key=key;
		this.degree =0;		
		this.left=this;
		this.right=this;
		this.Parent=null;
		this.child=null;
		this.childcut=false;
		this.hashtag=hashtag;
	}	
}	
