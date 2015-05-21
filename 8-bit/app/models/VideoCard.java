package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.jpa.GenericModel;

@Entity
public class VideoCard extends GenericModel {
	
	@Id
	private String name;
	
	private String chipset;
	
	private String manufacturer;
	
	private Integer memory;
	
	private String versionDirectX;

	/* *** Relationships *** */
//	
//	@OneToMany(mappedBy="videoCard")
//	private Configuration configuration;	
//	
	/* ** Setters / Getters ** */	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChipset() {
		return chipset;
	}

	public void setChipset(String chipset) {
		this.chipset = chipset;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public String getVersionDirectX() {
		return versionDirectX;
	}

	public void setVersionDirectX(String versionDirectX) {
		this.versionDirectX = versionDirectX;
	}
	
	
	
	
	
	
}
