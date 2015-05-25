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
	
	@Column(nullable=false)
    @Email
    private String mail;
    
    private Blob avatar;
    
    /* *** Construtor *** */
    
    public User () {
    	super();
    }
    
	public User(String pseudo, String mail, Blob avatar) {
		super();
		this.pseudo = pseudo;
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
}
