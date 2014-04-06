package org.springframework.social.gdata.api.picasa;

import java.util.Date;

import org.springframework.social.gdata.api.ApiEntity;

public class BaseEntry extends ApiEntity {
	
	private String gphotoId;
	private String title;
	private String summary;
	private Date publishedDate;
	private Date updatedDate;
	private String editUrl;
	
	public String getGphotoId() {
		return gphotoId;
	}
	public void setGphotoId(String gphotoId) {
		this.gphotoId = gphotoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getEditUrl() {
		return editUrl;
	}
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
}
