package by.grodno.vasili.presentation.screen.notes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Collection;

import javax.inject.Inject;

import by.grodno.vasili.domain.interactor.GetNotesListUseCase;
import by.grodno.vasili.domain.model.Note;
import io.reactivex.observers.DisposableSingleObserver;

public class NotesViewModel extends ViewModel {
    private final GetNotesListUseCase useCase;
    private MutableLiveData<Collection<Note>> notesLiveData;

    @Inject
    public NotesViewModel(GetNotesListUseCase useCase) {
        this.useCase = useCase;
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
