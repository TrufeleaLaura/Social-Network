package social_network.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * a class that stores a friendship's information.
 */
public class Friendship extends Entity<Long>{
    private Long idUser1;
    private Long idUser2;

    LocalDateTime friendsFrom = LocalDateTime.now();


    private String status;

    public void setFriendsfrom(String time) {
        if (!time.isEmpty())
            friendsFrom=LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        else friendsFrom=null;
    }
    public String getFriendsFrom(){
        if (friendsFrom==null)
            return "";
        return friendsFrom.format( DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public LocalDateTime getFriendsFromAsDate(){
        return friendsFrom;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Friendship(Long id, Long user_1, Long user_2,String status) {
        this.setId(id);
        this.idUser1 = user_1;
        this.idUser2= user_2;
        this.status=status;
    }

    public Friendship(Long id, Long user_1, Long user_2, LocalDateTime localDateTime, String status) {
        this.setId(id);
        this.idUser1 = user_1;
        this.idUser2= user_2;
        this.friendsFrom = localDateTime;
        this.status=status;
    }
    public void acceptFriend() {
        //this.setStatus("ACCEPTED");
        this.status="ACCEPTED";
        friendsFrom=LocalDateTime.now();
    }


    /**
     * getter for the first user in the friendship
     * @return the id of the first user
     */
    public Long getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Long idUser1) {
        this.idUser1 = idUser1;
    }

    /**
     * getter for the second user in the friendship
     * @return the id of the second user
     */
    public Long getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Long idUser2) {
        this.idUser2 = idUser2;
    }



    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + getId() +
                ", idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                friendsFrom+ " Friends from: "+ "}"+status+"Status=";
    }
}
