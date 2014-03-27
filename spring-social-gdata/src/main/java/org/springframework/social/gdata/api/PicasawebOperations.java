/**
 * 
 */
package org.springframework.social.gdata.api;

import java.util.List;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;

/**
 * @author apfesta
 *
 */
public interface PicasawebOperations {
	
	public PicasawebService getPicasawebService(String applicationName);
	
	//Albums
	
	public List<AlbumEntry> getAlbums(String userId);
	
	public List<AlbumEntry> getMyAlbums();
	
	public AlbumEntry addAlbum(String userId, AlbumEntry album);
	
	public AlbumEntry addAlbum(AlbumEntry album);
	
	//Photos
	
	public List<PhotoEntry> getPhotos(String userId, String albumId);
	
	public List<PhotoEntry> getPhotos(String userId);
	
	public List<PhotoEntry> getMyPhotos();
	
	public PhotoEntry addPhoto(String userId, String albumId, PhotoEntry photo);
	
	public PhotoEntry addPhoto(String albumId, PhotoEntry photo);
	
	public PhotoEntry addPhoto(PhotoEntry photo);
	
	public PhotoEntry addPhoto(String userId, String albumId, MediaSource media);
	
	public PhotoEntry addPhoto(String albumId, MediaSource media);
	
	public PhotoEntry addPhoto(MediaSource media);
}
