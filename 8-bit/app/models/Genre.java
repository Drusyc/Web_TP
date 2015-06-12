package models;

import play.db.jpa.GenericModel;

import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Genre extends GenericModel {

    /* Used for tests */
	public static final String Action = "Action";
	public static final String RPG = "RPG"; 
	public static final String Aventure = "Aventure";
	public static final String Reflexion = "Reflexion";
	public static final String Strategie = "Stratégie";

    @Id
    private String name;

    /* *** Relationships *** */

    @ManyToMany(mappedBy="genres")
    private Set<Game> games = new HashSet<Game>();
    
    /* *** Constructors *** */
    
    public Genre() {
		super();
	}
    
    public Genre(String name) {
		super();
		this.name = name;
	}

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

    /* *** Methods *** */

    public static List<Genre> getAll() {
        Query q = Genre.em().createNativeQuery("select * from Genre order by name", Genre.class);
        return q.getResultList();
    }
}
