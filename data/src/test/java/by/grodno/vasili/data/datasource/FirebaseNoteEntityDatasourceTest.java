package by.grodno.vasili.data.datasource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import by.grodno.vasili.data.entity.NoteEntity;
import by.grodno.vasili.data.entity.mapper.NoteEntityDataMapper;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Non production test read and write to Firebase
 */
public class FirebaseNoteEntityDatasourceTest {
    private static final String TEST_DESCRIPTION = "desc";
    private static FirebaseNoteEntityDatasource database;
    private static String testId;

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @BeforeClass
    public static void setUp() {
        NoteEntityDataMapper mapper = new NoteEntityDataMapper();
        database = new FirebaseNoteEntityDatasource(mapper);
        testId = database.insert(createNote()).blockingGet();
        assertNotNull("Saved id is null", testId);
    }

    @AfterClass
    public static void tearDown() {
        try {
            database.delete(testId).blockingAwait();
        } catch (Exception e) {
            fail("Error deleting test note");
        }
    }

    @Test()
    public void readAllTest() {
        Collection<NoteEntity> notes = database.all().blockingGet();
        assertFalse("Empty list when takes all notes", notes.isEmpty());
    }

    @Test()
    public void readOneTest() {
        NoteEntity note = database.one(testId).blockingGet();
        assertEquals("Received not what was previously saved", note.description, TEST_DESCRIPTION);
    }

    @Test()
    public void readOneInNotFound() {
        TestObserver<NoteEntity> testObserver = database.one("fake id").test();
        testObserver.awaitTerminalEvent();
        testObserver.assertError(RuntimeException.class);
    }

    private static NoteEntity createNote() {
        NoteEntity result = new NoteEntity();
        result.title = "test";
        result.description = TEST_DESCRIPTION;
        return result;
    }
}
