package org.springframework.social.gdata.api.picasa;

import java.util.List;

import org.springframework.social.gdata.api.picasa.xpp.Author;

public class BaseFeed<E> {

	private String title;
	private Author author;
	private List<E> entries;
	private String feedUri;
	private String alternateUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<E> getEntries() {
		return entries;
	}

	public void setEntries(List<E> entries) {
		this.entries = entries;
	}

	public String getFeedUri() {
		return feedUri;
	}

	public void setFeedUri(String feedUrl) {
		this.feedUri = feedUrl;
	}

	public String getAlternateUrl() {
		return alternateUrl;
	}

	public void setAlternateUrl(String alternateUrl) {
		this.alternateUrl = alternateUrl;
	}
	
}
