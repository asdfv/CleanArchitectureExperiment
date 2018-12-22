package by.grodno.vasili.presentation.feature.notes;

import android.arch.lifecycle.ViewModel;

import by.grodno.vasili.data.datasource.FirebaseNoteEntityDatasource;
import by.grodno.vasili.data.datasource.NoteEntityDatasource;
import by.grodno.vasili.data.entity.mapper.NoteEntityDataMapper;
import by.grodno.vasili.data.repository.NoteDataRepository;
import by.grodno.vasili.domain.interactor.GetNotesListUseCase;
import by.grodno.vasili.domain.repository.NoteRepository;
import by.grodno.vasili.presentation.dagger.ViewModelKey;
import by.grodno.vasili.presentation.thread.IOThread;
import by.grodno.vasili.presentation.thread.UIThread;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class NotesActivityModule {

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

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel.class)
    abstract ViewModel bindNotesViewModel(NotesViewModel notesViewModel);
}
