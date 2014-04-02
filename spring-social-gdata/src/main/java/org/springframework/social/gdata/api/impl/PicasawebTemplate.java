package org.springframework.social.gdata.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.social.gdata.api.PicasawebOperations;
import org.springframework.social.gdata.api.picasa.Access;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.BaseEntry;
import org.springframework.social.gdata.api.picasa.Photo;
import org.springframework.web.client.RestTemplate;

import com.sun.syndication.feed.atom.Category;
import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Link;
import com.sun.syndication.feed.module.gphoto.GphotoModule;
import com.sun.syndication.feed.module.mediarss.MediaEntryModuleImpl;
import com.sun.syndication.feed.module.mediarss.MediaModule;
import com.sun.syndication.feed.module.mediarss.types.MediaGroup;

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
	
	protected Feed getFeed(String url) {
		return this.getEntity(url, Feed.class);
	}
		
	/**
	 * 
	 * @param userId
	 * @param kind "album", or "tag", or "photo" or "comment"
	 * @return
	 */
	protected Feed getUserFeed(String userId, String[] kinds) {
		return getFeed(BASE_URL + getProjection() + "/user/" + userId + 
				"?kind="+((kinds!=null) ? getKinds(kinds) : "album"));
	}
	
	protected Feed getAlbumFeed(String userId) {
		return getUserFeed(userId, new String[]{"album"});
	}
	
	protected Feed getPhotoFeed(String userId) {
		return getUserFeed(userId, new String[]{"photo"});
	}
	
	protected Feed getCommentFeed(String userId) {
		return getUserFeed(userId, new String[]{"comment"});
	}
	
	protected Feed getTagFeed(String userId) {
		return getUserFeed(userId, new String[]{"tag"});
	}
	
	/**
	 * 
	 * @param userId
	 * @param albumId
	 * @param kind "photo" or "tag"
	 * @return
	 */
	protected Feed getAlbumFeed(String userId, String albumId, String[] kinds) {
		return getFeed(BASE_URL + getProjection() + "/user/" + userId + "/albumid/" + albumId + 
				"?kind="+((kinds!=null) ? getKinds(kinds) : "photo"));
	}
	
	protected Feed getPhotoFeed(String userId, String albumId) {
		return getAlbumFeed(userId, albumId, new String[] {"photo"});
	}
	
	protected Feed getTagFeed(String userId, String albumId) {
		return getAlbumFeed(userId, albumId, new String[] {"tag"});
	}
	
	@SuppressWarnings("unchecked")
	protected List<Entry> getAlbumEntries(String userId) {
		Feed feed = getAlbumFeed(userId);
		return feed.getEntries();
	}
	
	@SuppressWarnings("unchecked")
	public List<Entry> getPhotosEntries(String userId, String albumId) {
		Feed feed = getAlbumFeed(userId, albumId, new String[]{"photo"});
		return feed.getEntries();
	}
	
	@SuppressWarnings("unchecked")
	public List<Entry> getPhotosEntries(String userId) {
		Feed feed = getPhotoFeed(userId);
		return feed.getEntries();
	}
	
	protected Entry postEntryInternal(String userId, Entry entry) {
		this.requireAuthorization();
		
		return this.saveEntity(BASE_URL + getProjection() + "/user/" + userId, entry);
	}
	
	protected Entry uploadEntryInternal(String userId, String albumId, Entry entry, Resource resource, MediaType resourceType) {
		this.requireAuthorization();
		
		return this.uploadEntity(BASE_URL + getProjection() + "/user/" + userId + "/albumid/" + albumId, 
				entry, resource, resourceType, Entry.class);
	}
	
	protected Entry uploadResourceInternal(String userId, String albumId, Resource resource, MediaType resourceType) {
		this.requireAuthorization();
		
		return this.upload(BASE_URL + getProjection() + "/user/" + userId + "/albumid/" + albumId, 
				resource, resourceType, Entry.class);
	}
	
	protected Entry putEntryInternal(String url, Entry entry) {
		this.requireAuthorization();
		
		if (entry.getId()==null) {
			throw new IllegalArgumentException("Entity must have an ID");
		}
		
		return this.updateEntity(url, entry);
	}
	
	private void copyToBaseEntry(BaseEntry base, Entry entry) {
		base.setId(entry.getId());
		base.setTitle(entry.getTitle());
		base.setSummary(entry.getSummary().getValue());
		base.setPublishedDate(entry.getPublished());
		base.setUpdatedDate(entry.getUpdated());
		GphotoModule gphoto = FeedUtils.getModule(entry, GphotoModule.URI);
		if (gphoto!=null) {
			base.setGphotoId(gphoto.getId());
		}
		List<?> links = entry.getOtherLinks();
		for (Object olink: links) {
			Link link = (Link) olink;
			if (link.getRel().equals("edit")) {
				base.setEditUrl(link.getHref());
			}
		}
	}
	
	private void copyFromBaseEntry(Entry entry, BaseEntry base) {
		entry.setId(base.getId());
		entry.setTitle(base.getTitle());
		entry.setSummary(new Content());
		entry.getSummary().setType("text");
		entry.getSummary().setValue(base.getSummary());
		entry.setPublished(base.getPublishedDate());
		entry.setUpdated(base.getUpdatedDate());
		GphotoModule gphoto = FeedUtils.getModule(entry, GphotoModule.URI);
		if (gphoto!=null) {
			gphoto.setId(base.getGphotoId());
		}
	}
	
	private Album toAlbum(Entry entry) {
		Album album = new Album();
		copyToBaseEntry(album, entry);
		GphotoModule gphoto = FeedUtils.getModule(entry, GphotoModule.URI);
		if (gphoto!=null) {
			album.setAccess(Access.fromValue(gphoto.getAccess().value()));
			album.setNumOfPhotos(gphoto.getNumphotos());
			album.setLocation(gphoto.getLocation());
			album.setTimestamp(new Date(gphoto.getTimestamp()));
		}
		MediaEntryModuleImpl media = FeedUtils.getModule(entry, MediaModule.URI);
		if (media!=null) {
			MediaGroup[] groups = media.getMediaGroups();
			album.setKeywords(groups[0].getMetadata().getKeywords());
		}
		return album;
	}
	
	private Entry fromAlbum(Album album) {
		Entry entry = new Entry();
		List<Category> categories = new ArrayList<Category>();
		Category cat = new Category();
		cat.setScheme("http://schemas.google.com/g/2005#kind");
		cat.setTerm("http://schemas.google.com/photos/2007#album");
		categories.add(cat);
		entry.setCategories(categories);
		
		copyFromBaseEntry(entry, album);
		GphotoModule gphoto = FeedUtils.getModule(entry, GphotoModule.URI);
		if (gphoto!=null) {
			gphoto.setNumphotos(album.getNumOfPhotos());
			gphoto.setLocation(album.getLocation());
			gphoto.setTimestamp(album.getTimestamp().getTime());
		}
		return entry;
	}
		
	private Photo toPhoto(Entry entry) {
		Photo photo = new Photo();
		copyToBaseEntry(photo, entry);
		GphotoModule gphoto = FeedUtils.getModule(entry, GphotoModule.URI);
		if (gphoto!=null) {
			photo.setHeight(gphoto.getHeight());
			photo.setWidth(gphoto.getWidth());
			photo.setSize(gphoto.getSize());
			photo.setTimestamp(new Date(gphoto.getTimestamp()));
		}
		
		return photo;
	}
	
	private Entry fromPhoto(Photo photo) {
		Entry entry = new Entry();
		List<Category> categories = new ArrayList<Category>();
		Category cat = new Category();
		cat.setScheme("http://schemas.google.com/g/2005#kind");
		cat.setTerm("http://schemas.google.com/photos/2007#photo");
		categories.add(cat);
		entry.setCategories(categories);
		
		copyFromBaseEntry(entry, photo);
		GphotoModule gphoto = FeedUtils.getModule(entry, GphotoModule.URI);
		if (gphoto!=null) {
			gphoto.setHeight(photo.getHeight());
			gphoto.setWidth(photo.getWidth());
			gphoto.setSize(photo.getSize());
			gphoto.setTimestamp(photo.getTimestamp().getTime());
		}
		return entry;
	}
	
	//---ALBUMS
	
	@Override
	public List<Album> getAlbums(String userId) {
		List<Entry> entries = this.getAlbumEntries(userId);
		List<Album> albums = new ArrayList<Album>();
		for (Entry entry : entries) {
			albums.add(toAlbum(entry));
		}
		return albums;
	}
	
	@Override
	public List<Album> getMyAlbums() {
		return getAlbums(DEFAULT_USER_ID);
	}
	
	@Override
	public Album addAlbum(String userId, Album album) {
		Entry entry = this.postEntryInternal(userId, fromAlbum(album));
		return toAlbum(entry);
	}
	
	@Override
	public Album addAlbum(Album album) {
		return addAlbum(DEFAULT_USER_ID, album);
	}
	

	public Album updateAlbum(Album album) {
		Entry entry = fromAlbum(album);
		entry = this.putEntryInternal(album.getEditUrl(), entry);
		return toAlbum(entry);
	}

	
	//---ALBUM BASED PHOTOS
	
	@Override
	public List<Photo> getPhotos(String userId, String albumId) {
		List<Entry> entries = this.getPhotosEntries(userId, albumId);
		List<Photo> photos = new ArrayList<Photo>();
		for (Entry entry : entries) {
			photos.add(toPhoto(entry));
		}
		return photos;
	}
	
	@Override
	public List<Photo> getMyPhotos(String albumId) {
		return getPhotos(DEFAULT_USER_ID, albumId);
	}
	
	//---NON-ALBUM BASED PHOTOS
	
	@Override
	public List<Photo> getPhotos(String userId) {
		List<Entry> entries = this.getPhotosEntries(userId);
		List<Photo> photos = new ArrayList<Photo>();
		for (Entry entry : entries) {
			photos.add(toPhoto(entry));
		}
		return photos;
	}
	
	@Override
	public List<Photo> getMyPhotos() {
		return getPhotos(DEFAULT_USER_ID);
	}

	@Override
	public Photo addPhoto(String userId, String albumId, Photo photoMetaData, Resource resource, MediaType resourceType) {
		Entry entry = this.uploadEntryInternal(userId, albumId, fromPhoto(photoMetaData), resource, resourceType);
		return toPhoto(entry);
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
		Entry entry = this.uploadResourceInternal(userId, albumId, resource, resourceType);
		return toPhoto(entry);
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
