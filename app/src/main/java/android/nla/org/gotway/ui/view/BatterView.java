package android.nla.org.gotway.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import com.reb.hola.util.ViewUtil;

public class BatterView
  extends View
{
  private final float STROKE_WIDTH_SCALE = 0.033333335F;
  private ObjectAnimator anim;
  private float mCenterX;
  private int mPower = 0;
  private int mPowerColor = -14288878;
  private Paint mPowerPaint;
  private int mRectColor = -1;
  private RectF mRectF = new RectF();
  private Paint mRectPaint;
  private int[] mShadeColor;
  private Paint mShadePaint;
  private float mStrokeWidth;
  private Paint mTextPaint;
  private float mTextSize = 28.0F;
  private float yScale;
  
  public BatterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = new int[3];
    paramContext[0] = -12303292;
    paramContext[2] = -12303292;
    this.mShadeColor = paramContext;
    init();
  }
  
  private void drawBg(Canvas paramCanvas)
  {
    this.mRectPaint.setStrokeWidth(this.mStrokeWidth);
    paramCanvas.drawRoundRect(this.mRectF, this.mStrokeWidth * 2.0F, this.mStrokeWidth * 2.0F, this.mRectPaint);
    this.mRectPaint.setStrokeWidth(this.mStrokeWidth * 2.0F);
    float f1 = this.mCenterX;
    float f2 = this.mStrokeWidth;
    float f3 = this.mRectF.top;
    float f4 = this.mStrokeWidth;
    float f5 = this.mCenterX;
    paramCanvas.drawLine(f1 - f2 * 4.0F, f3 - f4, this.mStrokeWidth * 4.0F + f5, this.mRectF.top - this.mStrokeWidth, this.mRectPaint);
  }
  
  private void init()
  {
    this.mRectPaint = new Paint(1);
    this.mRectPaint.setColor(this.mRectColor);
    this.mRectPaint.setStyle(Style.STROKE);
    this.mShadePaint = new Paint(1);
    this.mPowerPaint = new Paint(1);
    this.mPowerPaint.setColor(this.mPowerColor);
    this.mTextPaint = new Paint(1);
    this.mTextPaint.setColor(this.mRectColor);
    this.mTextPaint.setTextAlign(Align.CENTER);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.drawText(this.mPower + "%", this.mCenterX, getMeasuredHeight() - getPaddingBottom(), this.mTextPaint);
    paramCanvas.drawRoundRect(this.mRectF, this.mStrokeWidth * 2.0F, this.mStrokeWidth * 2.0F, this.mShadePaint);
    paramCanvas.save();
    RectF localRectF = new RectF(this.mRectF);
    localRectF.top = (localRectF.bottom - this.mPower * this.yScale);
    paramCanvas.drawRoundRect(localRectF, this.mStrokeWidth * 2.0F, this.mStrokeWidth * 2.0F, this.mPowerPaint);
    paramCanvas.restore();
    drawBg(paramCanvas);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mCenterX = (paramInt1 / 2);
    paramInt1 = (int)(paramInt2 / 2.0F);
    this.mStrokeWidth = (paramInt1 * 0.033333335F);
    this.mTextSize = (paramInt2 / 13.0F);
    this.mTextPaint.setTextSize(this.mTextSize);
    this.mRectF.left = (this.mCenterX - paramInt1 / 2);
    this.mRectF.right = (this.mCenterX + paramInt1 / 2);
    this.mRectF.top = (this.mStrokeWidth * 2.0F + getPaddingTop());
    this.mRectF.bottom = (paramInt2 - getPaddingBottom() - ViewUtil.getTextHeight(this.mTextPaint) - this.mStrokeWidth * 2.0F);
    LinearGradient localLinearGradient = new LinearGradient(this.mRectF.left + this.mStrokeWidth, 0.0F, this.mRectF.right - this.mStrokeWidth, 0.0F, this.mShadeColor, null, Shader.TileMode.CLAMP);
    this.mShadePaint.setShader(localLinearGradient);
    this.yScale = (this.mRectF.height() / 100.0F);
  }
  
  protected void setPower(int paramInt)
  {
    this.mPower = paramInt;
    invalidate();
  }
  
  public void startAnim(int paramInt, long paramLong)
  {
    if (this.anim != null) {
      this.anim.cancel();
    }
    paramInt = Math.max(0, Math.min(100, paramInt));
    this.anim = ObjectAnimator.ofInt(this, "power", new int[] { this.mPower, paramInt }).setDuration(paramLong);
    this.anim.start();
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ui\view\BatterView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */