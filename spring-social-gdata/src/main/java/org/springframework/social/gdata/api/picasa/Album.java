/**
 * 
 */
package org.springframework.social.gdata.api.picasa;

import java.util.Date;


/**
 * @author apfesta
 *
 */
public class Album extends BaseEntry {
	
	private Integer numOfPhotos;
	private String location;
	private Date timestamp;
	private String[] keywords;
	private Access access;
		
	public Integer getNumOfPhotos() {
		return numOfPhotos;
	}

	public void setNumOfPhotos(Integer numOfPhotos) {
		this.numOfPhotos = numOfPhotos;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

}
