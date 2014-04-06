/**
 * 
 */
package org.springframework.social.gdata.api;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author apfesta
 *
 */
public abstract class ApiEntity {

	private String id;
	
	private String etag;

	protected ApiEntity() {
	}
	
	protected ApiEntity(String id) {
		this.id = hasText(id) ? id : null;
	}

	public String getId() {
		return id;
	}
	
	public String getEtag() {
		return etag;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}
	
}
