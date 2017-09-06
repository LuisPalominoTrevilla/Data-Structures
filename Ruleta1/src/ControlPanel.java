import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ControlPanel extends JPanel{
    
    private Maze maze;
    private JButton resetMaze;
    private JSlider slider;
    private JTextArea notesArea;
    private JRadioButton randomRB;
    private JRadioButton customRB;
    private JPanel randomJP;
    private JPanel customJP;
    private JComboBox wallOptions;
    private String filename;    // Usado cuando el usuario quiere usar su propio laberinto
    private JFileChooser fc;
    
    public ControlPanel(){
        super();
        this.setPreferredSize(new Dimension(400, 600));
        this.setBackground(Color.decode("#e3f7ed"));
        this.resetMaze = new JButton("Generar nuevo laberinto");
        
        this.resetMaze.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if(ControlPanel.this.maze.getState() != 4){
                    if(ControlPanel.this.randomRB.isSelected()){
                        // Cambiar la aglomeracion de bloques
                        switch((String)ControlPanel.this.wallOptions.getSelectedItem()){
                        case "60%":
                            ControlPanel.this.maze.setWallAglomeration(4);
                            break;
                        case "70%":
                            ControlPanel.this.maze.setWallAglomeration(3);
                            break;
                        case "50%":
                            ControlPanel.this.maze.setWallAglomeration(5);
                            break;
                        }
                        ControlPanel.this.maze.reset();
                    }else{
                        ControlPanel.this.maze.reset(ControlPanel.this.filename);
                    }
                }
            }
        });
        this.slider = new JSlider(SwingConstants.HORIZONTAL, 0, 150, 75);
        this.slider.setPaintTicks(true);    // Rayitas que graduan el slider
        this.slider.setPaintLabels(true);   // Valores del slider aparezcan
        this.slider.setMajorTickSpacing(25);
        this.slider.setMinorTickSpacing(5);
        this.slider.setBackground(Color.decode("#e3f7ed"));
        this.slider.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                ControlPanel.this.maze.setVelocidad(ControlPanel.this.slider.getValue());
            }
        });
        JLabel noteLabel = new JLabel("Notas:");
        noteLabel.setFont(new Font("normal", Font.BOLD, 20));
        this.notesArea = new JTextArea(5, 30);
        // Radio Buttons
        this.randomRB = new JRadioButton("Laberinto generado aleatoriamente", true);
        this.customRB = new JRadioButton("Laberinto personalizado");
        this.randomRB.setBackground(Color.decode("#e3f7ed"));
        this.customRB.setBackground(Color.decode("#e3f7ed"));
        ButtonGroup bg = new ButtonGroup();
        this.randomRB.addActionListener(new ActionListener() {
             
            public void actionPerformed(ActionEvent e) {
                ControlPanel.this.randomJP.setVisible(true);
                ControlPanel.this.customJP.setVisible(false);
                ControlPanel.this.resetMaze.setEnabled(true);
            }
        });
        this.customRB.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                ControlPanel.this.randomJP.setVisible(false);
                ControlPanel.this.customJP.setVisible(true);
                if(ControlPanel.this.filename == null){
                    ControlPanel.this.resetMaze.setEnabled(false);
                }
            }
        });
        bg.add(this.randomRB);
        bg.add(this.customRB);
        // Panel de generacion random
        this.randomJP = new JPanel();
        this.randomJP.setPreferredSize(new Dimension(400, 100));
        String[] wallNumber = {"70%", "60%", "50%"};
        this.wallOptions = new JComboBox(wallNumber);
        this.wallOptions.setSelectedIndex(1);
        this.randomJP.add(new JLabel("Selecciona el porcentaje de aglomeracion de paredes en el laberinto"));
        this.randomJP.add(this.wallOptions);
        this.randomJP.setBackground(Color.decode("#e8e8b0"));
        
        // Panel de generacion customizada
        this.customJP = new JPanel();
        this.customJP.setPreferredSize(new Dimension(400, 100));
        this.customJP.setVisible(false);    // Originalmente empieza no visible
        this.customJP.add(new JLabel("Subir archivo con laberinto .txt"));
        this.customJP.add(new JLabel("Debe tener medidas 20x20 y no tener errores"));
        JButton chooseFile = new JButton("Selecciona un archivo");
        chooseFile.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                int returnVal = ControlPanel.this.fc.showOpenDialog(ControlPanel.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    ControlPanel.this.filename = ControlPanel.this.fc.getSelectedFile().toString();
                    ControlPanel.this.resetMaze.setEnabled(true);
                }
                
            }
        });
        this.fc = new JFileChooser(new File(System.getProperty("user.dir")));
        this.fc.setFileFilter(new FileNameExtensionFilter("text file", "txt"));
        
        this.customJP.add(chooseFile);
        this.customJP.setBackground(Color.decode("#bcdae5"));
        
        // Panel de velocidad
        JPanel velocityPanel = new JPanel();
        velocityPanel.setPreferredSize(new Dimension(300, 100));
        JLabel velLabel = new JLabel("Cambiar velocidad en milisegundos:");
        velLabel.setFont(new Font("normal", Font.ITALIC, 16));
        velocityPanel.setBackground(Color.decode("#e3f7ed"));
        velocityPanel.add(velLabel);
        velocityPanel.add(this.slider);
        
        this.add(noteLabel);
        this.add(this.notesArea);
        this.add(this.randomRB);
        this.add(this.customRB);
        this.add(this.randomJP);
        this.add(this.customJP);
        this.add(this.resetMaze);
        this.add(velocityPanel);
    }
    
    public void setNotesText(String text){
        this.notesArea.setText(text);
    }
    
    public void addMaze(Maze maze){
        this.maze = maze;
    }
}
