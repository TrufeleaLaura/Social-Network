package social_network.repository;


import social_network.domain.Entity;
import social_network.exceptions.ValidationException;

import java.util.List;

public interface Repository <ID, E extends Entity<ID>> {

    /**
     *
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     *          or null - if there is no entity with the given id
     * @throws IllegalArgumentException
     *                  if id is null.
     */
    E findOne(ID id);

    E findOne_email(String str);

    /**
     *
     * @return all entities
     */
    List<E> findAll();

    /**
     *
     * @param entity
     *         entity must be not null
     * @return the saved entity - if the given entity is saved
     *         otherwise returns null (id already exists)
     * @throws ValidationException
     *            if the entity is not valid
     * @throws IllegalArgumentException
     *             if the given entity is null.     *
     */
    E save(E entity) throws ValidationException;


    /**
     *  removes the entity with the specified id
     * @param id
     *      id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException
     *                   if the given id is null.
     */
    ID delete(ID id);

    /**
     *
     * @param entity
     *          entity must not be null
     * @return the updated entity - if the entity is updated,
     *                otherwise  returns null  - (e.g id does not exist).
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidationException
     *             if the entity is not valid.
     */
    E update(E entity);



}

