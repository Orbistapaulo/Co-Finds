package com.example.androidapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtility {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int cornerRadius, int rectWidth, int rectHeight) {
        float maxCornerRadius = Math.min(rectWidth, rectHeight) / 3f;
        cornerRadius = (int) Math.min(cornerRadius, maxCornerRadius);
        Bitmap output = Bitmap.createBitmap(rectWidth, rectHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, rectWidth, rectHeight);
        final RectF rectF = new RectF(rect);
        int left = (bitmap.getWidth() - rectWidth) / 2;
        int top = (bitmap.getHeight() - rectHeight) / 2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.RED);
        Path path = new Path();
        path.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, left, top, rectWidth, rectHeight);
        canvas.drawBitmap(croppedBitmap, 0, 0, paint);

        return output;
    }
}
