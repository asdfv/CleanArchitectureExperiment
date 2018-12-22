package by.grodno.vasili.presentation.dagger;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import by.grodno.vasili.data.datasource.FirebaseNoteEntityDatasource;
import by.grodno.vasili.data.datasource.NoteEntityDatasource;
import by.grodno.vasili.data.entity.mapper.NoteEntityDataMapper;
import by.grodno.vasili.data.repository.NoteDataRepository;
import by.grodno.vasili.domain.repository.NoteRepository;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
interface AppModule {
    @Binds
    @Singleton
    @SuppressWarnings("unused")
    Context bindContext(Application application);

    @Provides
    @Singleton
    static ViewModelProvider.Factory bindViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        return new ViewModelProviderFactory(creators);
    }

    @Provides
    @Singleton
    static NoteRepository provideNoteRepository() {
        NoteEntityDataMapper mapper = new NoteEntityDataMapper();
        NoteEntityDatasource datasource = new FirebaseNoteEntityDatasource(mapper);
        return new NoteDataRepository(datasource, mapper);
    }
}
