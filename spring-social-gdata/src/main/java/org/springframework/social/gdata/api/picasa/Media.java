package org.springframework.social.gdata.api.picasa;

public class Media {
	String title;
	Content content;
	Thumbnail thumbnail;
	String credit;
	
	public class Content {
		String url;
		String type;
		Medium medium;
		int height;
		int width;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Medium getMedium() {
			return medium;
		}
		public void setMedium(Medium medium) {
			this.medium = medium;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		
	}
	
	public class Thumbnail {
		String url;
		int height;
		int width;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		
	}
	
	public enum Medium {
		IMAGE("image"),
		VIDEO("video");
		
		private String value;

		private Medium(String value) {
			this.value = value;
		}
		
		public String value() {
			return value;
		}
		
		public static Medium fromValue(String value) {
			if (value.equals(IMAGE.value)) {
				return IMAGE;
			}
			if (value.equals(VIDEO.value)) {
				return VIDEO;
			}
			throw new IllegalArgumentException(value+" is not a valid medium type");
		}
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	
}
