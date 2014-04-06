package org.springframework.social.gdata.api.picasa.xpp;

class Attribute {
	String namespace;
	String name;
	String value;
	public Attribute(String prefix, String name, String value) {
		super();
		this.namespace = prefix;
		this.name = name;
		this.value = value;
	}
}
