
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
    
    private TreeNode<K, V> min(TreeNode<K, V> node){
        if(node == null){
            return null;
        }
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
    
    public V search(K key){
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
        bs.add(4, 4);
        bs.add(2, 2);
        bs.add(3, 3);
        bs.add(7, 7);
        bs.add(6, 6);
        bs.add(9, 9);
        bs.add(5, 5);
        bs.remove(2);
        System.out.println(bs.inorder());
    }
}
