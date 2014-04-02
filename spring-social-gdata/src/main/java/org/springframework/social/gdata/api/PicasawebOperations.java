/**
 * 
 */
package org.springframework.social.gdata.api;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.Photo;

/**
 * @author apfesta
 *
 */
public interface PicasawebOperations {
		
	//Albums
	
	public List<Album> getAlbums(String userId);
	
	public List<Album> getMyAlbums();
	
	public Album addAlbum(String userId, Album album);
	
	public Album addAlbum(Album album);
	
	public Album updateAlbum(Album album);
	
	//Photos
	
	public List<Photo> getPhotos(String userId, String albumId);
	
	public List<Photo> getPhotos(String userId);
	
	public List<Photo> getMyPhotos(String albumId);
	
	public List<Photo> getMyPhotos();
	
	/**
	 * Posts a photo with meta data
	 * 
	 * @param userId
	 * @param albumId
	 * @param photo metadata
	 * @param resource resource containing photo
	 * @param resourceType MIME photo type
	 * @return
	 */
	public Photo addPhoto(String userId, String albumId, Photo photo, Resource resource, MediaType resourceType);
	
	public Photo addPhoto(String albumId, Photo photo, Resource resource, MediaType resourceType);
	
	public Photo addPhoto(Photo photo, Resource resource, MediaType resourceType);
	
	/**
	 * Posts a photo without meta data
	 * 
	 * @param userId
	 * @param albumId
	 * @param resource resource containing photo
	 * @param resourceType MIME photo type
	 * @return
	 */
	public Photo addPhoto(String userId, String albumId, Resource resource, MediaType resourceType);
	
	public Photo addPhoto(String albumId, Resource resource, MediaType resourceType);
	
	public Photo addPhoto(Resource resource, MediaType resourceType);
		
}
