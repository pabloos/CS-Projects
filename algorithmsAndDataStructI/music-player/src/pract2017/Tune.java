package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

public class Tune implements TuneIF{
	private final String title;
	private final String author;
	private final String genre;
	private final String album;
	private final int year;
	private final int duration;

    public Tune(String title, String author, String genre, String album, int year, int duration){
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.album = album;
		this.year = year;
		this.duration = duration;
	}

    public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public String getAlbum() {
		return album;
	}

	public int getYear() {
		return year;
	}

	public int getDuration() {
		return duration;
	}

	public boolean match(QueryIF q){
		if(matchAuthor(q) && matchTitle(q) && matchAlbum(q) && matchGenre(q) 
		&& intMatchMin(q.getMin_duration(), this.duration) && intMatchMax(q.getMax_duration(), this.duration) 
		&& intMatchMin(q.getMin_year(), this.year) && intMatchMax(q.getMax_year(), this.year)){
			return true;
		} else {
			return false;
		}
	}

	private boolean matchTitle(QueryIF q){
		if(q.getTitle().equalsIgnoreCase(this.title) || q.getTitle().equals("")){
			return true;
		} else {
			return false;
		}
	}
	private boolean matchAuthor(QueryIF q){
		if(q.getAuthor().equalsIgnoreCase(this.author) || q.getAuthor().equals("")){
			return true;
		} else {
			return false;
		}
	}
	private boolean matchGenre(QueryIF q){
		if(q.getGenre().equalsIgnoreCase(this.genre) || q.getGenre().equals("")){
			return true;
		} else {
			return false;
		}
	}
	private boolean matchAlbum(QueryIF q){
		if(q.getAlbum().equalsIgnoreCase(this.album) || q.getAlbum().equals("")){
			return true;
		} else {
			return false;
		}
	}

	private boolean intMatchMin(int query, int param){
		if (query == -1) {
			return true;
		} else if (query <= param) {
			return true;
		} else {
			return false;
		}
	}

	private boolean intMatchMax(int query, int param){
		if (query == -1) {
			return true;
		} else if (query >= param) {
			return true;
		} else {
			return false;
		}
	}
}
