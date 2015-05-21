package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import net.sf.oval.constraint.MaxSize;

import org.hibernate.annotations.DiscriminatorOptions;

import play.db.jpa.GenericModel;

@Inheritance
@DiscriminatorOptions(force=true)
@DiscriminatorColumn(name="role", discriminatorType=DiscriminatorType.STRING)
@Entity
public abstract class User extends GenericModel {
	
	@Id
    public String pseudo;
	
	@Column(nullable=false)
	@MaxSize(value=255, message = "email.maxsize")
    @play.data.validation.Email
    public String mail;	
    
    public /*Blob*/ String avatar;
    
    
    
    
    /* ** Setters / Getters ** */

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
    
}
