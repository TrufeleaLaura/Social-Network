import UI.Console;
import domain.Entity;
import domain.Friendship;
import domain.User;
import repository.InMemoryRepository;
import repository.database.DBFriendshipRepo;
import repository.database.DBUserRepo;
import repository.file.FileFriendshipRepo;
import repository.file.FileUserRepo;
import service.FriendshipService;
import service.UserService;

import java.io.File;


public class Main {
    public static void main(String[] args) {

       /* InMemoryRepository<Long, User> repoUser =  new InMemoryRepository<>();
        InMemoryRepository<Long, Friendship> repoFriendship =  new InMemoryRepository<>();
        */
        /*FileUserRepo repoUser=new FileUserRepo("data\\user.csv");
        FileFriendshipRepo repoFriendship=new FileFriendshipRepo("data\\friendship.csv",repoUser);*/

            String url = "jdbc:postgresql://localhost:5432/social_network";
            String username = "postgres";
            String password = "postgres";

            DBUserRepo repoUser = new DBUserRepo(url , username , password);
            DBFriendshipRepo repoFriendship = new DBFriendshipRepo(url, username, password);

            FriendshipService friendshipService = new FriendshipService(repoFriendship, repoUser);
            UserService userService = new UserService(repoUser, repoFriendship);
            Console consoleUI = new Console(userService, friendshipService);
            consoleUI.showUi();

    }
}