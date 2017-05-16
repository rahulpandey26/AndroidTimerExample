package com.robosoft.androidtimerexample;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTimerValue;
    private long mStartTime = 0L;
    private Handler handler = new Handler();
    private long mTimeInMilliseconds = 0L;
    private long mTimeSwapBuff = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize view
        initView();
    }

    private void initView() {
        mTimerValue = (TextView) findViewById(R.id.timerValue);
        findViewById(R.id.startButton).setOnClickListener(this);
        findViewById(R.id.pauseButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startButton:
                mStartTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimerThread, 0);
                break;

            case R.id.pauseButton:
                mTimeSwapBuff += mTimeInMilliseconds;
                handler.removeCallbacks(updateTimerThread);
                break;

            default: // do nothing

        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            mTimeInMilliseconds = SystemClock.uptimeMillis() - mStartTime;
            long updatedTime = mTimeSwapBuff + mTimeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            mTimerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}
