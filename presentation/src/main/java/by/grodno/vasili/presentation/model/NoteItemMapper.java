package by.grodno.vasili.presentation.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.presentation.feature.notes.NotesActivityScope;

/**
 * Mapper class used to convert {@link Note} from Domain layer to
 * {@link NoteItem} in Presentation layer
 */
@NotesActivityScope
public class NoteItemMapper {

    @Inject
    NoteItemMapper() {
    }

    /**
     * Convert single {@link Note}
     */
    @Nullable
    private NoteItem convert(Note note) {
        if (note == null) {
            return null;
        }
        return new NoteItem(note.id, note.title, note.description);
    }

    /**
     * Convert list of {@link Note}
     */
    @NonNull
    public List<NoteItem> convert(List<Note> notes) {
        List<NoteItem> items = new ArrayList<>();
        if (CollectionUtils.isEmpty(notes)) {
            return items;
        }
        for (Note note : notes) {
            if (note == null) {
                continue;
            }
            NoteItem transform = convert(note);
            items.add(transform);
        }
        return items;
    }
}
