package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Configuration extends Model{
	public enum OS {
		Win7("Windows 7"),
		Win8("Windows 8"),
		Linux("Linux"),
		Mac("Macintosh");
		
		private final String text;
		
		private OS(final String txt) {
			this.text = txt;
		}
		
		@Override
		public String toString(){
			return text;			
		}
	}

	@Enumerated(EnumType.STRING)
	private OS operatingSystem;
	
	private Integer freeDiskSpace;
	
	private Integer RAM;
	
	
	/* *** Relationships *** */
	
	@ManyToOne
	@JoinColumn(name="processor")
	private Processor processor;
	
	@ManyToOne
	@JoinColumn(name="videoCard")
	private VideoCard videoCard;
	
	@OneToOne(mappedBy="configuration",cascade=CascadeType.ALL)
	private Game game;
	
	
	/* *** Getters / Setters *** */
	
	public OS getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(OS operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public Integer getFreeDiskSpace() {
		return freeDiskSpace;
	}

	public void setFreeDiskSpace(Integer freeDiskSpace) {
		this.freeDiskSpace = freeDiskSpace;
	}

	public Integer getRAM() {
		return RAM;
	}

	public void setRAM(Integer RAM) {
		this.RAM = RAM;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public VideoCard getVideoCard() {
		return videoCard;
	}

	public void setVideoCard(VideoCard videoCard) {
		this.videoCard = videoCard;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	
	
	
}
