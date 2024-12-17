package com.xxmassdeveloper.mpchartexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Scrollbar;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.LabelSpacings;
import com.github.mikephil.charting.utils.Utils;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * Example of a heavily customized {@link LineChart} with limit lines, custom line shapes, etc.
 *
 * @version 3.1.0
 * @since 1.7.4
 */
public class LineChartActivity1 extends DemoBase implements OnChartValueSelectedListener
{
	
	private LineChart chart;
	
	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_linechart);
		
		setTitle("LineChartActivity1");
		
		
		{   // // Chart Style // //
			chart = findViewById(R.id.chart1);

			chart.labelSpacings = new LabelSpacings(
					getResources().getDimension(R.dimen.padding_horizontal),
					getResources().getDimension(R.dimen.padding_vertical),
					getResources().getDimension(R.dimen.margin_vertical)
			);
			
			// background color
			chart.setBackgroundColor(Color.WHITE);
			
			// disable description text
			chart.getDescription().setEnabled(false);
			
			// enable touch gestures
			chart.setTouchEnabled(false);
			
			// set listeners
			chart.setOnChartValueSelectedListener(this);
			
			// enable scaling and dragging
			chart.setScaleEnabled(false);
			// chart.setScaleXEnabled(true);
			// chart.setScaleYEnabled(true);
			chart.setDragEnabled(true);
			chart.setTouchEnabled(true);
			chart.enableScroll();
			Scrollbar scrollbar = new Scrollbar();
			scrollbar.setRadiusCorners(16);
			scrollbar.setScrollHeight(10);
			scrollbar.setScrollColor(Color.parseColor("#f0edf2"));
			chart.setScroll(scrollbar);
			
		}
		
		XAxis xAxis;
		{   // // X-Axis Style // //
			xAxis = chart.getXAxis();
			
			// vertical grid lines
			xAxis.enableGridDashedLine(10f, 10f, 0f);
			
		}
		
		YAxis yAxis;
		{   // // Y-Axis Style // //
			yAxis = chart.getAxisLeft();
			
			// disable dual axis (only use LEFT axis)
			chart.getAxisRight().setEnabled(false);
			
			// horizontal grid lines
			yAxis.enableGridDashedLine(10f, 10f, 0f);
			
			// axis range
			yAxis.setAxisMaximum(200f);
			yAxis.setAxisMinimum(-50f);
		}
		
		setData(10, 180);
	}
	
	private void setData(int count, float range)
	{
		
		ArrayList <Entry> values = new ArrayList <>();
		
		for(int i = 0 ; i < count ; i++)
		{
			
			float val = (float)(Math.random() * range) - 30;
			values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
		}
		
		LineDataSet set1;
		
		if(chart.getData() != null && chart.getData().getDataSetCount() > 0)
		{
			set1 = (LineDataSet)chart.getData().getDataSetByIndex(0);
			set1.setValues(values);
			set1.notifyDataSetChanged();
			chart.getData().notifyDataChanged();
			chart.notifyDataSetChanged();
		}
		else
		{
			// create a dataset and give it a type
			set1 = new LineDataSet(values, "DataSet 1");
			
			set1.setDrawIcons(false);
			
			// draw dashed line
			set1.enableDashedLine(10f, 5f, 0f);
			
			// black lines and points
			set1.setColor(Color.RED);
			set1.setCircleColor(Color.BLACK);
			
			// line thickness and point size
			set1.setLineWidth(1f);
			set1.setCircleRadius(3f);
			set1.setValueTextColor(Color.RED);
			
			// draw points as solid circles
			set1.setDrawCircleHole(false);
			
			// customize legend entry
			set1.setFormLineWidth(1f);
			set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
			set1.setFormSize(15.f);
			
			// text size of value
			set1.setValueTextSize(15f);
			
			// draw selection line as dashed
			set1.enableDashedHighlightLine(10f, 5f, 0f);
			XAxis xAxis;
			xAxis = chart.getXAxis();
			xAxis.setDrawGridLines(false);
			xAxis.setDrawAxisLine(false);
			xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
			xAxis.setTextColor(Color.GRAY);
			xAxis.setTextSize(14f);
			xAxis.setLabelCount(3, true);
			xAxis.setGranularityEnabled(true);
			xAxis.setGranularity(1f);
			xAxis.setSpaceMax(0.3f);
			xAxis.setSpaceMin(0.3f);
			xAxis.setYOffset(15);
			
			// set color of filled area
			if(Utils.getSDKInt() >= 18)
			{
				// drawables only supported on api level 18 and above
				Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
				set1.setFillDrawable(drawable);
			}
			else
			{
				set1.setFillColor(Color.BLACK);
			}
			
			ArrayList <ILineDataSet> dataSets = new ArrayList <>();
			dataSets.add(set1); // add the data sets
			
			// create a data object with the data sets
			LineData data = new LineData(dataSets);
			chart.setTouchEnabled(true);
			// set data
			chart.setData(data);
			chart.setVisibleXRangeMaximum(5);
			chart.getLegend().setEnabled(false);
			chart.setExtraOffsets(20f, 35f, 20f, 35f);
			chart.setHorizontalScrollBarEnabled(true);
			chart.setScrollbarFadingEnabled(true);
			chart.centerViewTo(count, 0, null);
		}
	}
	
	@Override public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.line, menu);
		return true;
	}
	
	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		
		int itemId = item.getItemId();
		if(itemId == R.id.viewGithub)
		{
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/LineChartActivity1.java"));
			startActivity(i);
		}
		else if(itemId == R.id.actionToggleValues)
		{
			List <ILineDataSet> sets = chart.getData().getDataSets();
			
			for(ILineDataSet iSet : sets)
			{
				
				LineDataSet set = (LineDataSet)iSet;
				set.setDrawValues(!set.isDrawValuesEnabled());
			}
			
			chart.invalidate();
		}
		else if(itemId == R.id.actionToggleIcons)
		{
			List <ILineDataSet> sets = chart.getData().getDataSets();
			
			for(ILineDataSet iSet : sets)
			{
				
				LineDataSet set = (LineDataSet)iSet;
				set.setDrawIcons(!set.isDrawIconsEnabled());
			}
			
			chart.invalidate();
		}
		else if(itemId == R.id.actionToggleHighlight)
		{
			if(chart.getData() != null)
			{
				chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
				chart.invalidate();
			}
		}
		else if(itemId == R.id.actionToggleFilled)
		{
			List <ILineDataSet> sets = chart.getData().getDataSets();
			
			for(ILineDataSet iSet : sets)
			{
				
				LineDataSet set = (LineDataSet)iSet;
				if(set.isDrawFilledEnabled())
				{
					set.setDrawFilled(false);
				}
				else
				{
					set.setDrawFilled(true);
				}
			}
			chart.invalidate();
		}
		else if(itemId == R.id.actionToggleCircles)
		{
			List <ILineDataSet> sets = chart.getData().getDataSets();
			
			for(ILineDataSet iSet : sets)
			{
				
				LineDataSet set = (LineDataSet)iSet;
				if(set.isDrawCirclesEnabled())
				{
					set.setDrawCircles(false);
				}
				else
				{
					set.setDrawCircles(true);
				}
			}
			chart.invalidate();
		}
		else if(itemId == R.id.actionToggleCubic)
		{
			List <ILineDataSet> sets = chart.getData().getDataSets();
			
			for(ILineDataSet iSet : sets)
			{
				
				LineDataSet set = (LineDataSet)iSet;
				set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER ? LineDataSet.Mode.LINEAR : LineDataSet.Mode.CUBIC_BEZIER);
			}
			chart.invalidate();
		}
		else if(itemId == R.id.actionToggleStepped)
		{
			List <ILineDataSet> sets = chart.getData().getDataSets();
			
			for(ILineDataSet iSet : sets)
			{
				
				LineDataSet set = (LineDataSet)iSet;
				set.setMode(set.getMode() == LineDataSet.Mode.STEPPED ? LineDataSet.Mode.LINEAR : LineDataSet.Mode.STEPPED);
			}
			chart.invalidate();
		}
		else if(itemId == R.id.actionToggleHorizontalCubic)
		{
			List <ILineDataSet> sets = chart.getData().getDataSets();
			
			for(ILineDataSet iSet : sets)
			{
				
				LineDataSet set = (LineDataSet)iSet;
				set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER ? LineDataSet.Mode.LINEAR : LineDataSet.Mode.HORIZONTAL_BEZIER);
			}
			chart.invalidate();
		}
		else if(itemId == R.id.actionTogglePinch)
		{
			if(chart.isPinchZoomEnabled())
			{
				chart.setPinchZoom(false);
			}
			else
			{
				chart.setPinchZoom(true);
			}
			
			chart.invalidate();
		}
		else if(itemId == R.id.actionToggleAutoScaleMinMax)
		{
			chart.setAutoScaleMinMaxEnabled(!chart.isAutoScaleMinMaxEnabled());
			chart.notifyDataSetChanged();
		}
		else if(itemId == R.id.animateX)
		{
			chart.animateX(2000);
		}
		else if(itemId == R.id.animateY)
		{
			chart.animateY(2000, Easing.EaseInCubic);
		}
		else if(itemId == R.id.animateXY)
		{
			chart.animateXY(2000, 2000);
		}
		else if(itemId == R.id.actionSave)
		{
			if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
			{
				saveToGallery();
			}
			else
			{
				requestStoragePermission(chart);
			}
		}
		return true;
	}
	
	@Override protected void saveToGallery()
	{
		saveToGallery(chart, "LineChartActivity1");
	}
	
	@Override public void onValueSelected(Entry e, Highlight h)
	{
		Log.i("Entry selected", e.toString());
		Log.i("LOW HIGH", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
		Log.i("MIN MAX", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() + ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());
	}
	
	@Override public void onNothingSelected()
	{
		Log.i("Nothing selected", "Nothing selected.");
	}
}
