package by.grodno.vasili.presentation.feature.notes;

import android.arch.lifecycle.ViewModel;

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
    static GetNotesListUseCase provideUserCase(IOThread ioThread, UIThread uiThread, NoteRepository repository) {
        return new GetNotesListUseCase(ioThread, uiThread, repository);
    }

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel.class)
    abstract ViewModel bindNotesViewModel(NotesViewModel notesViewModel);
}
