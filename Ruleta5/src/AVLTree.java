import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class AVLTree <K extends Comparable<K>, V>{

    private TreeNode<K, V> root;
    
    /**
     * Default constructor
     */
    public AVLTree(){
        this.root = null;
    }
    
    /**
     * 
     * @return returns true if the tree is empty
     */
    public boolean isEmpty(){
        return this.root == null;
    }
    
    /**
     * Adds a key-value pair to the tree
     * @param key the key to be inserted
     * @param value the value we map the key with
     */
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
                this.findUbnbalancedNode(addNode);
            }else{
                this.recursiveAdd(currentNode.leftChild, addNode);
            }
        }else{
            if(currentNode.rightChild == null){
                addNode.parent = currentNode;
                currentNode.rightChild = addNode;
                this.findUbnbalancedNode(addNode);
            }else{
                this.recursiveAdd(currentNode.rightChild, addNode);
            }
        }
    }
    
    /**
     * Used after INSERTION ONLY
     * Bubble up from the node until we find the first unbalanced node
     * @param node
     */
    private void findUbnbalancedNode(TreeNode<K, V> node){
        StackLinkedList<TreeNode<K, V>> visitedNodes = new StackLinkedList<>();
        while(node != null){
            int leftHeight = 0;
            int rightHeight = 0;
            visitedNodes.push(node);
            if(node.leftChild != null){
                leftHeight = this.altura(node.leftChild);
            }
            if(node.rightChild != null){
                rightHeight = this.altura(node.rightChild);
            }
            if(Math.abs(leftHeight-rightHeight) > 1){
                this.balance(visitedNodes.pop(), visitedNodes.pop(), visitedNodes.pop());
                return;
            }
            node = node.parent;
        }
    }
    
    /**
     * Balances the tree
     * @param z
     * @param y
     * @param x
     */
    private void balance(TreeNode<K, V> z, TreeNode<K, V> y, TreeNode<K, V> x){
        // Find which case we are dealing with
        if(z.leftChild == y){       // y is the left child of z
            if(y.leftChild == x){   // x is the left child of y
                //left left case
                this.rightRotation(z);
            }else{                  // x is the right child of y
                //left right case
                this.leftRotation(y);
                this.rightRotation(z);
            }
        }else{                      // y is the right child of z
            if(y.leftChild == x){   // x is the left child of y
                //right left case
                this.rightRotation(y);
                this.leftRotation(z);
            }else{                  // x is the right child of y
                //right right case
                this.leftRotation(z);
            }
        }
    }
    
    private void leftRotation(TreeNode<K, V> rotateNode){
        // Assumes its right child is not null
        if(rotateNode == this.root){
            this.root = rotateNode.rightChild;
            rotateNode.rightChild.parent = null;
        }else{
            if(rotateNode.parent.leftChild == rotateNode){      // rotateNode is left child of its parent
                rotateNode.parent.leftChild = rotateNode.rightChild;
            }else{                                              // rotateNode is right child of its parent
                rotateNode.parent.rightChild = rotateNode.rightChild;
            }
            rotateNode.rightChild.parent = rotateNode.parent;
        }
        TreeNode<K, V> rightChild = rotateNode.rightChild;
        rotateNode.rightChild = rightChild.leftChild;
        if(rightChild.leftChild != null) rightChild.leftChild.parent = rotateNode;
        rightChild.leftChild = rotateNode;
        rotateNode.parent = rightChild;
    }
    
    private void rightRotation(TreeNode<K, V> rotateNode){
        // Assumes its right child is not null
        if(rotateNode == this.root){
            this.root = rotateNode.leftChild;
            rotateNode.leftChild.parent = null;
        }else{
            if(rotateNode.parent.leftChild == rotateNode){      // rotateNode is left child of its parent
                rotateNode.parent.leftChild = rotateNode.leftChild;
            }else{                                              // rotateNode is right child of its parent
                rotateNode.parent.rightChild = rotateNode.leftChild;
            }
            rotateNode.leftChild.parent = rotateNode.parent;
        }
        TreeNode<K, V> leftChild = rotateNode.leftChild;
        rotateNode.leftChild = leftChild.rightChild;
        if(leftChild.rightChild != null) leftChild.rightChild.parent = rotateNode;
        leftChild.rightChild = rotateNode;
        rotateNode.parent = leftChild;
    }
    
    /**
     * 
     * @return returns the height of the binary tree
     */
    public int altura(){
        if(this.isEmpty()){
            return 0;
        }
        int h1 = this.altura(this.root.leftChild);
        int h2 = this.altura(this.root.rightChild);
        return Math.max(h1, h2);
    }
    
    private int altura(TreeNode<K, V> node){
        if(node == null){
            return 0;
        }
        int altura1 = this.altura(node.leftChild);
        int altura2 = this.altura(node.rightChild);
        return Math.max(altura1, altura2) + 1;
    }

    /**
     * Removes a value from the tree and returns it
     * @param key the key whose value we want to remove
     * @return the value, returns null if there's no key
     */
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
        TreeNode<K, V> parentRemove = remove.parent;
        
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
        this.findUbnbalancedNodeDeletion(parentRemove);
    }
    
    /**
     * Used after DELETION ONLY
     * Bubble up from the node until we find the first unbalanced node
     * @param node
     */
    private void findUbnbalancedNodeDeletion(TreeNode<K, V> node){
        while(node != null){
            int leftHeight = 0;
            int rightHeight = 0;
            if(node.leftChild != null){
                leftHeight = this.altura(node.leftChild);
            }
            if(node.rightChild != null){
                rightHeight = this.altura(node.rightChild);
            }
            if(Math.abs(leftHeight-rightHeight) > 1){       // We found z: the first unbalanced node
                TreeNode<K, V> y;
                if(node.leftChild == null){
                    y = node.rightChild;
                }else if(node.rightChild == null){
                    y = node.leftChild;
                }else{
                    y = (this.altura(node.leftChild)- this.altura(node.rightChild) > 0)? node.leftChild:node.rightChild;
                }
                TreeNode<K, V> x;
                if(y.leftChild == null){
                    x = node.rightChild;
                }else if(y.rightChild == null){
                    x = y.leftChild;
                }else{
                    x = (this.altura(y.leftChild)- this.altura(y.rightChild) > 0)? y.leftChild:y.rightChild;
                }
                this.balance(node, y, x);
                return;
            }
            node = node.parent;
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
    
    /**
     * Gets a value from the tree
     * @param key the key whose value we want to get
     * @return returns the value associated with the key returns null if the key is not in the tree
     */
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

    /**
     * traverse through the tree inorder
     * @return returns a string with the inorder path
     */
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

    /**
     * traverse the tree in preorder
     * @return returns a string with the preorder path
     */
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

    /**
     * traverse the tree in postorder
     * @return returns a string with the postorder path
     */
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
    
    /**
     * @return returns a string that contains every node ordered by level
     */
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
    
    /**
     * 
     * @param n the level we want to get the nodes from
     * @return returns a string with all the nodes on the provided level
     */
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
    
    /**
     * 
     * @return returns the number of nodes of the tree
     */
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
        AVLTree<Integer, Integer> bs = new AVLTree<>();
        bs.add(2, 2);
        bs.add(1, 1);
        bs.add(4, 4);
        bs.add(5, 5);
        bs.add(9, 9);
        bs.add(3, 3);
        bs.add(6, 6);
        bs.add(7, 7);
        bs.remove(4);
        System.out.println(bs.porNiveles());
    }
}
