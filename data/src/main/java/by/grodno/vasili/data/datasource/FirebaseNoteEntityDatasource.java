package by.grodno.vasili.data.datasource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import by.grodno.vasili.data.entity.NoteEntity;
import by.grodno.vasili.data.error.DeletingError;
import by.grodno.vasili.data.error.SavingError;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * {@link NoteEntityDatasource} implementation for work with Firebase realtime database
 */
public class FirebaseNoteEntityDatasource implements NoteEntityDatasource {
    private static final String NOTES_PATH = "notes/";
    private static final String DATABASE_URL = "https://cleanarchitectureexperiment.firebaseio.com/";
    private static final String KEY_PATH = "E:\\Cloud\\Mail.Ru\\key.json";
    private final DatabaseReference notesReference;

    FirebaseNoteEntityDatasource() {
        init();
        notesReference = FirebaseDatabase.getInstance().getReference(NOTES_PATH);
    }

    @Override
    public Single<Collection<NoteEntity>> all() {
        return Single.create(observer -> notesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, NoteEntity>> type = new GenericTypeIndicator<Map<String, NoteEntity>>() {
                };
                try {
                    Map<String, NoteEntity> map = dataSnapshot.getValue(type);
                    observer.onSuccess(map.values());
                } catch (DatabaseException e) {
                    observer.onError(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                observer.onError(new Exception(error.getMessage()));
            }
        }));
    }

    @Override
    public Single<NoteEntity> one(String id) {
        return Single.create(observer -> notesReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    NoteEntity noteEntity = dataSnapshot.getValue(NoteEntity.class);
                    noteEntity.id = dataSnapshot.getKey();
                    observer.onSuccess(noteEntity);
                } catch (DatabaseException e) {
                    observer.onError(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                observer.onError(new Exception(error.getMessage()));
            }
        }));
    }

    @Override
    public Completable delete(String id) {
        return Completable.create(observer -> notesReference.child(id).removeValue(((error, ref) -> {
            if (error == null) {
                observer.onComplete();
            } else {
                observer.onError(new DeletingError(error.getDetails()));
            }
        }
        )));
    }

    @Override
    public Single<String> insert(NoteEntity noteEntity) {
        return Single.create(observer -> notesReference.push().setValue(noteEntity, (error, ref) -> {
            if (error == null) {
                observer.onSuccess(ref.getKey());
            } else {
                observer.onError(new SavingError(error.getMessage()));
            }
        }));
    }

    private void init() {
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(KEY_PATH));
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
