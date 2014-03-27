package org.springframework.social.gdata.api.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.social.gdata.api.PicasawebOperations;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.IEntry;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;

public class PicasawebTemplate extends AbstractGdataOperations<PicasawebService> implements PicasawebOperations {

	private String defaultUserId = "default";
	private String defaultAlbumId = "default";
	
	private static final String BASE_DATA_FEED_URL = "https://picasaweb.google.com/data/feed";
	
	public PicasawebTemplate(String accessToken, String applicationName,
			boolean isAuthorized) {
		super(accessToken, applicationName, isAuthorized);
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
			feedUrl = new URL(BASE_DATA_FEED_URL+"/api/user/"+userId+"?kind=album");
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
			feedUrl = new URL(BASE_DATA_FEED_URL+"/api/user/"+userId+"/albumid/"+albumId);
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
			feedUrl = new URL(BASE_DATA_FEED_URL+"/api/user/"+userId+"?kind=photo");
		} catch (MalformedURLException e) {}
		
		try {
			return getService().getFeed(feedUrl, AlbumFeed.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	protected URL getPostAlbumUrl(String userId) {
		try {
			return new URL(BASE_DATA_FEED_URL+"/api/user/"+userId);
		} catch (MalformedURLException e) {}
		return null;
	}
	
	protected URL getPostPhotoUrl(String userId, String albumId) {
		try {
			return new URL(BASE_DATA_FEED_URL+"/api/user/"+userId+"/albumid/"+albumId);
		} catch (MalformedURLException e) {}
		return null;
	}

	public List<AlbumEntry> getAlbums(String userId) {
		return getUserFeed(userId).getAlbumEntries();
	}
	
	public List<AlbumEntry> getMyAlbums() {
		return getUserFeed(defaultUserId).getAlbumEntries();
	}
	
	protected <E extends IEntry> E add(URL url, E entry) {
		try {
			return getService().insert(url, entry);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	protected <E extends IEntry> E add(URL url, Class<E> entryClass, MediaSource media) {
		try {
			return getService().insert(url, entryClass, media);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	public AlbumEntry addAlbum(String userId, AlbumEntry album) {
		return add(getPostAlbumUrl(userId), album);
	}
	
	public AlbumEntry addAlbum(AlbumEntry album) {
		return add(getPostAlbumUrl(defaultUserId), album);
	}
	
	public List<PhotoEntry> getPhotos(String userId, String albumId) {
		return getAlbumFeed(userId, albumId).getPhotoEntries();
	}
	
	public List<PhotoEntry> getPhotos(String userId) {
		return getAlbumFeed(userId).getPhotoEntries();
	}
	
	public List<PhotoEntry> getMyPhotos() {
		return getAlbumFeed(defaultUserId).getPhotoEntries();
	}
	
	public PhotoEntry addPhoto(String userId, String albumId, PhotoEntry photo) {
		return add(getPostPhotoUrl(userId, albumId), photo);
	}
	
	public PhotoEntry addPhoto(String albumId, PhotoEntry photo) {
		return add(getPostPhotoUrl(defaultUserId, albumId), photo);
	}
	
	public PhotoEntry addPhoto(PhotoEntry photo) {
		return add(getPostPhotoUrl(defaultUserId, defaultAlbumId), photo);
	}
	
	public PhotoEntry addPhoto(String userId, String albumId, MediaSource media) {
		return add(getPostPhotoUrl(userId, albumId), PhotoEntry.class, media);
	}
	
	public PhotoEntry addPhoto(String albumId, MediaSource media) {
		return add(getPostPhotoUrl(defaultUserId, albumId), PhotoEntry.class, media);
	}
	
	public PhotoEntry addPhoto(MediaSource media) {
		return add(getPostPhotoUrl(defaultUserId, defaultAlbumId), PhotoEntry.class, media);
	}
}
