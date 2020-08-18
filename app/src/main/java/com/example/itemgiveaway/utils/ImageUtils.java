package com.example.itemgiveaway.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class ImageUtils {
    public String viewToString(View view) {
        String res = null;
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
        byte[] bytes = bos.toByteArray();
        res = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
        return res;
    }

    public Bitmap stringToBitmap(String img) {
        byte[] bytes = Base64.decode(img,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
