package social_network.Validators;

import social_network.Validators.FriendshipValidator;
import social_network.Validators.UserValidator;
import social_network.Validators.Validator;
import social_network.Validators.ValidatorType;

public class ValidatorFactory {
    private static ValidatorFactory instance = null;

    private ValidatorFactory() {
    }

    public Validator createValidator(ValidatorType type) {
        return switch (type) {
            case FRIENDSHIP -> new FriendshipValidator();
            case USER -> new UserValidator();
        };
    }

    public static ValidatorFactory getInstance() {
        if (instance == null) instance = new ValidatorFactory();
        return instance;
    }
}
