package by.grodno.vasili.presentation.dagger;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
interface AppModule {
    @SuppressWarnings("unused")
    @Binds
    @Singleton
    Context bindContext(Application application);

    @SuppressWarnings("unused")
    @Binds
    ViewModelProvider.Factory provideViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
