import java.util.Arrays;
import java.util.Random;

public class InsertionSort <V extends Comparable<V>>{
    
    public void sort(V[] list){
        for(int i = 1; i < list.length; i++){
            int j = i;
            while(j > 0 && list[j].compareTo(list[j-1]) < 0) {
                V tmp = list[j];
                list[j] = list[j-1];
                list[j-1] = tmp;
                j--;
            }
        }
    }
    
    public static void main(String[] args) {
        Integer[] a = new Integer[100000];
        Random ran = new Random();
        for(int i = 0; i < a.length; i++){
            a[i] = ran.nextInt(100);
        }
        
        InsertionSort<Integer> inS = new InsertionSort<Integer>();
        double time = System.nanoTime();
        inS.sort(a);
        System.out.println((System.nanoTime()-time)/1000000000);
    }

}
