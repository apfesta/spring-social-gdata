package org.springframework.social.gdata.api.picasa;

import java.util.List;

public class BaseFeed<E> {

	private List<E> entries;

	public List<E> getEntries() {
		return entries;
	}

	public void setEntries(List<E> entries) {
		this.entries = entries;
	}
	
}
