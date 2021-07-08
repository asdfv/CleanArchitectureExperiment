package by.grodno.vasili.presentation;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import javax.inject.Inject;

import by.grodno.vasili.presentation.dagger.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Main application class
 */
public class NoteApplication extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        initDagger();
    }

    private void initDagger() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
