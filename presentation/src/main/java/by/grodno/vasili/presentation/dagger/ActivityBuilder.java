package by.grodno.vasili.presentation.dagger;

import by.grodno.vasili.presentation.feature.notes.NotesActivityScope;
import by.grodno.vasili.presentation.feature.notes.NotesActivity;
import by.grodno.vasili.presentation.feature.notes.NotesActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Create subcomponent and injector for activities
 */
@Module
interface ActivityBuilder {
    @SuppressWarnings("unused")
    @NotesActivityScope
    @ContributesAndroidInjector(modules = {NotesActivityModule.class})
    NotesActivity bindNotesActivity();
}
