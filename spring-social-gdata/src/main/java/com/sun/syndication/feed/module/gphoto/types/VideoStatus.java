package com.sun.syndication.feed.module.gphoto.types;

public enum VideoStatus {
	
	PENDING("pending"),
	READY("ready"),
	FINAL("final"),
	FAILED("failed");
	
	private String value;
	
	private VideoStatus(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static VideoStatus fromValue(String value) {
		if (value.equals(PENDING.value)) {
			return PENDING;
		}
		if (value.equals(READY.value)) {
			return READY;
		}
		if (value.equals(FINAL.value)) {
			return FINAL;
		}
		if (value.equals(FAILED.value)) {
			return FAILED;
		}
		throw new IllegalArgumentException(value+" is not a valid video status type");
	}

}
