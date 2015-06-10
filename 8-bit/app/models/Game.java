package models;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.Logger;
import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

import javax.persistence.*;

@Entity
public class Game extends GenericModel {

	@Id
    private String name;

	private Blob photo;
	
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
    @Column(name="release_date")
    private Map<String, Date> releaseDates;
    
    /* *** Constructors *** */
    

	public Game() {
		super();
	}
    
	public Game(String name, Blob photo) {
		super();
		this.name = name;
		this.photo = photo;
	}
	
	public Game(String name, Blob photo, Set<String> developers,
			Set<String> modes, Map<String, Date> releaseDates) {
		super();
		this.name = name;
		this.photo = photo;
		this.developers = developers;
		this.modes = modes;
		this.releaseDates = releaseDates;
	}

	public Game(String name, Set<String> developers, Set<String> modes,
			Map<String, Date> releaseDates, Blob photo, Set<Genre> genres,
			Provider provider, Configuration configuration) {
		super();
		this.name = name;
		this.developers = developers;
		this.modes = modes;
		this.releaseDates = releaseDates;
		this.photo = photo;
		this.genres = genres;
		this.provider = provider;
		this.configuration = configuration;
	}


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
    
    /* *** Methods *** */

    public static List<String> findByName (String name) {
    	String NAME = "name";
    	String GAME_REQUEST = "SELECT NAME FROM GAME "
    			+ "WHERE LOWER(NAME) LIKE LOWER(" + ":" + NAME + ") ORDER BY NAME;";
    	
    	EntityManager m = Game.em();

    	/* Création de la requête */
    	Query q = m.createNativeQuery(GAME_REQUEST);
    	q.setParameter(NAME, "%" + name.toUpperCase() + "%");
    	
    	return q.getResultList();
    }

    public static List<Game> getAll() {
        Query q = Game.em().createNativeQuery("select * from Game order by name", Game.class);
        return q.getResultList();
    }

}