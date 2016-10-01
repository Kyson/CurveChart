package com.tt.curvechatsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tt.curvechartlib.CurveChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurveChartView curveChartView = (CurveChartView)this.findViewById(R.id.curve_chart_view);
        curveChartView.addData(3);
        curveChartView.addData(5);
        curveChartView.addData(1);
        curveChartView.addData(7);
        curveChartView.addData(5);
        curveChartView.addData(4);
        curveChartView.addData(1);
        curveChartView.addData(2);
        curveChartView.addData(6);
    }
}
