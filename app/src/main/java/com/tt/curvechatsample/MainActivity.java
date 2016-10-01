package com.tt.curvechatsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tt.curvechartlib.CurveChartView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CurveChartView curveChartView = (CurveChartView) this.findViewById(R.id.curve_chart_view);
        for (int i = 0; i < 30; i++) {
            curveChartView.addData(new Random().nextInt(5) + 5);
        }

        final CurveChartView curveChartView2 = (CurveChartView) this.findViewById(R.id.curve_chart_view2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    curveChartView2.addData(new Random().nextInt(200) + 500);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true;
    }
}
