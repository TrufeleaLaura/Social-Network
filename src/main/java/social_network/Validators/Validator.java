package social_network.Validators;


import social_network.exceptions.ValidationException;

/**
 * An interface to be implemented from all the validators
 * @param <T> the type of the objects we're validating
 */
public interface Validator<T> {
    void validate(T t) throws ValidationException;
}
