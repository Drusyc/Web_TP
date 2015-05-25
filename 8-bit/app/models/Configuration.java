package models;


import play.db.jpa.Model;

import javax.persistence.*;

import java.util.Set;

@Entity
public class Configuration extends Model {

	/* Exprimée en GigaOctets*/
	private Integer freeDiskSpace;

	/* Exprimée en MegaOctets */
	private Integer RAM;
	
	
	/* *** Constructors *** */	
	
	public Configuration() {
		super();
	}

	public Configuration(Integer freeDiskSpace, Integer rAM) {
		super();
		this.freeDiskSpace = freeDiskSpace;
		RAM = rAM;
	}

	public Configuration(Integer freeDiskSpace, Integer rAM,
			Set<OS> operatingSystems, Set<Processor> processors,
			Set<VideoCard> videoCards) {
		super();
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

	public Integer getFreeDiskSpace() {
		return freeDiskSpace;
	}

	public void setFreeDiskSpace(Integer freeDiskSpace) {
		this.freeDiskSpace = freeDiskSpace;
	}

	public Integer getRAM() {
		return RAM;
	}

	public void setRAM(Integer RAM) {
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
}
