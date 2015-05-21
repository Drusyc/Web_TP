package models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Request extends Model {
	public enum Status {
		OK,
		NOK,
		IN_PROGRESS;
	}
	
	
	@Column(nullable=false)
	private Date date;
	
	@Required
	@Column(nullable=false)
	private String game;
	
	private Configuration.OS OS;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Status status = Status.IN_PROGRESS;
	
	/* *** Relationships *** */
	
	@ManyToOne
	@JoinColumn(name="requester")
	private Gamer requester;
	
	/* *** Getters / Setters *** */

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	
	public Configuration.OS getOS() {
		return OS;
	}

	public void setOS(Configuration.OS OS) {
		this.OS = OS;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Gamer getRequester() {
		return requester;
	}

	public void setRequester(Gamer requester) {
		this.requester = requester;
	}
	
}
