package models;

import java.sql.Date;
import java.util.Map;
import java.util.Set;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

import javax.persistence.*;

@Entity
public class Game extends GenericModel {

	@Id
    private String name;

    @ElementCollection
    @CollectionTable(joinColumns=@JoinColumn(name="game_name"))
    @Column(name="developer")
    private Set<String> developers;

    @ElementCollection
    @CollectionTable(joinColumns=@JoinColumn(name="game_name"))
    @Column(name="mode")
    private Set<String> modes;

    @ElementCollection
    @MapKeyColumn(name="country")
    @CollectionTable(joinColumns=@JoinColumn(name="game_name"))
    @Column(name="date")
    private Map<String, Date> releaseDates;

    private Blob photo;


	/* *** Relationships *** */

    @ManyToMany
    @JoinTable(
            name="Game_genres",
            joinColumns={@JoinColumn(name="game_name", referencedColumnName="name")},
            inverseJoinColumns={@JoinColumn(name="genre_name", referencedColumnName="name")})
    private Set<Genre> genres;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="provider")
	private Provider provider;
	
	@OneToOne
	@JoinColumn(name="configuration")
	private Configuration configuration;


	/* *** Getters / Setters *** */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
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

    public Set<String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<String> developers) {
        this.developers = developers;
    }

    public Set<String> getModes() {
        return modes;
    }

    public void setModes(Set<String> modes) {
        this.modes = modes;
    }

    public Map<String, Date> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(Map<String, Date> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}