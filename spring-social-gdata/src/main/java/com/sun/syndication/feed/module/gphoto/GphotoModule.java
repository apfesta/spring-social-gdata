/**
 * 
 */
package com.sun.syndication.feed.module.gphoto;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.gphoto.types.Access;
import com.sun.syndication.feed.module.gphoto.types.VideoStatus;

/**
 * @author apfesta
 *
 */
public interface GphotoModule extends Module {
	
	public static final String URI = "http://schemas.google.com/photos/2007";
	
	public String getAlbumId();

	public String getId();

	public Integer getMaxPhotosPerAlbum();

	public String getNickname();

	public Long getQuotacurrent();

	public Long getQuotalimit();

	public String getThumbnail();

	public String getUser();

	public Access getAccess();

	public Integer getBytesUsed();

	public String getLocation();

	public Integer getNumphotos();

	public Integer getNumphotosremaining();

	public String getChecksum();

	public Integer getCommentCount();

	public Long getHeight();

	public Integer getRotation();

	public Long getSize();

	public Long getTimestamp();

	public VideoStatus getVideostatus();

	public Long getWidth();

	public String getAlbumtitle();

	public String getAlbumdesc();

	public String getSnippet();

	public String getSnippettype();

	public Boolean getTruncated();

	public String getPhotoid();

	public Integer getWeight();

	public void setAlbumId(String albumId);

	public void setId(String id);

	public void setMaxPhotosPerAlbum(Integer maxPhotosPerAlbum);

	public void setNickname(String nickname);

	public void setQuotacurrent(Long quotacurrent);

	public void setQuotalimit(Long quotalimit);

	public void setThumbnail(String thumbnail);

	public void setUser(String user);

	public void setAccess(Access access);

	public void setBytesUsed(Integer bytesUsed);
	
	public void setLocation(String location);

	public void setNumphotos(Integer numphotos);

	public void setNumphotosremaining(Integer numphotosremaining);

	public void setChecksum(String checksum);

	public void setCommentCount(Integer commentCount);

	public void setHeight(Long height);

	public void setRotation(Integer rotation);

	public void setSize(Long size);

	public void setTimestamp(Long timestamp);

	public void setVideostatus(VideoStatus videostatus);

	public void setWidth(Long width);

	public void setAlbumtitle(String albumtitle);

	public void setAlbumdesc(String albumdesc);

	public void setSnippet(String snippet);

	public void setSnippettype(String snippettype);

	public void setTruncated(Boolean truncated);

	public void setPhotoid(String photoid);

	public void setWeight(Integer weight);

}
