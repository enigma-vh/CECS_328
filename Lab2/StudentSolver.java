
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeMap;


public class StudentSolver {
	
	// A big prime number for the first case a > kb
	public static double MAX_VALUE = 10069.0;

	/*
	list_range(ArrayList<Pair<Integer, Integer>> list):
		Function: this arraylist will be use for the interval
		* initialize ArrayList<Double> result
		* add the MAX_VALUE to the result as the 1st element
		* search for all the swappable pairs ((key[curr] - key[next])*(val[curr]-val[next]) < 0)
		* find their division
			- EX: 5a+4b > 1a+5b --> 4a > b --> a > (1/4)b
		* add 0.0 as the last element since a,b > 0

		return result;
	*/
	public static ArrayList<Double> list_range(ArrayList<Pair<Integer,Integer>> list){
		ArrayList<Double> result = new ArrayList<>();
		ArrayList<Pair<Integer,Integer>> tmp = new ArrayList<Pair<Integer,Integer>>(list);
		int n = list.size();
		
		result.add(MAX_VALUE);
		for(int i = 0; i < n-1; i++) {
			for(int j = i+1; j < n; j++) {
				if( (tmp.get(i).first-tmp.get(j).first)*(tmp.get(i).second-tmp.get(j).second)<0) {
					double divide = (double)(tmp.get(j).second-tmp.get(i).second) / (double)(tmp.get(i).first-tmp.get(j).first);
					if(!result.contains(divide)) {
						result.add(divide);
					}
				}
			}
		}
		
		Collections.sort(result, Collections.reverseOrder());
		result.add(0.0);
		return result; 	
	}
	
	/*
	random_generator(double a, double b):
		* Generate a random number in a given interval (start, end)
	*/
	public static int random_generator(double a, double b) {
	    Random random = new Random();
	    int start = (int) Math.round(a+0.5);
	    int end = (int) Math.round(b-0.5);

	    return random.nextInt((end - start)) + start;
	}
	
	/*
	sort_list(ArrayList<Pair<Integer, Integer>> list, double start, double end):
		* b := n*100
		* a := random_generator(start*b, end*b) where start and end are from list_range()
			- EX: {b < a < 2b} = randome_generator(1*b, 2*b)
		* product = list_key*a + list_val*b
		* store the product and pair<integer, integer> into TreeMap so that it can be automatically sorted

		return new ArrayList<Pair<Integer, Integer>>(map_list.values());
	*/
	public static ArrayList<Pair<Integer, Integer>> sort_list(ArrayList<Pair<Integer,Integer>> list, double start, double end) {
		int n = list.size();
		int b = n*100;
		int a = random_generator(start*b, end*b);
				
		TreeMap<Integer, Pair<Integer, Integer>> map_list = new TreeMap<>();
		
		for(int i = 0; i < n; i++) {
			int product = list.get(i).first*a + list.get(i).second*b;
			map_list.put(product, list.get(i));
		}
		
		return new ArrayList<Pair<Integer, Integer>>(map_list.values());
	}
	

	public static ArrayList<ArrayList<Pair<Integer,Integer>>> solve(ArrayList<Pair<Integer,Integer>> list){
		
		ArrayList<ArrayList<Pair<Integer,Integer>>> res = new ArrayList<ArrayList<Pair<Integer,Integer>>>();

		ArrayList<Double> l = list_range(list);
				
		int n = l.size();

		for(int i = 0; i < n-1; i++) {
			int j = i+1;
			ArrayList<Pair<Integer,Integer>> tmp = sort_list(list, l.get(j), l.get(i));
			if(!res.contains(tmp)) {
				res.add(tmp);
			}
		}
		
		return res;
	}
}