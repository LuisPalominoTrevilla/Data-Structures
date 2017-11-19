import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class test {

    public static void main(String[] args) {
        myCV mycv = new myCV();
        
        BufferedImage conejito = mycv.readImage("conejo.png");
        int W, H;
        W = conejito.getWidth();
        H = conejito.getHeight();
        
        BufferedImage resizedConejito = mycv.resizeImage(conejito, (int)(W*.05), (int)(H*.05));
        resizedConejito = mycv.convertToGrayScale(resizedConejito, BufferedImage.TYPE_BYTE_GRAY);
        mycv.savePNG("conejitoResizeado.png", resizedConejito);
        
        int sum = 0;
        int n = 0;
        for(int y = 0; y < resizedConejito.getHeight(); y++){
            for(int x = 0; x < resizedConejito.getWidth(); x++){
                n++;
                sum += mycv.getGrayPixel(resizedConejito, x, y);
            }
        }
        System.out.println(sum);
        System.out.println(n);
        System.out.println(sum/n);
    }

}
