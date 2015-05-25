package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class OS extends Model {

    @Column(nullable=false)
    private String name;

    private Double version;
    
    
    /* *** Constructors *** */
    
    public OS() {
		super();
	}

	public OS(String name, Double version) {
		super();
		this.name = name;
		this.version = version;
	}

	/* *** Getters / Setters *** */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }
}
