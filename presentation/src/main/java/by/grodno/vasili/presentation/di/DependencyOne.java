package by.grodno.vasili.presentation.di;

import javax.inject.Inject;

import by.grodno.vasili.presentation.di.scopes.NotesActivityScope;

@NotesActivityScope
class DependencyOne implements Dependency {
    @Inject
    DependencyOne() {
    }

    @Override
    public String getName() {
        return "DependencyOne!";
    }
}
