package org.springframework.social.gdata.api.impl;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.social.gdata.api.PicasawebOperations;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.AlbumFeed;
import org.springframework.social.gdata.api.picasa.BaseFeed;
import org.springframework.social.gdata.api.picasa.Photo;
import org.springframework.social.gdata.api.picasa.PhotoFeed;
import org.springframework.web.client.RestTemplate;


public class PicasawebTemplate extends AbstractGdataOperations implements PicasawebOperations {
	
	private static final String BASE_URL = "https://picasaweb.google.com/data/feed/";
	
	private static final String DEFAULT_USER_ID = "default";
	private static final String DEFAULT_ALBUM_ID = "default";
	
	boolean isAuthorized;

	public PicasawebTemplate(RestTemplate restTemplate,
			boolean isAuthorized) {
		super(restTemplate, isAuthorized);
		this.isAuthorized = isAuthorized;
	}
	
	private String getProjection() {
		return (isAuthorized)?"api":"base";
	}
	
	private String getKinds(String[] kinds) {
		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<kinds.length; i++) {
			buffer.append(kinds[i]);
			if (i+1<kinds.length) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
//	protected Feed getFeed(String url) {
//		return this.getEntity(url, Feed.class);
//	}
		
	/**
	 * 
	 * @param userId
	 * @param kind "album", or "tag", or "photo" or "comment"
	 * @return
	 */
	protected <F extends BaseFeed<?>> F getUserFeed(String userId, String[] kinds, Class<F> feedClass) {
		return getEntity(BASE_URL + getProjection() + "/user/" + userId + 
				"?kind="+((kinds!=null) ? getKinds(kinds) : "album"), feedClass);
	}
	
	protected AlbumFeed getAlbumFeed(String userId) {
		return getUserFeed(userId, new String[]{"album"}, AlbumFeed.class);
	}
	
	protected PhotoFeed getPhotoFeed(String userId) {
		return getUserFeed(userId, new String[]{"photo"}, PhotoFeed.class);
	}
		
	/**
	 * 
	 * @param userId
	 * @param albumId
	 * @param kind "photo" or "tag"
	 * @return
	 */
	protected PhotoFeed getPhotoFeed(String userId, String albumId, String[] kinds) {
		return getEntity(BASE_URL + getProjection() + "/user/" + userId + "/albumid/" + albumId + 
				"?kind="+((kinds!=null) ? getKinds(kinds) : "photo"), PhotoFeed.class);
	}
		
		
	//---ALBUMS
	
	@Override
	public List<Album> getAlbums(String userId) {
		AlbumFeed feed = getAlbumFeed(userId);
		return feed.getEntries();
	}
	
	@Override
	public List<Album> getMyAlbums() {
		return getAlbums(DEFAULT_USER_ID);
	}
	
	@Override
	public Album addAlbum(String userId, Album album) {
		this.requireAuthorization();
		
		return this.saveEntity(BASE_URL + getProjection() + "/user/" + userId, album);
	}
	
	@Override
	public Album addAlbum(Album album) {
		return addAlbum(DEFAULT_USER_ID, album);
	}
	

	public Album updateAlbum(Album album) {
		this.requireAuthorization();
		
		if (album.getId()==null) {
			throw new IllegalArgumentException("Entity must have an ID");
		}
		
		return this.saveEntity(album.getEditUrl(), album);
	}

	
	//---ALBUM BASED PHOTOS
	
	@Override
	public List<Photo> getPhotos(String userId, String albumId) {
		PhotoFeed feed = getPhotoFeed(userId, albumId, new String[]{"photo"});
		return feed.getEntries();
	}
	
	@Override
	public List<Photo> getMyPhotos(String albumId) {
		return getPhotos(DEFAULT_USER_ID, albumId);
	}
	
	//---NON-ALBUM BASED PHOTOS
	
	@Override
	public List<Photo> getPhotos(String userId) {
		PhotoFeed feed = getPhotoFeed(userId);
		return feed.getEntries();
	}
	
	@Override
	public List<Photo> getMyPhotos() {
		return getPhotos(DEFAULT_USER_ID);
	}

	@Override
	public Photo addPhoto(String userId, String albumId, Photo photoMetaData, Resource resource, MediaType resourceType) {
		this.requireAuthorization();
		
		return this.uploadEntity(BASE_URL + getProjection() + "/user/" + userId + "/albumid/" + albumId, 
				photoMetaData, resource, resourceType, Photo.class);
	}

	@Override
	public Photo addPhoto(String albumId, Photo photo, Resource resource,
			MediaType resourceType) {
		return this.addPhoto(DEFAULT_USER_ID, albumId, photo, resource, resourceType);
	}
	
	@Override
	public Photo addPhoto(Photo photo, Resource resource, MediaType resourceType) {
		return this.addPhoto(DEFAULT_ALBUM_ID, photo, resource, resourceType);
	}

	@Override
	public Photo addPhoto(String userId, String albumId, Resource resource,
			MediaType resourceType) {
		this.requireAuthorization();
		
		return this.upload(BASE_URL + getProjection() + "/user/" + userId + "/albumid/" + albumId, 
				resource, resourceType, Photo.class);
	}

	@Override
	public Photo addPhoto(String albumId, Resource resource,
			MediaType resourceType) {
		return this.addPhoto(DEFAULT_USER_ID, albumId, resource, resourceType);
	}

	@Override
	public Photo addPhoto(Resource resource, MediaType resourceType) {
		return this.addPhoto(DEFAULT_ALBUM_ID, resource, resourceType);
	}

}
