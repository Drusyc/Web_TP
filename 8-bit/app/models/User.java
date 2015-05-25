package models;

import javax.persistence.*;

import org.hibernate.annotations.DiscriminatorOptions;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;
import play.data.validation.Email;

@Inheritance
@DiscriminatorOptions(force=true)
@DiscriminatorColumn(name="role", discriminatorType=DiscriminatorType.STRING)
@Entity
public abstract class User extends GenericModel {
	
	@Id
    private String pseudo;

    private String password;
	
	@Column(nullable=false)
    private String mail;
    
    private Blob avatar;
    
    /* *** Constructor *** */
    
    public User () {
    	super();
    }
    
	public User(String pseudo, String password, String mail, Blob avatar) {
		super();
		this.pseudo = pseudo;
        this.password = password;
		this.mail = mail;
		this.avatar = avatar;
	}

    /* *** Getters / Setters *** */


	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Blob getAvatar() {
		return avatar;
	}

	public void setAvatar(Blob avatar) {
		this.avatar = avatar;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
