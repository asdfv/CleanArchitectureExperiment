package by.grodno.vasili.presentation.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import javax.inject.Singleton;

import by.grodno.vasili.data.datasource.FirebaseNoteEntityDatasource;
import by.grodno.vasili.data.datasource.NoteEntityDatasource;
import by.grodno.vasili.data.entity.mapper.NoteEntityDataMapper;
import by.grodno.vasili.data.repository.NoteDataRepository;
import by.grodno.vasili.domain.interactor.GetNotesListUseCase;
import by.grodno.vasili.domain.repository.NoteRepository;
import by.grodno.vasili.presentation.NoteApplication;
import by.grodno.vasili.presentation.di.scopes.NotesActivityScope;
import by.grodno.vasili.presentation.screen.notes.NotesActivity;
import by.grodno.vasili.presentation.thread.IOThread;
import by.grodno.vasili.presentation.thread.UIThread;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
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
abstract class NotesActivityModule {

    @Provides
    @NotesActivityScope
    static GetNotesListUseCase provideUserCase() {
        NoteEntityDataMapper mapper = new NoteEntityDataMapper();
        NoteEntityDatasource datasource = new FirebaseNoteEntityDatasource(mapper);
        NoteRepository repository = new NoteDataRepository(datasource, mapper);
        UIThread uiThread = new UIThread();
        IOThread ioThread = new IOThread();
        return new GetNotesListUseCase(ioThread, uiThread, repository);
    }

    @Provides
    @NotesActivityScope
    static ViewModelProvider.Factory bindFactory(GetNotesListUseCase useCase) {
        return new ViewModelProviderFactory<>(useCase);
    }
}
