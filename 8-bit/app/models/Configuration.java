package models;

import play.db.jpa.Model;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Configuration extends Model {

	@Column(nullable=false)
	private String name;
	
	/* Exprimée en GigaOctets*/
	private Double freeDiskSpace;

	/* Exprimée en Giga */
	private Double RAM;
	
	
	/* *** Constructors *** */	
	
	public Configuration() {
		super();
	}

	public Configuration(String name, Double freeDiskSpace, Double rAM) {
		super();
		this.name = name;
		this.freeDiskSpace = freeDiskSpace;
		RAM = rAM;
	}

	public Configuration(String name, Double freeDiskSpace, Double rAM,
			Set<OS> operatingSystems, Set<Processor> processors,
			Set<VideoCard> videoCards) {
		super();
		this.name = name;
		this.freeDiskSpace = freeDiskSpace;
		RAM = rAM;
		this.operatingSystems = operatingSystems;
		this.processors = processors;
		this.videoCards = videoCards;
	}

	/* *** Relationships *** */

    @ManyToMany
    @JoinTable(
            name="Configuration_OS",
            joinColumns={@JoinColumn(name="configuration_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="OS_id", referencedColumnName="id")})
    private Set<OS> operatingSystems;

    @ManyToMany
    @JoinTable(
            name="Configuration_processors",
            joinColumns={@JoinColumn(name="configuration_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="processor_name", referencedColumnName="name")})
	private Set<Processor> processors;

	@ManyToMany
    @JoinTable(
            name="Configuration_video_cards",
            joinColumns={@JoinColumn(name="configuration_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="video_card_name", referencedColumnName="name")})
	private Set<VideoCard> videoCards;
	
	
	/* *** Getters / Setters *** */
	
	public Set<OS> getOperatingSystems() {
		return operatingSystems;
	}

	public void setOperatingSystems(Set<OS> operatingSystems) {
		this.operatingSystems = operatingSystems;
	}

	public Double getFreeDiskSpace() {
		return freeDiskSpace;
	}

	public void setFreeDiskSpace(Double freeDiskSpace) {
		this.freeDiskSpace = freeDiskSpace;
	}

	public Double getRAM() {
		return RAM;
	}

	public void setRAM(Double RAM) {
		this.RAM = RAM;
	}

	public Set<Processor> getProcessors() {
		return processors;
	}

	public void setProcessors(Set<Processor> processors) {
		this.processors = processors;
	}

	public Set<VideoCard> getVideoCards() {
		return videoCards;
	}

	public void setVideoCards(Set<VideoCard> videoCards) {
		this.videoCards = videoCards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	    /* *** Methods *** */

	public static List<Configuration> findByName (String name) {
		String NAME = "name";
		String CONF_REQUEST = "SELECT * FROM CONFIGURATION "
				+ "WHERE LOWER(NAME) LIKE LOWER(" + ":" + NAME + ") ORDER BY NAME;";

		EntityManager m = Configuration.em();

    	/* Création de la requête */
		Query q = m.createNativeQuery(CONF_REQUEST);
		q.setParameter(NAME, "%" + name.toUpperCase() + "%");

		return q.getResultList();
	}

}
