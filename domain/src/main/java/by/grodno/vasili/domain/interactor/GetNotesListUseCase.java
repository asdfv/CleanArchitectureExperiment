package by.grodno.vasili.domain.interactor;

import java.util.Collection;

import by.grodno.vasili.domain.executor.PostExecutionThread;
import by.grodno.vasili.domain.executor.ThreadExecutor;
import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.domain.repository.NoteRepository;
import io.reactivex.Single;

/**
 * Use case retrieving notes list from repository
 */
public class GetNotesListUseCase extends UseCase<Collection<Note>, Void> {
    private final NoteRepository repository;

    public GetNotesListUseCase(ThreadExecutor executor, PostExecutionThread postExecutionThread, NoteRepository repository) {
        super(executor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Single<Collection<Note>> buildObservableForUseCase(Void noParams) {
        return repository.getAll();
    }
}
