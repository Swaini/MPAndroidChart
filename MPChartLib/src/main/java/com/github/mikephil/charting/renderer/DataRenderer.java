package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Superclass of all render classes for the different data types (line, bar, ...).
 *
 * @author Philipp Jahoda
 */
public abstract class DataRenderer extends Renderer
{
	
	/**
	 * the animator object used to perform animations on the chart data
	 */
	protected ChartAnimator mAnimator;
	
	/**
	 * main paint object used for rendering
	 */
	protected Paint mRenderPaint;
	
	/**
	 * paint used for highlighting values
	 */
	protected Paint mHighlightPaint;
	
	protected Paint mDrawPaint;

	protected Paint mMarginPaint;

	protected Paint mShadowPaint;
	
	/**
	 * paint object for drawing values (text representing values of chart
	 * entries)
	 */
	protected Paint mValuePaint;
	
	public DataRenderer(ChartAnimator animator, ViewPortHandler viewPortHandler)
	{
		super(viewPortHandler);
		this.mAnimator = animator;
		
		mRenderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRenderPaint.setStyle(Style.FILL);
		
		mDrawPaint = new Paint(Paint.DITHER_FLAG);
		
		mValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mValuePaint.setColor(Color.rgb(63, 63, 63));
		mValuePaint.setTextAlign(Align.CENTER);
		mValuePaint.setTextSize(Utils.convertDpToPixel(9f));
		
		mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHighlightPaint.setStyle(Paint.Style.STROKE);
		mHighlightPaint.setStrokeWidth(2f);
		mHighlightPaint.setColor(Color.rgb(255, 187, 115));

		mShadowPaint = new Paint();
		mShadowPaint.setShadowLayer(16, 4.0f, 4.0f, Color.parseColor("#26333333"));
		mShadowPaint.setColor(Color.WHITE);
		mShadowPaint.setStyle(Style.FILL);

		mMarginPaint = new Paint();
		mMarginPaint.setColor(Color.TRANSPARENT);
	}
	
	protected boolean isDrawingValuesAllowed(ChartInterface chart)
	{
		return chart.getData().getEntryCount() < chart.getMaxVisibleCount() * mViewPortHandler.getScaleX();
	}
	
	/**
	 * Returns the Paint object this renderer uses for drawing the values
	 * (value-text).
	 */
	public Paint getPaintValues()
	{
		return mValuePaint;
	}
	
	/**
	 * Returns the Paint object this renderer uses for drawing highlight
	 * indicators.
	 */
	public Paint getPaintHighlight()
	{
		return mHighlightPaint;
	}
	
	/**
	 * Returns the Paint object used for rendering.
	 */
	public Paint getPaintRender()
	{
		return mRenderPaint;
	}
	
	/**
	 * Applies the required styling (provided by the DataSet) to the value-paint
	 * object.
	 */
	protected void applyValueTextStyle(IDataSet set)
	{
		mValuePaint.setTypeface(set.getValueTypeface());
		mValuePaint.setTextSize(set.getValueTextSize());
	}
	
	/**
	 * Initializes the buffers used for rendering with a new size. Since this
	 * method performs memory allocations, it should only be called if
	 * necessary.
	 */
	public abstract void initBuffers();
	
	/**
	 * Draws the actual data in form of lines, bars, ... depending on Renderer subclass.
	 */
	public abstract void drawData(Canvas c);
	
	/**
	 * Loops over all Entrys and draws their values.
	 */
	public abstract void drawValues(Canvas c);
	
	/**
	 * Draws the value of the given entry by using the provided IValueFormatter.
	 *
	 * @param c canvas
	 * @param formatter formatter for custom value-formatting
	 * @param value the value to be drawn
	 * @param entry the entry the value belongs to
	 * @param dataSetIndex the index of the DataSet the drawn Entry belongs to
	 * @param x position
	 * @param y position
	 */
	public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color)
	{
		Rect textBounds = new Rect();
		String formatted = formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler);
		mValuePaint.setColor(color);
		mValuePaint.getTextBounds(formatted, 0, formatted.length(), textBounds);
		
		float margin = mViewPortHandler.labelSpacings.marginBottom;
		float paddingVertical = mViewPortHandler.labelSpacings.paddingVertical;
		float paddingHorizontal = mViewPortHandler.labelSpacings.paddingHorizontal;
		y = y - margin - paddingVertical;

		float w = textBounds.width();
		float h = textBounds.height();
		float left = x - (w * 0.5f) - paddingHorizontal;
		float right = x + (w * 0.5f) + paddingHorizontal;
		float top = y + paddingVertical;
		float bottom = y - h - paddingVertical;
		
		RectF rect = new RectF(left, top, right, bottom);
		c.drawRoundRect(rect, 16, 16, mShadowPaint);
		
		c.drawText(formatted, x, y, mValuePaint);
		RectF marginRect = new RectF(left, bottom, right, bottom - margin);
		c.drawRect(marginRect, mMarginPaint);
	}
	
	/**
	 * Draws any kind of additional information (e.g. line-circles).
	 */
	public abstract void drawExtras(Canvas c);
	
	/**
	 * Draws all highlight indicators for the values that are currently highlighted.
	 *
	 * @param indices the highlighted values
	 */
	public abstract void drawHighlighted(Canvas c, Highlight[] indices);
}
