import java.util.Arrays;

public class MaxHeap <V extends Comparable<V>>{
    
    public MaxHeap(){
    }
    
    private int parent(int i){
        return 1/2;
    }
    
    private int left(int i){
        return 2*i;
    }
    
    private int right(int i){
        return 2*i + 1;
    }
    
    private void MaxHeapify(V[] heap, int i, int heapSize){
        int l = this.left(i);
        int r = this.right(i);
        int largest;
        if(l <= heapSize-1 && heap[l].compareTo(heap[i]) > 0){
            largest = l;
        }else{
            largest = i;
        }
        if(r <= heapSize-1 && heap[r].compareTo(heap[largest]) > 0){
            largest = r;
        }
        if(largest != i){
            V tmp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = tmp;
            MaxHeapify(heap, largest, heapSize);
        }
    }
    
    public void buildMaxHeap(V[] arr){
        int heapSize = arr.length;
        for(int i = heapSize; i >= 1; i--){
            this.MaxHeapify(arr, i, arr.length);
        }
    }
    
    public void heapSort(V[] arr){
        this.buildMaxHeap(arr);
        int heapSize = arr.length;
        for(int i = heapSize-1; i > 1; i--){
            V tmp = arr[1];
            arr[1] = arr[i];
            arr[i] = tmp;
            heapSize--;
            this.MaxHeapify(arr, 1, heapSize);
        }
    }
    
    public static void main(String[] args){
        MaxHeap<Integer> a = new MaxHeap<>();
        Integer[] arr = {90000, 90000, 0, 89, 67, 4, 5, 8, 199, 290, 2, 10, 1, 2};
        a.heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
