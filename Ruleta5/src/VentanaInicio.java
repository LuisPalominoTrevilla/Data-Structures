import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaInicio extends JFrame{

    private JButton btPathChooser, start;
    private String dirName;
    private JFileChooser fc;
    private JLabel info;
    
    public VentanaInicio(){
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 200));
        
        this.btPathChooser = new JButton("Abrir");
        this.btPathChooser.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = VentanaInicio.this.fc.showOpenDialog(VentanaInicio.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    VentanaInicio.this.dirName = VentanaInicio.this.fc.getSelectedFile().toString() + "\\";
                    VentanaInicio.this.info.setText("Directorio:" + VentanaInicio.this.dirName);
                    VentanaInicio.this.start.setEnabled(true);
                }
            }
        });
        this.fc = new JFileChooser();
        this.fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.info = new JLabel();
        
        this.start = new JButton("Comenzar");
        this.start.setEnabled(false);
        this.start.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                 new Window(VentanaInicio.this.dirName);
                 VentanaInicio.this.dispose();
            }
        });
        
        panel.add(new JLabel("Elija un directorio de Imagenes"));
        panel.add(this.btPathChooser);
        panel.add(this.start);
        panel.add(this.info);
        
        this.add(panel);
        this.pack();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new VentanaInicio();
    }
}
