package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;

import play.db.jpa.GenericModel;

import java.util.List;

@Entity
public class Processor extends GenericModel {

	@Id
	private String name;
	
	private String manufacturer;
	
	/* Exprimée en GigaHertz */
	private Double speed;
	
	private Integer cores;
	
	
	/* *** Constructors *** */ 

    public Processor() {
		super();
	}

	public Processor(String name, String manufacturer, Double speed,
			Integer cores) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.speed = speed;
		this.cores = cores;
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

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Integer getCores() {
		return cores;
	}

	public void setCores(Integer cores) {
		this.cores = cores;
	}

    /* *** Methods *** */

    public static List<Processor> getAll() {
        Query q = Processor.em().createNativeQuery("select * from Processor order by manufacturer, name", Processor.class);
        return q.getResultList();
    }

}
