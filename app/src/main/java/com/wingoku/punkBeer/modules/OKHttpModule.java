package com.wingoku.punkBeer.modules;

import android.content.Context;

import com.wingoku.punkBeer.BuildConfig;
import com.wingoku.punkBeer.interfaces.qualifiers.PerFragmentScope;
import com.wingoku.punkBeer.interfaces.qualifiers.PicassoLoggingInterceptorQualifier;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Umer on 6/14/2017.
 */

@Module (includes = ContextModule.class)
public class OKHttpModule {

    private static final int REQUEST_RETRIES = 5;
    private static final int CONNECTION_TIME_OUT = 25;

    // NOTE:
    // USE ADD_INTERCEPTOR INSTEAD OF ADD_NETWORK_INTERCEPTOR IF WE WANT TO CALL CHAIN.PROCEED(REQUEST) MULTIPLE TIMES. ADd_NETWORK+INTERCEPTOR ALLOWS TO CALL CHAIN.PROCEED(REQUESt) ONLY ONCE!
    @Provides
    @PerFragmentScope
    public OkHttpClient providesOkHttpClient(final Context context, OkHttpClient.Builder builder, HttpLoggingInterceptor okhttpLoggingInterceptor, Cache cache, @Named("RetryInterceptor") Interceptor retriesInterceptor) {
        builder.cache(cache);
        Timber.d("added interceptor");
        builder.addInterceptor(retriesInterceptor);

        if(BuildConfig.DEBUG) {
            builder.addInterceptor(okhttpLoggingInterceptor);
        }

        return builder.build();
    }

    @Provides
    @PerFragmentScope
    @PicassoLoggingInterceptorQualifier
    public OkHttpClient providesOkHttpClientForPicasso(OkHttpClient.Builder builder, HttpLoggingInterceptor okhttpLoggingInterceptor) {
        if(BuildConfig.DEBUG) {
            builder.addInterceptor(okhttpLoggingInterceptor);
        }

        return builder.build();
    }

    @Provides
    public OkHttpClient.Builder provideOkHttpClientBuilder() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient().newBuilder();
        okhttpClientBuilder.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        return okhttpClientBuilder;
    }

    @Provides
    @PerFragmentScope
    public Cache providesCache(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "beer_responses");
        int cacheSize = 100 * 1024 * 1024; // 100 MB
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Provides
    public HttpLoggingInterceptor providesOkhttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Named("RetryInterceptor")
    public Interceptor providesRetryInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                int retries = 0;
                while(!response.isSuccessful() && retries < REQUEST_RETRIES) {
                    Timber.e("RESPONSE_ERROR: response message: %s response Code: %s", response.message(), response.code());

                    response = chain.proceed(chain.request());
                    retries++;
                }
                Timber.e("RESPONSE_SUCCESS: response message: %s response Code: %s", response.message(), response.code());
                return response;
            }
        };
    }
}
