package com.lin.jiang.appart;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 小米手机除了需要在 manifest 中添加权限外，还需要在手机设置中手动给予 APP 权限
 */
public class TestActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "TestActivity";

    private Button mCreateWindowButton;
    private Button mFloatingButton;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mCreateWindowButton = (Button) findViewById(R.id.button1);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    public void onButtonClick(View v) {
        if (v == mCreateWindowButton) {
            Log.d(TAG, "onButtonClick: ");
            mFloatingButton = new Button(this);
            mFloatingButton.setText("click me");
            mLayoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    0, 0, PixelFormat.TRANSPARENT);
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            mLayoutParams.x = 100;
            mLayoutParams.y = 300;
            mFloatingButton.setOnTouchListener(this);
            mWindowManager.addView(mFloatingButton, mLayoutParams);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(TAG, "onTouch: ACTION_MOVE");
                int x = (int) event.getX();
                int y = (int) event.getY();
                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        try {
            mWindowManager.removeView(mFloatingButton);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
