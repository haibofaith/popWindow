package com.simple.systemwidget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends Activity {
    private Button bt;
    private PopupWindow popupWindow;
    private View view;
    private int animationStyle;
    private int color = Color.parseColor("#cccccc");
    private static final int DEFAULT_ANIM_STYLE = R.style.TRM_ANIM_STYLE;
    private Context context;
    private float alpha = 0.75f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.bt);
        context = this;
        animationStyle = DEFAULT_ANIM_STYLE;
        view = LayoutInflater.from(this).inflate(R.layout.pop_window,null);
        popupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);

        popupWindow.setBackgroundDrawable(dw);

        popupWindow.setAnimationStyle(animationStyle);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();

                }else{
                    popupWindow.showAsDropDown(view,0,0);
                    setBackgroundAlpha(1f, alpha, 240);
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(alpha,1f, 240);
            }
        });
    }

    private void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                MainActivity.this.getWindow().setAttributes(lp);
            }
        });
        animator.start();
    }

}
