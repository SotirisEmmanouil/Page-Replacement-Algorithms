import java.util.LinkedHashMap;
import java.util.Map;

public class Page {
	
	public static int LRU(String page) {
		int faults = 0, MAX = 3;	//max size of LinkedHashMap
		
		LinkedHashMap<Character,Integer> LM = new LinkedHashMap<Character, Integer>(MAX, 0.75f, true) {
		
			private static final long serialVersionUID = 1L;//create an instance of a LinkedHashMap to represent memory with 3 frames
			
			  @Override
			  protected boolean removeEldestEntry(Map.Entry<Character, Integer> eldest){
			                return size() > MAX;
			            }//overridden removeEldestEntry method which will remove the least
			        }; //recently used entry once the size of the LHM has surpassed the max value   
			 
			   for(int i = 0; i < page.length(); i++) {   
				 if(!(LM.containsKey(page.charAt(i)))) {	
					 		faults++;
				 	 	  }	           //if memory does not contain page reference, increment fault
				 LM.put(page.charAt(i),i);	//append value as well or if it does exist, the same value.
			 			}
			      return faults;		//return the number of faults
		    }
		

	public static int FIFO(String page) {
		int position = 0, po2 = 0, fault = 0, po3 = 1, po4 = 2;	//position variable to note position in ArrayList
		char [] memory = new char [3];	         //character array to represent memory address with 3 frames
	    for(int i = 0; i < page.length(); i++) {		//iterate through page reference string and see if the number is located in the string
		   if(memory[position] != page.charAt(i) && memory[po2] != page.charAt(i) && memory[po3] != page.charAt(i) && memory[po4] != page.charAt(i)) {	// if
			     memory[position] = page.charAt(i);
				    fault++;				 //count page faults
			        position++;				//go to next frame
			    if(position == 3){		   //if the position has gone beyond the size of 
			       position = 0; 	      //the array, go back to 0
			        }
			}
		}
		return fault;
	}
	
	public static int OPT(String page) {
		  int fault = 0, position1 = 0, position2 = 1, position3 = 2, count1 = 0, count2 = 0, count3 = 0;
		  char[] memory = new char[3];		//memory array
		  memory[position1] = page.charAt(0);	//add first three pages to memory and increment fault
		  fault++;
		  memory[position2] = page.charAt(1);	
		  fault++;
		  memory[position3] = page.charAt(2);		
		  fault++;

		  for (int i = 3; i < page.length(); i++) {	
		    if (memory[position1] != page.charAt(i) && memory[position2] != page.charAt(i) && memory[position3] != page.charAt(i)) {
		      fault++;		  //if page is not found in memory, increment page fault 
		      for (int j = i + 1; j < page.length(); j++) {  // for page in frame 1, find how it appears next in memory 
		        if (memory[position1] == page.charAt(j)) {	//stop once it appears
		          count1 = j;								//count becomes index 
		          break;
		        }
		        if (j == page.length() - 1) {	// if the page does not appear again, count1 will be max int value
		          count1 = Integer.MAX_VALUE;
		        }
		      }
		      for (int j = i + 1; j < page.length(); j++) {  // for page in frame 2, find how it appears next in memory 
		        if (memory[position2] == page.charAt(j)) {	//stop once it appears
		          count2 = j;						//count becomes index 
		          break;
		        }
		        if (j == page.length() - 1) {
		          count2 = Integer.MAX_VALUE; // if the page does not appear again, count1 will be max integer value
		        }
		      }
		      for (int j = i + 1; j < page.length(); j++) { // for page in frame 3, find how it appears next in memory 
		        if (memory[position3] == page.charAt(j)) {  //stop once it appears
		          count3 = j;				//count becomes index 
		          break;
		        }
		        if (j == page.length() - 1) {
		          count3 = Integer.MAX_VALUE;    // if the page does not appear again, count1 will be max integer value
		        }
		      }

		      if (count1 > count2 && count1 > count3) { // do comparisons of which one is farthest and then the new page will be placed at that frame
		        memory[position1] = page.charAt(i);
		      } else if (count2 > count1 && count2 > count3) {  // do comparisons of which one is farthest and then the new page will be placed at that frame
		        memory[position2] = page.charAt(i);
		      } else if (count3 > count1 && count3 > count2) {  // do comparisons of which one is farthest and then the new page will be placed at that frame
		        memory[position3] = page.charAt(i);  
		      } else if (count3 == count2) {			//if they are equal, just choose either one
		        memory[position3] = page.charAt(i);   
		      } else if (count3 == count1) {			//if they are equal, just choose either one
		        memory[position1] = page.charAt(i);
		      } else if (count2 == count1) {			//if they are equal, just choose either one
		        memory[position2] = page.charAt(i);
		      }
		    }
		  }
		  return fault;   // return fault count
		}
	
	public static int SEC(String page) {
        int point = 0, fault = 0;	
        char memory[] = new char [3];	          //memory array
        boolean second[] = new boolean[3];	//boolean array to mimic reference bits
       
        for (int i = 0; i < page.length(); i++) {
            boolean discovered = false;	
            for (int j = 0; j < 3; j++) {	//if page is found in memory
                if (memory[j] == page.charAt(i)) {
                    second[j] = true;	//turn reference bit to true (1)
                    discovered = true;	//it has been found
                    break;				//break loop
                }
            }
            if (!discovered) {	//if it has not been discovered
                while (true) {
                    if (!second[point]) {
                        memory[point] = page.charAt(i);	//pointer to memory becomes that page character
                        point = (point + 1) % 3;	//reflect circular rotation by doing mod operation
                        fault++;		//increment fault counter
                        break;
                    }
                    second[point] = false;			//pointer to reference bit turns false
                    point = (point + 1) % 3;		//pointer goes in circular motion (circular queue)
                			}
            			}
        			}
        		return fault;	//return fault
				}

		
	public static void main(String [] args) {
			
			String header = "Page-Reference String: ";
			String pageRef1 = "26924217305212957385";
			String pageRef2 = "06302635241306142357";
			String pageRef3 = "31425413520110234501";
			String pageRef4 = "42179835268107241358";
			String pageRef5 = "01234432100123443210";
			//print results
			System.out.println(header+pageRef1);
			System.out.println("OPT: "+OPT(pageRef1)+"      FIFO: "+ FIFO(pageRef1)+"     LRU: "+LRU(pageRef1)+"     SEC: "+ SEC(pageRef1));
			
			System.out.println(header+pageRef2);
			System.out.println("OPT: "+OPT(pageRef2)+"      FIFO: "+ FIFO(pageRef2)+"     LRU: "+LRU(pageRef2)+"     SEC: "+ SEC(pageRef2));
			
			System.out.println(header+pageRef3);
			System.out.println("OPT: "+OPT(pageRef3)+"      FIFO: "+ FIFO(pageRef3)+"     LRU: "+LRU(pageRef3)+"     SEC: "+ SEC(pageRef3));
			
			System.out.println(header+pageRef4);
			System.out.println("OPT: "+OPT(pageRef4)+"      FIFO: "+ FIFO(pageRef4)+"     LRU: "+LRU(pageRef4)+"     SEC: "+ SEC(pageRef4));
			
			System.out.println(header+pageRef5);
			System.out.println("OPT: "+OPT(pageRef5)+"      FIFO: "+ FIFO(pageRef5)+"     LRU: "+LRU(pageRef5)+"     SEC: "+ SEC(pageRef5));
		
		}
}
