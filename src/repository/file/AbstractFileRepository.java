package repository.file;

import domain.User;
import exceptions.ExistingException;
import exceptions.ValidationException;
import domain.Entity;
import repository.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * An abstract class that has the needed method for getting entities from
 * a file and writing them back
 * @param <ID> the type of the id of the entity
 * @param <E> the type of the entity
 */
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E>  {
    private final String fileName;


    /**
     * First we set the filename we'll use and the load the data from it
     * @param fileName
     */
    public AbstractFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    /**
     * Gets the data from the file
     * Iterates over each line and used the abstract method extractEntity
     * to map the String line to the needed entity
     */
    private void loadData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> attributes = Arrays.asList(line.split(";"));
                E e = extractEntity(attributes);
                if (e != null)
                    super.save(e);
            }
        } catch (IOException | ValidationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets an entity and uses the entityToString method
     * to map the entity to a String we'll put in the file
     * @param e the entity to write in the file
     */
    private void writeEntityToFile(E e) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            bufferedWriter.write(entityToString(e));
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Clear the file we have in the repo
     */
    private void clearFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false));
            bufferedWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the entity in the repository and then writes it in the file
     * @param entity - the entity to save and write in file
     * @return the saved entity
     * @throws ValidationException - if the entity to save is invalid
     */
    @Override
    public E save(E entity) throws ValidationException {
        if (super.save(entity)== null)
            return null;
        writeEntityToFile(entity);
        return entity;
    }

    /**
     * removes the entity from the repo
     * if the file does not exists then we throw and {@link ExistingException}
     * we clear the file and then rewrite all entities on the file
     * @param id - the id of the entity we'll remove
     * @return the removed entity
     */
    @Override
    public ID delete(ID id) {
        ID id1 = super.delete(id);
        if (id1 == null) throw new ExistingException("entitatea data nu exista");
        clearFile();
        super.findAll().forEach(this::writeEntityToFile);
        return id1;
    }



    /**
     * abstract method to get the entity from a string
     * @param attributes - a list of string representing the arguments of the entity class
     * @return the entity
     */
    abstract E extractEntity(List<String> attributes);

    /**
     * abstract method to get the string from an entity
     * @param e - the entity to map to a string
     * @return the string
     */
    abstract String entityToString(E e);

    public User getUserById(Long id) {
        return null;
    }

    @Override
    public E update(E entity) {
        E entity1 = super.update(entity);
        if (entity1 == null) throw new ExistingException("entitatea data nu exista");
        clearFile();
        super.findAll().forEach(this::writeEntityToFile);
        return entity;
    }

}
