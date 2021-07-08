package by.grodno.vasili.data.datasource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

import by.grodno.vasili.data.entity.NoteEntity;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import timber.log.Timber;

import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * {@link NoteEntityDatasource} implementation for work with Firebase realtime database
 */
public class FirebaseNoteEntityDatasource implements NoteEntityDatasource {
    private static final String NOTES_PATH = "notes";
    private final DatabaseReference notesRef;

    public FirebaseNoteEntityDatasource(FirebaseDatabase database) {
        this.notesRef = database.getReference(NOTES_PATH);
    }

    @Override
    public Maybe<NoteEntity> one(String id) {
        return Maybe.create(emitter -> notesRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        emitter.onSuccess(convert(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                })
        );
    }

    @Override
    public Single<List<NoteEntity>> all() {
        return Single.create(observer -> notesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    observer.onSuccess(convertToCollection(dataSnapshot));
                } catch (DatabaseException e) {
                    observer.onError(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                observer.onError(new Exception(error.getMessage()));
            }
        }));
    }

    @Override
    public Completable delete(String id) {
        return Completable.create(emitter -> {
            DatabaseReference noteRef = notesRef.child(id);
            noteRef.removeValue(completableListener(emitter));
        });
    }

    @Override
    public Single<String> insert(NoteEntity entity) {
        return Single.create(emitter -> notesRef
                .push()
                .setValue(entity, completionListener(emitter)));
    }

    @Override
    public Single<String> update(NoteEntity entity) {
        return Single.create(emitter -> notesRef.child(entity.id)
                .setValue(entity, completionListener(emitter)));
    }

    @NonNull
    private DatabaseReference.CompletionListener completionListener(SingleEmitter<String> emitter) {
        return (error, ref) -> {
            if (emitter.isDisposed()) {
                return;
            }
            if (error == null) {
                emitter.onSuccess(ref.getKey());
            } else {
                emitter.onError(error.toException());
            }
        };
    }

    @NonNull
    private DatabaseReference.CompletionListener completableListener(CompletableEmitter emitter) {
        return (error, ref) -> {
            if (emitter.isDisposed()) {
                return;
            }
            if (error == null) {
                emitter.onComplete();
            } else {
                emitter.onError(error.toException());
            }
        };
    }

    @Nullable
    private NoteEntity convert(DataSnapshot snapshot) {
        try {
            NoteEntity noteEntity = snapshot.getValue(NoteEntity.class);
            if (noteEntity == null) {
                Timber.d("Empty snapshot");
                return null;
            }
            noteEntity.id = snapshot.getKey();
            return noteEntity;
        } catch (DatabaseException e) {
            Timber.e(e, "Error converting snapshot to NoteEntity");
        }
        return null;
    }

    @NonNull
    private List<NoteEntity> convertToCollection(DataSnapshot snapshot) {
        return Stream.of(snapshot)
                .filter(DataSnapshot::exists)
                .map(this::getNoteEntitiesMap)
                .flatMap(map -> Stream.of(map.entrySet()))
                .filter(entry -> entry.getValue() != null)
                .map(this::setId)
                .toList();
    }

    @NonNull
    private NoteEntity setId(Map.Entry<String, NoteEntity> entry) {
        NoteEntity entity = entry.getValue();
        entity.id = entry.getKey();
        return entity;
    }

    @NonNull
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
