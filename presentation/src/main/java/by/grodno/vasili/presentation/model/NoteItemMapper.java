package by.grodno.vasili.presentation.model;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.grodno.vasili.domain.mapper.Mapper;
import by.grodno.vasili.domain.model.Note;

/**
 * Mapper for conversation objects from domain-layer into objects in presentation-layer
 */
@Singleton
public class NoteItemMapper extends Mapper<Note, NoteItem> {

    @Inject
    NoteItemMapper() {
    }


    @Override
    public NoteItem map(Note note) {
        if (note == null) {
            return null;
        }
        return new NoteItem(note.id, note.title, note.description, note.created);
    }

    @Override
    public Note reverseMap(NoteItem item) {
        if (item == null) {
            return null;
        }
        return new Note(item.id, item.title, item.description, item.created);
    }
}
