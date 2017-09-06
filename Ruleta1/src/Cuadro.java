import java.awt.Color;

public class Cuadro{
    private Color color;
    private int x, y;
    private int side;
    public static final String colorStart = "#e88420";
    public static final String colorEnd = "#0aa84c";
    public static final String colorSearch = "#bf3a1c";
    public static final String colorPath = "#0aa84c";
    public static final String colorLine = "#848484";
    
    public Cuadro(int x, int y){
        this(x, y, 50);
    }
    
    public Cuadro(int x, int y, int size){
        color = Color.decode("#c6c119");
        this.x = x;
        this.y = y;
        this.side = size;
    }
    
    public Color getColor(){
        return this.color;
    }
    
    public void setColor(String color){
        this.color = Color.decode(color);
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getSide(){
        return this.side;
    }

}
