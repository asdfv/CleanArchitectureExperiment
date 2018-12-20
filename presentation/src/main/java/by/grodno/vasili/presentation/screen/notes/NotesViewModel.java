package by.grodno.vasili.presentation.screen.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Collection;

import by.grodno.vasili.data.datasource.FirebaseNoteEntityDatasource;
import by.grodno.vasili.data.datasource.NoteEntityDatasource;
import by.grodno.vasili.data.entity.mapper.NoteEntityDataMapper;
import by.grodno.vasili.data.repository.NoteDataRepository;
import by.grodno.vasili.domain.interactor.GetNotesListUseCase;
import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.domain.repository.NoteRepository;
import by.grodno.vasili.presentation.thread.IOThread;
import by.grodno.vasili.presentation.thread.UIThread;
import io.reactivex.observers.DisposableSingleObserver;

public class NotesViewModel extends ViewModel {
    private final GetNotesListUseCase useCase;
    private MutableLiveData<Collection<Note>> notesLiveData;

    // TODO: IOC
    public NotesViewModel() {
        NoteEntityDataMapper mapper = new NoteEntityDataMapper();
        NoteEntityDatasource datasource = new FirebaseNoteEntityDatasource(mapper);
        NoteRepository repository = new NoteDataRepository(datasource, mapper);
        UIThread uiThread = new UIThread();
        IOThread ioThread = new IOThread();
        this.useCase = new GetNotesListUseCase(ioThread, uiThread, repository);
    }

    LiveData<Collection<Note>> getNotesLiveData() {
        if (notesLiveData == null) {
            notesLiveData = new MutableLiveData<>();
            loadNotesAsync();
        }
        return notesLiveData;
    }

    private void loadNotesAsync() {
        DisposableSingleObserver<Collection<Note>> observer = new DisposableSingleObserver<Collection<Note>>() {
            @Override
            public void onSuccess(Collection<Note> receivedNotes) {
                notesLiveData.setValue(receivedNotes);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
        useCase.execute(observer, null);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        useCase.dispose();
    }
}
