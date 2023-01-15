package domain;


import java.time.LocalDateTime;

/**
 * a class that stores a friendship's information.
 */
public class Friendship extends Entity<Long>{
    private Long idUser1;
    private Long idUser2;

    LocalDateTime friendsfrom;

    public Friendship(Long id, Long idUser1, Long idUser2,LocalDateTime frfrom) {
        this.setId(id);
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.friendsfrom=frfrom;
    }

    public Friendship(Long idUser1, Long idUser2,LocalDateTime frfrom) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
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

    public LocalDateTime getFriendsfrom() {
        return friendsfrom;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + getId() +
                ", idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                friendsfrom+ " Friends from: "+ "}";
    }
}
