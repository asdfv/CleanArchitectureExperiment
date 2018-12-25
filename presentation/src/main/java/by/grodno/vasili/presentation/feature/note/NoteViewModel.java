package by.grodno.vasili.presentation.feature.note;

import android.arch.lifecycle.ViewModel;

import com.annimon.stream.function.Consumer;

import javax.inject.Inject;

import by.grodno.vasili.domain.interactor.SaveNoteUseCase;
import by.grodno.vasili.domain.model.Note;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

public class NoteViewModel extends ViewModel {
    private final SaveNoteUseCase useCase;

    @Inject
    NoteViewModel(SaveNoteUseCase useCase) {
        this.useCase = useCase;
    }

    void saveNoteAsync(Note note, Consumer<String> action, Consumer<Throwable> onError) {
        DisposableSingleObserver<String> observer = new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String savedId) {
                action.accept(savedId);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Error executing use case");
                onError.accept(e);
            }
        };
        useCase.execute(observer, SaveNoteUseCase.Params.create(note));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        useCase.dispose();
    }
}
