import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.ColorConvertOp;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.WritableRaster;

public class myCV
{
	final public static BufferedImage readImage( String filename )
	{
		try
		{
			return ImageIO.read(new File(filename));
		}catch (IOException e)
		{
			return null;
		}
	}
	
	public int savePNG(String filename, BufferedImage internImage)
	{
		try
		{
			ImageIO.write(internImage, "PNG", new File(filename));
		}catch (IOException e)
		{
			return 0;
		}
		return 1;
	}

	final public static BufferedImage convertToGrayScale(BufferedImage image,int newType) 
	{
		try
		{
			BufferedImage raw_image = image;
			image =new BufferedImage(
										raw_image.getWidth(),
										raw_image.getHeight(),
										newType
									);
			ColorConvertOp xformOp = new ColorConvertOp(null);
			xformOp.filter(raw_image, image);
		}
		catch (Exception e){}
		return image;
	}
	
	/**
	* Resizes an image using a Graphics2D object backed by a BufferedImage.
	* @param srcImg - source image to scale
	* @param w - desired width
	* @param h - desired height
	* @return - the new resized image
	* https://stackoverflow.com/questions/16497853/scale-a-bufferedimage-the-fastest-and-easiest-way
	*/
	final public static BufferedImage resizeImage(BufferedImage src, int w, int h){
		int finalw = w;
		int finalh = h;
		double factor = 1.0d;
		if(src.getWidth() > src.getHeight()){
			factor = ((double)src.getHeight()/(double)src.getWidth());
			finalh = (int)(finalw * factor);                
		}else{
			factor = ((double)src.getWidth()/(double)src.getHeight());
			finalw = (int)(finalh * factor);
		}   

		BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(src, 0, 0, finalw, finalh, null);
		g2.dispose();
		return resizedImg;
	}
	
	public int[] getRGBPixel(BufferedImage rgbImage, int x, int y)
	{
		int rgb = rgbImage.getRGB(x, y);
		return new int[] {(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF,(rgb & 0xFF)};
	}
	
	public int getGrayPixel(BufferedImage grayImage, int x, int y)
	{		
		try
		{
			WritableRaster wr 	= grayImage.getRaster();
			return wr.getSample(x, y, 0);
		}
		catch(Exception e){System.out.println("ERROR");return 255;}
	}


}
