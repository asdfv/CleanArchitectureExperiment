package by.grodno.vasili.data.entity.mapper;

import com.google.firebase.database.DataSnapshot;

import by.grodno.vasili.data.entity.NoteEntity;
import by.grodno.vasili.domain.mapper.Mapper;
import by.grodno.vasili.domain.model.Note;
import timber.log.Timber;

/**
 * Mapper class used to transform Firebase {@link DataSnapshot} to {@link NoteEntity}
 * from data layer and {@link Note} in the domain layer.
 */
public class NoteEntityDataMapper extends Mapper<NoteEntity, Note> {

    @Override
    public Note map(NoteEntity entity) {
        if (entity == null) {
            Timber.d("Null NoteEntity while convert to Note");
            return null;
        }
        return new Note(entity.id, entity.title, entity.description, entity.getCreatedTimestamp());
    }

    @Override
    public NoteEntity reverseMap(Note note) {
        if (note == null) {
            Timber.d("Null Note while convert to NoteEntity");
            return null;
        }
        return new NoteEntity(note.id, note.title, note.description);
    }
}
