package com.wingoku.punkBeer.modules;

import com.wingoku.punkBeer.database.BeersCacheDBController;
import com.wingoku.punkBeer.interfaces.qualifiers.PerFragmentScope;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by Umer on 6/14/2017.
 */

@Module
public class BeerCacheDBModule {

    @Provides
    @PerFragmentScope
    public BeersCacheDBController providesBeersDBController() {
        Timber.e("proviesComicsDBController()");
        return new BeersCacheDBController();
    }
}
