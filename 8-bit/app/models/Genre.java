package models;

import play.db.jpa.GenericModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Genre extends GenericModel {

    @Id
    private String name;


    /* *** Relationships *** */

    @ManyToMany(mappedBy="genres")
    private Set<Game> games;


    /* *** Getters / Setters *** */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
