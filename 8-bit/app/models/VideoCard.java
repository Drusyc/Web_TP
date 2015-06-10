package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;

import play.db.jpa.GenericModel;

import java.util.List;

@Entity
public class VideoCard extends GenericModel {
	
	@Id
	private String name;
	
	private String manufacturer;
	
	/* Exprimée en MegaHertz */
	private Integer speed;
	
	private String versionDirectX;
	
	
	/* *** Constructors *** */

	public VideoCard() {
		super();
	}

	public VideoCard(String name, String manufacturer,
			Integer speed, String versionDirectX) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.speed = speed;
		this.versionDirectX = versionDirectX;
	}

	/* *** Getters / Setters *** */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public String getVersionDirectX() {
		return versionDirectX;
	}

	public void setVersionDirectX(String versionDirectX) {
		this.versionDirectX = versionDirectX;
	}

    /* *** Methods *** */

    public static List<VideoCard> getAll() {
        Query q = Processor.em().createNativeQuery("select * from VideoCard order by manufacturer, name", VideoCard.class);
        return q.getResultList();
    }
}
