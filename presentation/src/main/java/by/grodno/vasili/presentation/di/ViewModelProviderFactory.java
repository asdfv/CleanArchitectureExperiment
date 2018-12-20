package by.grodno.vasili.presentation.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import by.grodno.vasili.domain.interactor.GetNotesListUseCase;
import by.grodno.vasili.presentation.screen.notes.NotesViewModel;

// TODO: generify this
public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {
    private final GetNotesListUseCase useCase;

    @Inject
    public ViewModelProviderFactory(GetNotesListUseCase useCase) {
        this.useCase = useCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NotesViewModel.class)) {
            return (T) new NotesViewModel(useCase);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
