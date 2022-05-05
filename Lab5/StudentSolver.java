
import java.util.ArrayList;
import java.util.Collections;

public class StudentSolver {
	/*
	 * heap_sort(int[] arr, ArrayList<> mess):
	 	* Function: Create an array that can store the mess' index in an ascending order. 
	 			 The first element of the array will be the index of the lowest mess.
	 * Reference: Introduction to Algorithms - 3rd edition
	 */
	public static void heap_sort(int[] arr, ArrayList<Pair<Pair<Double, Double>, Integer>> mess) {
		int n = arr.length;

		for(int i = n/2 - 1; i >= 0; i--) {
			max_heapify(arr, mess, n , i);
		}

		for(int i = n -1; i > 0; i--) {
			int tmp = arr[0];
			arr[0] = arr[i];
			arr[i] = tmp;
			
			max_heapify(arr, mess, i, 0);
		}
	}
	
	/*
	 * Reference: Introduction to Algorithms - 3rd edition
	 */
	public static void max_heapify(int[] arr, ArrayList<Pair<Pair<Double, Double>, Integer>> mess, int n, int i) {
		int l = 2*i + 1;
		int r = 2*i + 2;
		int largest = 0;
		
		if(l < n && mess.get(arr[l]).first.second >  mess.get(arr[i]).first.second) {
			largest = l;
		}
		else {
			largest = i;
		}
		
		if(r < n && mess.get(arr[r]).first.second >  mess.get(arr[largest]).first.second) {
			largest = r;
		}
		
		if(largest != i) {
			int tmp = arr[i];
			arr[i] = arr[largest];
			arr[largest] = tmp;
			
			max_heapify(arr, mess, n, largest);
		}
		
	}
	
	/*
	 * compare_mess(Pair m1, Pair m2, double r):
	 	* Function: Determine if m1 can reach m2
	 		- deltaX: vertical change between two messes
	 		- deltaY: horizontal change between two messes
	 		- ratio: deltaX/deltaY
	 		* return (ratio > r || ratio < -r)
	 	* Proof:
	 		- Assume: V := vector representing the movement of Roomba
	 		- We'll have:
	 		 	- v = V * sin(α) --> V = v/sin(α)	(Note: v := Vx given in the description)
	 			- Vy = V * cos(α) = [-v/r, v/r]
	 			
	 			--> v * (cos(α)/sin(α)) = [-v/r, v/r]
	 			--> v/tan(α) = [-v/r, v/r]
	 			--> tan(α) > r || tan(α) < -r			(1)
	 				tan(α) = deltaX/deltaY				(2)
	 		From (1) and (2), we can prove that deltaX/deltaY > r || deltaX/deltaY < -r
	 */
	public static boolean compare_mess(Pair<Pair<Double, Double>, Integer> m1, Pair<Pair<Double, Double>, Integer> m2, double r) {
		double deltaX = m2.first.first - m1.first.first;
		double deltaY = m2.first.second - m1.first.second;
		double ratio = deltaY/deltaX;

		if(ratio > r || ratio < r*(-1)) {
			return true;
		}
		return false;
	}
	
	
	public static ArrayList<Integer> run(double r, ArrayList<Pair<Pair<Double, Double>, Integer>> mess){
		ArrayList<Integer> res = new ArrayList<>();
		int numMess = mess.size();
		
		/*
		 * Generate an array containing 0 through numMess to easily keep track of the index for the sorting function
		 */
		int[] heap_sort =new int[numMess];
		for (int i = 0; i < numMess; i++){
				heap_sort[i]=i;
		}
		
		/* 
		 * The dist[] stores the optimal distance from a point p to the index
		 * The prev[] keeps track of the index's previous point
		 * Pair<Integer, Integer> max_mess: the mess has the longest path
		 	- max_mess.first := its previous point
		 	- max_mess.second := the length of the path to it 
		 */
		int[] dist = new int[numMess];
		int[] prev = new int[numMess];
		Pair<Integer, Integer> max_mess = new Pair<>(0,0);
		
		heap_sort(heap_sort, mess);
		
		/*
		 * This below function is similar to the process of finding the longest path of the DAG
		 */
		for(int i = 0; i < numMess; i++) {
			int start = heap_sort[i];
			for(int j = i+1; j < numMess; j++) {
				int end = heap_sort[j];
				if(compare_mess(mess.get(start), mess.get(end), r)) {
					int s = 0;
					if(dist[start] == 0) {
						s = (-1)*(mess.get(start).second) - mess.get(end).second;
					}
					else {
						s = dist[start] - mess.get(end).second;
					}
					
					if(s < dist[end] || dist[end] == 0) {
						dist[end] = s;
						prev[end] = start;
						if(s < max_mess.second) {
							max_mess = new Pair<>(end, s);
						}
					}
				}
			}

		}
		
		/*
		 * From the max_mess -> Trace back the previous points
		 */
		int curr = max_mess.first;
		while(curr != 0) {
			res.add(curr);
			curr = prev[curr];
		}
		
		Collections.reverse(res);
		
		return res;
	}
}
