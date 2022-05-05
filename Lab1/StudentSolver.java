import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class StudentSolver {
	
	static int add_in =0;
	
	static int max_num(int[] arr, int start, int end) {
		int max = arr[start];
		int index = start;
		for(int i = start+1; i < end; i++) {
			if(arr[i] >= max) {
				max = arr[i];
				index = i;
			}
		}
		return index;
	}
	
	static void left(int[] arr) {
		int root = max_num(arr,0, arr.length);
		while(root != 0) {
			int max = max_num(arr, 0, root);
			if(root == 0) {
				break;
			}
			else {
				if(Math.abs(root-max) != 1) {
					for(int i = max+1; i < root; i++) {
						add_in += (arr[max]-arr[i]);
						arr[i] = arr[max];
					}
				}
			}
			root = max;	
		}
	}
	
	static void right(int[] arr) {
		int root = max_num(arr,0, arr.length);
		while(root != arr.length-1) {
			int max = max_num(arr, root+1, arr.length);
			//System.out.println(max);
			if(root == arr.length-1) {
				break;
			}
			else {
				if(Math.abs(root-max) != 1) {
					for(int i = root+1; i < max; i++) {
						add_in += (arr[max]-arr[i]);
						arr[i] = arr[max];
					}
				}
			}
			root = max;
		}
	}
	
	static void transpose(int[][] arr, int[][] res)
	{
	    for (int i = 0; i < arr.length; i++)
	        for (int j = 0; j < arr[0].length; j++)
	            res[i][j] = arr[j][i];
	}
	
	
	public static int solve(int[][] grid) {
		System.out.println("Base: " + Arrays.deepToString(grid));
		int[][] tmp = new int[grid.length][grid[0].length];
		
		for(int i = 0; i < grid.length; i++) {
			left(grid[i]);
			right(grid[i]);
		}
		
		transpose(grid, tmp);
				
		for(int i = 0; i < tmp.length; i++) {
			left(tmp[i]);
			right(tmp[i]);
		}
		
		transpose(tmp, grid);
		
		for(int i = 0; i < grid.length; i++) {
			left(grid[i]);
			right(grid[i]);
		}
		
		
		
		System.out.println("Modified: " + Arrays.deepToString(grid));
		return add_in;
	}
}
