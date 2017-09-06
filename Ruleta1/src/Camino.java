import java.awt.Color;

public class Camino extends Cuadro{
    
    QueueArray<Integer> path;
    
    public Camino(int x, int y){
        this(x, y, 50);
    }
    
    public Camino(int x, int y, int size) {
        super(x, y, size);
        this.path = new QueueArray<Integer>();
    }
    
    public QueueArray<Integer> getPath(){
        return this.path;
    }
    
    public void addPath(int[] newPath){
        for(int coordinate: newPath){
            path.enQueue(coordinate);
        }
    }
    
    public void changeColor(String color){
        this.setColor(color);
    }
    
}
