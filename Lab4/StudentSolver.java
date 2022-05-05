import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentSolver {
	
	public static Set<ArrayList<Integer>> pattern_func(Set<ArrayList<Integer>> val_list, int[] arr, int row_index, ArrayList<Integer> tmp){
		int len = arr.length;
		int[] newArr = Arrays.copyOfRange(arr, row_index, len);
		int n = newArr.length;
		
		if(n == 4) {
			for(int i = row_index; i < len; i++) {
				val_list.add(new ArrayList<Integer>(Arrays.asList(i)));
				if(!tmp.isEmpty()) {
					for(int k : tmp) {
						if( (i-k) != 1) {
							val_list.add(new ArrayList<Integer>(Arrays.asList(k, i)));
							for(int j = i+1; j < len; j++) {
								if( (j-i) != 1) {
									val_list.add(new ArrayList<Integer>(Arrays.asList(i, j)));
									val_list.add(new ArrayList<Integer>(Arrays.asList(k, i, j)));
								}	
							}
						}
					}
				}
				else {
					for(int j = i+1; j < len; j++) {
						if( (j-i) != 1) {
							val_list.add(new ArrayList<Integer>(Arrays.asList(i, j)));
						}	
					}
				}
			}
		}
		
		if(n > 4) {
			tmp.add(row_index);
			val_list.add(new ArrayList<Integer>(Arrays.asList(row_index)));
			return pattern_func(val_list, arr, row_index+1, tmp);
		}

		return val_list;
	}
	
	public static void find_pattern(Set<ArrayList<Integer>> val_list, int[][] board) {
		ArrayList<Integer> tmp = new ArrayList<>();
	    int[] column = new int[board.length];
	    for(int i=0; i<column.length; i++){
	       column[i] = board[i][0];
	    } 
	    pattern_func(val_list, column, 0, tmp);
	    
	}

	public static Map<Integer, ArrayList<ArrayList<Integer>>> pattern_map(Set<ArrayList<Integer>> val_list, int[][] board){
		Map<Integer, ArrayList<ArrayList<Integer>>> res = new LinkedHashMap<>();
		for(int i = 0; i < board.length; i++) {
			ArrayList<ArrayList<Integer>> a = new ArrayList<>();
			for(ArrayList<Integer> m : val_list) {
				if(!m.contains(i)){
				  a.add(m);
				}
			}
			res.put(i,a);
		}
		return res;
	}
	
	public static ArrayList<ArrayList<Integer>> find_next(Map<Integer, ArrayList<ArrayList<Integer>>> pat, ArrayList<Integer> list){
		ArrayList<ArrayList<Integer>> res = new ArrayList<>();
		int n = list.size();
		if(n == 1) {
			return pat.get(list.get(0));
		}
		else {
			List<Integer> sub = list.subList(1, n);
			for(ArrayList<Integer> l : pat.get(list.get(0))) {
				if(Collections.disjoint(l, sub)) {
					res.add(l);
				}
			}	
		}
		return res;
		
	}
	
	public static ArrayList<Integer> getSum(ArrayList<Integer> list, int index, int[][] board, ArrayList<Integer> prev_sum) {
		int sum = prev_sum.get(0);
		
		for(int i : list) {
			sum += board[i][index];
		}
		return new ArrayList<Integer>(Arrays.asList(sum));
	}
	
	public static Map<String, ArrayList<Integer>> find_sol(Set<ArrayList<Integer>> val_list, int[][] board) {
		find_pattern(val_list, board);
		Map<Integer, ArrayList<ArrayList<Integer>>> pat = pattern_map(val_list, board);
		ArrayList<Map<String, ArrayList<Integer>>> curr_map = new ArrayList<>();
		ArrayList<Map<String, ArrayList<Integer>>> prev_map = new ArrayList<>();
		int c = board[0].length;
		int max_sum = 0;
		int index = 0;
		
		while(index < c) {
			if(index == 0) {
				for(ArrayList<Integer> l : val_list) {
					ArrayList<Integer> sum = getSum(l, index, board, new ArrayList<Integer>(Arrays.asList(0)));
					Map<String, ArrayList<Integer>> newMap = new LinkedHashMap<>();
					newMap.put("Sum", sum);
					newMap.put(String.valueOf(index), l);
					prev_map.add(newMap);
				}
			}
			else {
				for(Map<String, ArrayList<Integer>> m : prev_map) {
					String k = String.valueOf(index-1);
					ArrayList<Integer> prev_sum = m.get("Sum");
					ArrayList<Integer> prev_list = m.get(k);
					for(ArrayList<Integer> curr_list : find_next(pat, prev_list)) {
						ArrayList<Integer> sum = getSum(curr_list, index, board, prev_sum);
						Map<String, ArrayList<Integer>> newMap = new LinkedHashMap<>(m);
						newMap.put("Sum", sum);
						newMap.put(String.valueOf(index), curr_list);
						if(index == (c-1)) {
							if(sum.get(0) > max_sum) {
								curr_map.clear();
								curr_map.add(newMap);
								max_sum = sum.get(0);
							}
						}
						else {
							curr_map.add(newMap);
						}
					}	
				}
				prev_map.clear();
				prev_map.addAll(curr_map);
				curr_map.clear();
			}
			index++;
		}
		return prev_map.get(0);
	}
	
	public static ArrayList<Pair<Integer,Integer>> solve(int[][] board){
		ArrayList<Pair<Integer,Integer>> res = new ArrayList<>();
		Set<ArrayList<Integer>> val_list = new LinkedHashSet<>();
		Map<String, ArrayList<Integer>> res_map = new LinkedHashMap<>();
		res_map = find_sol(val_list, board);
		
		for (Map.Entry<String, ArrayList<Integer>> entry : res_map.entrySet()) {
			String key = entry.getKey();
			if(!key.equals("Sum")) {
				for(int i : entry.getValue()) {
					res.add(new Pair<>(i,Integer.parseInt(key)));
					
				}
			}
		}
		return res;
	}
	
	
}