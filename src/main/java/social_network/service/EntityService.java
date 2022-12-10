package social_network.service;


import social_network.domain.Entity;
import social_network.exceptions.ValidationException;

import java.util.List;

public interface EntityService<ID, E extends Entity<ID>> {
    /**
     * Ads the entity to the service
     * @param e the entity to add
     * @throws ValidationException if the entity is invalid
     */
    void addEntity(E e) throws ValidationException;

    /**
     * Remove the entity with the given id
     * @param id the given id of the entity
     * @return the id
     */
    ID deleteEntity(ID id);

    /**
     * Gets all the entites
     * @return a list with the entities
     */
    List<E> getAll();

    void updateEntity(E e ) throws ValidationException;

    E findOne(ID id) throws IllegalArgumentException;

}
