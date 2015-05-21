package models;

import java.util.Set;

import javax.persistence.*;

@Entity
@DiscriminatorValue("gamer")
public class Gamer extends User {

	/* *** Relationships *** */

    @ManyToMany
    @JoinTable(
            name="Gamer_genres",
            joinColumns={@JoinColumn(name="gamer_pseudo", referencedColumnName="pseudo")},
            inverseJoinColumns={@JoinColumn(name="genre_name", referencedColumnName="name")})
    private Set<Genre> preferredGenres;

	@OneToMany
    @JoinTable(
            name="Gamer_configurations",
            joinColumns={@JoinColumn(name="gamer_pseudo", referencedColumnName="pseudo")},
            inverseJoinColumns={@JoinColumn(name="configuration_id", referencedColumnName="id")})
	private Set<Configuration> configurations;


	/* *** Getters / Setters *** */
	
	public Set<Genre> getPreferredGenres() {
		return preferredGenres;
	}

	public void setPreferredGenres(Set<Genre> preferredGenres) {
		this.preferredGenres = preferredGenres;
	}

    public Set<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Set<Configuration> configurations) {
        this.configurations = configurations;
    }
}
