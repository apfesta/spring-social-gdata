/**
 * 
 */
package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.social.gdata.api.picasa.Access;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.AlbumFeed;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author apfesta
 *
 */
public class AlbumConverterTest {
	
	@Test
	public void testParseFeed() throws IOException, XmlPullParserException {
		AlbumConverter converter = new AlbumConverter();
		
		HttpInputMessage inputMessage = 
				new MockHttpInputMessage(atomResource("userfeed").getInputStream());
		
		AlbumFeed feed = converter.parseFeed(inputMessage);
		System.out.println("Feed title:"+feed.getTitle());
		System.out.println("Feed user:"+feed.getGphotoUser());
		System.out.println("Feed nickname:"+feed.getGphotoNickname());
		System.out.println("Feed thumbnail:"+feed.getGphotoThumbnail());
		for (Album result: feed.getEntries()) {
			System.out.println("Etag:"+result.getEtag());
			System.out.println("URI:"+result.getId());
			System.out.println("Album ID:"+result.getGphotoId());
			System.out.println("Title:"+result.getTitle());
			System.out.println("Summary:"+result.getSummary());
			System.out.println("Published Date:"+result.getPublishedDate());
			System.out.println("Updated Date:"+result.getUpdatedDate());
			System.out.println("Timestamp:"+result.getTimestamp());
			System.out.println("Location:"+result.getLocation());
			System.out.println("Access:"+result.getAccess());
			System.out.println("Edit URL:"+result.getEditUrl());	
			System.out.println("Media URL:"+result.getMedia().getContent().getUrl());
			System.out.println("Media Type:"+result.getMedia().getContent().getType());
			System.out.println("Thumbnail URL:"+result.getMedia().getThumbnail().getUrl());
			System.out.println("Media Credit:"+result.getMedia().getCredit());
		}
	}
	
	@Test
	public void testParseEntry() throws IOException, XmlPullParserException {
		AlbumConverter converter = new AlbumConverter();
		
		HttpInputMessage inputMessage = 
				new MockHttpInputMessage(atomResource("createalbum").getInputStream());
		
		Album result = converter.parseEntry(inputMessage);
		
		System.out.println("Title:"+result.getTitle());
		System.out.println("Summary:"+result.getSummary());
		System.out.println("Timestamp:"+result.getTimestamp());
		System.out.println("Location:"+result.getLocation());
		System.out.println("Access:"+result.getAccess());
	}
	
	@Test
	public void testSerializeFeed() throws XmlPullParserException, HttpMessageNotWritableException, IOException {
		AlbumConverter converter = new AlbumConverter();
		
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		
		AlbumFeed feed = new AlbumFeed();
		feed.setEntries(new ArrayList<Album>());
		
		Album album = new Album();
		album.setId("12345");
		album.setEtag("W/AbCdEfG");
		album.setTitle("Trip To Italy");
		album.setSummary("This was the recent trip I took to Italy.");
		album.setLocation("Italy");
		album.setAccess(Access.PUBLIC);
		album.setTimestamp(new Date(1152255600000L));
		album.setKeywords(new String[]{"italy", "vacation"});
		album.setEditUrl("https://picasaweb.google.com/data/entry/api/user/default/albumid/12345/1236820754876000");
		feed.getEntries().add(album);
		
		converter.serializeFeed(outputMessage, feed);
		
		System.out.println(outputMessage.getBodyAsString());
	}
	
	@Test
	public void testSerializeEntry() throws XmlPullParserException, HttpMessageNotWritableException, IOException {
		AlbumConverter converter = new AlbumConverter();
		
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		
		Album album = new Album();
		album.setId("12345");
		album.setEtag("W/AbCdEfG");
		album.setTitle("Trip To Italy");
		album.setSummary("This was the recent trip I took to Italy.");
		album.setLocation("Italy");
		album.setAccess(Access.PUBLIC);
		album.setTimestamp(new Date(1152255600000L));
		album.setKeywords(new String[]{"italy", "vacation"});
		album.setEditUrl("https://picasaweb.google.com/data/entry/api/user/default/albumid/12345/1236820754876000");
				
		converter.serializeEntry(outputMessage, album);
		
		System.out.println(outputMessage.getBodyAsString());
	}
	
	protected Resource atomResource(String filename) {
		return new ClassPathResource(filename + ".xml", getClass());
	}

}
