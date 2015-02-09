/**
 * 
 */
package org.springframework.social.gdata.api.picasa;

/**
 * @author apfesta
 *
 */
public class AlbumFeed extends BaseFeed<Album> {
	
	private String gphotoUser;
	private String gphotoNickname;
	private String gphotoThumbnail;
	
	public String getGphotoUser() {
		return gphotoUser;
	}

	public void setGphotoUser(String gphotoUser) {
		this.gphotoUser = gphotoUser;
	}

	public String getGphotoNickname() {
		return gphotoNickname;
	}

	public void setGphotoNickname(String gphotoNickname) {
		this.gphotoNickname = gphotoNickname;
	}

	public String getGphotoThumbnail() {
		return gphotoThumbnail;
	}

	public void setGphotoThumbnail(String gphotoThumbnail) {
		this.gphotoThumbnail = gphotoThumbnail;
	}

}
