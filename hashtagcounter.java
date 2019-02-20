
import java.io.*;
import java.util.*;

public class hashtagcounter {

	public static void main(String[] args) {
		Fibheap heap = new Fibheap();
		String file_name = args[0];
		String output_name="output_file.txt";
		String line;
		BufferedReader br=null;
		Hashtable<String,Node> table=new Hashtable<String,Node>();
		//String input="";
		try{ 
			PrintWriter output =new PrintWriter(output_name);
			try{
			   br=new BufferedReader(new FileReader(file_name));
			   while((line=br.readLine())!=null){	
				//input+=line+" ";
                                   if(line.charAt(0)=='#'){
                                      String[] arr=line.split(" ");
                                      String hashTag= arr[0].substring(1);
				      int key=Integer.parseInt(arr[1]);
				      if(heap.keynum<1){
					    heap.insert(key,hashTag,table);
				      }else{
					   if(table.containsKey(hashTag)){
						   heap.increasekey(hashTag, key, table);
					   }else{
						   heap.insert(key,hashTag,table);
					   }
				       }
				      //i++;
			     }else if(line.equals("stop")){
					 
				     break;
			     }else if(Character.isDigit(line.charAt(0))){				      
				      int num=Integer.parseInt(line);
				      String [] hashtag=new String[num];
				      int[] key=new int[num];
				      for (int j=0;j<num;j++){
					     key[j]=heap.getmax();
					     hashtag[j]=heap.getMaxhashtag(table);
					     heap.removeMax();
						 if(j<num-1){
							 output.print(hashtag[j].substring(0)+",");
						 }else{
							 output.print(hashtag[j].substring(0)+"");
						 }				       						 
				      }
					  output.println();					  
				      for (int j=0;j<num;j++){
					  heap.insert(key[j], hashtag[j], table);
				 }				
			 }

			   }			
		    }catch (IOException e){
			   e.printStackTrace();
		     }
		     
		  output.close();   
		  System.out.println("Output file Done!");
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		 
	}

}	
