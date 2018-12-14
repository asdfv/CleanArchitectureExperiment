package by.grodno.vasili.presentation.screen.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Collection;

import by.grodno.vasili.data.datasource.FirebaseNoteEntityDatasource;
import by.grodno.vasili.data.entity.NoteEntityDataMapper;
import by.grodno.vasili.data.repository.NoteDataRepository;
import by.grodno.vasili.domain.interactor.GetNotesListUseCase;
import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.thread.IOThread;
import by.grodno.vasili.presentation.thread.UIThread;
import io.reactivex.observers.DisposableSingleObserver;

public class NotesViewModel extends ViewModel {
    private final GetNotesListUseCase notesListUseCase;
    private MutableLiveData<Collection<Note>> notesLiveData;

    // TODO: IOC
    public NotesViewModel() {
        FirebaseNoteEntityDatasource datasource = new FirebaseNoteEntityDatasource();
        NoteEntityDataMapper mapper = new NoteEntityDataMapper();
        this.notesListUseCase = new GetNotesListUseCase(
                new IOThread(),
                new UIThread(),
                new NoteDataRepository(datasource, mapper)
        );
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
        notesListUseCase.execute(observer, null);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        notesListUseCase.dispose();
    }
}
