package com.wingoku.punkBeer.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Umer on 6/14/2017.
 */

@Module
public class ContextModule {

    private Context mContext;

    public ContextModule(Context context) {
        this.mContext = context;
    }

    @Provides
    public Context providesContext() {
        return mContext;
    }
}
