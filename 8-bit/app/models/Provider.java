package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Blob;

@Entity
@DiscriminatorValue("provider")
public class Provider extends User {

	/* *** Relationships *** */

	@OneToMany(mappedBy="provider")
	private Set<Game> games = new HashSet<Game>();

	/* *** Constructors *** */
	
	public Provider() {
		super();
	}

	public Provider(String pseudo, String password, String mail, Blob avatar) {
		super(pseudo, password, mail, avatar);
	}

    /* *** Getters / Setters *** */

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}
}
