package by.grodno.vasili.presentation.feature.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import by.grodno.vasili.domain.interactor.GetNotesListUseCase;
import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.model.NoteItem;
import by.grodno.vasili.presentation.model.NoteItemMapper;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

public class NotesViewModel extends ViewModel {
    private final GetNotesListUseCase useCase;
    private final NoteItemMapper mapper;
    private MutableLiveData<List<NoteItem>> notesLiveData;

    @Inject
    NotesViewModel(GetNotesListUseCase useCase, NoteItemMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    LiveData<List<NoteItem>> getNotesLiveData() {
        if (notesLiveData == null) {
            notesLiveData = new MutableLiveData<>();
            loadNotesAsync();
        }
        return notesLiveData;
    }

    private void loadNotesAsync() {
        DisposableSingleObserver<List<Note>> observer = new DisposableSingleObserver<List<Note>>() {
            @Override
            public void onSuccess(List<Note> receivedNotes) {
                notesLiveData.setValue(mapper.convert(receivedNotes));
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Error executing use case for get all notes");
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
