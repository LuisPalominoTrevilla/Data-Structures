import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelImagen extends JPanel{
    
    private String path;
    private JPanel imageSelectorPanel, imageDisplayPanel;
    private Image displayImage;
    private AVLTree<Integer, String> imgDatabase;
    private JButton btSelectImage, btSearchImage;
    private JFileChooser fc;
    private Integer imgToSearch;
    
    public PanelImagen(String dirName){
        super();
        this.setPreferredSize(new Dimension(800, 600));
        this.path = dirName;
        this.imgDatabase = new AVLTree<>();
        this.imageSelectorPanel = new JPanel();
        this.imageSelectorPanel.setPreferredSize(new Dimension(300, 600));
        this.imageSelectorPanel.setBackground(Color.decode("#d6d2cb"));
        this.imageDisplayPanel = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                if(PanelImagen.this.displayImage != null){
                    g.drawImage(PanelImagen.this.displayImage, 0, 0,PanelImagen.this.imageDisplayPanel.getWidth(), PanelImagen.this.imageDisplayPanel.getHeight() ,PanelImagen.this);
                }
            }
        };
        this.imageDisplayPanel.setPreferredSize(new Dimension(500, 600));
        this.imageDisplayPanel.setBackground(Color.WHITE);
        
        // Create buttons
        this.btSelectImage = new JButton("Seleccionar Imagen");
        this.btSelectImage.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = PanelImagen.this.fc.showOpenDialog(PanelImagen.this);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    String img = PanelImagen.this.fc.getSelectedFile().toString();
                    PanelImagen.this.imgToSearch = PanelImagen.this.getHashCode(img);
                    PanelImagen.this.btSearchImage.setEnabled(true);
                }
            }
        });
        this.fc = new JFileChooser();
        
        this.btSearchImage = new JButton("Buscar Imagen");
        this.btSearchImage.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Buscar la imagen en el arbol AVL
                String pathToImage = PanelImagen.this.imgDatabase.get(PanelImagen.this.imgToSearch);
                if(pathToImage != null){        // Si se encontro la imagen
                    PanelImagen.this.paintImage(pathToImage);
                }else{                          // No se encontro la imagen
                    PanelImagen.this.paintImage("notfound.png");
                }
            }
        });
        this.btSearchImage.setEnabled(false);
        
        this.imageSelectorPanel.add(new JLabel("Seleccione una imagen que quiera buscar"));
        this.imageSelectorPanel.add(this.btSelectImage);
        this.imageSelectorPanel.add(this.btSearchImage);
        this.loadImages();
        
        this.add(this.imageSelectorPanel);
        this.add(this.imageDisplayPanel);
    }
    
    private void paintImage(String imgPath){
        this.displayImage = new ImageIcon(imgPath).getImage();
        this.imageDisplayPanel.repaint();
    }
    
    private void loadImages(){
        
        String[] files = new File(this.path).list();
        
        for(String file:files){
            int sum = this.getHashCode(this.path+file);
            this.imgDatabase.add(sum, this.path+file);
        }
        System.out.println(this.imgDatabase.size());
    }
    
    /**
     * computes hash code of image
     * @return returns the hash code of an image
     */
    private int getHashCode(String imageFile){
        myCV cv = new myCV();
        BufferedImage img = cv.readImage(imageFile);
        img = cv.resizeImage(cv.convertToGrayScale(img, BufferedImage.TYPE_BYTE_GRAY), 20, 20);
        int sum = 0;
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                sum += cv.getGrayPixel(img, x, y);
            }
        }
        return sum;
    }
}
