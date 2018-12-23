package by.grodno.vasili.presentation.dagger;

import by.grodno.vasili.presentation.feature.note.NoteActivity;
import by.grodno.vasili.presentation.feature.note.NoteActivityModule;
import by.grodno.vasili.presentation.feature.note.NoteActivityScope;
import by.grodno.vasili.presentation.feature.notes.NotesActivity;
import by.grodno.vasili.presentation.feature.notes.NotesActivityModule;
import by.grodno.vasili.presentation.feature.notes.NotesActivityScope;
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

    @SuppressWarnings("unused")
    @NoteActivityScope
    @ContributesAndroidInjector(modules = {NoteActivityModule.class})
    NoteActivity bindNoteActivity();
}
