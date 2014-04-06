package org.springframework.social.gdata.api.picasa.xpp;

class Link {
	String rel;
	String type;
	String href;
	public Link() {
		super();
	}
	public Link(String rel, String type, String href) {
		super();
		this.rel = rel;
		this.type = type;
		this.href = href;
	}
}
