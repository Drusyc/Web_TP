package models;

import play.db.jpa.GenericModel;

import javax.persistence.*;

import java.util.*;

@Entity
public class Genre extends GenericModel {

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

    public static List<String> getNames(Collection<Genre> genres) {
        List<String> strings = new ArrayList<String>();

        for (Genre g : genres) {
            strings.add(g.getName());
        }
        return strings;
    }

    public static List<Genre> getAll() {
        Query q = Genre.em().createNativeQuery("select * from Genre order by name", Genre.class);
        return q.getResultList();
    }
}
