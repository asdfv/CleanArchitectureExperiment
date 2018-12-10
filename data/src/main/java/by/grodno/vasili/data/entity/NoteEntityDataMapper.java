package by.grodno.vasili.data.entity;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import by.grodno.vasili.domain.model.Note;

/**
 * Mapper class used to transform {@link NoteEntity} from data layer to {@link Note} in the domain layer.
 */
public class NoteEntityDataMapper {

    /**
     * Transform a {@link NoteEntity} into an {@link Note}.
     *
     * @param noteEntity Object to be transformed.
     * @return {@link Note} if {@link NoteEntity} is not null otherwise null.
     */
    public Note convert(NoteEntity noteEntity) {
        if (noteEntity == null) {
            return null;
        }
        return new Note(noteEntity.id, noteEntity.title, noteEntity.description);
    }

    /**
     * Transform a List of {@link NoteEntity} into a List of {@link Note}.
     *
     * @param noteEntities List to be transformed.
     * @return List of {@link Note}`s if {@link NoteEntity} is not null otherwise null.
     */
    public Collection<Note> convert(Collection<NoteEntity> noteEntities) {
        List<Note> result = Collections.emptyList();
        if (CollectionUtils.isEmpty(noteEntities)) {
            return result;
        }
        for (NoteEntity noteEntity : noteEntities) {
            if (noteEntity != null) {
                result.add(convert(noteEntity));
            }
        }
        return result;
    }
}
