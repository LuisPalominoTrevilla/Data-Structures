import java.util.EmptyStackException;

public class StackArray <V>{
    private V[] stack;
    private int top;
    
    public StackArray(){
        this.stack = (V[])(new Object[10]);
        this.top = 0;
    }
    
    public void push(V value){
        if(this.top != this.stack.length){
            // Checar si el stack no esta lleno
            this.stack[top] = value;
            this.top++;
        }else{
            V[] tmp = (V[])new Object[this.stack.length*2];
            for(int i = 0; i < top; i++){
                tmp[i] = this.stack[i];
            }
            this.stack = tmp;
            this.top++;
        }
    }
    
    public V pop(){
        if(!this.isEmpty()){
            V val = this.stack[this.top-1];
            this.stack[this.top-1] = null;
            top--;
            return val;
        }else{
            throw new EmptyStackException();
        }
    }
    
    public boolean isEmpty(){
        return this.top == 0;
    }
    
    public int size(){
        return this.top;
    }
    
    public V peek(){
        if(!this.isEmpty()){
            return this.stack[this.top-1];
        }else{
            return null;
        }
    }
    
    public boolean contains(V value){
        for(int i = 0; i < this.top; i++){
            if(this.stack[i] == value){
                return true;
            }
        }
        return false;
    }
    
    public String toString(){
        String resultado = "[";
        for(int i = 0; i < this.stack.length; i++){
            if(this.stack[i] == null){
                return resultado + "]";
            }
            resultado += this.stack[i] + ", ";
        }
        return resultado + "]";
    }
}
