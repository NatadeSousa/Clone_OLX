package com.example.clone_olx.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

public class OrientationFixer {



    public static Bitmap fixOrientation(String chosenPicturePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(chosenPicturePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientationCode = Integer.parseInt(orientation);
        int degree = 0;

        switch(orientationCode){
            case ExifInterface.ORIENTATION_NORMAL:
                degree = 0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
        }

        return openPictureAndRotate(chosenPicturePath,degree);
    }

    private static Bitmap openPictureAndRotate(String chosenPicturePath, int degree) {
        Bitmap bitmap;

        bitmap = BitmapFactory.decodeFile(chosenPicturePath);


        Matrix matrix = new Matrix(); matrix.postRotate(degree);


        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
