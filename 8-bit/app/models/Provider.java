package models;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("provider")
public class Provider extends User {

	/* *** Relationships *** */
	
	@OneToMany(mappedBy="provider")
	private Set<Game> games;


    /* *** Getters / Setters *** */

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}
}
