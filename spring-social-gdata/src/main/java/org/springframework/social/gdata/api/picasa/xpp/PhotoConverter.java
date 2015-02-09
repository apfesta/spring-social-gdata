package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;
import java.util.Date;

import org.springframework.social.gdata.api.picasa.AlbumFeed;
import org.springframework.social.gdata.api.picasa.Photo;
import org.springframework.social.gdata.api.picasa.PhotoFeed;
import org.xmlpull.v1.XmlSerializer;

public class PhotoConverter extends BaseAtomConverter<PhotoFeed, Photo> {
	
	public PhotoConverter() {
		super();
		this.parser = new PhotoParser();
		this.serializer = new PhotoSerializer();
	}

	class PhotoParser extends BaseAtomParser {

		@Override
		protected PhotoFeed createFeed() {
			return new PhotoFeed();
		}

		@Override
		protected Photo createEntry() {
			return new Photo();
		}
		
		
		
		@Override
		protected void onFeedAttribute(PhotoFeed feed, String ns, String name,
				String value) {
			super.onFeedAttribute(feed, ns, name, value);
			if (ns.equals(NS_GPHOTO)) {
				if (name.equals("user")) feed.setGphotoUser(value);
				else if (name.equals("nickname")) feed.setGphotoNickname(value);
			}
		}

		@Override
		protected void onEntryAttribute(Photo entry, String namespace, String name, String value) {
			super.onEntryAttribute(entry, namespace, name, value);

			if (namespace.equals(NS_GPHOTO)) {
				if (name.equals("height")) entry.setHeight(Long.parseLong(value));
		    	else if (name.equals("width")) entry.setWidth(Long.parseLong(value));
		    	else if (name.equals("timestamp")) entry.setTimestamp(new Date(Long.parseLong(value)));
		    	else if (name.equals("size")) entry.setSize(Long.parseLong(value));
			} 
		}
		
	}
	
	class PhotoSerializer extends BaseAtomSerializer {
		
		@Override
		protected void serializeCategories(PhotoFeed feed, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException,
				IOException {
			cs.serialize(new Category(CATEGORY_SCHEME_KIND,
					"http://schemas.google.com/photos/2007#album"), xs);
		}

		@Override
		protected void serializeCategories(Photo entry, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException, IOException {
			cs.serialize(new Category(CATEGORY_SCHEME_KIND,
					"http://schemas.google.com/photos/2007#photo"), xs);
		}

		@Override
		protected void serializeAttributes(Photo entry, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException, IOException {
			super.serializeAttributes(entry, xs);
			
			if (entry.getHeight()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "height", entry.getHeight().toString()), xs);
			}
			if (entry.getWidth()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "width", entry.getWidth().toString()), xs);
			}
			if (entry.getSize()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "size", entry.getSize().toString()), xs);
			}
			if (entry.getTimestamp()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "timestamp", String.valueOf(entry.getTimestamp().getTime())), xs);
			}
		}
	}

}
