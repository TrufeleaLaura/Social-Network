package social_network.Validators;


import social_network.domain.Friendship;
import social_network.exceptions.ValidationException;

public class FriendshipValidator extends BaseValidator<Friendship> {
    /**
     * Checks if the friendship has two not null members
     * @param friendship the given frienship
     * @throws ValidationException if the friendship is invalid
     */
    @Override
    public void validate(Friendship friendship) throws ValidationException {
        if (friendship.getIdUser1() == null || friendship.getIdUser2() == null)
            throw new ValidationException("Invalid friendship");
    }
}