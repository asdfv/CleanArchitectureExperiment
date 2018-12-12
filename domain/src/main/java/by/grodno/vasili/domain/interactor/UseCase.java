package by.grodno.vasili.domain.interactor;

import by.grodno.vasili.domain.executor.PostExecutionThread;
import by.grodno.vasili.domain.executor.ThreadExecutor;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a Use Case.
 * This interface represents a execution unit for different use cases.
 */
public abstract class UseCase<T, Param> {
    private final CompositeDisposable disposables;
    private final ThreadExecutor executor;
    private final PostExecutionThread postExecutionThread;

    UseCase(ThreadExecutor executor, PostExecutionThread postExecutionThread) {
        this.disposables = new CompositeDisposable();
        this.executor = executor;
        this.postExecutionThread = postExecutionThread;
    }

    /**
     * Creating observable for executing use case
     * @param params parameters with needed for execute user case
     * @return Single observable
     */
    abstract Single<T> buildObservableForUseCase(Param params);

    /**
     * Execute use case with params and observer
     * @param observer for observing result of execution use case
     * @param params for executing use case
     */
    public void execute(DisposableSingleObserver<T> observer, Param params) {
        final Single<T> observable = buildObservableForUseCase(params)
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postExecutionThread.getScheduler());
        disposables.add(observable.subscribeWith(observer));
    }

    /**
     * Dispose execution
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
