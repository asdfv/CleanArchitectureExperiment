package by.grodno.vasili.presentation.feature.notedetails;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import by.grodno.vasili.domain.interactor.GetNoteUseCase;
import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.model.NoteItem;
import by.grodno.vasili.presentation.model.NoteItemMapper;
import io.reactivex.observers.DisposableMaybeObserver;
import timber.log.Timber;

/**
 * View model for activity with note details
 */
public class DetailsViewModel extends ViewModel {
    private final GetNoteUseCase getNoteUseCase;
    private final NoteItemMapper mapper;
    MutableLiveData<NoteItem> noteLiveData;

    @Inject
    DetailsViewModel(GetNoteUseCase getNoteUseCase, NoteItemMapper mapper) {
        this.getNoteUseCase = getNoteUseCase;
        this.mapper = mapper;
        this.noteLiveData = new MutableLiveData<>();
    }

    /**
     * Get one note from repository
     */
    void getNoteAsync(String id) {
        DisposableMaybeObserver<Note> observer = new DisposableMaybeObserver<Note>() {
            @Override
            public void onSuccess(Note note) {
                noteLiveData.setValue(mapper.convert(note));
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Error while retrieving Note");
            }

            @Override
            public void onComplete() {
                Timber.i("Not found Note with id: %s", id);
            }
        };
        getNoteUseCase.execute(observer, GetNoteUseCase.Params.create(id));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getNoteUseCase.dispose();
    }
}
