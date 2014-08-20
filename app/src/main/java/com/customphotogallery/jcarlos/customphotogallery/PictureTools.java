package com.customphotogallery.jcarlos.customphotogallery;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

public class PictureTools {
	
	private static final String TAG = "PictureTools";

	private static int getCameraPhotoOrientation(Uri imageUri, String imagePath){
		int rotate = 0;
		try {
			File imageFile = new File(imagePath);
			ExifInterface exif = new ExifInterface(
					imageFile.getAbsolutePath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			}
			
			Log.v(TAG, "Exif orientation: " + orientation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotate;
	}
		
	public static Bitmap decodeSampledBitmapFromUri(String dir, int Width, int Height) 
	{   
		Bitmap rotatedBitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(dir, options);
			
			options.inSampleSize = calculateInSampleSize(options, Width, Height);
			options.inJustDecodeBounds = false;
			Bitmap bitmap = BitmapFactory.decodeFile(dir, options);
			Uri pictureUri = Uri.parse(dir);
			Matrix matrix = new Matrix();
			matrix.postRotate(PictureTools.getCameraPhotoOrientation(pictureUri, dir));
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} catch(Exception e) {
			return null;
		}    
		return rotatedBitmap;   
	}
	
	private static int calculateInSampleSize( BitmapFactory.Options options, int Width, int Height) 
	{
		final int height = options.outHeight;
		final int width = options.outWidth;
		int size_inicialize = 1;
		
		if (height > Height || width > Width) 
		{
			if (width > height) 
			{		
				size_inicialize = Math.round((float)height / (float)Height);    
			} 
			else 
			{
				size_inicialize = Math.round((float)width / (float)Width);    
			}   
		}
		return size_inicialize;    
	}
}
