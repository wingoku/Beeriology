package com.wingoku.punkBeer.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.wingoku.punkBeer.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Umer on 6/14/2017.
 */

public class SplashScreenFragment extends Fragment {

    @BindView(R.id.animation_view)
    LottieAnimationView mAnimationView;

    @BindView(R.id.tv_splash_screen)
    TextView mSplashScreenTV;

    private boolean onAnimationComplete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        mSplashScreenTV.setText(R.string.app_name);
        initAnimation();
        mAnimationView.playAnimation();
        mAnimationView.setSpeed(1.7f);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!onAnimationComplete) {
            // sending event to open beer list fragment
            EventBus.getDefault().post(true);
            mAnimationView.cancelAnimation();
        }
    }

    /**
     * Initialize Lottie Animation View and add animation listeners on it
     */
    private void initAnimation() {
        mAnimationView.setAnimation(getString(R.string.splash_screen_animation_file_name));
        mAnimationView.loop(false);
        mAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mSplashScreenTV.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                onAnimationComplete = true;
                EventBus.getDefault().post(onAnimationComplete);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }
}
