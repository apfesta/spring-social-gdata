/**
 * 
 */
package org.springframework.social.gdata.api;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.AlbumFeed;
import org.springframework.social.gdata.api.picasa.Photo;
import org.springframework.social.gdata.api.picasa.PhotoFeed;

/**
 * @author apfesta
 *
 */
public interface PicasawebOperations {
		
	//Albums
	
	public AlbumFeed getAlbumFeed(String userId, QueryParameters params);
	
	public AlbumFeed getAlbumFeed(String userId);
	
	public AlbumFeed getMyAlbumFeed(QueryParameters params);
	
	public AlbumFeed getMyAlbumFeed();
	
	public List<Album> getAlbums(String userId);
	
	public List<Album> getMyAlbums();
	
	public Album addAlbum(String userId, Album album);
	
	public Album addAlbum(Album album);
	
	public Album updateAlbum(Album album);
	
	/**
	 * Deletes an album 
	 * @param album
	 */
	public void deleteAlbum(Album album);
		
	/**
	 * Deletes an album when you already know the user, and 
	 * album
	 * 
	 * @param userId
	 * @param albumId
	 */
	public void deleteAlbum(String userId, String albumId);
	
	//Photos
	public PhotoFeed getPhotoFeed(String userId, String albumId, QueryParameters params);
	
	public PhotoFeed getPhotoFeed(String userId, String albumId);
	
	public PhotoFeed getMyPhotoFeed(String albumId, QueryParameters params);
	
	public PhotoFeed getMyPhotoFeed(String albumId);
	
	public PhotoFeed getMyPhotoFeed(QueryParameters params);
	
	public List<Photo> getPhotos(String userId, String albumId);
	
	public List<Photo> getPhotos(String userId);
	
	public List<Photo> getMyPhotos(String albumId);
	
	public List<Photo> getMyPhotos();
	
	/**
	 * Posts a photo with meta data
	 * 
	 * @param userId
	 * @param albumId
	 * @param metadata information about the photo such as title and summary
	 * @param resource resource containing photo
	 * @param resourceType MIME photo type
	 * @return
	 */
	public Photo addPhoto(String userId, String albumId, Photo metadata, Resource resource, MediaType resourceType);
	
	public Photo addPhoto(String albumId, Photo metadata, Resource resource, MediaType resourceType);
	
	public Photo addPhoto(Photo metadata, Resource resource, MediaType resourceType);
	
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
		
	/**
	 * Deletes a photo and its meta data
	 * 
	 * @param photo
	 */
	public void deletePhoto(Photo photo);
	
	/**
	 * Deletes a photo and its meta data when you already know the user, album
	 * and photo id.
	 * 
	 * @param userId
	 * @param albumId
	 * @param photoId
	 */
	public void deletePhoto(String userId, String albumId, String photoId);
	
	public class QueryParameters {

		private int imageSizeMax;
		private boolean imageSizeCropped;
		private String[] kinds;
		
		public String[] getKinds() {
			return kinds;
		}
		public void setKinds(String[] kinds) {
			this.kinds = kinds;
		}
		public void setKind(String kind) {
			this.kinds = new String[]{kind};
		}
		public int getImageSizeMax() {
			return imageSizeMax;
		}
		public void setImageSizeMax(int imageSizeMax) {
			this.imageSizeMax = imageSizeMax;
		}
		public boolean isImageSizeCropped() {
			return imageSizeCropped;
		}
		public void setImageSizeCropped(boolean imageSizeCropped) {
			this.imageSizeCropped = imageSizeCropped;
		}
		public String getImgmax() {
			if (imageSizeMax>0) {
				return imageSizeMax + (imageSizeCropped ? "c":"u");
			}
			return null;
		}
		public String getKindsAsString() {
			StringBuffer buffer = new StringBuffer();
			for (int i=0; i<kinds.length; i++) {
				buffer.append(kinds[i]);
				if (i+1<kinds.length) {
					buffer.append(",");
				}
			}
			return buffer.toString();
		}
		
		
	}
}
