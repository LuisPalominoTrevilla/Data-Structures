import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame{
    
    public Window(String dirName){
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        PanelImagen PI = new PanelImagen(dirName);
        
        this.add(PI);
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setResizable(false);
        this.setVisible(true);
    }
}
