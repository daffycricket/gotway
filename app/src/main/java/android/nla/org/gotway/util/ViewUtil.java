package android.nla.org.gotway.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class ViewUtil
{
  public static Bitmap blur(Bitmap paramBitmap, Context paramContext)
  {
    Object localObject = RenderScript.create(paramContext);
    Allocation allocation = Allocation.createFromBitmap((RenderScript) localObject, paramBitmap);
    localObject = ScriptIntrinsicBlur.create((RenderScript)localObject, allocation.getElement());
    ((ScriptIntrinsicBlur)localObject).setInput(allocation);
    ((ScriptIntrinsicBlur)localObject).setRadius(25.0F);
    ((ScriptIntrinsicBlur)localObject).forEach(allocation);
    allocation.copyTo(paramBitmap);
    return paramBitmap;
  }
  
  public static int getColorBetweenAB(int paramInt1, int paramInt2, float paramFloat, int paramInt3)
  {
    float f1 = (((paramInt2 & 0xFF0000) >> 16) - ((paramInt1 & 0xFF0000) >> 16)) / paramFloat;
    float f2 = paramInt3;
    float f3 = (paramInt1 & 0xFF0000) >> 16;
    float f4 = ((paramInt2 & 0xFF00) - (paramInt1 & 0xFF00) >> 8) / paramFloat;
    float f5 = paramInt3;
    float f6 = (paramInt1 & 0xFF00) >> 8;
    paramFloat = ((paramInt2 & 0xFF) - (paramInt1 & 0xFF)) / paramFloat;
    float f7 = paramInt3;
    float f8 = paramInt1 & 0xFF;
    return Color.rgb((int)(f1 * f2 + f3), (int)(f4 * f5 + f6), (int)(paramFloat * f7 + f8));
  }
  
  public static float getTextHeight(Paint paramPaint)
  {
    Paint.FontMetrics fontMetrics = paramPaint.getFontMetrics();
    return fontMetrics.bottom - fontMetrics.top;
  }
  
  public static float getTextRectHeight(Paint paramPaint, String paramString)
  {
    Rect localRect = new Rect();
    paramPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
    return localRect.height();
  }
  
  public static float getTextRectWidth(Paint paramPaint, String paramString)
  {
    Rect localRect = new Rect();
    paramPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
    return localRect.width();
  }
  
  public static float px2Dp(int paramInt, Context paramContext)
  {
    return paramInt * paramContext.getResources().getDisplayMetrics().density;
  }
}