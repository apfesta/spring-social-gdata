package org.springframework.social.gdata.api.picasa;

import java.util.List;

import org.springframework.social.gdata.api.picasa.xpp.Author;

public class BaseFeed<E> {

	private String title;
	private Author author;
	private List<E> entries;

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
	
}
