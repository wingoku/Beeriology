package com.wingoku.punkBeer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.wingoku.punkBeer.R;
import com.wingoku.punkBeer.models.db.Beer;
import com.wingoku.punkBeer.utils.Constants;
import com.wingoku.punkBeer.utils.WingokuWebview;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Umer on 6/14/2016.
 */

public class ImageBrowserFragment extends Fragment {

    @BindView(R.id.forgotPasswordToolbar)
    Toolbar mToolbar;

    @BindView(R.id.webview)
    WingokuWebview mImageWebView;

    @BindView(R.id.progressbar_webview)
    ProgressBar mWebViewProgressbar;

    private Beer mBeer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_webview, container, false);
        ButterKnife.bind(this, view);

        mBeer = getArguments().getParcelable(Constants.PARCELABLE_BEER_OBJECT);

        mToolbar.setTitle(getString(R.string.string_toolbar_beer_name)+mBeer.getName());
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            setHasOptionsMenu(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadRegistrationWebsite();
        return view;
    }

    private void loadRegistrationWebsite() {
        mImageWebView.setProgressbar(mWebViewProgressbar);
        mImageWebView.loadUrl(mBeer.getImageUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // id of the back button in toolbar
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
