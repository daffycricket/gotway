package android.nla.org.gotway.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import com.reb.hola.util.ViewUtil;

public class TemperatureView
  extends View
{
  private final float LINE_CIRCLE_SCALE = 0.8F;
  private final int MAX_TEMPER = 80;
  private final int MIN_TEMPER = -30;
  private final float STROKE_WIDTH_SCALE = 0.125F;
  private ObjectAnimator anim;
  private int h;
  private Paint mBgPaint;
  private float mCenterX;
  private float mCenterY;
  private LinearGradient mLinearGradient;
  private int mMainColor = -1;
  private RadialGradient mRadialGradientBig;
  private RadialGradient mRadialGradientSmall;
  private float mRadius;
  private float mSmallRadius;
  private float mStokeWidth;
  private int mTemper = 0;
  private int mTemperColor = -65536;
  private LinearGradient mTemperGradient;
  private Paint mTemperPaint;
  private Path mTemperPath;
  private float mTemperTop;
  private int mTextColor = -1;
  private Paint mTextPaint;
  private float mTextSize = 28.0F;
  private float mTextTop;
  private int w;
  private float y0;
  private float yScale;
  
  public TemperatureView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void drawBg(Canvas paramCanvas)
  {
    this.mBgPaint.setStyle(Style.STROKE);
    paramCanvas.drawPath(this.mTemperPath, this.mBgPaint);
    float f1 = (this.y0 - this.mTemperTop) / 5.0F;
    float f2 = this.mCenterX;
    float f3 = this.mSmallRadius;
    float f4 = this.mCenterX;
    float f5 = this.mSmallRadius;
    int i = 1;
    for (;;)
    {
      if (i >= 5)
      {
        this.mBgPaint.setStyle(Style.FILL);
        this.mBgPaint.setAlpha(220);
        i = paramCanvas.saveLayer(0.0F, 0.0F, this.w, this.h, this.mBgPaint, 31);
        this.mBgPaint.setShader(this.mRadialGradientBig);
        paramCanvas.drawCircle(this.mCenterX, this.mCenterY, this.mRadius, this.mBgPaint);
        this.mBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.mBgPaint.setShader(this.mLinearGradient);
        paramCanvas.drawRect(this.mCenterX - this.mSmallRadius * 0.8F, this.mTemperTop, this.mCenterX + this.mSmallRadius * 0.8F, this.y0 - 0.2F * this.mRadius, this.mBgPaint);
        paramCanvas.restoreToCount(i);
        this.mBgPaint.setXfermode(null);
        this.mBgPaint.setShader(this.mRadialGradientSmall);
        paramCanvas.drawArc(new RectF(this.mCenterX - this.mSmallRadius * 0.8F, this.mTemperTop - this.mSmallRadius * 0.8F, this.mCenterX + this.mSmallRadius * 0.8F, this.mTemperTop + this.mSmallRadius * 0.8F), 180.0F, 180.0F, false, this.mBgPaint);
        this.mBgPaint.setShader(null);
        this.mBgPaint.setAlpha(255);
        return;
      }
      float f6 = this.y0 - i * f1;
      paramCanvas.drawLine(f2 + f3 * 0.8F, f6, f4 - f5 * 0.3F, f6, this.mBgPaint);
      i += 1;
    }
  }
  
  private void drawTemper(Canvas paramCanvas)
  {
    this.mTemperPaint.setStrokeWidth(1.0F);
    paramCanvas.drawCircle(this.mCenterX, this.mCenterY, this.mRadius, this.mTemperPaint);
    this.mTemperPaint.setStrokeWidth(this.mSmallRadius * 0.8F * 2.0F);
    paramCanvas.drawLine(this.mCenterX, this.mCenterY, this.mCenterX, this.mCenterY - this.mSmallRadius - (this.mTemper + 30) * this.yScale, this.mTemperPaint);
  }
  
  private void init()
  {
    this.mBgPaint = new Paint(1);
    this.mBgPaint.setColor(this.mMainColor);
    this.mBgPaint.setStrokeJoin(Join.ROUND);
    this.mTextPaint = new Paint(1);
    this.mTextPaint.setTextAlign(Align.CENTER);
    this.mTextPaint.setColor(this.mTextColor);
    this.mTemperPaint = new Paint(1);
    this.mTemperPaint.setColor(this.mTemperColor);
    this.mTemperPaint.setStyle(Style.FILL);
    this.mTemperPaint.setStrokeCap(Cap.ROUND);
    this.mTemperPath = new Path();
  }
  
  private void initGradient()
  {
    int i = this.mMainColor;
    float f1 = this.mCenterX;
    float f2 = this.mTemperTop;
    float f3 = this.mSmallRadius;
    Object localObject = new int[3];
    localObject[2] = i;
    Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
    this.mRadialGradientSmall = new RadialGradient(f1, f2, f3 * 0.8F, (int[])localObject, new float[] { 0.0F, 0.08F, 1.0F }, localTileMode);
    f1 = this.mCenterX;
    f2 = this.mCenterY;
    f3 = this.mRadius;
    localObject = new int[3];
    localObject[2] = i;
    localTileMode = Shader.TileMode.CLAMP;
    this.mRadialGradientBig = new RadialGradient(f1, f2, f3, (int[])localObject, new float[] { 0.2F, 0.3F, 1.0F }, localTileMode);
    f1 = this.mCenterX;
    f2 = this.mSmallRadius;
    f3 = this.mCenterX;
    float f4 = this.mSmallRadius;
    localObject = new int[3];
    localObject[0] = i;
    localObject[2] = i;
    this.mLinearGradient = new LinearGradient(f1 - f2 * 0.8F, 0.0F, f4 * 0.8F + f3, 0.0F, (int[])localObject, null, Shader.TileMode.CLAMP);
    f1 = this.mCenterY;
    f2 = this.mRadius;
    f3 = this.mTemperTop;
    localObject = Shader.TileMode.CLAMP;
    this.mTemperGradient = new LinearGradient(0.0F, f2 + f1, 0.0F, f3, new int[] { -16698673, -14288878, -65536 }, null, (Shader.TileMode)localObject);
    this.mTemperPaint.setShader(this.mTemperGradient);
  }
  
  private void setPath()
  {
    float f1 = this.mCenterX - this.mSmallRadius * 0.8F;
    float f2 = this.mCenterX + this.mSmallRadius * 0.8F;
    this.mTemperPath.moveTo(f1, this.y0 - this.mRadius * 0.268F);
    this.mTemperPath.lineTo(f1, this.mTemperTop);
    float f3 = this.mSmallRadius * 0.8F;
    RectF localRectF = new RectF(f1, this.mTemperTop - f3, f2, this.mTemperTop + f3);
    this.mTemperPath.addArc(localRectF, 180.0F, 180.0F);
    this.mTemperPath.lineTo(f2, this.y0 - this.mRadius * 0.268F);
    localRectF = new RectF(this.mCenterX - this.mRadius, this.mCenterY - this.mRadius, this.mCenterX + this.mRadius, this.mCenterY + this.mRadius);
    this.mTemperPath.addArc(localRectF, -90.0F + (float)30.0D, (float)(360.0D - 2.0D * 30.0D));
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.drawText(this.mTemper + "℃", this.mCenterX, this.h - getPaddingBottom(), this.mTextPaint);
    drawTemper(paramCanvas);
    drawBg(paramCanvas);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.w = paramInt1;
    this.h = paramInt2;
    float f = (int)(paramInt2 / 4.0F);
    this.mStokeWidth = (0.125F * f);
    this.mRadius = (f / 2.0F - this.mStokeWidth);
    this.mSmallRadius = (this.mRadius * 0.6F);
    this.mTextSize = (paramInt2 / 13.0F);
    this.mTextPaint.setTextSize(this.mTextSize);
    this.mTextTop = (paramInt2 - getPaddingBottom() - ViewUtil.getTextHeight(this.mTextPaint) - paramInt2 / 19.0F);
    this.y0 = (this.mTextTop - this.mRadius - this.mSmallRadius);
    this.mTemperTop = (getPaddingTop() + this.mSmallRadius * 0.8F);
    this.yScale = ((this.y0 - this.mTemperTop) / 110.0F);
    this.mCenterX = (paramInt1 / 2);
    this.mCenterY = (this.mTextTop - this.mRadius);
    setPath();
    initGradient();
  }
  
  protected void setTemper(int paramInt)
  {
    this.mTemper = paramInt;
    invalidate();
  }
  
  public void startAnim(int paramInt, long paramLong)
  {
    if (this.anim != null) {
      this.anim.cancel();
    }
    paramInt = Math.max(-30, Math.min(80, paramInt));
    this.anim = ObjectAnimator.ofInt(this, "temper", new int[] { this.mTemper, paramInt }).setDuration(paramLong);
    this.anim.start();
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ui\view\TemperatureView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */