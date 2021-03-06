package models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Query;

import play.db.jpa.Model;

@Entity
public class OS extends Model {

    @Column(nullable=false)
    private String name;

    private String version;
    
    private Date releaseDate;

    
    /* *** Constructors *** */
    
    public OS() {
		super();
	}

    public OS(String name, String version) {
        super();
        this.name = name;
        this.version = version;
    }

	public OS(String name, String version, Date date) {
		super();
		this.name = name;
		this.version = version;
		this.releaseDate = date;
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
		return releaseDate;
	}

	public void setDate(Date date) {
		this.releaseDate = date;
	}

    /* *** Methods *** */

    public static List<OS> getAll() {
        Query q = OS.em().createNativeQuery("select * from OS order by name, version", OS.class);
        return q.getResultList();
    }

}
