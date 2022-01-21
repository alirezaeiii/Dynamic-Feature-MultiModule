package com.android.sample.core.di

import javax.inject.Scope

/*
* Scope for the entire app runtime.
*/
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

/**
 * Scope for a feature module.
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope