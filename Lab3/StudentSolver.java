import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class StudentSolver {

	/*
	 map_value(boolean[] m, boolean flag, Map<Integer, ArrayList<Integer>> value_map):
	 	* Function: this function will store the index of cells containing False value in each row to the value_map
	 	
	 	*Example:
	 		m = {	{0, 1, 0},
	 				{1, 0, 0},
	 				{0, 0, 0}	}
	 	
	 		value_map (Output):
	 					<key> | <ArrayList>
	 	 				  2		  [0, 1]
	 	
	 */
	public static void map_value(boolean[][] m, boolean flag, Map<Integer, ArrayList<Integer>> value_map) {
		/*
		 If there is a False value in that row, light == true --> value_map.put(i, value_list)
		 */
		boolean light = false;
		
		/*
		 Lower Triangular Matrix
		 	* Reason: 
		 		+ If a contradicts b, then b contradicts a --> only need to traverse lower or upper triangular matrix
		 		+ Ignore the diagonal entries
		 */
		for(int i = m.length-1; i > 0; i--) {
			ArrayList<Integer> value_list = new ArrayList<Integer>();
			for(int j = 0; j < i; j++) {
				if(m[i][j] == flag) {
					value_list.add(j);
					light = true;
				}
			}
			if(light == true) {
				value_map.put(i, value_list);
			}		
			light = false;
		}
	}
	
	/*
	 map_remove(Map<Integer, ArrayList<Integer>> value_map, int k1x, int k2x):
	 	*Function: Select the keys in value_map that satisfy given restrictions
	 	
	 	for entry: value_map.entrySet():
	 		key := entry.getKey()
	 		value_list := entry.getValue()
	 		n := value_list.size()
	 		
	 		a key meets the requirements iff:
	 			+ n >= k2x
	 			+ key - n >= k1x since value_list contains the index of cells containing False value, 
	 				+ key - n := numbers of cells containing True value in that row
	 */
	
	public static Map<Integer, ArrayList<Integer>> map_remove(Map<Integer, ArrayList<Integer>> value_map, int k1x, int k2x) {
		Map<Integer, ArrayList<Integer>> result = new LinkedHashMap<Integer, ArrayList<Integer>>();

		for (Map.Entry<Integer, ArrayList<Integer>> entry : value_map.entrySet()) {
			int key = entry.getKey();
			ArrayList<Integer> value_list = entry.getValue();
			int n = value_list.size();
			if((key-n) >= k1x && n >= k2x) {
				result.put(key, value_list);
			}
		}
		
		return result;
	}
	
	
	
	public static HashSet<Integer> solve(boolean[][] m, int k1x, int k2x){
		HashSet<Integer> result = new HashSet<Integer>();
		Map<Integer, ArrayList<Integer>> value_map = new LinkedHashMap<Integer, ArrayList<Integer>>();
		Map<Integer, ArrayList<Integer>> removal = new LinkedHashMap<Integer, ArrayList<Integer>>();


		map_value(m, false, value_map);
		removal = map_remove(value_map, k1x, k2x);
		
		for (Map.Entry<Integer, ArrayList<Integer>> entry : removal.entrySet()) {
			result.add(entry.getKey());
			ArrayList<Integer> value_list = entry.getValue();
			for(int i = 0; i < value_list.size(); i++) {
				result.add(value_list.get(i));
			}
		}
		
		
		return result;
	}
}