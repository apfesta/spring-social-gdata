package org.springframework.social.gdata.api.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.social.gdata.api.PicasawebOperations;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.IEntry;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;

public class PicasawebTemplate extends AbstractGdataOperations<PicasawebService> implements PicasawebOperations {

	private String userId;
	
	public PicasawebTemplate(String accessToken, String applicationName, String userId,
			boolean isAuthorized) {
		super(accessToken, applicationName, isAuthorized);
		this.userId = userId;
	}

	public PicasawebService getPicasawebService(String applicationName) {
		return getService();
	}

	@Override
	protected PicasawebService createGoogleService(String applicationName) {
		return new PicasawebService(applicationName);
	}
	
	protected UserFeed getUserFeed(String userId) {
		URL feedUrl=null;
		try {
			feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/"+userId+"?kind=album");
		} catch (MalformedURLException e) {}
		
		try {
			return getService().getFeed(feedUrl, UserFeed.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	protected AlbumFeed getAlbumFeed(String userId, String albumId) {
		URL feedUrl=null;
		try {
			feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/"+userId+"/albumid/"+albumId);
		} catch (MalformedURLException e) {}
		
		try {
			return getService().getFeed(feedUrl, AlbumFeed.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	protected AlbumFeed getAlbumFeed(String userId) {
		URL feedUrl=null;
		try {
			feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/"+userId+"?kind=photo");
		} catch (MalformedURLException e) {}
		
		try {
			return getService().getFeed(feedUrl, AlbumFeed.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	protected URL getPostUrl(String userId) {
		try {
			return new URL("https://picasaweb.google.com/data/feed/api/user/"+userId);
		} catch (MalformedURLException e) {}
		return null;
	}

	public List<AlbumEntry> getAlbums(String userId) {
		return getUserFeed(userId).getAlbumEntries();
	}
	
	public List<AlbumEntry> getMyAlbums() {
		return getUserFeed(userId).getAlbumEntries();
	}
	
	protected <E extends IEntry> E add(String userId, E album) {
		try {
			return getService().insert(getPostUrl(userId), album);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	public AlbumEntry addAlbum(String userId, AlbumEntry album) {
		return add(userId, album);
	}
	
	public AlbumEntry addAlbum(AlbumEntry album) {
		return add(userId, album);
	}
	
	public List<PhotoEntry> getPhotos(String userId, String albumId) {
		return getAlbumFeed(userId, albumId).getPhotoEntries();
	}
	
	public List<PhotoEntry> getPhotos(String userId) {
		return getAlbumFeed(userId).getPhotoEntries();
	}
	
	public List<PhotoEntry> getMyPhotos() {
		return getAlbumFeed(userId).getPhotoEntries();
	}
}
