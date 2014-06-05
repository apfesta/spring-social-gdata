package org.springframework.social.gdata.api.picasa;

import java.util.List;

public class BaseFeed<E> {

	private String title;
	private List<E> entries;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<E> getEntries() {
		return entries;
	}

	public void setEntries(List<E> entries) {
		this.entries = entries;
	}
	
}
