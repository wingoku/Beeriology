package com.wingoku.punkBeer.modules;

import com.wingoku.punkBeer.interfaces.PunkAPI;
import com.wingoku.punkBeer.interfaces.qualifiers.PerFragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Umer on 6/14/2017.
 */
@Module
public class RetrofitBaseUrlModule {

    @Provides
    @PerFragmentScope
    public String providesPunkAPIUrl() {
        return PunkAPI.BASE_URL;
    }
}
