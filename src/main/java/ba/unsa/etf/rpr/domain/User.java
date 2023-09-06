package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * User class is a Java Bean that represents one table of the database used for users with essential properties
 * including unique ID, name, password, and an 'about me' description.
 */

public class User implements Idable {

    private int id;
    private String name;
    private String password;
    private String aboutMe;

    public User() {
    }
 
    public User(int id, String name, String password, String aboutMe) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.aboutMe = aboutMe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(aboutMe, user.aboutMe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, aboutMe);
    }

    @Override
    public String toString() {
        return name;
    }
}
