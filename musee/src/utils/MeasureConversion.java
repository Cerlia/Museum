package utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class MeasureConversion {
	
	/**
	 * convertit un texte (float) en mesure en mètres sous forme de int
	 * @param texte
	 */
	public static int textToInt(String texte) {	
		float dimFlt = Float.parseFloat(texte)*100;
		return (int)dimFlt;
	}
	
	/**
	 * convertit une mesure en cm (int) en texte
	 * @param texte
	 */
	public static String intToString(int dim) {
		float dmFloat = (float)dim/100;
		return dmFloat+"";
	}
}
