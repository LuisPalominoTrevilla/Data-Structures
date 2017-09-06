package programaEstadistico;

import java.util.Arrays;
import java.util.Scanner;

public class RadixSort {
	public RadixSort(){
	}
	
	private int getMax(Integer[] array){
		int max=array[0];
		for(int i=0;i<array.length;i++){
			if(max<array[i]){
				max=array[i];
			}
		}
		return max;
	}
	
	private void countSort(Integer[] arrayPrincipal, int large,int exp){
		Integer[] output=new Integer[large];
		int i;
		int count[]=new int[10];
		Arrays.fill(count, 0);
		
		for(i=0;i<large;i++){
			count[(arrayPrincipal[i]/exp)%10]++;
		}
		for(i=1;i<10;i++){
			count[i]+=count[i-1];
		}
		for(i=large-1;i>=0;i--){
			output[count[(arrayPrincipal[i]/exp)%10]-1]=arrayPrincipal[i];
			count[(arrayPrincipal[i]/exp)%10]--;
		}
		for(i=0;i<large;i++){
			arrayPrincipal[i]=output[i];
		}
		
	}
	
	public Integer[] radixSort(Integer[] arrayR){
		int cuenta=0;
		for(int i=0;i<arrayR.length;i++){
			if(arrayR[i]==null){
				break;
			}
			cuenta++;
		}
		Integer[] array=new Integer[cuenta];
		System.arraycopy(arrayR, 0, array, 0, array.length);
		
		int max=getMax(array);
		for(int exp=1;max/exp>0;exp*=10){
			countSort(array,array.length,exp);
		}
		return array;
	}
}
