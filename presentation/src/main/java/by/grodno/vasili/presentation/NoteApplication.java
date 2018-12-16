package by.grodno.vasili.presentation;

import android.support.multidex.MultiDexApplication;

import timber.log.Timber;

/**
 * Main application class
 */
public class NoteApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
