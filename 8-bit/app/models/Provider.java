package models;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Blob;

@Entity
@DiscriminatorValue("provider")
public class Provider extends User {

	/* *** Relationships *** */

	@OneToMany(mappedBy="provider")
	private Set<Game> games;

	/* *** Construtors *** */
	
	public Provider() {
		super();
	}
	
	public Provider(String pseudo, String mail, Blob avatar) {
		super();
        setPseudo(pseudo);
        setMail(mail);
        setAvatar(avatar);
	}

	public Provider(Set<Game> games) {
		super();
		this.games = games;
	}
	

    /* *** Getters / Setters *** */

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}
}
