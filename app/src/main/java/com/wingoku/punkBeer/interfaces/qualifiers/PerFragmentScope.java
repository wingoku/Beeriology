package com.wingoku.punkBeer.interfaces.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Umer on 6/14/2017.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface PerFragmentScope {
}
