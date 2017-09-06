import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame{
    
    private Maze sm;
    private ControlPanel cp;
    
    public Window(){
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.cp = new ControlPanel();
        this.sm = new Maze(this.cp);
        
        this.cp.addMaze(this.sm);
        
        this.add(this.sm, BorderLayout.WEST);
        this.add(this.cp);
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Window();

    }

}
