package UI;

import domain.User;
import exceptions.ExistingException;
import exceptions.ValidationException;
import service.FriendshipService;
import service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Console implements Ui {
    private final UserService userService;
    private final FriendshipService friendshipService;

    public Console(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    private void showMenu() {
        System.out.println("Optiunile dumneavoastra sunt:");
        System.out.println("1 - afiseaza userii si relatiile");
        System.out.println("2 - adauga un User dupa nume si email");
        System.out.println("3 - adauga o relatie de prietenie intre userii cu id-urile date");
        System.out.println("4 - sterge user-ul cu id-ul dat");
        System.out.println("5 - sterge prietenia-ul cu id-ul dat");
        System.out.println("6 - pentru a afisa numarul de comunitati");
        System.out.println("7 - modifica emailul unui user");
        System.out.println("0 - pentru a iesi");
    }

    @Override
    public void showUi() {
        Scanner in = new Scanner(System.in);
        while (true) {
            showMenu();
            String line = in.nextLine();
            List<String> strings = Arrays.asList(line.split(" "));
            int option;
            try {
                option = Integer.parseInt(strings.get(0));
            } catch (NumberFormatException ignored) {
                continue;
            }

            boolean closing = false;

            switch (option) {
                case 0 -> {
                    closing = true;
                }
                case 1 -> {
                    showUsers();
                    showFriendships();
                }
                case 2 -> {
                    if (strings.size() < 4) {
                        System.out.println("Introdu te rog email si nume");
                        continue;
                    }

                    System.out.println(strings.get(1));
                    System.out.println(strings.get(2));
                    System.out.println(strings.get(3));

                    addUserID(strings.get(1), strings.get(2), strings.get(3));
                }
                case 3 -> {
                    if (strings.size() < 4) {
                        System.out.println("Introdu te rog 2 id-uri de useri");
                        continue;
                    }
                    addFriendship(strings.get(1), strings.get(2), strings.get(3),strings.get(4));
                }
                case 4 -> {
                    if (strings.size() < 2) {
                        System.out.println("Introdu te rog un id");
                        continue;
                    }
                    removeUser(strings.get(1));
                }
                case 5 -> {
                    if (strings.size() < 2) {
                        System.out.println("Introdu te rog un id pentru relatie");
                        continue;
                    }
                    removeFriendship(strings.get(1));
                }
                case 6 -> {
                    showNumberOfComponents();
                }
                case 7->{
                    if (strings.size() < 3) {
                        System.out.println("Introdu te rog id si emailul nou");
                        continue;
                    }

                    System.out.println(strings.get(1));
                    System.out.println(strings.get(2));
                    modifyEmail(strings.get(1), strings.get(2));
                }

            }
            if (closing) break;
        }
    }

    /**
     * Prints all users
     */
    private void showUsers() {
        System.out.println("====    USERS   ====");
        userService.getAll().forEach(System.out::println);
        System.out.println("==========================");
    }

    /**
     * Prints all friendships
     */
    private void showFriendships() {
        System.out.println("====    FRIENDSHIPS     ====");
        Map<User, List<User>> allUsersFriends = friendshipService.getAllUsersFriends();
        allUsersFriends.forEach((k, v) -> {
            System.out.print(k.getName() + " -> ");
            v.forEach(user -> System.out.print(user.getName() + " "));
            System.out.print("\n");
        });
        System.out.println("=========================");
    }


    /**
     * adds a new user
     * @param ID user's id
     * @param email user's email
     * @param name user's name
     */
    private void addUserID(String ID, String email, String name) {
        try {
            Long id = Long.parseLong(ID);
            userService.addUserID(id, email, name);
        } catch (ValidationException | ExistingException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid id");
        }
    }

    private void modifyEmail(String ID, String email){
        try {
            Long id = Long.parseLong(ID);
            User user=userService.findOne(id);
            user.setEmail(email);
            userService.updateEntity(user);
        }catch(ValidationException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * deletes an user
     * @param userId user's id
     */
    private void removeUser(String userId) {
        try {
            Long id = Long.parseLong(userId);
            userService.deleteEntity(id);
        } catch (ExistingException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * adds a new friendship
     * @param id friendship's id
     * @param id1 the first user's id
     * @param id2 the second user's id
     */
    private void addFriendship(String id, String id1, String id2,String since) {
        try {
            friendshipService.addFriend(id, id1, id2,since);
        } catch (ExistingException | ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a friendship
     * @param id the firendhip's id.
     */
    private void removeFriendship(String id) {
        try {
            friendshipService.deleteEntity(Long.parseLong(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ExistingException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints (and calculates) the number of communities in the app.
     */
    private void showNumberOfComponents() {
        System.out.println("Numarul de comunitati este: " +
                friendshipService.ConnectedComponents().size());
    }



}
