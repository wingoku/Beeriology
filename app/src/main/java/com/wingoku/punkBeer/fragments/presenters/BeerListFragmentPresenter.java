package com.wingoku.punkBeer.fragments.presenters;

import com.squareup.picasso.Picasso;
import com.wingoku.punkBeer.database.BeersCacheDBController;
import com.wingoku.punkBeer.eventBus.OnBeerListCreationCompleteEvent;
import com.wingoku.punkBeer.eventBus.OnBeerListCreationFailureEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFetchFailureEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFetchSuccessEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFilterTaskCompleteEvent;
import com.wingoku.punkBeer.eventBus.OnBeersFilterTaskFailureEvent;
import com.wingoku.punkBeer.interfaces.PunkAPI;
import com.wingoku.punkBeer.models.db.Beer;
import com.wingoku.punkBeer.models.db.Beers;
import com.wingoku.punkBeer.models.serverResponse.punkAPI.BeerServer;
import com.wingoku.punkBeer.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Umer on 6/14/2017.
 */

public class BeerListFragmentPresenter {

    private Beers mBeers;
    private Beers mFilteredBeers; // this object will used to stored beers filtered based on ph level criteria
    private CompositeDisposable mCompositeDisposable;

    private BeersCacheDBController mBeersCacheDBController;
    private Retrofit mRetrofit;
    private Picasso mPicasso;

    /**
     * @param retrofit instance of retrofit
     * @param picasso instance of picasso
     * @param beersCacheDBController instance of beersCacheDB
     */
    @Inject //This is called constructor injection
    public BeerListFragmentPresenter(Retrofit retrofit, Picasso picasso, BeersCacheDBController beersCacheDBController) {
        mRetrofit = retrofit;
        mPicasso = picasso;
        mBeersCacheDBController = beersCacheDBController;

        Timber.e("BeerListFragmentPresenter");
        mBeers = new Beers();
        mFilteredBeers = new Beers();
        mCompositeDisposable = new CompositeDisposable();

        mBeersCacheDBController.validateExpiryDateForDBEntry(Constants.MAX_STALE_DAYS);
    }

    /**
     *  Fetch beer From beer Server
     * @param limit number of beers to fetched in a single network call
     * @param pageNumber number of beer pages to offset
     */
    public void fetchBeers(int limit, int pageNumber) {
        Timber.d("Fetch Comics From Server");
        Disposable beerNetworkDisposable = PunkAPI.Factory.getInstance(mRetrofit).getBeers(limit, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<BeerServer>>() {
                    @Override
                    public void onNext(List<BeerServer> response) {
                        Timber.e("response size is: %s", response.size());
                        if(response.size() == 0 /*&& response.getBeerServerList().size() > 0*/) {
                            beerFetchingFailure("");
                            return;
                        }
                        beerFetchingSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        beerFetchingFailure(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mCompositeDisposable.add(beerNetworkDisposable);
    }

    /**
     * call upon beers fetching failure from server
     * @param localizedMessage ERROR MESSAGE
     */
    private void beerFetchingFailure(String localizedMessage) {
        EventBus.getDefault().post(new OnBeersFetchFailureEvent(localizedMessage));
    }

    /**
     * Call upon success in fetching beers from server.
     * @param beerServerList list containing {@link Beer}
     */
    private void beerFetchingSuccess(List<BeerServer> beerServerList) {
        for(BeerServer beer : beerServerList)
            Timber.e("name is: %s", beer.getName());
        EventBus.getDefault().post(new OnBeersFetchSuccessEvent(beerServerList));
    }

    /**
     * Generate List of  from the List of {@link Beer} fetched from Beer server
     * @param beerServerList list of {@link Beer} fetching server
     */
    @Deprecated
    public void createBeersListFromServerResponse(final List<BeerServer> beerServerList) {
        Disposable createBeerListDisposable = Observable.fromIterable(beerServerList)
                .map(new Function<BeerServer, Beer>() {
                    @Override
                    public Beer apply(@NonNull BeerServer beerServer) throws Exception {
                        Beer beer = new Beer();
                        Timber.d("Beer DB Entry Date: %s", beer.getDBEntryDate());
                        beer.setId(beerServer.getId());
                        beer.setName(beerServer.getName());
                        beer.setDescription(beerServer.getDescription());
                        beer.setImageUrl(beerServer.getImageUrl());
                        beer.setPH(beerServer.getPh());
                        beer.setVolume(beerServer.getVolume().getValue() + " " + beerServer.getVolume().getUnit());
                        beer.setContributedBy(beerServer.getContributedBy());

                        return beer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Beer>() {
                    @Override
                    public void onNext(Beer beer) {
                        Timber.d("createComicListFromServerResponse()::onNext");
                        getBeersList().add(beer);
                        mBeersCacheDBController.insertBeer(beer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("createBeerListFromServerResponse()::onError: %s", e);
                        EventBus.getDefault().post(new OnBeerListCreationFailureEvent(e.getLocalizedMessage()));
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("createBeerListFromServerResponse()::onComplete %s", getBeersList().size());
                        EventBus.getDefault().post(new OnBeerListCreationCompleteEvent());
                    }
                });

        mCompositeDisposable.add(createBeerListDisposable);
    }

    /**
     * Filter the beers list according to specified budget
     * @param phLevel budget amount
     */
    public void filterBeersAccordingToPhLevel(final double phLevel) {
        Disposable filterDisposable = Observable.fromIterable(getBeersList())
                .filter(new Predicate<Beer>() {
                    @Override
                    public boolean test(@NonNull Beer beer) throws Exception {
                        return beer.getPH() == phLevel;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Beer>() {
                    @Override
                    public void onNext(Beer beer) {
                        mFilteredBeers.getBeerList().add(beer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new OnBeersFilterTaskFailureEvent(e.getLocalizedMessage()));
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new OnBeersFilterTaskCompleteEvent());
                    }
                });

        mCompositeDisposable.add(filterDisposable);
    }

    /**
     * Get {@link Beers}
     * @return BeersServer
     */
    public Beers getBeers() {
        return mBeers;
    }

    public Beers getFilteredBeers() {
        return mFilteredBeers;
    }

    public void clearFilteredBears() {
        mFilteredBeers.getBeerList().clear();
    }

    /**
     * Get {@link Beer} List
     * @return beer List
     */
    public List<Beer> getBeersList() {
        return mBeers.getBeerList();
    }

    /**
     * {@link Beer} list size
     * @return Beers List size
     */
    public int getBeerListSize() {
        return mBeers.getBeerList().size();
    }

    /**
     * Get picasso Instance from Network Component used in this presenter
     * @return Picasso instance
     */
    public Picasso getPicassoInstance() {
        return mPicasso;
    }

    /**
     * Rerturns list of {@link Beer} list from DB to be used as offline cache
     * @return List<beer></>
     */
    public List<Beer> getCachedBeersFromDB() {
        return mBeersCacheDBController.getRealm().copyFromRealm(mBeersCacheDBController.getAllBeers());
    }

    /**
     * Clears off all the observables that are current active.
     * Must call this method upon configuration/orientation change!
     */
    public void clearOffObservables() {
        mCompositeDisposable.clear();
    }
}