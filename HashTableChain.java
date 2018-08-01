
public class HashTableChain<K, V>{

    private int size;
    private Node<K, V>[] table;
    private int occupied;
    private static final double OVERLOAD_FACTOR = 7.0/8;
    private static final int INIT_CAPACITY = 1;
    
    /**
     * Default Constructor
     * Initializes initial capacity with 1
     */
    public HashTableChain(){
        this(INIT_CAPACITY);
    }

    /**
     * 
     * @param size the desired initial size of the table
     */
    public HashTableChain(int size){
        this.size = size;
        this.table = (Node<K, V>[]) new Node[this.size];
        this.occupied = 0;
    }
    
    /**
     * Hash function
     * @param key key to hash
     * @return the hash to that key
     */
    private int HashDiv(K key){
        int s = key.hashCode();
        return Math.abs(s)%this.size;
    }

    /**
     * 
     * @return returns the size of the table (slots occupied)
     */
    public int size(){
        return this.occupied;
    }

    /**
     * 
     * @return returns if the table is empty
     */
    public boolean isEmpty(){
        return this.occupied == 0;
    }

    /**
     * 
     * @param key the key of the value to look for
     * @return return the value hashed to the key
     */
    public V get(K key){
        if(key == null){
            throw new NullPointerException("Null key");
        }
        int pos = this.HashDiv(key);
        Node<K, V> tmp = this.table[pos];
        while(tmp != null){
            if(tmp.key.equals(key)) return tmp.value;
            tmp = tmp.next;
        }
        return null;
    }
    
    /**
     * Maps a key to a value
     * @param key
     * @param value
     */
    public void put(K key, V value){
        if(key == null || value == null){
            throw new NullPointerException("Null value or key");
        }
        if((double)this.occupied/this.size > HashTableChain.OVERLOAD_FACTOR){
            this.reHash();
        }
        int pos = this.HashDiv(key);
        Node<K, V> tmp = this.table[pos]; 
        
        while(tmp != null){
            if(tmp.key.equals(key)){
                tmp.value = value;
                return;
            }
            tmp = tmp.next;
        }
        Node<K, V> newNode = new Node<>(key, value);
        Node<K, V> firstNode = this.table[pos];
        newNode.next = firstNode;
        this.table[pos] = newNode;
        this.occupied++;
    }
    
    /**
     * Returns and removes the value map to the key
     * @param key
     * @return the value maped to the key
     */
    public V remove(K key){
        if(key == null){
            throw new NullPointerException("Null key");
        }
        int pos = this.HashDiv(key);
        Node<K, V> current = this.table[pos];
        Node<K, V> prev = null;
        while(current != null){
            if(current.key.equals(key)){
                V val = current.value;
                if(prev == null){
                    this.table[pos] = current.next;
                }else{
                    prev.next = current.next;
                }
                current = null;
                this.occupied--;
                return val;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }
    
    // Function used to duplicate the table length and rehash its keys
    private void reHash(){
        this.size*=2;
        Node<K, V>[] tableCopy = this.table;
        this.table = (Node<K, V>[]) new Node[this.size];
        this.occupied = 0;
        for(int i = 0; i < tableCopy.length; i++){
            Node<K, V> current = tableCopy[i];
            while(current != null){
                this.put(current.key, current.value);
                current = current.next;
            }
        }
    }
    
    static class Node<K, V>{
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

}
