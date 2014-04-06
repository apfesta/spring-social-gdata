package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.social.gdata.api.picasa.BaseEntry;
import org.springframework.social.gdata.api.picasa.BaseFeed;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class BaseAtomConverter<F extends BaseFeed<E>, E extends BaseEntry> {

	protected static final String NS_MEDIA= "http://search.yahoo.com/mrss/";
	protected static final String NS_GPHOTO= "http://schemas.google.com/photos/2007";
	protected static final String NS_GD= "http://schemas.google.com/g/2005";
	protected static final String NS = "http://www.w3.org/2005/Atom";
	protected static final String CATEGORY_SCHEME_KIND = "http://schemas.google.com/g/2005#kind";
	
	protected XmlPullParserFactory factory;
	
	protected BaseAtomParser parser;
	protected BaseAtomSerializer serializer;
	
	
	public BaseAtomConverter() {
		super();
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
		} catch (XmlPullParserException e) {
			throw new RuntimeException("Unable to load XmlPullParserFactory.",e);
		}
		
	}

	public F parseFeed(HttpInputMessage inputMessage) throws XmlPullParserException, IOException {
		return parser.doXmlPullParseFeed(inputMessage);
	}
	
	public E parseEntry(HttpInputMessage inputMessage) throws XmlPullParserException, IOException {
		return parser.doXmlPullParseEntry(inputMessage);
	}
	
	public void serializeFeed(HttpOutputMessage outputMessage, F feed) throws IllegalArgumentException, IllegalStateException, XmlPullParserException, IOException {
		serializer.serializeFeed(outputMessage, feed);
	}
	
	public void serializeEntry(HttpOutputMessage outputMessage, E entry)
			throws XmlPullParserException, IllegalArgumentException,
			IllegalStateException, IOException {
		serializer.serializeEntry(outputMessage, entry);
	}

	abstract class BaseAtomParser {
						
		protected abstract F createFeed();

		protected abstract E createEntry();
		
		@SuppressWarnings("unchecked")
		public F doXmlPullParseFeed(HttpInputMessage inputMessage)
				throws XmlPullParserException, IOException {
			return (F) this.doXmlPullParse(inputMessage);
		}
		
		@SuppressWarnings("unchecked")
		public E doXmlPullParseEntry(HttpInputMessage inputMessage)
				throws XmlPullParserException, IOException {
			return (E) this.doXmlPullParse(inputMessage);
		}
		
		public Object doXmlPullParse(HttpInputMessage inputMessage)
				throws XmlPullParserException, IOException {
		
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new InputStreamReader(inputMessage.getBody()));
			
			Object root=null;
			F feed=null;
			E entry=null;
			Link link=null;
			Category category=null;
			String elementName=null;
			String namespace=null;
			
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					
				} else if (eventType == XmlPullParser.START_TAG) {
					elementName = xpp.getName();
					namespace = xpp.getNamespace();
					if (feed==null && xpp.getName().equals("feed")) {
						feed = createFeed();
						if (root==null) root = feed;
					} else if (entry==null && xpp.getName().equals("entry")) {
						entry = createEntry();
						if (feed!=null) {
							if (feed.getEntries()==null) {
								feed.setEntries(new ArrayList<E>());
							}
							feed.getEntries().add(entry);
						}
						entry.setEtag(xpp.getAttributeValue(xpp.getNamespace("gd"), "etag"));
						if (root==null) root = entry;
					} else if (link==null && xpp.getName().equals("link")) {
						link = new Link();
						link.rel = xpp.getAttributeValue(null,"rel");
						link.type = xpp.getAttributeValue(null,"type");
						link.href = xpp.getAttributeValue(null,"href");
						if (entry!=null) {
							onEntryLink(entry, link);
						}
					} else if (category==null && xpp.getName().equals("category")) {
						category = new Category();
						category.scheme = xpp.getAttributeValue(null,"scheme");
						category.term = xpp.getAttributeValue(null,"term");
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (xpp.getName().equals("feed")) {
		        		feed = null;
		        	}
		        	if (xpp.getName().equals("entry")) {
		        		entry = null;
		        	}
		        	if (xpp.getName().equals("link")) {
		        		link = null;
		        	}
		        	if (xpp.getName().equals("category")) {
		        		link = null;
		        	}
		        	elementName = null;
		        	namespace = null;
				} else if (eventType == XmlPullParser.TEXT) {
		            if (entry!=null && elementName!=null) {
		            	onEntryAttribute(entry, namespace, elementName, xpp.getText());
		            }
				}
				eventType = xpp.next();
			}
			return root;
		}
				
		protected void onEntryLink(E entry, Link link) {
			if (link.rel.equals("edit")) {
				entry.setEditUrl(link.href);
			}
		}
		
		protected void onEntryAttribute(E entry, String ns, String name, String value) {
			if (ns.equals(NS)) {
				if (name.equals("id"))
					entry.setId(value);
				else if (name.equals("title"))
					entry.setTitle(value);
				else if (name.equals("summary"))
					entry.setSummary(value);
				else if (name.equals("published"))
					entry.setPublishedDate(toDate(value));
				else if (name.equals("updated"))
					entry.setUpdatedDate(toDate(value));
			} else if (ns.equals(NS_GPHOTO)) {
				if (ns.equals(NS_GPHOTO) && name.equals("id"))
					entry.setGphotoId(value);
			}
		}
		
		protected Date toDate(String string) {
			return DateTime.parse(string).toDate();
		}
		
	}
	
	public class BaseAtomSerializer {
		
		AttributeSerializer as = new AttributeSerializer();
		CategorySerializer cs = new CategorySerializer();
		LinkSerializer ls = new LinkSerializer();
		EntrySerializer es = new EntrySerializer();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
				
		public void serializeFeed(HttpOutputMessage outputMessage, F feed) throws XmlPullParserException, IllegalArgumentException, IllegalStateException, IOException {
			XmlSerializer xs = factory.newSerializer();
			xs.setOutput(new OutputStreamWriter(outputMessage.getBody()));
					
			xs.startDocument("utf-8", null);
			serializeNamespaces(xs);
			xs.startTag(NS, "feed");
			serializeCategories(feed, xs);
			for (E entry : feed.getEntries()) {
				es.serialize(entry, xs);
			}
			xs.endTag(NS, "feed");
			xs.endDocument();
		}
		
		public void serializeEntry(HttpOutputMessage outputMessage, E entry) throws XmlPullParserException, IllegalArgumentException, IllegalStateException, IOException {
			XmlSerializer xs = factory.newSerializer();
			xs.setOutput(new OutputStreamWriter(outputMessage.getBody()));
					
			xs.startDocument("utf-8", null);
			serializeNamespaces(xs);
			es.serialize(entry, xs);
			xs.endDocument();
		}
		
		protected final void serializeNamespaces(XmlSerializer xs) throws IllegalArgumentException, IllegalStateException, IOException {
			xs.setPrefix("", NS);
			xs.setPrefix("media", NS_MEDIA);
			xs.setPrefix("gphoto", NS_GPHOTO);
			xs.setPrefix("gd", NS_GD);
		}
		
		protected void serializeCategories(F feed, XmlSerializer xs) throws IllegalArgumentException, IllegalStateException, IOException {
		}
		
		protected void serializeCategories(E entry, XmlSerializer xs) throws IllegalArgumentException, IllegalStateException, IOException {
		}
		
		protected void serializeLinks(E entry, XmlSerializer xs) throws IllegalArgumentException, IllegalStateException, IOException {
			if (entry.getEditUrl()!=null) {
				ls.serialize(new Link("edit",MediaType.APPLICATION_ATOM_XML_VALUE,
						entry.getEditUrl()), xs);
			}
		}
		
		protected void serializeAttributes(E entry, XmlSerializer xs) throws IllegalArgumentException, IllegalStateException, IOException {
			//required fields
			as.serialize(new Attribute(NS,"title",entry.getTitle()), xs);
			as.serialize(new Attribute(NS,"summary",entry.getSummary()), xs);
			//optional fields
			if (entry.getId()!=null) {
				as.serialize(new Attribute(NS,"id",entry.getId()), xs);
			}
			if (entry.getPublishedDate()!=null) {
				as.serialize(new Attribute(NS,"published",toString(entry.getPublishedDate())), xs);
			}
			if (entry.getUpdatedDate()!=null) {
				as.serialize(new Attribute(NS,"updated",toString(entry.getUpdatedDate())), xs);
			}
			if (entry.getGphotoId()!=null) {
				as.serialize(new Attribute(NS_GPHOTO,"id",entry.getGphotoId()), xs);
			}		
		}
		
		protected String toString(Date date) {
			return date!=null ? fmt.print(date.getTime()) : "";
		}
			
		
		
		class CategorySerializer implements Serializer<Category> {
			@Override
			public void serialize(Category category, XmlSerializer xs)
					throws IllegalArgumentException, IllegalStateException,
					IOException {
				xs.startTag(NS, "category");
				xs.attribute("", "scheme", category.scheme);
				xs.attribute("", "term", category.term);
				xs.endTag(NS, "category");
			}
		}
		
		class EntrySerializer implements Serializer<E> {

			@Override
			public void serialize(E entry, XmlSerializer xs)
					throws IllegalArgumentException, IllegalStateException,
					IOException {
				xs.startTag(NS, "entry");
				if (entry.getEtag()!=null) xs.attribute(NS_GD, "etag", entry.getEtag());
				serializeCategories(entry, xs);
				serializeLinks(entry, xs);
				serializeAttributes(entry, xs);
				xs.endTag(NS, "entry");
			}
		}
		
		class AttributeSerializer implements Serializer<Attribute> {

			@Override
			public void serialize(Attribute attribute, XmlSerializer xs)
					throws IllegalArgumentException, IllegalStateException,
					IOException {
				xs.startTag(attribute.namespace, attribute.name);
				if (attribute.value!=null) {
					xs.text(attribute.value);
				}
				xs.endTag(attribute.namespace, attribute.name);
			}
		}
		
		class LinkSerializer implements Serializer<Link> {

			@Override
			public void serialize(Link link, XmlSerializer xs)
					throws IllegalArgumentException, IllegalStateException,
					IOException {
				xs.startTag(NS, "link");
				xs.attribute("", "rel", link.rel);
				xs.attribute("", "type", link.type);
				xs.attribute("", "href", link.href);
				xs.endTag(NS, "link");
			}
			
		}

	}
	
	interface Serializer<O> {
		void serialize(O object, XmlSerializer xs) throws IllegalArgumentException, IllegalStateException, IOException;
	}
	
}
