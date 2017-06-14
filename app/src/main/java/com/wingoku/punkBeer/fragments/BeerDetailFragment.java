package com.wingoku.punkBeer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wingoku.punkBeer.R;
import com.wingoku.punkBeer.models.db.Beer;
import com.wingoku.punkBeer.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Umer on 6/14/2017.
 */

public class BeerDetailFragment extends Fragment{

    @BindView(R.id.tv_title_value)
    TextView mBeerNameTV;

    @BindView(R.id.tv_author_value)
    TextView mBeerContributorNameTV;

    @BindView(R.id.tv_beerPh_value)
    TextView mBeerPhTV;

    @BindView(R.id.tv_description_value)
    TextView mBeerDescriptionTV;

    @BindView(R.id.tv_beerVolume_value)
    TextView mBeerVolumeTV;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppbarLayout;

    // might get used by Snackbar upon image loading failure
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_beer_details, container, false);

        //must bind for View Injection
        ButterKnife.bind(this, mView);

        // getting the beer object that contains all the details about the user selected beer
        Beer beer = getArguments().getParcelable(Constants.PARCELABLE_BEER_OBJECT);

        // if there is no data to be shown, then don't execute this fragment further
        if(beer == null) {
            getActivity().onBackPressed();
            return mView;
        }

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            setHasOptionsMenu(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String beerName = beer.getName();
        mToolbar.setTitle(beerName);
        addOffsetListenerToAppbarLayout();
        assignDetailDataToTextViews(beer);
        return mView;
    }

    /**
     * Add listener to AppbarLayout
     */
    private void addOffsetListenerToAppbarLayout() {
        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mCollapsingToolbarLayout.setTitleEnabled(verticalOffset == 0);
            }
        });
    }

    /**
     * Assign beer details to the layout views
     * @param beer Beer object containing details about the user tapped beer card
     */
    private void assignDetailDataToTextViews(Beer beer) {
        mBeerNameTV.setText(beer.getName());
        mBeerDescriptionTV.setText(beer.getDescription());
        mBeerContributorNameTV.setText(beer.getContributedBy());
        mBeerVolumeTV.setText(beer.getVolume());
        mBeerPhTV.setText(beer.getPH().toString());
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
