package by.grodno.vasili.data.util;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import by.grodno.vasili.data.exception.RxFirebaseDataException;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class FirebaseUtils {

    /**
     * Listener for a single change in te data at the given query location.
     *
     * @param query reference represents a particular location in your Database and can be used for reading or writing data to that Database location.
     * @return a {@link Maybe} which emits the actual state of the database for the given query. onSuccess will be only call when
     * the given {@link DataSnapshot} exists onComplete will only called when the data doesn't exist.
     */
    @NonNull
    public static Maybe<DataSnapshot> observeSingleValueEvent(@NonNull final Query query) {
        return Maybe.create(emitter -> query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    emitter.onSuccess(dataSnapshot);
                } else {
                    emitter.onComplete();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if (!emitter.isDisposed())
                    emitter.onError(new RxFirebaseDataException(error));
            }
        }));
    }

    /**
     * Set the given value on the specified {@link DatabaseReference}.
     *
     * @param dbRef reference represents a particular location in your database.
     * @param value value to update.
     * @return a {@link Single<String>} which is complete with id of saved object.
     */
    @NonNull
    public static Single<String> pushAndSetValue(@NonNull final DatabaseReference dbRef, final Object value) {
//        return Single.create(emitter -> dbRef
//                .push()
//                .setValue(value, (error, ref) -> {
//                    System.out.println("sdfdsf");
//                }));
//    }
    return Single.create(emitter -> dbRef
                .push()
                .setValue(value, (error, ref) -> {
                    if (error == null) {
                        emitter.onSuccess(ref.getKey());
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(error.toException());
                        }
                    }
                }));
    }
}
