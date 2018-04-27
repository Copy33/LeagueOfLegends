package com.joemerhej.leagueoflegends.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.enums.RegionCode;
import com.joemerhej.leagueoflegends.models.Region;

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
        return "RGAPI-73c4015b-d970-41ed-9323-d18bda55f00a";
    }

    public static Region getRegionFromCode(RegionCode regionCode)
    {
        switch(regionCode)
        {
            case NA:
                return Regions.NORTH_AMERICA;
            case KR:
                return Regions.KOREA;
            case JP:
                return Regions.JAPAN;
            case EUW:
                return Regions.EUROPE_WEST;
            case EUNE:
                return Regions.EUROPE_NORDIC_AND_EAST;
            case OCE:
                return Regions.OCEANIA;
            case BR:
                return Regions.BRAZIL;
            case LAS:
                return Regions.LATIN_AMERICA_SOUTH;
            case LAN:
                return Regions.LATIN_AMERICA_NORTH;
            case RU:
                return Regions.RUSSIA;
            case TR:
                return Regions.TURKEY;
            case PBE:
                return Regions.PUBLIC_BETA;
            default:
                return Regions.NORTH_AMERICA;
        }
    }

    public static int getColorIdFromSelectedRadioImageDrawableId(int selectedRadioImageDrawableId)
    {
        switch(selectedRadioImageDrawableId)
        {
            case R.drawable.radio_black_selected:
                return R.color.background_black;
            case R.drawable.radio_gray_selected:
                return R.color.background_gray;
            case R.drawable.radio_white_selected:
                return R.color.background_white;
            case R.drawable.radio_transparent_selected:
                return R.color.background_transparent;
            default:
                return R.color.background_gray;
        }
    }

}












