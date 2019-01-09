package by.grodno.vasili.presentation.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.grodno.vasili.domain.model.Note;

/**
 * Mapper class used to convert {@link Note} from Domain layer to
 * {@link NoteItem} in Presentation layer
 */
@Singleton
public class NoteItemMapper {
    private static final String DATE_FORMAT ="yyyy-MM-dd HH:mm:ss";
    private static final String NO_DATE = "No date";
    private final SimpleDateFormat simpleDateFormat;

    @Inject
    NoteItemMapper() {
        this.simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        simpleDateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
    }

    /**
     * Convert single {@link Note}
     */
    @Nullable
    public NoteItem convert(Note note) {
        if (note == null) {
            return null;
        }
        long timestamp = note.created;
        String created = timestamp == 0 ? NO_DATE : simpleDateFormat.format(new Date(timestamp));
        return new NoteItem(note.id, note.title, note.description, created);
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
