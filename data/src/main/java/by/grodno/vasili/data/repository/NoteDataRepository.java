package by.grodno.vasili.data.repository;

import java.util.Arrays;
import java.util.List;

import by.grodno.vasili.data.datasource.NoteEntityDatasource;
import by.grodno.vasili.data.entity.mapper.NoteEntityDataMapper;
import by.grodno.vasili.domain.model.Note;
import by.grodno.vasili.domain.repository.NoteRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * {@link NoteRepository} implementation for retrieving {@link by.grodno.vasili.data.entity.NoteEntity}
 * from datasource and convert to {@link Note} for domain layer
 */
public class NoteDataRepository implements NoteRepository {
    private final NoteEntityDatasource datasource;
    private final NoteEntityDataMapper mapper;

    public NoteDataRepository(NoteEntityDatasource datasource, NoteEntityDataMapper mapper) {
        this.datasource = datasource;
        this.mapper = mapper;
    }

    @Override
    public Single<Note> getOne(String id) {
        return datasource.one(id).map(mapper::convert);
    }

    // TODO: Replace fake data
    @Override
    public Single<List<Note>> getAll() {
//        Single<Collection<NoteEntity>> noteEntities = datasource.all();
//        return noteEntities.map(mapper::convert);
        return Single.fromObservable(Observable.just(Arrays.asList(
                new Note("1", "title1", "desc1"),
                new Note("2", "title2", "desc2"),
                new Note("3", "title3", "desc3"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("4", "title4", "desc4"),
                new Note("5", "title5", "desc5")
        )));
    }

    @Override
    public Completable delete(String id) {
        return null;
    }

    @Override
    public Single<String> insert(Note note) {
        return datasource.insert(mapper.convert(note));
    }
}
