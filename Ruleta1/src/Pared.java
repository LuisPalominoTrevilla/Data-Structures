import java.awt.Color;

public class Pared extends Cuadro{

    public Pared(int x, int y) {
        this(x, y, 50);
    }
    
    public Pared(int x, int y, int size){
        super(x, y, size);
        this.setColor("#3a71c9");
    }

}
