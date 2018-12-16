package by.grodno.vasili.data.entity.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import by.grodno.vasili.data.entity.NoteEntity;
import by.grodno.vasili.domain.model.Note;
import timber.log.Timber;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * Mapper class used to transform Firebase {@link DataSnapshot} to {@link NoteEntity}
 * from data layer and {@link Note} in the domain layer.
 */
public class NoteEntityDataMapper {
    /**
     * Transform a {@link DataSnapshot} into an {@link NoteEntity}.
     */
    @Nullable
    public NoteEntity convert(DataSnapshot snapshot) {
        if (!snapshot.exists()) {
            Timber.d("Empty snapshot");
            return null;
        }
        try {
            NoteEntity noteEntity = snapshot.getValue(NoteEntity.class);
            noteEntity.id = snapshot.getKey();
            return noteEntity;
        } catch (DatabaseException e) {
            Timber.e(e, "Error converting snapshot to NoteEntity");
        }
        return null;
    }

    /**
     * Transform a {@link DataSnapshot} into a Collection of {@link NoteEntity}.
     */
    @NonNull
    public Collection<NoteEntity> convertToCollection(DataSnapshot snapshot) {
        if (!snapshot.exists()) {
            Timber.d("Empty snapshot");
            return emptyList();
        }
        Map<String, NoteEntity> map = getNoteEntitiesMap(snapshot);
        Collection<NoteEntity> result = new LinkedList<>();
        for (Map.Entry<String, NoteEntity> entry : map.entrySet()) {
            NoteEntity entity = entry.getValue();
            if (entity != null) {
                entity.id = entry.getKey();
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * Transform a {@link NoteEntity} into an {@link Note}.
     */
    @Nullable
    public Note convert(NoteEntity entity) {
        if (entity == null) {
            Timber.d("Null NoteEntity while convert to Note");
            return null;
        }
        return new Note(entity.id, entity.title, entity.description);
    }

    /**
     * Transform a collection of {@link NoteEntity} into an collection of {@link Note}.
     */
    @NonNull
    public Collection<Note> convert(Collection<NoteEntity> entities) {
        Collection<Note> result = new LinkedList<>();
        for (NoteEntity entity : entities) {
            if (entity != null) {
                result.add(convert(entity));
            }
        }
        return result;
    }

    private Map<String, NoteEntity> getNoteEntitiesMap(DataSnapshot snapshot) {
        try {
            GenericTypeIndicator<Map<String, NoteEntity>> type = new GenericTypeIndicator<Map<String, NoteEntity>>() {
            };
            return defaultIfNull(snapshot.getValue(type), emptyMap());
        } catch (DatabaseException e) {
            Timber.e(e, "Error converting snapshot to NoteEntity map");
        }
        return emptyMap();
    }
}
