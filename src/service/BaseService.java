package service;

import exceptions.ExistingException;
import exceptions.ValidationException;
import Validators.Validator;
import Validators.ValidatorFactory;
import Validators.ValidatorType;
import domain.Entity;
import repository.Repository;

import java.util.List;


public class BaseService<ID, E extends Entity<ID>> implements EntityService<ID, E> {
    private final Validator<E> validator;
    protected final Repository<ID, E> repository;

    /**
     * This gets the needed validator by using the factory pattern
     * @param validatorType the type of the validator we'll use
     * @param repository the repository used in the service
     */
    public BaseService(ValidatorType validatorType, Repository<ID, E> repository) {
        ValidatorFactory instance = ValidatorFactory.getInstance();
        Validator validator = instance.createValidator(validatorType);
        this.validator = validator;
        this.repository = repository;
    }

    /**
     * Adds the entity in the repo after valdiation it
     * @param e the entity to save
     * @throws ValidationException if the user is invalid
     */
    @Override
    public void addEntity(E e) throws ValidationException {
        validator.validate(e);
        if (repository.save(e) == null)
            throw new ExistingException("Entity already exists");
        repository.save(e);
    }

    /**
     * Removes an entity by its id
     * @param id  the id of the entity to remove
     * @return the id
     */
    @Override
    public ID deleteEntity(ID id) {
        repository.delete(id);
        return id;
    }

    /**
     * Gets a list of all entities
     * @return the list with all entities
     */
    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public void updateEntity(E e) throws ValidationException {
        repository.update(e);
    }

    @Override
    public E findOne(ID id) throws IllegalArgumentException {
        if(repository.findOne(id)==null)
            throw new IllegalArgumentException("entity does not exist");
        E enity=repository.findOne(id);
        validator.validate(enity);
        return enity;

    }


}
