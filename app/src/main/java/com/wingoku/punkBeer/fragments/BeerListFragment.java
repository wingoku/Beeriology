package com.wingoku.punkBeer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.wingoku.punkBeer.MainActivity;
import com.wingoku.punkBeer.R;
import com.wingoku.punkBeer.adapters.BeersListRecyclerViewAdapter;
import com.wingoku.punkBeer.eventBus.OnBeerListCardClickedEvent;
import com.wingoku.punkBeer.eventBus.OnBeerListCreationCompleteEvent;
import com.wingoku.punkBeer.eventBus.OnBeerListCreationFailureEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFetchFailureEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFetchSuccessEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFilterTaskCompleteEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFilterTaskFailureEvent;
import com.wingoku.punkBeer.fragments.presenters.BeerListFragmentPresenter;
import com.wingoku.punkBeer.interfaces.components.BeerListFragmentComponent;
import com.wingoku.punkBeer.interfaces.components.DaggerBeerListFragmentComponent;
import com.wingoku.punkBeer.models.db.Beers;
import com.wingoku.punkBeer.modules.ContextModule;
import com.wingoku.punkBeer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Umer on 6/14/2017.
 */

public class BeerListFragment extends Fragment {

    @BindView(R.id.list_beers)
    RecyclerView mBeersListRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.progressBar)
    CircleProgressBar mProgressBar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppbarLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private View mView;
    private SearchView mSearchView;

    private final int mBeersLimitPerPage = 10;
    private int mPageOffset = 1;
    private int mNumberOfFetchedBeers;
    private double mPH;
    private boolean mNetworkRequestEnqueue;
    private boolean isAppbarExpanded;
    private boolean isFilterModeActive;
    private BeersListRecyclerViewAdapter mAdapter;

    // this will tell Dagger to call BeerListFragmentPresenter constructor to inject dependencies. NOTE that
    // the BeerListFragmentPresetner class must have a constructor with all the required dependencies as arguments
    // & the constructor must be annotated with @Inject. This is called constructor injection
    @Inject
    BeerListFragmentPresenter mBeerListFragmentPresenter;

    private BeerListFragmentComponent mBeerListFragmentComponent;

    /**
     * BEERS LIST FRAGMENT ONSAVEDINSTANCE KEYS
     */

    private final String FETCHED_BEERS_COUNT = "fetchedBeersCount";
    private final String IS_FILTER_MODE_ACTIVE = "isFilterModeActive";
    private final String IS_APP_BAR_EXPANDED = "isAppbarExpanded";
    private final String BEERS_PHP = "beersPH";
    private final String BEERS_PARCELABLE_OBJECT = "beersParcelableObject";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Building dagger DI component
        mBeerListFragmentComponent = DaggerBeerListFragmentComponent
                .builder()
                .contextModule(new ContextModule(getContext()))
                .build();
        mBeerListFragmentComponent.inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Timber.d("onActivityCreated");
        if(savedInstanceState != null) {
            Timber.d("onActivityCreated::savedInstanceState");
            mNumberOfFetchedBeers = savedInstanceState.getInt(FETCHED_BEERS_COUNT);
            Beers savedBeers = savedInstanceState.getParcelable(BEERS_PARCELABLE_OBJECT);
            mBeerListFragmentPresenter.getBeersList().addAll(savedBeers.getBeerList());
            updateBeersAdapter(mBeerListFragmentPresenter.getBeers());
            isFilterModeActive = savedInstanceState.getBoolean(IS_FILTER_MODE_ACTIVE);
            isAppbarExpanded = savedInstanceState.getBoolean(IS_APP_BAR_EXPANDED);
            mPH = savedInstanceState.getDouble(BEERS_PHP);

            if(mNetworkRequestEnqueue) {
                initiateBeersFetchingCall();
            }
        }

        if(isFilterModeActive) {
            Timber.d("beer count in budget mode: %s", mBeerListFragmentPresenter.getFilteredBeers().getBeerList().size());
            updateBeersAdapter(mBeerListFragmentPresenter.getFilteredBeers());
            mBeerListFragmentPresenter.filterBeersAccordingToPhLevel(mPH * 1.0);
            showEndBudgetModeSnackBar();
        }
        else {
            updateBeersAdapter(mBeerListFragmentPresenter.getBeers());
        }

        if(mBeerListFragmentPresenter.getBeerListSize() == 0 && !mNetworkRequestEnqueue) {
            initiateBeersFetchingCall();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_beers_list, container, false);

        //must bind for View Injection
        ButterKnife.bind(this, mView);

        // register event bus to receive events
        EventBus.getDefault().register(this);

        getMainActivity().setSupportActionBar(mToolbar);
        if(getMainActivity().getSupportActionBar() != null) {
            setHasOptionsMenu(true);
        }

        addOffsetListenerToAppbarLayout();
        readFromCacheIfNetworkNotAvailable();

        initRecyclerView();
        initBeersRecyclerViewAdapter();
        addScrollListenerToRecyclerView();

        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("onSavedInstance");
        outState.putParcelable(BEERS_PARCELABLE_OBJECT, mBeerListFragmentPresenter.getBeers());
        outState.putInt(FETCHED_BEERS_COUNT, mNumberOfFetchedBeers);
        outState.putBoolean(IS_FILTER_MODE_ACTIVE, isFilterModeActive);
        outState.putBoolean(IS_APP_BAR_EXPANDED, isAppbarExpanded);
        outState.putDouble(BEERS_PHP, mPH);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        final MenuItem actionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) actionMenuItem.getActionView();
        mSearchView.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        setQueryListenerOnSearchView(actionMenuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                mAppbarLayout.setExpanded(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        Timber.e("onDestroyView");
        // unregister event bus
        EventBus.getDefault().unregister(this);
        if(getMainActivity() != null) {
            getMainActivity().onNetworkProcessEnded();
        }
        // clear off all the active observables
        mBeerListFragmentPresenter.clearOffObservables();
        mNetworkRequestEnqueue = false;
        super.onDestroyView();
    }

    /**
     * Add listener to AppbarLayout
     */
    private void addOffsetListenerToAppbarLayout() {
        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                isAppbarExpanded = (verticalOffset == 0);
                mCollapsingToolbarLayout.setTitleEnabled(isAppbarExpanded);
            }
        });
    }

    /**
     * Read Beers from DB when offline
     */
    private void readFromCacheIfNetworkNotAvailable() {
        if(!Utils.isNetworkAvailable(getContext())) {
            mBeerListFragmentPresenter.getBeersList().addAll(mBeerListFragmentPresenter.getCachedBeersFromDB());
        }
    }

    /**
     * Initialize RecyclerView and assign the LayoutManger
     */
    private void initRecyclerView() {
        // show two columns in the RecyclerView grid
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.beer_grid_columns));
        mBeersListRecyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Send required info in the RecyclerView's adapter for displaying data
     */
    private void initBeersRecyclerViewAdapter() {
        mAdapter = new BeersListRecyclerViewAdapter(getContext(), R.layout.layout_beer_card, mBeerListFragmentPresenter.getPicassoInstance());
        mBeersListRecyclerView.setAdapter(mAdapter);
    }

    private void onFailureInDataFetching() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar snackbar = Utils.initSnackbar(getContext(), mView);
        snackbar.setText(getString(R.string.failure_message)).show();
    }

    private void initiateBeersFetchingCall() {
        if(!mNetworkRequestEnqueue && !isFilterModeActive) {
            mProgressBar.setVisibility(View.VISIBLE);
            mNetworkRequestEnqueue = true;
            if(getMainActivity() != null) {
                getMainActivity().onNetworkProcessStarted();
            }

            mBeerListFragmentPresenter.fetchBeers(mBeersLimitPerPage, mPageOffset);
        }
    }

    private void addScrollListenerToRecyclerView() {
        mBeersListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Timber.d("onScrollStateChanged : isFilterModeActive: %s mNetworkRequestEnqueed: %s", isFilterModeActive, mNetworkRequestEnqueue);
                if(!recyclerView.canScrollVertically(RecyclerView.VERTICAL)) {
                    Timber.d("onScrollStateChanged:: can't scroll vertically");
                    initiateBeersFetchingCall();
                }
            }
        });
    }

    /**
     * Update Beers list size in the adaper. This method will be used to manipulate the number of beers to be shown for a specific budget
     * @param beers filtered BeersList
     */
    private void updateBeersAdapter(Beers beers) {
        Timber.e("updated adapter size: %s", beers.getBeerList().size());
        mAdapter.updateDataSet(beers);
    }

    private void showEndBudgetModeSnackBar() {
        if(mView == null) {
            return;
        }

        Snackbar snackbar = Utils.initSnackbar(getContext(), mView);
        snackbar.setText(R.string.string_end_budget_mode);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.ok_string), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.d("end budget mode now!");
                isFilterModeActive = false;
                updateBeersAdapter(mBeerListFragmentPresenter.getBeers());
                mBeerListFragmentPresenter.clearFilteredBears();
            }
        });
        snackbar.show();
    }

    private void setQueryListenerOnSearchView(final MenuItem actionMenuItem) {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.d("Budget Entered By User: %s", query);
                mPH = Double.valueOf(query);
                mBeerListFragmentPresenter.filterBeersAccordingToPhLevel(mPH * 1.0);
                if(!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }
                actionMenuItem.collapseActionView();
                mAppbarLayout.setExpanded(isAppbarExpanded);

                showEndBudgetModeSnackBar();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private MainActivity getMainActivity() {
        return ((MainActivity) getActivity());
    }

    @Subscribe
    public void onBeerListCellClicked(OnBeerListCardClickedEvent event) {
        Timber.d("onBeerListCellClicked");
        EventBus.getDefault().post(mBeerListFragmentPresenter.getBeersList().get(event.getClickedItemPosition()));
    }

    @Subscribe
    public void onBeersFetchedSuccessfully(OnBeersFetchSuccessEvent event) {
        if(getMainActivity() != null) {
            getMainActivity().onNetworkProcessEnded();
        }
        mPageOffset += 1;

        mNumberOfFetchedBeers+=mBeersLimitPerPage;
        mNetworkRequestEnqueue = false;
        mProgressBar.setVisibility(View.GONE);
        mBeerListFragmentPresenter.createBeersListFromServerResponse(event.getBeerList());
    }

    @Subscribe
    public void onBeersFetchingFailed(OnBeersFetchFailureEvent event) {
        Timber.e("beer Fetching failure message: %s", event.getError());
        mNetworkRequestEnqueue = false;
        if(getMainActivity() != null) {
            getMainActivity().onNetworkProcessEnded();
        }
        onFailureInDataFetching();
    }

    @Subscribe
    public void onBeerListCreationComplete(OnBeerListCreationCompleteEvent event) {
        Timber.e("onBeerListCreationComplete");
        updateBeersAdapter(mBeerListFragmentPresenter.getBeers());
    }

    @Subscribe
    public void onBeerListCreationFailure(OnBeerListCreationFailureEvent event) {
        Timber.e("onBeerListCreationFailure: %s", (event.getError().isEmpty())?getString(R.string.string_no_data_found_on_server):event.getError());
    }

    @Subscribe
    public void onFilterTaskComplete(final OnBeersFilterTaskCompleteEvent event) {
        Timber.e("onFilterTaskComplete");
        isFilterModeActive = true;
        updateBeersAdapter(mBeerListFragmentPresenter.getFilteredBeers());
    }

    @Subscribe
    public void onFilterTaskFailure(OnBeersFilterTaskFailureEvent event) {
        Timber.e("onFilterTaskComplete %s", event.getError());
    }
}
