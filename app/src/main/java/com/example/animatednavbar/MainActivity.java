package com.example.animatednavbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewGroup sceneRoot;
    private int defaultWidthButton = 0;
    private int widthButton;
    private View activeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SecondActivity.class));

        sceneRoot = findViewById(R.id.rootScene);

        widthButton = (int) getResources().getDisplayMetrics().density * 150;

        findViewById(R.id.llFirstButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expand(sceneRoot, v, "Back");
            }
        });

        findViewById(R.id.llSecondButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expand(sceneRoot, v, "Play");
            }
        });

        findViewById(R.id.llThirdButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expand(sceneRoot, v, "Pause");
            }
        });

        findViewById(R.id.llFourthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expand(sceneRoot, v, "Next");
            }
        });
    }

    private void expand(ViewGroup sceneRoot, View v, String textButton) {
        if(activeButton == v) return;
        if(defaultWidthButton == 0) defaultWidthButton = v.getWidth();
        final View previousActiveButton = activeButton;

        final TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.setDuration(200);
        transitionSet.setInterpolator(new AccelerateInterpolator());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTarget(v);
        if(activeButton != null){
            transitionSet.addTarget(activeButton);
        }
        transitionSet.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.e(getLocalClassName(), "Transition Start");
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Log.e(getLocalClassName(), "Transition End");
                if(previousActiveButton != null) {
                    previousActiveButton.setBackground(null);
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                Log.e(getLocalClassName(), "Transition Cancel");
            }

            @Override
            public void onTransitionPause(Transition transition) {
                Log.e(getLocalClassName(), "Transition Pause");
            }

            @Override
            public void onTransitionResume(Transition transition) {
                Log.e(getLocalClassName(), "Transition Resume");
            }
        });
        TransitionManager.beginDelayedTransition(sceneRoot, transitionSet);

        actButton(v, textButton, widthButton, getDrawable(R.drawable.button_long_circle));
        if(activeButton != null){
            actButton(activeButton, null, defaultWidthButton, getDrawable(R.drawable.button_long_circle));
        }

        activeButton = v;
    }

    private void actButton(
            View v,
            String textButton,
            int endWidthButton,
            Drawable background
    ){
        ViewGroup.LayoutParams paramsButton = v.getLayoutParams();
        v.setBackground(background);
        paramsButton.width = endWidthButton;
        v.setLayoutParams(paramsButton);
        ((TextView)v.findViewWithTag("tvName")).setText(textButton);
    }
}
