package utils;

import java.io.File;

import javax.imageio.ImageIO;

public class InputCheck {
	
	/**
	 * check if file contains image data
	 * @param file
	 * @return true if file contains image data, or false if not
	 */
	public static boolean CheckType(File file) {
		boolean result = false;
		try {
			if(ImageIO.read(file) != null)
	            result = true;
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}	
	
	/**
	 * check if file size does not exceed given size (in bytes)
	 * @param file
	 * @param size
	 * @return true if file does not exceed given size
	 */
	public static boolean CheckFileSize(File file, int size) {
		boolean result = false;
		if (file.length() <= size) {
			result = true;
		}		
		return result;
	}
}
