package edu.ucas.rdms.filer;

public class FunctionUtil{
	
	public static int find(String[] arr, String str){
		int n = arr.length;
		int i = 0;
		for(;i<n;i++){
			if(arr[i].equals(str)){
				return i;
			}
		}
		return -1;
	}
}
