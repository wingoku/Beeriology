package com.wingoku.punkBeer.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by Umer on 6/14/2016.
 */

public class WingokuWebview extends WebView {

    private ProgressBar mProgressbar;

    public WingokuWebview(Context context) {
        super(context);
        initWebview();
    }

    public WingokuWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebview();
    }


    public WingokuWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebview();
    }

    @TargetApi(21)
    public WingokuWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWebview();
    }

    public void initWebview() {
        WebSettings settings = getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);

        setWebChromeClient(new WingokuWebClient());
    }

    public void setProgressbar(ProgressBar progressbar) {
        mProgressbar = progressbar;
    }

    private class WingokuWebClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if(newProgress > 70)
                mProgressbar.setVisibility(View.GONE);
            else
                mProgressbar.setVisibility(View.VISIBLE);
        }
    }
}
