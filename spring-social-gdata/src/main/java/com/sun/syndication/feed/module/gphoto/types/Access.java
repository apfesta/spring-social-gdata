/**
 * 
 */
package com.sun.syndication.feed.module.gphoto.types;

/**
 * @author apfesta
 *
 */
public enum Access {
	
	PUBLIC("public"),
	PRIVATE("private"),
	PROTECTED("protected");
	
	private String value;
	
	private Access(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static Access fromValue(String value) {
		if (value.equals(PUBLIC.value)) {
			return PUBLIC;
		}
		if (value.equals(PRIVATE.value)) {
			return PRIVATE;
		}
		if (value.equals(PROTECTED.value)) {
			return PROTECTED;
		}
		throw new IllegalArgumentException(value+" is not a valid access type");
	}
}
