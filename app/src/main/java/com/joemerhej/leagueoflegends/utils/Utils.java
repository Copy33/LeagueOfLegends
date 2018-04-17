package com.joemerhej.leagueoflegends.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;

import com.joemerhej.leagueoflegends.R;

/**
 * Created by Joe Merhej on 4/15/18.
 */

public final class Utils
{
    private Utils()
    {
    }

    // creates a bitmap from a font, to use in widgets
    public static Bitmap getFontBitmap(Context context, String text, int color, float fontSizeSP)
    {
        int fontSizePX = convertDPtoPX(context, fontSizeSP);
        int pad = (fontSizePX / 9);
        Paint paint = new Paint();
        Typeface typeface = ResourcesCompat.getFont(context, R.font.myfontbold);
        paint.setAntiAlias(true);
        paint.setTypeface(typeface);
        paint.setColor(color);
        paint.setTextSize(fontSizePX);

        int textWidth = (int) (paint.measureText(text) + pad * 2);
        int height = (int) (fontSizePX / 0.75);
        Bitmap bitmap = Bitmap.createBitmap(textWidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        float xOriginal = pad;
        canvas.drawText(text, xOriginal, fontSizePX, paint);
        return bitmap;
    }

    // converts dp unit to pixels
    public static int convertDPtoPX(Context context, float dip)
    {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return value;
    }

    public static String getApiKey()
    {
        return "RGAPI-143b7936-1f8e-4565-b245-32ccbc6f6f8e";
    }

}
