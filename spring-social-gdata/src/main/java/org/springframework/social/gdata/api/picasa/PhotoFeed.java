package org.springframework.social.gdata.api.picasa;

public class PhotoFeed extends BaseFeed<Photo> {
	
	private String gphotoUser;
	private String gphotoNickname;
	
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

}
