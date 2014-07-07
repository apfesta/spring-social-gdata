package org.springframework.social.gdata.api.impl;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.social.gdata.api.PicasawebOperations;
import org.springframework.social.gdata.api.PicasawebOperations.QueryParameters;
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
			ChunkedRestTemplate chunkedRestTemplate,
			boolean isAuthorized) {
		super(restTemplate, chunkedRestTemplate, isAuthorized);
		this.isAuthorized = isAuthorized;
	}
	
	private String getProjection() {
		return (isAuthorized)?"api":"base";
	}
	
//	protected Feed getFeed(String url) {
//		return this.getEntity(url, Feed.class);
//	}
	
	protected String createQueryString(QueryParameters params, String defaultKind) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("?kind="+((params.getKinds()!=null) ? params.getKindsAsString() : defaultKind));
		if (params.getImageSizeMax()>0) {
			queryString.append("&imgmax=" + params.getImgmax());
		}
		return queryString.toString();
	}
		
	/**
	 * 
	 * @param userId
	 * @param kind "album", or "tag", or "photo" or "comment"
	 * @return
	 */
	protected <F extends BaseFeed<?>> F getUserFeed(String userId, QueryParameters params, Class<F> feedClass) {
		return getEntity(BASE_URL + getProjection() + "/user/" + userId + 
				createQueryString(params, "album"), feedClass);
	}
	
	protected AlbumFeed getAlbumFeedForUser(String userId, QueryParameters params) {
		return getUserFeed(userId, params, AlbumFeed.class);
	}
	
	protected AlbumFeed getAlbumFeedForUser(String userId) {
		QueryParameters params = new QueryParameters();
		params.setKind("album");
		return getUserFeed(userId, params, AlbumFeed.class);
	}
	
	protected PhotoFeed getPhotoFeedForUser(String userId) {
		QueryParameters params = new QueryParameters();
		params.setKind("photo");
		return getUserFeed(userId, params, PhotoFeed.class);
	}
		
	/**
	 * 
	 * @param userId
	 * @param albumId
	 * @param kind "photo" or "tag"
	 * @return
	 */
	protected PhotoFeed getPhotoFeedForAlbum(String userId, String albumId, QueryParameters params) {
		return getEntity(BASE_URL + getProjection() + "/user/" + userId + "/albumid/" + albumId + 
				createQueryString(params, "photo"), PhotoFeed.class);
	}
		
		
	//---ALBUMS
		
	@Override
	public AlbumFeed getAlbumFeed(String userId, QueryParameters params) {
		return getAlbumFeedForUser(userId, params);
	}

	@Override
	public AlbumFeed getAlbumFeed(String userId) {
		return getAlbumFeedForUser(userId);
	}

	@Override
	public AlbumFeed getMyAlbumFeed(QueryParameters params) {
		return getAlbumFeedForUser(DEFAULT_USER_ID, params);
	}

	@Override
	public AlbumFeed getMyAlbumFeed() {
		return getAlbumFeedForUser(DEFAULT_USER_ID);
	}

	@Override
	public List<Album> getAlbums(String userId) {
		AlbumFeed feed = getAlbumFeedForUser(userId);
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
	public PhotoFeed getPhotoFeed(String userId, String albumId,
			QueryParameters params) {
		return getPhotoFeedForAlbum(userId, albumId, params);
	}
	
	@Override
	public PhotoFeed getPhotoFeed(String userId, String albumId) {
		QueryParameters params = new QueryParameters();
		params.setKind("photo");
		return getPhotoFeedForAlbum(userId, albumId, params);
	}

	@Override
	public PhotoFeed getMyPhotoFeed(String albumId, QueryParameters params) {
		return getPhotoFeedForAlbum(DEFAULT_USER_ID, albumId, params);
	}

	@Override
	public PhotoFeed getMyPhotoFeed(String albumId) {
		QueryParameters params = new QueryParameters();
		params.setKind("photo");
		return getPhotoFeedForAlbum(DEFAULT_USER_ID, albumId, params);
	}
	
	@Override
	public PhotoFeed getMyPhotoFeed(QueryParameters params) {
		return getPhotoFeedForAlbum(DEFAULT_USER_ID, DEFAULT_ALBUM_ID, params);
	}

	@Override
	public List<Photo> getPhotos(String userId, String albumId) {
		QueryParameters params = new QueryParameters();
		params.setKind("photo");
		PhotoFeed feed = getPhotoFeedForAlbum(userId, albumId, params);
		return feed.getEntries();
	}
	
	@Override
	public List<Photo> getMyPhotos(String albumId) {
		return getPhotos(DEFAULT_USER_ID, albumId);
	}
	
	//---NON-ALBUM BASED PHOTOS
	
	@Override
	public List<Photo> getPhotos(String userId) {
		PhotoFeed feed = getPhotoFeedForUser(userId);
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
