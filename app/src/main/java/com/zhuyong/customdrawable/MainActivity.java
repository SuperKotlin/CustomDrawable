package com.zhuyong.customdrawable;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private LineChart mChart1;

    protected String[] mMonths = new String[]{"1:00", "2:00", "3:00", "4:00", "5:00",
            "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00", "00:00"};

    private Context mContext;
    protected DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        df.setMaximumFractionDigits(1);

        mChart1 = (LineChart) findViewById(R.id.chart1);
        initLineView(mChart1, 400f);

        List<Float> valList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            float val = (float) (Math.random() * 80) + 185;
            valList.add(val);
        }
        //找出最大值和最小值，然后添加icon
        float mMaxData = Collections.max(valList);
        float mMinData = Collections.min(valList);
        ArrayList<Entry> values3 = new ArrayList<Entry>();
        for (int i = 0; i < valList.size(); i++) {
            float data = valList.get(i);
            if (data == mMaxData) {
                CustomDrawable customDrawable = new CustomDrawable(mContext);
                customDrawable.setmIsUp(true);
                customDrawable.setmBackGroundColor(ContextCompat.getColor(mContext, R.color.color_green_alpa));
                customDrawable.setmText(df.format(mMaxData) + " kg");
                values3.add(new Entry(i, valList.get(i), customDrawable));
//                values3.add(new Entry(i, valList.get(i), ContextCompat.getDrawable(mContext,R.drawable.star)));
            } else if (data == mMinData) {
                CustomDrawable customDrawable = new CustomDrawable(mContext);
                customDrawable.setmIsUp(false);
                customDrawable.setmBackGroundColor(ContextCompat.getColor(mContext, R.color.color_red_alpa));
                customDrawable.setmText(df.format(mMinData) + " kg");
                values3.add(new Entry(i, valList.get(i), customDrawable));
//                values3.add(new Entry(i, valList.get(i), ContextCompat.getDrawable(mContext,R.drawable.star)));
            } else {
                values3.add(new Entry(i, valList.get(i)));
            }
        }
        setData(mChart1, values3);
    }

    private void initLineView(LineChart mChart, float maxData) {
        mChart.setNoDataText("图表无数据");

        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDoubleTapToZoomEnabled(false);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.animateX(1000);

        mChart.getLegend().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths[(int) value % mMonths.length];
            }
        };

        xAxis.setValueFormatter(formatter);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);//是否显示水平标尺线
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(maxData);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setEnabled(false);
    }

    private void setData(LineChart mChart, ArrayList<Entry> yVals1) {

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ContextCompat.getColor(mContext, R.color.color_F8B551));
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ContextCompat.getColor(mContext, R.color.color_F8B551));
            set1.setHighLightColor(ContextCompat.getColor(mContext, R.color.color_F8B551));
            set1.setDrawCircleHole(true);
            set1.setCircleColor(ContextCompat.getColor(mContext, R.color.color_F8B551));
            set1.setMode(LineDataSet.Mode.LINEAR);
            set1.setDrawCircles(true);

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }
}
