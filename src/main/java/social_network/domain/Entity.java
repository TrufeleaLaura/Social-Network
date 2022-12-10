package social_network.domain;

/**
 * a generic class to use in our specific objects
 * @param <ID>  generic member - T the type of the id's (String, Long etc)
 */
public class Entity <ID>{
    private ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }

}
