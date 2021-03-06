package org.springframework.social.gdata.api.picasa;

import java.util.Date;

import org.springframework.social.gdata.api.ApiEntity;
import org.springframework.social.gdata.api.picasa.xpp.Author;

public class BaseEntry extends ApiEntity {
	
	private String gphotoId;
	private String title;
	private String summary;
	private String content;
	private Date publishedDate;
	private Date updatedDate;
	private String feedUri;
	private String editUrl;
	private String alternateUrl;
	private String selfUrl;
	private String canonicalUrl;
	private Author author;
	private Media media;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getFeedUri() {
		return feedUri;
	}
	public void setFeedUri(String feedUrl) {
		this.feedUri = feedUrl;
	}
	public String getEditUrl() {
		return editUrl;
	}
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	public String getAlternateUrl() {
		return alternateUrl;
	}
	public void setAlternateUrl(String alternateUrl) {
		this.alternateUrl = alternateUrl;
	}
	public String getSelfUrl() {
		return selfUrl;
	}
	public void setSelfUrl(String selfUrl) {
		this.selfUrl = selfUrl;
	}
	public String getCanonicalUrl() {
		return canonicalUrl;
	}
	public void setCanonicalUrl(String canonicalUrl) {
		this.canonicalUrl = canonicalUrl;
	}
	
}
