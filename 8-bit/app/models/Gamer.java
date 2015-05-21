package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("gamer")
public class Gamer extends User {

	
	@ElementCollection(targetClass=Genre.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="Gamer_Genre")
	private Set<Genre> preferredGenres;

	/* *** Relationships *** */

	@ElementCollection(targetClass=Configuration.class)
	@CollectionTable(name="Gamer_Configuration")
	private Set<Configuration> configurations;
	
	/* ** Setters / Getters ** */
	
	public Set<Genre> getPreferredGenres() {
		return preferredGenres;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void setPreferredGenres(Set<Genre> preferredGenres) {
		this.preferredGenres = preferredGenres;
	}
}
