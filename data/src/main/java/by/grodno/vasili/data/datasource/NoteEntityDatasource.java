package by.grodno.vasili.data.datasource;

import java.util.Collection;

import by.grodno.vasili.data.entity.NoteEntity;
import io.reactivex.Completable;
import io.reactivex.Maybe;
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
    Maybe<NoteEntity> one(String id);

    /**
     * Delete by id
     */
    Completable delete(String id);

    /**
     * Save one {@link NoteEntity}
     *
     * @return Single observable assigned id
     */
    Single<String> insert(NoteEntity noteEntity);

    /**
     * Update one {@link NoteEntity} by id
     *
     * @return Single observable updated id
     */
    Single<String> update(NoteEntity noteEntity);
}
