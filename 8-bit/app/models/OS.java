package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import play.db.jpa.Model;

@Entity
public class OS extends Model {

    @Column(nullable=false)
    private String name;

    private Float version;


    /* *** Getters / Setters *** */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }
}
