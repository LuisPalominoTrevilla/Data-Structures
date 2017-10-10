import java.util.NoSuchElementException;

public class DoublyLinkedList<V>{
    
    private int size;
    private Node<V> head;
    private Node<V> tail;
    
    public DoublyLinkedList(){
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    /**
     * 
     * @return returns the size of the list
     */
    public int getSize(){
        return this.size;
    }
    
    public boolean isEmpty(){
        return this.size==0;
    }
    
    public void addFirst(V value){
        Node<V> tmp = new Node<V>(value);
        tmp.next = this.head;
        if(this.isEmpty()){
            this.tail = tmp;
        }else{
            this.head.prev = tmp;
        }
        this.head = tmp;
        this.size++;
    }
    
    public void addLast(V value){
        if(this.isEmpty()){
            this.addFirst(value);
        }else{
            Node<V> tmp = new Node<V>(value);
            tmp.prev = this.tail;
            this.tail.next = tmp;
            this.tail = tmp;
            this.size++;
        }
        
    }
    
    public void addAt(V value, int index){
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            this.addFirst(value);
        }
        else if(index == this.size){
            this.addLast(value);
        }else{
            Node<V> tmp = new Node<V>(value);
            Node<V> current = this.head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
            tmp.next = current;
            tmp.prev = current.prev;
            current.prev.next = tmp;
            current.prev = tmp;
            this.size++;
        }
    }
    
    public int indexOf(V value){
        Node<V> current = this.head;
        int index = 0;
        while(current != null){
            if(current.value.equals(value)){
                return index;
            }
            index++;
            current = current.next;
        }
        throw new NoSuchElementException();
    }
    
    public V remove(int index){
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            return this.removeFirst();
        }
        if(index == this.size-1){
            return this.removeLast();
        }
        Node<V> current = this.head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        V value = current.value;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = null;
        this.size--;
        return value;
    }
    
    public V removeFirst(){
        if(this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        V value = this.head.value;
        this.head = this.head.next;
        this.size--;
        return value;
    }
    
    public V removeLast(){
        if(this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        V value = this.tail.value;
        this.tail = this.tail.prev;
        this.tail.next = null;
        this.size--;
        return value;
        
    }
    
    public V get(int index){
        if(index < 0 || index > this.size){
            throw new IndexOutOfBoundsException();
        }
        Node<V> current = this.head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.value;
    }
    
    public V getFirst(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        return this.head.value;
        
    }
    
    public void iterate(){
        Node<V> current = this.head;
        while(current != null){
            System.out.print(current.value + " -> ");
            current = current.next;
        }
    }

    public V getLast(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        return this.tail.value;
    }
    
    static class Node<V>{
        
        V value;
        Node<V> next, prev;
        
        public Node(V value){
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }
    
    public static void main(String[] args) {
        DoublyLinkedList<Integer> a = new DoublyLinkedList<Integer>();
        
        a.addLast(5);
        a.addFirst(4);
        a.addLast(6);
        a.addAt(3, 2);
        a.addAt(90, 4);
        a.addFirst(900);        
        a.iterate();
        System.out.println(a.remove(3));
        a.addAt(34, 3);
        a.iterate();
        System.out.println(a.indexOf(90));
    }

}
