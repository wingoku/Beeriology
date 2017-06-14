package com.wingoku.punkBeer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.wingoku.punkBeer.fragments.BeerDetailFragment;
import com.wingoku.punkBeer.fragments.BeerListFragment;
import com.wingoku.punkBeer.fragments.ImageBrowserFragment;
import com.wingoku.punkBeer.fragments.SplashScreenFragment;
import com.wingoku.punkBeer.models.db.Beer;
import com.wingoku.punkBeer.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // this idling resource will be used by Espresso to wait for and synchronize with RetroFit Network call
    // https://youtu.be/uCtzH0Rz5XU?t=3m23s
    CountingIdlingResource mEspressoTestIdlingResource = new CountingIdlingResource("Network_Call");
    private FragmentManager mFManager;

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initializing Crashlytics for error reporting
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        //must bind for View Injection
        ButterKnife.bind(this);
        mFManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            mEspressoTestIdlingResource.increment();
            openFragment(new SplashScreenFragment(), true, false, false, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Open a fragment
     *
     * @param frag Fragment to open
     * @param isReplaced should this fragment replace current visible fragment
     * @param isAdded should this fragment be added on top of current fragment
     * @param addToBackStack should this fragment be added to backstack for removal upon onBackPressed
     * @param setEnterAnimation should this fragment be animated when replaced/added in the fragment container
     *
     */
    private void openFragment(Fragment frag, boolean isReplaced, boolean isAdded, boolean addToBackStack, boolean setEnterAnimation) {
        FragmentTransaction fTranscation = mFManager.beginTransaction();

        if(setEnterAnimation)
            fTranscation.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        else
            fTranscation.setCustomAnimations(0, 0, R.anim.enter_from_left, R.anim.exit_to_right);

        if (isReplaced)
            fTranscation.replace(R.id.fragment_container, frag);
        else if (isAdded)
            fTranscation.add(R.id.fragment_container, frag);

        if (addToBackStack)
            fTranscation.addToBackStack(frag.getClass().getSimpleName());

        fTranscation.commit();
    }

    private void showOptionsAlertDialog(final Beer beer) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.string_choose);
        builder.setPositiveButton(R.string.string_dialog_details_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // open fragment
                dialogInterface.dismiss();
                openBeerDetailsFragment(beer);
            }
        });

        builder.setNeutralButton(R.string.string_dialog_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(R.string.string_dialog_browser_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // open browser
                dialogInterface.dismiss();
                openBeerImageBrowserFragment(beer);
            }
        });

        builder.create().show();
    }

    private void openBeerDetailsFragment(Beer beer) {
        BeerDetailFragment beerDetailFragment = new BeerDetailFragment();
        Bundle bund = new Bundle();
        bund.putParcelable(Constants.PARCELABLE_BEER_OBJECT, beer);
        beerDetailFragment.setArguments(bund);
        openFragment(beerDetailFragment, true, false, true, true);
    }

    private void openBeerImageBrowserFragment(Beer beer) {
        ImageBrowserFragment imageBrowserFragment = new ImageBrowserFragment();
        Bundle bund = new Bundle();
        bund.putParcelable(Constants.PARCELABLE_BEER_OBJECT, beer);
        imageBrowserFragment.setArguments(bund);
        openFragment(imageBrowserFragment, true, false, true, true);
    }

    public void onNetworkProcessStarted() {
        mEspressoTestIdlingResource.increment();
    }

    public void onNetworkProcessEnded() {
        if(!mEspressoTestIdlingResource.isIdleNow()) {
            mEspressoTestIdlingResource.decrement();
        }
    }

    /**
     * This method will return Espresso IdlingResource for aiding sync between RetroFit's custom background threads & Espresso
     *
     * @return MainActvity's idling resource for Espresso testing
     */
    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return mEspressoTestIdlingResource;
    }

    @Subscribe
    public void onBeerCardClickCallBacks(Beer beer) {
        showOptionsAlertDialog(beer);
    }

    // this method will be called when splash screen animation has been completed
    @Subscribe
    public void onAnimationEndEvent(Boolean onAnimationCompleted) {
        if(onAnimationCompleted) {
            mEspressoTestIdlingResource.decrement();
            openFragment(new BeerListFragment(), true, false, false, false);
        }
    }
}
