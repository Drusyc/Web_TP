package models;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity
public class Game extends GenericModel {

	@Id
    private String name;

	@Required
	@ElementCollection(targetClass=Genre.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="Game_Genre")
	private Set<Genre> Genres;
	
	/* *** Relationships *** */
	
	@ManyToOne
	@JoinColumn(name="provider")
	private Provider provider;
	
	@OneToOne
	@JoinColumn(name="configuration")
	private Configuration configuration;

	/* ** Setters / Getters ** */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Genre> getGenres() {
		return Genres;
	}

	public void setGenres(Set<Genre> genres) {
		Genres = genres;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	
}