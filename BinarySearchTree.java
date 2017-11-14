import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree <K extends Comparable<K>, V>{

    private TreeNode<K, V> root;
    
    public BinarySearchTree(){
        this.root = null;
    }
    
    public boolean isEmpty(){
        return this.root == null;
    }
    
    public void add(K key, V value){
        if(key == null || value == null){
            throw new NullPointerException();
        }
        
        TreeNode<K, V> newNode = new TreeNode<>(key, value);
        if(this.isEmpty()){
            this.root = newNode;
        }else{
            this.recursiveAdd(this.root, newNode);
        }
    }

    private void recursiveAdd(TreeNode<K, V> currentNode, TreeNode<K, V> addNode){
        int cmp = addNode.key.compareTo(currentNode.key);
        if(cmp == 0){   // Si la llava es igual solo cambio el valor asociado
            currentNode.value = addNode.value;
            return;
        }
        if(cmp < 0){
            if(currentNode.leftChild == null){
                addNode.parent = currentNode;
                currentNode.leftChild = addNode;
            }else{
                this.recursiveAdd(currentNode.leftChild, addNode);
            }
        }else{
            if(currentNode.rightChild == null){
                addNode.parent = currentNode;
                currentNode.rightChild = addNode;
            }else{
                this.recursiveAdd(currentNode.rightChild, addNode);
            }
        }
    }
    
    public void put(K key, V value){
        if(key == null || value == null){
            throw new NullPointerException();
        }
        if(this.isEmpty()){
            this.root = new TreeNode<K, V>(key, value);
            return;
        }
        int cmp;
        TreeNode<K, V> current = this.root;
        TreeNode<K, V> prev = null;
        while(current != null){
            prev = current;
            cmp = key.compareTo(current.key);
            if(cmp > 0){
                current = current.rightChild;
            }else if(cmp < 0){
                current = current.leftChild;
            }else{
                current.value = value;
                return;
            }
        }
        cmp = key.compareTo(prev.key);
        if(cmp > 0){
            prev.rightChild = new TreeNode<K, V>(key, value);
            prev.rightChild.parent = prev;
        }else{
            prev.leftChild = new TreeNode<K, V>(key, value);
            prev.leftChild.parent = prev;
        }
    }

    public V remove(K key){
        TreeNode<K, V> node = this.searchNode(key, this.root);
        if(node != null){
            this.recursiveRemove(node);
            return node.value;
        }else{
            return null;
        }
    }
    
    private void recursiveRemove(TreeNode<K, V> remove){
        if(remove.leftChild == null && remove.rightChild == null){
            // If the remove node has no children
            if(remove == this.root){
                this.root = null;
                return;
            }
            if(remove.parent.leftChild == remove){
                remove.parent.leftChild = null;
            }else{
                remove.parent.rightChild = null;
            }
            
        }else if(remove.leftChild == null && remove.rightChild != null || remove.leftChild != null && remove.rightChild == null){
            // If the remove node only has one child
            if(remove.leftChild != null){
                remove.key = remove.leftChild.key;
                remove.value = remove.leftChild.value;
                TreeNode<K, V> leftChild = remove.leftChild;
                remove.leftChild = leftChild.leftChild;
                remove.rightChild = leftChild.rightChild;
            }else{
                remove.key = remove.rightChild.key;
                remove.value = remove.rightChild.value;
                TreeNode<K, V> rightChild = remove.rightChild;
                remove.rightChild = rightChild.rightChild;
                remove.leftChild = rightChild.leftChild;
            }
            if(remove.leftChild != null){
                remove.leftChild.parent = remove;
            }else if(remove.rightChild != null){
                remove.rightChild.parent = remove;
            }
        }else{
            // If the remove node has both right and left child
            TreeNode<K, V> predecesor = this.getPredecesor(remove);
            remove.key = predecesor.key;
            remove.value = predecesor.value;
            this.recursiveRemove(predecesor);
        }
    }
    
    private TreeNode<K, V> getPredecesor(TreeNode<K, V> node){
        if(node == null){
            return null;
        }
        return this.max(node.leftChild);
    }
    
    private TreeNode<K, V> getSuccesor(TreeNode<K, V> node){
        if(node == null){
            return null;
        }
        return this.min(node.rightChild);
    }
    
    private TreeNode<K, V> min(TreeNode<K, V> node){
        if(node.leftChild == null){
            return node;
        }
        return this.min(node.leftChild);
    }
    
    private TreeNode<K, V> max(TreeNode<K, V> node){
        if(node.rightChild == null){
            return node;
        }
        return this.max(node.rightChild);
    }
    
    public V get(K key){
        if(key == null){
            throw new NullPointerException();
        }
        if(this.isEmpty()){
            return null;
        }else{
            return (this.searchNode(key, this.root) == null)? null:this.searchNode(key, this.root).value;
        }
    }
    
    public boolean contains(K key){
        return (this.get(key) == null)? false:true;
    }
    
    private TreeNode<K, V> searchNode(K key, TreeNode<K, V> node){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp == 0){
            return node;
        }
        if(cmp > 0){
            return this.searchNode(key, node.rightChild);
        }else{
            return this.searchNode(key, node.leftChild);
        }
    }

    public String inorder(){
        return this.inorder(this.root);
    }
    
    private String inorder(TreeNode<K, V> node){
        String acum ="";
        if(node == null){
            return "";
        }
        acum += this.inorder(node.leftChild);
        acum += node;
        acum += this.inorder(node.rightChild);
        return acum;
    }
    
    public String descendente(){
        return this.descendente(this.root);
    }
    
    private String descendente(TreeNode<K, V> node){
        String acum ="";
        if(node == null){
            return "";
        }
        acum += this.descendente(node.rightChild);
        acum += node;
        acum += this.descendente(node.leftChild);
        return acum;
    }
    
    public String preorder(){
        return this.preorder(this.root);
    }
    
    private String preorder(TreeNode<K, V> node){
        String acum = "";
        if(node == null){
            return "";
        }
        acum += node;
        acum += this.preorder(node.leftChild);
        acum += this.preorder(node.rightChild);
        return acum;
    }
    
    public String preOrderIt(){
        String acum = "";
        Stack<TreeNode<K, V>> stack = new Stack<>();
        stack.push(this.root);
        TreeNode<K, V> current = this.root;
        while(!stack.isEmpty()){
            acum += current;
            if(current.leftChild != null){
                current = current.leftChild;
                stack.push(current);
            }else{
                TreeNode<K, V> newNode = stack.pop();
                while(newNode.rightChild == null){
                    if(stack.isEmpty()){
                        return acum;
                    }
                    newNode = stack.pop();
                }
                current = newNode.rightChild;
                stack.push(current);
            }
        }
        return acum;
    }
    
    public String postorder(){
        return this.postorder(this.root);
    }
    
    private String postorder(TreeNode<K, V> node){
        String acum = "";
        if(node == null){
            return "";
        }
        acum += this.postorder(node.leftChild);
        acum += this.postorder(node.rightChild);
        acum += node;
        return acum;
    }
    
    public String porNiveles(){
        String res = "";
        Queue<TreeNode<K, V>> visitNodes = new LinkedList<>();
        visitNodes.add(this.root);
        TreeNode<K, V> currentNode;
        while(!visitNodes.isEmpty()){
            currentNode = visitNodes.remove();
            res += currentNode;
            if(currentNode.leftChild != null){
                visitNodes.add(currentNode.leftChild);
            }
            if(currentNode.rightChild != null){
                visitNodes.add(currentNode.rightChild);
            }
        }
        return res;
    }
    
    public String mayoresA(K key){
        TreeNode<K, V> node = this.searchNode(key, this.root);
        if(node == null){
            return null;
        }else{
            return this.mayoresA(this.root, key);
        }
    }
    
    private String mayoresA(TreeNode<K, V> node, K key){
        String acum ="";
        if(node == null){
            return "";
        }
        acum += this.mayoresA(node.leftChild, key);
        acum += (node.key.compareTo(key) > 0)? node:"";
        acum += this.mayoresA(node.rightChild, key);
        return acum;
    }
    
    public String cualesPorNivel(int n){
        Queue<TreeNode<K, V>> levelNodes = new LinkedList<>();
        levelNodes.add(this.root);
        return this.cualesPorNivel(levelNodes, n, levelNodes.size());
    }
    
    private String cualesPorNivel(Queue<TreeNode<K, V>> levelNodes, int n, int numElements){
        // If we have arrived to the desired level
        if(numElements == 0){
            return "";
        }
        if(n == 0){
            String acum = "";
            for(TreeNode<K, V> node:levelNodes){
                acum += node;
            }
            return acum;
        }
        else{
            for(int i = 0; i < numElements; i++){
                TreeNode<K, V> current = levelNodes.remove();
                if(current.leftChild != null) levelNodes.add(current.leftChild);
                if(current.rightChild != null) levelNodes.add(current.rightChild);
                
            }
            return this.cualesPorNivel(levelNodes, --n, levelNodes.size());
        }
        
        
    }
    
    public int size(){
        return this.size(this.root);
    }
    
    private int size(TreeNode<K, V> node){
        int acum = 0;
        if(node == null){
            return 0;
        }
        acum += this.size(node.leftChild);
        acum += 1;
        acum += this.size(node.rightChild);
        return acum;
    }
    
    public int cuantasHojas(){
        return this.cuantasHojas(this.root);
    }
    
    private int cuantasHojas(TreeNode<K, V> node){
        int numHojas = 0;
        if(node == null){
            return 0;
        }
        numHojas += this.cuantasHojas(node.leftChild);
        numHojas += (node.leftChild == null && node.rightChild == null)? 1:0;
        numHojas += this.cuantasHojas(node.rightChild);
        return numHojas;
    }
    
    public String hojas(){
        return this.hojas(this.root);
    }
    
    private String hojas(TreeNode<K, V> node){
        String acum ="";
        if(node == null){
            return "";
        }
        acum += this.hojas(node.leftChild);
        acum += (node.leftChild == null && node.rightChild == null)? node:"";
        acum += this.hojas(node.rightChild);
        return acum;
    }
    
    public int altura(){
        if(this.isEmpty()){
            return 0;
        }
        int h1 = this.altura(this.root.leftChild);
        int h2 = this.altura(this.root.rightChild);
        return Math.max(h1, h2);
    }
    
    public int altura(TreeNode<K, V> node){
        if(node == null){
            return 0;
        }
        int altura1 = this.altura(node.leftChild);
        int altura2 = this.altura(node.rightChild);
        return Math.max(altura1, altura2) + 1;
    }
    
    static class TreeNode<K extends Comparable<K>, V>{
        
        K key;
        V value;
        TreeNode<K, V> parent, rightChild, leftChild;
        
        public TreeNode(K key, V value){
            this.key = key;
            this.value = value;
        }
        
        public String toString(){
            return "[" + key + "-" + value + "]";
        }
    }
    
    public static void main(String[] args){
        BinarySearchTree<Integer, Integer> bs = new BinarySearchTree<>();
        bs.put(7, 7);
        bs.put(2, 2);
        bs.put(5, 5);
        bs.put(-3, -3);
        bs.put(9, 9);
        bs.put(1, 1);
        bs.put(3, 3);
        bs.add(4, 4);
        bs.put(8, 8);
        bs.put(10, 10);
        System.out.println(bs.cuantasHojas());
    }
}
