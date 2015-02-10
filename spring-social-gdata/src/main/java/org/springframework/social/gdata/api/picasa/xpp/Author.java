package org.springframework.social.gdata.api.picasa.xpp;

public class Author {
	String name;
	String uri;
	public Author() {
		super();
	}
	public Author(String name, String uri) {
		super();
		this.name = name;
		this.uri = uri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
