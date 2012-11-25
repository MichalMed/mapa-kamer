package cz.mapakamer.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.util.Base64;

public class ImageUtility {

	public static String encodeImagetoBase64(Bitmap bitmap) {

		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, bao);
		byte[] ba = bao.toByteArray();
		return Base64.encodeToString(ba, 0);
		
	}
	
	
}
