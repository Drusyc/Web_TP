package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity
public class VideoCard extends GenericModel {
	
	@Id
	private String name;
	
	private String chipset;
	
	private String manufacturer;
	
	/* Exprimée en MegaHertz */
	private Double speedMemory;
	
	private String versionDirectX;
	
	
	/* *** Construtors *** */

	public VideoCard() {
		super();
	}

	public VideoCard(String name, String chipset, String manufacturer,
			Double memory, String versionDirectX) {
		super();
		this.name = name;
		this.chipset = chipset;
		this.manufacturer = manufacturer;
		this.speedMemory = memory;
		this.versionDirectX = versionDirectX;
	}

	/* *** Getters / Setters *** */
	
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

	public Double getMemory() {
		return speedMemory;
	}

	public void setMemory(Double memory) {
		this.speedMemory = memory;
	}

	public String getVersionDirectX() {
		return versionDirectX;
	}

	public void setVersionDirectX(String versionDirectX) {
		this.versionDirectX = versionDirectX;
	}
}
