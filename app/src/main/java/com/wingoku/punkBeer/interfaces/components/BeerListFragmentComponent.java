package com.wingoku.punkBeer.interfaces.components;

import com.wingoku.punkBeer.fragments.BeerListFragment;
import com.wingoku.punkBeer.interfaces.qualifiers.PerFragmentScope;
import com.wingoku.punkBeer.modules.BeerCacheDBModule;
import com.wingoku.punkBeer.modules.PicassoModule;
import com.wingoku.punkBeer.modules.RetrofitModule;

import dagger.Component;

/**
 * Created by Umer on 6/14/2017.
 */

@Component(modules = {RetrofitModule.class, PicassoModule.class, BeerCacheDBModule.class})
@PerFragmentScope
public interface BeerListFragmentComponent {
    void inject(BeerListFragment fragment);
}
