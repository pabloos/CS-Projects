package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

public class Query implements QueryIF {

	private final String title;
	private final String author;
	private final String genre;
	private final String album;
	private final int min_year;
	private final int max_year;
	private final int min_duration;
	private final int max_duration;

	public Query(String title, String author, String genre, String album, int min_year, int max_year, int min_duration, int max_duration){
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.album = album;
		this.min_year = min_year;
		this.max_year = max_year;
		this.min_duration = min_duration;
		this.max_duration = max_duration;
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

	public int getMin_year() {
		return min_year;
	}

	public int getMax_year() {
		return max_year;
	}

	public int getMin_duration() {
		return min_duration;
	}

	public int getMax_duration() {
		return max_duration;
	}
}