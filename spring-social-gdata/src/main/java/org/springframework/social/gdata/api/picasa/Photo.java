/**
 * 
 */
package org.springframework.social.gdata.api.picasa;

import java.util.Date;


/**
 * @author apfesta
 *
 */
public class Photo extends BaseEntry {
	
	Long height;
	Long width;
	Long size;
	Date timestamp;
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
	}
	public Long getWidth() {
		return width;
	}
	public void setWidth(Long width) {
		this.width = width;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
		
}
