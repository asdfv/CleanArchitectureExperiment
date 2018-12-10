package by.grodno.vasili.data.datasource;

import java.util.Collection;

import by.grodno.vasili.data.entity.NoteEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Interface for work with {@link NoteEntity} in datasource
 */
public interface NoteEntityDatasource {

    /**
     * Take all
     */
    Single<Collection<NoteEntity>> all();

    /**
     * Take by id
     */
    Single<NoteEntity> one(String id);

    /**
     * Delete by id
     */
    Completable delete(String id);

    /**
     * Save one
     */
    Single<String> insert(NoteEntity noteEntity);
}
