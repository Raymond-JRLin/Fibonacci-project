import java.util.*;

public class Fibheap {
	public Node max; // the maximum value node//
	public int keynum; //the number of nodes in heap//  
	
	//initial the max Fibheap//
	public Fibheap(){
    	this.keynum=0; 
    	this.max=null;  
    }
	
	//add node like this figure//
    //   node1…… root//
    //   node1 …… node …… root//
	private void addNode(Node node, Node root) {		
	    node.left       = root.left;
	    root.left.right = node;
	    node.right      = root;
	    root.left       = node;
	}
	
	//insert operation and add new link in HashTable//
	public void insert(int key,String hashtag,Hashtable<String,Node> table1){	
		Node node= new Node(key,hashtag);
		// if there is no node in the heap//
		if (keynum==0){
			max=node;
		}
		if(max!=null){
		addNode(node,max);			
		if(node.key>max.key){
			max=node;
		}
		//put the new items in hashtable//
		table1.put(hashtag, node);
		keynum++;
		}	
	}
	
	//UnlinkNode operation//
	//this operation will unlink node and it's subtree from its siblings//
        //   node1 …… node …… root//
	//   node1…… root//	
	private void unlinkNode(Node node){
		node.left.right=node.right;
		node.right.left=node.left;
		}
		
	//cutNode operation//
	//cut the target from it's parent node//
		private void cutNode(Node node,Node parent){
			unlinkNode(node);
			parent.degree--;
		  //if parent has only one child/
			if(node.right==node){
				parent.child=null;
			}else{
				//make node's sibling parent's child//
				parent.child=node.right;
			}
			node.Parent=null;
			node.childcut=false;
			//move node to the root list//
			addNode(node,max);	
		}
		
	//cascading cut operation//
	private void CascadingCut(Node node){
		Node parent=node.Parent;
		if (node.Parent!=null){
			//if node's childcut is false, make it true, else cut it from its parent and cascading cut its parent//
			if (node.childcut==false){
				node.childcut=true;
			}else{
				cutNode(node,parent);
				CascadingCut(parent);			    
			}						
		}		
	}
	///increase key operation///
		public void increasekey(String hashtag,int num,Hashtable<String,Node> table1){
			//find the target node by searching hashtable//
			Node node=table1.get(hashtag);
			node.key=node.key+num;
			if(max==null||node==null)
				return;
			// the increasekey operation will change the value of child, call cascadingcut function to make heap right//
			if(node.Parent!=null){
			  Node parent=node.Parent;		  
			  if (node.key>parent.key){
				cutNode(node,parent);
				CascadingCut(parent);
			  }	
			}
			 if(node.key>max.key)
				  max=node;
		}
	
		
	//link operation//
	//link the node and its subtree to the root node//
	private void link(Node node,Node root){
			unlinkNode(node);
			//make the node root's child or sibling of root's child//
			if(root.child==null){
				root.child=node;
			}else{	
			    addNode(node,root.child);
			}
			node.Parent=root;
			root.degree++;
			node.childcut=false;			
	}	
	
	//Meld operation//
	private void Meldsamedegree(){
		//max degree of the heap//
		int Max=(int) Math.floor(Math.log(keynum) / Math.log(2.0));
		Node[] nodes =new Node[Max+2];
		for(int i=0;i<0;i++){
			nodes[i]=null;
		}
	    //meld same degree so every degree is unique//
		while (max != null) {
            //extract the max node from the heap//
			Node x = max;
            if (x == x.right){
                max = null;
            }else {
                unlinkNode(x);
                max = x.right;
            }
            x.left = x.right = x;
            int d= x.degree;
            while(nodes[d]!=null){
            	Node y=nodes[d];
            	if (x.key < y.key) {  
                      Node tmp = x;
                      x = y;
                      y = tmp;
                }
            link(y,x);
            nodes[d]=null;
            d++;
            } 
            nodes[d]=x;      
	    }
		max=null;
		
		//reconnect the node from nodes array to the root List//
		for (int i=0;i<Max+2;i++){
			 if (nodes[i] != null) {
	                if (max == null)
	                    max = nodes[i];
	                else {
	                    addNode(nodes[i], max);
	                    if ((nodes[i]).key > max.key)
	                        max = nodes[i];
	                }
			 }
		}
	} 	
	////Remove max node of heap////
	public void removeMax(){
		if(max==null){
			return;
		}	
		
		Node maxnode=max;
		
	    //unlink every child of MaxNode and add them to the RootLink//
		while(maxnode.child!=null){
			Node child=maxnode.child;
			unlinkNode(child);
			if (child.right==child){
				maxnode.child=null;
			}
			else{
				maxnode.child=child.right;
			}
			addNode(child,max);
			child.Parent=null;			
		}
		unlinkNode(maxnode);
		if(maxnode.right==maxnode){
			max=null;
		}
		else{
			max=maxnode.right;
			Meldsamedegree();
		}
		keynum=keynum-1;
		maxnode=null;
	}

	//getMax operation:return max value//
	public int getmax(){
		if(max==null){
			return 0;		
		}
		return max.key;
	}
	//this operation will return the max node's HashTag//
	public String getMaxhashtag(Hashtable<String,Node> table1){
		String maxhashtag=null;
		if(max==null){
			return null;
		}else{
			maxhashtag=max.hashtag;
		}
	
		return maxhashtag;
	}
	
}
