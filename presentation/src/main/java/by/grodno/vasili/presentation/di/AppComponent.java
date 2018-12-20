package by.grodno.vasili.presentation.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import by.grodno.vasili.presentation.NoteApplication;
import by.grodno.vasili.presentation.di.scopes.NotesActivityScope;
import by.grodno.vasili.presentation.screen.notes.NotesActivity;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
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
    @Binds
    @Singleton
    Context bindContext(Application application);
}

@Module
interface ActivityBuilder {
    @NotesActivityScope
    @ContributesAndroidInjector(modules = {NotesActivityModule.class})
    NotesActivity bindNotesActivity();
}

@Module
interface NotesActivityModule {
    @Binds
    @NotesActivityScope
    Dependency bindDependencyOne(DependencyOne dependencyOne);
}
