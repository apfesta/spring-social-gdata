package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;
import java.util.Date;

import org.springframework.social.gdata.api.picasa.Access;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.AlbumFeed;
import org.xmlpull.v1.XmlSerializer;

public class AlbumConverter extends BaseAtomConverter<AlbumFeed, Album> {
	
	public AlbumConverter() {
		super();
		this.parser = new AlbumParser();
		this.serializer = new AlbumSerializer();
	}

	class AlbumParser extends BaseAtomParser {

		@Override
		protected AlbumFeed createFeed() {
			return new AlbumFeed();
		}

		@Override
		protected Album createEntry() {
			return new Album();
		}
		
		@Override
		protected void onFeedAttribute(AlbumFeed feed, String ns, String name,
				String value) {
			super.onFeedAttribute(feed, ns, name, value);
			if (ns.equals(NS_GPHOTO)) {
				if (name.equals("user")) feed.setGphotoUser(value);
				else if (name.equals("nickname")) feed.setGphotoNickname(value);
				else if (name.equals("thumbnail")) feed.setGphotoThumbnail(value!=null?value.trim():value);
			}
		}

		@Override
		protected void onEntryAttribute(Album entry, String namespace, String name, String value) {
			super.onEntryAttribute(entry, namespace, name, value);
			if (namespace.equals(NS_GPHOTO)) {
				if (name.equals("numphotos")) entry.setNumOfPhotos(Integer.parseInt(value));
		    	else if (name.equals("location")) entry.setLocation(value);
		    	else if (name.equals("timestamp")) entry.setTimestamp(new Date(Long.parseLong(value)));
		    	else if (name.equals("access")) entry.setAccess(Access.fromValue(value));
			} else if (namespace.equals(NS_MEDIA)) {
				if (name.equals("keywords")) entry.setKeywords(value.split(","));
			}
		}
		
	}
	
	class AlbumSerializer extends BaseAtomSerializer {
		
		@Override
		protected void serializeCategories(AlbumFeed feed, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException,
				IOException {
			cs.serialize(new Category(CATEGORY_SCHEME_KIND,
					"http://schemas.google.com/photos/2007#user"), xs);
		}

		@Override
		protected void serializeCategories(Album entry, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException, IOException {
			cs.serialize(new Category(CATEGORY_SCHEME_KIND,
					"http://schemas.google.com/photos/2007#album"), xs);
		}

		@Override
		protected void serializeAttributes(Album entry, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException, IOException {
			super.serializeAttributes(entry, xs);
			
			//optional fields
			if (entry.getNumOfPhotos()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "numphotos", 
						entry.getNumOfPhotos()!=null ? entry.getNumOfPhotos().toString() : ""), xs);
			}
			if (entry.getLocation()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "location", entry.getLocation()), xs);
			}
			if (entry.getTimestamp()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "timestamp", String.valueOf(entry.getTimestamp().getTime())), xs);
			}
			if (entry.getAccess()!=null) {
				as.serialize(new Attribute(NS_GPHOTO, "access", entry.getAccess().value()), xs);
			}
		}
	}

}
