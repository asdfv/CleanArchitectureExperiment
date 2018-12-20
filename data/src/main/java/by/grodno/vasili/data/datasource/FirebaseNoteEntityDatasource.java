package by.grodno.vasili.data.datasource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import by.grodno.vasili.data.entity.NoteEntity;
import by.grodno.vasili.data.entity.mapper.NoteEntityDataMapper;
import by.grodno.vasili.data.error.DeletingError;
import by.grodno.vasili.data.error.SavingError;
import by.grodno.vasili.data.util.SingleValueOnSubscribe;
import by.grodno.vasili.domain.error.NotFoundError;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.exceptions.Exceptions;
import timber.log.Timber;

/**
 * {@link NoteEntityDatasource} implementation for work with Firebase realtime database
 */
public class FirebaseNoteEntityDatasource implements NoteEntityDatasource {
    private static final String NOTES_PATH = "notes/";
    private static final String DATABASE_URL = "https://cleanarchitectureexperiment.firebaseio.com/";
    private static final String KEY_STRING = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"cleanarchitectureexperiment\",\n" +
            "  \"private_key_id\": \"2226909a36c84c205fddd7e8bc646c4a2b67e440\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCjcQJ9o9Oe6Enm\\nXINqklGewmusnOEMOqHtaUIXO+JvJiE1xABKjSnbI8envgjQ14AkibGzV9fINcGQ\\nuzPw02uQbq6sg8OrCrtH/gaLbzqozbrmgZhEsh741FSUFDjqwdanZfcjO8ATRtP6\\nsKzpRoQBmmDABOFiGQltfSn5ZhDdV7XvTnx5o5H1yJqtgHDBJdwPA7t2b6v77dS1\\nxcpCpx0sJvurxC8WpQli6qc3BFpiOPOsx9JbVEOQsNP6QxNoE9mCS0j9KzjPjmPA\\nRoaOM1KjUAzXa+KtcT7jvXdfUkdGBIoADos8ISVCc0GMqiAtdM6uIavfV/he2x6Z\\nvRctNzvRAgMBAAECggEAAzyvwfFGtbUAEbHvYGL5ft126HpK8sUOg/c7ud4TxAPL\\nUJdLMi7Nj2uL/sSCxDAgd5jKjjTFL0/xP36VrsVhMgEfMIeDjpWt0+UyF/zh+ono\\nk7wpWeL1PhRmgHBOGEgyiio41NfQHBGhDSwGoLAm2SlHlUYDeL/qMeLyu9/Lobac\\n2BfpUBKh24ALTQrGahAMxGRRSN+s5bX0Ux9bEJ9XD7XRaRpks9p/oSh46fvkw4gD\\np6w1PdDwuvNXpNEYhpXxkyoc3kYZpfjCfSWnqj8HP1UUKkuQ5UIxFWh5f7GRcS2J\\nBV0kP3ltA9V0A/4gR/dLacjtndiA5ug8XbpFfzkJCQKBgQDnHvtNCoOAFz4jXk+v\\nx1dq4bm7JwwsSdOLlN7JMPbUmBxC0AJ5QQ5BqgWd9bvEXE6QlGc+dx32Kop3LGpC\\no9ILRS9vekX/GpVxHuyMl40B/izOMzWf9dAb0gtVpS6iktv40XIg1lto1mL0rlzU\\nAgqHUxcKj4pM+ype2tOK7O6XVwKBgQC1CPon8U48DLYXozGmYdm3smZ3MzFyzMHr\\n00IV+rr/ALzLN072edbIlDDBxN8engVe0WcaZSmScycIwRQc9XiaWMg4GpGd4kEK\\ntdWsq5EdS4hS4cy08jBBhVuRUvoAddNTeFd2gQ3TDU14XhOnRSNmTfUNojhrNbWg\\nMlx8kp6VFwKBgEXDPkuk7siNO35li1wdqa9jbzKkuLHtnuM1DoJgO0E0oOMx8m0z\\nS+BaN9PURtcgnb5abUeXoP2bHayXRYPT+yTXv1fA6rv0BEPe97wAStndJR0grAEa\\nq7SaFD3H3GnKNofGgNKUoYbln30H3JFKBG+QjxXq+e3+qm1zVANNPT+FAoGALfC8\\nQpyaxORY5UC1ncphDJvKIt+r4b0STP0MXTWGdKXEQzGi6StcFQR+Jv4FvOhO5pPv\\nPSSE2zQ7qJ4Os5abZ1e2X35mi5FZ/hTMx4e2SJ/dVWMVpql7E3V7GjLbXHiKCzmT\\nwG/ZiUBr82q0RBNoe1K3KCGNfa9giumczMQMWDsCgYB9lo1TDsZqhj0OEMqO0Pj4\\nI4SQoGVwnfDOBQiUSoKLy0ZKICghwpfGTSQ/25KfiOMR4I+GqX8SyXjJzD8HWlhT\\n3FiXOgadXMapIgmKkFzrQ4tiD2YIjN3NPw9M+tfZdPtB9u2MKpiW1xUCAtIIrbb0\\nAPrQPbVJnQWL1A66nluePg==\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-qpgre@cleanarchitectureexperiment.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"100586595846708204517\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-qpgre%40cleanarchitectureexperiment.iam.gserviceaccount.com\"\n" +
            "}\n";
    private final FirebaseDatabase database;
    private final NoteEntityDataMapper mapper;

    public FirebaseNoteEntityDatasource(NoteEntityDataMapper mapper) {
        this.database = initFirebase();
        this.mapper = mapper;
    }

    @Override
    public Single<NoteEntity> one(String id) {
        Query noteRef = database.getReference(NOTES_PATH).child(id);
        return Single.create(new SingleValueOnSubscribe(noteRef))
                .map(snapshot -> {
                    NoteEntity entity = mapper.convert(snapshot);
                    if (entity == null) {
                        throw Exceptions.propagate(new NotFoundError("Not found entity with id = " + id));
                    }
                    return entity;
                });
    }

    @Override
    public Single<Collection<NoteEntity>> all() {
        Query notesRef = database.getReference(NOTES_PATH);
        return Single.create(new SingleValueOnSubscribe(notesRef))
                .map(mapper::convertToCollection);
    }

    @Override
    public Completable delete(String id) {
        return Completable.create(observer -> {
            DatabaseReference noteRef = database.getReference(NOTES_PATH).child(id);
            noteRef.removeValue(((error, ref) -> {
                if (error == null) {
                    observer.onComplete();
                } else {
                    observer.onError(new DeletingError(error.getDetails()));
                }
            }
            ));
        });
    }

    @Override
    public Single<String> insert(NoteEntity noteEntity) {
        return Single.create(observer -> {
            DatabaseReference noteRef = database.getReference(NOTES_PATH);
            noteRef.push().setValue(noteEntity, (error, ref) -> {
                if (error == null) {
                    observer.onSuccess(ref.getKey());
                } else {
                    observer.onError(new SavingError(error.getMessage()));
                }
            });
        });
    }

    private FirebaseDatabase initFirebase() {
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(KEY_STRING.getBytes(StandardCharsets.UTF_8)));
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            Timber.e(e, "Error initializing Firebase");
        }
        return FirebaseDatabase.getInstance();
    }
}
