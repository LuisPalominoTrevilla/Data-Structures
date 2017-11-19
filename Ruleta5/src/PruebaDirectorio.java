import java.awt.image.BufferedImage;
import java.io.File;

public class PruebaDirectorio {

    public static void main(String[] args) {
        myCV cv = new myCV();
        String[] files = {"C:\\Users\\Luis Palomino\\Desktop\\imagensita.jpg", "C:\\Users\\Luis Palomino\\Desktop\\img.jpg"};
        
        for(String file:files){
            BufferedImage img = cv.convertToGrayScale(cv.readImage(file), BufferedImage.TYPE_BYTE_GRAY);
            int sum = 0;
            int n = 0;
            for(int y = 0; y < img.getHeight(); y++){
                for(int x = 0; x < img.getWidth(); x++){
                    n++;
                    sum += cv.getGrayPixel(img, x, y);
                }
            }
            System.out.println(sum);
        }
    }

}
