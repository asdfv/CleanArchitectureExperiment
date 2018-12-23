package by.grodno.vasili.domain.interactor;

import by.grodno.vasili.domain.executor.PostExecutionThread;
import by.grodno.vasili.domain.executor.SubscriberThread;
import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.domain.repository.NoteRepository;
import io.reactivex.Single;

/**
 * Use case saving Note to repository
 */
public class SaveNoteUseCase extends UseCase<String, SaveNoteUseCase.Params> {
    private final NoteRepository repository;

    public SaveNoteUseCase(SubscriberThread subscriberThread, PostExecutionThread postExecutionThread, NoteRepository repository) {
        super(postExecutionThread, subscriberThread);
        this.repository = repository;
    }

    @Override
    Single<String> buildObservableForUseCase(Params params) {
        return repository.insert(params.note);
    }

    public static final class Params {
        private final Note note;

        private Params(Note note) {
            this.note = note;
        }

        public static Params create(Note note) {
            return new Params(note);
        }
    }
}
