package models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class OS extends Model {

    @Column(nullable=false)
    private String name;

    private String version;

    private Date date;
    
    
    /* *** Constructors *** */
    
    public OS() {
		super();
	}

	public OS(String name, String version, Date date) {
		super();
		this.name = name;
		this.version = version;
		this.date = date;
	}

	/* *** Getters / Setters *** */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


    
    
}
