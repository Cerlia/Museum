package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class ImageConversion {
	
	/**
	 * convertit un tableau d'octets en image
	 * @param imgData
	 * @return
	 * @throws IOException
	 */
	public static Image byteArrayToImage(byte[] imgData) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
		Image artImage = new Image(bais);
		return artImage;	         	
	}
	
	/**
	 * convertit une image en tableau d'octets
	 * @param imgFile
	 * @return
	 * @throws IOException 
	 */
	public static byte[] imageToByteArray(File imgFile) throws IOException {
		BufferedImage bImage = ImageIO.read(imgFile);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(bImage, "jpg", baos);
	    byte[] imgData = baos.toByteArray();
	    baos.flush();
	    return imgData;    
	}
}
