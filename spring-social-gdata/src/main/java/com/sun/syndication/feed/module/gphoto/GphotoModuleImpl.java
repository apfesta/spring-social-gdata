/**
 * 
 */
package com.sun.syndication.feed.module.gphoto;

import com.sun.syndication.feed.module.ModuleImpl;
import com.sun.syndication.feed.module.gphoto.types.Access;
import com.sun.syndication.feed.module.gphoto.types.VideoStatus;

/**
 * @author apfesta
 *
 */
public class GphotoModuleImpl extends ModuleImpl implements GphotoModule {

	private static final long serialVersionUID = 1L;
		
	//common
	String albumId;
	String id;
	
	
	//user
	Integer maxPhotosPerAlbum;
	String nickname;
	Long quotacurrent;
	Long quotalimit;
	String thumbnail;
	String user;
		
	//album
	Access access;
	Integer bytesUsed;
	String location;
	Integer numphotos;
	Integer numphotosremaining;
	
	//photo
	String checksum;
	Integer commentCount;
	Long height;
	Integer rotation;
	Long size;
	Long timestamp;
	VideoStatus videostatus;
	Long width;
	
	//search
	String albumtitle;
	String albumdesc;
	String snippet;
	String snippettype;
	Boolean truncated;
	
	//comments
	String photoid;
	
	//tag
	Integer weight;
	
	
	
	public GphotoModuleImpl() {
		super(GphotoModule.class, GphotoModule.URI);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getInterface() {
		return GphotoModule.class;
	}

	@Override
	public void copyFrom(Object obj) {
		GphotoModule module = (GphotoModule) obj;
		setId(module.getId());
		setLocation(module.getLocation());
		setAccess(module.getAccess());
		setTimestamp(module.getTimestamp());
		setNumphotos(module.getNumphotos());
		setNumphotosremaining(module.getNumphotosremaining());
		setBytesUsed(module.getBytesUsed());
		setUser(module.getUser());
		setNickname(module.getNickname());
	}

	public String getAlbumId() {
		return albumId;
	}

	public String getId() {
		return id;
	}

	public Integer getMaxPhotosPerAlbum() {
		return maxPhotosPerAlbum;
	}

	public String getNickname() {
		return nickname;
	}

	public Long getQuotacurrent() {
		return quotacurrent;
	}

	public Long getQuotalimit() {
		return quotalimit;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getUser() {
		return user;
	}

	public Access getAccess() {
		return access;
	}

	public Integer getBytesUsed() {
		return bytesUsed;
	}

	public String getLocation() {
		return location;
	}

	public Integer getNumphotos() {
		return numphotos;
	}

	public Integer getNumphotosremaining() {
		return numphotosremaining;
	}

	public String getChecksum() {
		return checksum;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public Long getHeight() {
		return height;
	}

	public Integer getRotation() {
		return rotation;
	}

	public Long getSize() {
		return size;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public VideoStatus getVideostatus() {
		return videostatus;
	}

	public Long getWidth() {
		return width;
	}

	public String getAlbumtitle() {
		return albumtitle;
	}

	public String getAlbumdesc() {
		return albumdesc;
	}

	public String getSnippet() {
		return snippet;
	}

	public String getSnippettype() {
		return snippettype;
	}

	public Boolean getTruncated() {
		return truncated;
	}

	public String getPhotoid() {
		return photoid;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMaxPhotosPerAlbum(Integer maxPhotosPerAlbum) {
		this.maxPhotosPerAlbum = maxPhotosPerAlbum;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setQuotacurrent(Long quotacurrent) {
		this.quotacurrent = quotacurrent;
	}

	public void setQuotalimit(Long quotalimit) {
		this.quotalimit = quotalimit;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

	public void setBytesUsed(Integer bytesUsed) {
		this.bytesUsed = bytesUsed;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setNumphotos(Integer numphotos) {
		this.numphotos = numphotos;
	}

	public void setNumphotosremaining(Integer numphotosremaining) {
		this.numphotosremaining = numphotosremaining;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public void setRotation(Integer rotation) {
		this.rotation = rotation;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public void setVideostatus(VideoStatus videostatus) {
		this.videostatus = videostatus;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public void setAlbumtitle(String albumtitle) {
		this.albumtitle = albumtitle;
	}

	public void setAlbumdesc(String albumdesc) {
		this.albumdesc = albumdesc;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public void setSnippettype(String snippettype) {
		this.snippettype = snippettype;
	}

	public void setTruncated(Boolean truncated) {
		this.truncated = truncated;
	}

	public void setPhotoid(String photoid) {
		this.photoid = photoid;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	
}
