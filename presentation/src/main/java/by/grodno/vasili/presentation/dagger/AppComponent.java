package by.grodno.vasili.presentation.dagger;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import by.grodno.vasili.presentation.NoteApplication;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {
    void inject(NoteApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

@Module
interface AppModule {
    @SuppressWarnings("unused")
    @Binds
    @Singleton
    Context bindContext(Application application);

    @Provides
    static ViewModelProvider.Factory bindViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        return new ViewModelProviderFactory(creators);
    }
}

