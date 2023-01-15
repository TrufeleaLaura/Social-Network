package repository.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import domain.Friendship;
import domain.User;
import exceptions.ExistingException;
import exceptions.ValidationException;

public class FileFriendshipRepo extends AbstractFileRepository<Long, Friendship> {
    private final FileUserRepo fileUserRepo;

    public FileFriendshipRepo(String fileName, FileUserRepo fileUserRepo) {
        super(fileName);
        this.fileUserRepo = fileUserRepo;
    }

    /**
     * Saves the friendship in the file
     *
     * @param friendship the friendship to save
     * @return the saved friendship
     * @throws ValidationException if the friendship is invalid
     * @throws ExistingException   if the friendship is begin between the same user or a user
     *                             does not exists or if there is already a relation between the 2 users
     */
    @Override
    public Friendship save(Friendship friendship) throws ValidationException {
        if (friendship.getIdUser1().equals(friendship.getIdUser2()))
            throw new ValidationException("Nu puteti adauga relatie intre aceeasi useri");
        if (fileUserRepo.findOne(friendship.getIdUser1()) == null)
            throw new ExistingException("User-ul 1 nu exista");
        if (fileUserRepo.findOne(friendship.getIdUser2()) == null)
            throw new ExistingException("User-ul 2 nu exista");

        return super.save(friendship);
    }

    /**
     * Maps the saved string to the frienship
     *
     * @param attributes - a list of string representing the arguments of the friendship class
     * @return the converted friendship
     */
    @Override
    Friendship extractEntity(List<String> attributes) {
        try {
            long id = Long.parseLong(attributes.get(0));
            long id1 = Long.parseLong(attributes.get(1));
            long id2 = Long.parseLong(attributes.get(2));
            String frFrom = attributes.get(3);
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd[ HH:mm:ss]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
            LocalDateTime since = LocalDateTime.parse(frFrom, formatter);
            return new Friendship(id, id1, id2, since);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Maps the friendship to the saved string
     *
     * @param friendship - the frienship to convert
     * @return the string converted
     */
    @Override
    String entityToString(Friendship friendship) {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]");
        String frfrom=dtf.format(friendship.getFriendsfrom());
        return String.format("%s;%s;%s;%s", friendship.getId(), friendship.getIdUser1(), friendship.getIdUser2(), frfrom);
    }

}

