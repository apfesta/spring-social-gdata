package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;

import org.springframework.social.gdata.api.picasa.Comment;
import org.springframework.social.gdata.api.picasa.CommentFeed;
import org.xmlpull.v1.XmlSerializer;

public class CommentConverter extends BaseAtomConverter<CommentFeed, Comment> {

	public CommentConverter() {
		super();
		this.parser = new CommentParser();
		this.serializer = new CommentSerializer();
	}
	
	class CommentParser extends BaseAtomParser {

		@Override
		protected CommentFeed createFeed() {
			return new CommentFeed();
		}

		@Override
		protected Comment createEntry() {
			return new Comment();
		}
		
		@Override
		protected void onFeedAttribute(CommentFeed feed, String ns, String name,
				String value) {
			super.onFeedAttribute(feed, ns, name, value);
			if (ns.equals(NS_GPHOTO)) {
				if (name.equals("user")) feed.setGphotoUser(value);
				else if (name.equals("nickname")) feed.setGphotoNickname(value);
				else if (name.equals("thumbnail")) feed.setGphotoThumbnail(value!=null?value.trim():value);
			}
		}

		@Override
		protected void onEntryAttribute(Comment entry, String namespace, String name, String value) {
			super.onEntryAttribute(entry, namespace, name, value);
		}
		
	}
	
	class CommentSerializer extends BaseAtomSerializer {
		
		@Override
		protected void serializeCategories(CommentFeed feed, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException,
				IOException {
			cs.serialize(new Category(CATEGORY_SCHEME_KIND,
					"http://schemas.google.com/photos/2007#user"), xs);
		}

		@Override
		protected void serializeCategories(Comment entry, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException, IOException {
			cs.serialize(new Category(CATEGORY_SCHEME_KIND,
					"http://schemas.google.com/photos/2007#comment"), xs);
		}

		@Override
		protected void serializeAttributes(Comment entry, XmlSerializer xs)
				throws IllegalArgumentException, IllegalStateException, IOException {
			super.serializeAttributes(entry, xs);
		}

		@Override
		protected boolean serializeMediaNamespace() {return false;}

		@Override
		protected boolean serializeGphotoNamespace() {return false;}

		@Override
		protected boolean serializeGdNamespace() {return false;}
	}
	
}
