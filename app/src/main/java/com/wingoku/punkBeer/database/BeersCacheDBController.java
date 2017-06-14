package com.wingoku.punkBeer.database;

import com.wingoku.punkBeer.models.db.Beer;
import com.wingoku.punkBeer.utils.Utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by Umer on 6/14/2017.
 */

public class BeersCacheDBController {
    private final Realm realm;

    public BeersCacheDBController() {
        realm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return realm;
    }

    public void insertBeer(Beer beer) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(beer);
        realm.commitTransaction();
    }

    public void insertBeerRealmList(final List<Beer> beerList) {
        for(Beer beer : beerList) {
            insertBeer(beer);
        }
    }

    /**
     * Clear all the entries in the RealmDB
     */
    public void clearAll() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    /**
     * Get all Beer entries from RealmDB
     */
    public RealmResults<Beer> getAllBeers() {
        return realm.where(Beer.class).findAll();
    }

    /**
     * Delete a beer entry from database created on specific date
     * @param date date on which the beer was inserted in DB
     */
    public void deleteBeersInsertedOnDate(final String date) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Beer> result = realm.where(Beer.class).equalTo("mDBEntryDate", date).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    /**
     * Get @{@link Beer} inserted in DB on a specific date
     * @param date date on which Beer was inserted in DB
     * @return returns {@link Beer}
     */
    public Beer getBeerRealmForDate(String date) {
        return realm.where(Beer.class).equalTo("mDBEntryDate", date).findFirst();
    }

    /**
     * Validate if the entries in DB are older then the {@link com.wingoku.punkBeer.utils.Constants#MAX_STALE_DAYS}, if yes, delete them
     * @param maxAge {@link com.wingoku.punkBeer.utils.Constants#MAX_STALE_DAYS}
     */
    public void validateExpiryDateForDBEntry(int maxAge) {
        Timber.d("previous date is: %s", Utils.getDate(maxAge));
        deleteBeersInsertedOnDate(Utils.getDate(maxAge));
    }
}
