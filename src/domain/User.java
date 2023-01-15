package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * a class that holds the user's information.
 */
public class User extends Entity<Long> {
    private long ID;
    private String name;
    private String email;



    public User(Long ID, String name, String email) {
        this.setId(ID);
        this.name = name;
        this.email = email;

    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    /**
     * getter for user's name
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for user's email
     * @return user's email
     */
    public String getEmail() {
        return email;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User: id=" + getId()+
                ", name='" + name + '\'' +
                ", email='" + email + '\'' ;
    }

    /**
     * the definition of when 2 users are considered equals
     * @param o the user to compare to
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID == user.ID && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, email);
    }
}
