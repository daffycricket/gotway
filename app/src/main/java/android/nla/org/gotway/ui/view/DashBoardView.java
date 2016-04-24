package android.nla.org.gotway.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.reb.hola.data.Data0x00;
import com.reb.hola.util.ViewUtil;

public class DashBoardView
  extends View
{
  private static final float START_ANGLE = 135.0F;
  private static final float STROKE_WIDTH_SCALE = 0.006666667F;
  private static final float SWEEP_ANGEL = 270.0F;
  private ObjectAnimator anim;
  private float mCenterX;
  private float mCenterY;
  private int mDistance = 0;
  private Paint mLinePaint = new Paint(1);
  private Paint mPointPaint;
  private float mRadius;
  private float mSpeed = 0.0F;
  private float mStrokeWidth;
  private Paint mTextPaint;
  private float mTextSize;
  private float mTotalDistance = 0.0F;
  
  public DashBoardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mLinePaint.setColor(-1);
    this.mLinePaint.setStyle(Style.STROKE);
    this.mTextPaint = new Paint(33);
    this.mTextPaint.setTypeface(Typeface.defaultFromStyle(2));
    this.mTextPaint.setTextAlign(Align.CENTER);
    this.mTextPaint.setColor(-1);
    this.mTextPaint.setTextSize(28.0F);
    this.mPointPaint = new Paint(1);
    this.mPointPaint.setColor(-65536);
    this.mPointPaint.setMaskFilter(new BlurMaskFilter(10.0F, Blur.SOLID));
  }
  
  private void drawPoint(Canvas paramCanvas)
  {
    setLayerType(1, this.mPointPaint);
    paramCanvas.drawCircle(this.mCenterX, this.mCenterY, this.mStrokeWidth * 3.0F, this.mPointPaint);
    paramCanvas.save();
    paramCanvas.rotate(135.0F + 5.4F * this.mSpeed, this.mCenterX, this.mCenterY);
    paramCanvas.drawLine(this.mCenterX, this.mCenterY, this.mCenterX + this.mRadius - this.mStrokeWidth * 25.0F, this.mCenterY, this.mPointPaint);
    paramCanvas.restore();
  }
  
  private void drawRuler(Canvas paramCanvas)
  {
    RectF localRectF = new RectF();
    localRectF.left = (this.mCenterX - this.mRadius);
    localRectF.top = (this.mCenterY - this.mRadius);
    localRectF.right = (this.mCenterX + this.mRadius);
    localRectF.bottom = (this.mCenterY + this.mRadius);
    paramCanvas.drawArc(localRectF, 135.0F, 270.0F, false, this.mLinePaint);
    float f1 = this.mStrokeWidth * 13.0F;
    float f2 = this.mStrokeWidth;
    float f3 = this.mCenterX + this.mRadius + this.mStrokeWidth / 2.0F;
    int i = 0;
    if (i >= 51) {
      return;
    }
    int j = paramCanvas.save();
    float f4 = 135.0F + i * 270.0F / 50.0F;
    paramCanvas.rotate(f4, this.mCenterX, this.mCenterY);
    if (i % 5 == 0)
    {
      paramCanvas.drawLine(f3, this.mCenterY, f3 - f1, this.mCenterY, this.mLinePaint);
      int k = paramCanvas.save();
      paramCanvas.translate(f3 - 1.6F * f1, this.mCenterY);
      paramCanvas.rotate(-f4);
      paramCanvas.drawText(i, 0.0F, 0.0F, this.mTextPaint);
      paramCanvas.restoreToCount(k);
    }
    for (;;)
    {
      paramCanvas.restoreToCount(j);
      i += 1;
      break;
      paramCanvas.drawLine(f3, this.mCenterY, f3 - f2 * 8.0F, this.mCenterY, this.mLinePaint);
    }
  }
  
  private void drawText(Canvas paramCanvas)
  {
    float f1 = this.mCenterY - 10.0F * this.mStrokeWidth;
    paramCanvas.drawText(String.format("%.1f", new Object[] { Float.valueOf(this.mSpeed) }), this.mCenterX, f1, this.mTextPaint);
    float f2 = ViewUtil.getTextHeight(this.mTextPaint);
    float f3 = this.mStrokeWidth;
    paramCanvas.drawText("km/h", this.mCenterX, f1 - (f2 + f3 * 3.0F), this.mTextPaint);
    f1 = this.mCenterY + this.mRadius * 0.5F;
    paramCanvas.drawText(this.mDistance + " m", this.mCenterX, f1, this.mTextPaint);
    f2 = ViewUtil.getTextHeight(this.mTextPaint);
    f3 = this.mStrokeWidth;
    paramCanvas.drawText(String.format("%.1f km", new Object[] { Float.valueOf(this.mTotalDistance) }), this.mCenterX, f1 + (f2 + f3 * 3.0F), this.mTextPaint);
  }
  
  private void startSpeedAnim(float paramFloat, long paramLong)
  {
    if (this.anim != null) {
      this.anim.cancel();
    }
    this.anim = ObjectAnimator.ofFloat(this, "speed", new float[] { this.mSpeed, paramFloat }).setDuration(paramLong);
    this.anim.start();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawRuler(paramCanvas);
    drawText(paramCanvas);
    drawPoint(paramCanvas);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mStrokeWidth = (paramInt1 * 0.006666667F);
    if (paramInt1 / 2 > paramInt2 / 1.98F) {}
    for (this.mRadius = (paramInt2 / 0.98F / 2.0F - this.mStrokeWidth / 2.0F);; this.mRadius = (paramInt1 / 2 - this.mStrokeWidth / 2.0F))
    {
      this.mCenterX = (paramInt1 / 2);
      this.mCenterY = (this.mRadius + this.mStrokeWidth / 2.0F);
      this.mLinePaint.setStrokeWidth(this.mStrokeWidth);
      this.mPointPaint.setStrokeWidth(this.mStrokeWidth);
      this.mTextSize = (this.mStrokeWidth * 6.5F);
      this.mTextPaint.setTextSize(this.mTextSize);
      return;
    }
  }
  
  public void setData(Data0x00 paramData0x00, long paramLong)
  {
    this.mDistance = paramData0x00.distance;
    this.mTotalDistance = paramData0x00.totalDistance;
    startSpeedAnim(paramData0x00.speed, paramLong);
    invalidate();
  }
  
  protected void setSpeed(float paramFloat)
  {
    this.mSpeed = Math.max(0.0F, Math.min(50.0F, paramFloat));
    invalidate();
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ui\view\DashBoardView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */