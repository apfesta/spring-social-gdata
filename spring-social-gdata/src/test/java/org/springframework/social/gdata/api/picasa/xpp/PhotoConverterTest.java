/**
 * 
 */
package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.social.gdata.api.picasa.Photo;
import org.springframework.social.gdata.api.picasa.PhotoFeed;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author apfesta
 *
 */
public class PhotoConverterTest {
	
	@Test
	public void testParseFeed() throws IOException, XmlPullParserException {
		PhotoConverter converter = new PhotoConverter();
		
		HttpInputMessage inputMessage = 
				new MockHttpInputMessage(atomResource("albumfeed").getInputStream());
		
		PhotoFeed feed = converter.parseFeed(inputMessage);
		for (Photo result: feed.getEntries()) {
			System.out.println("Etag:"+result.getEtag());
			System.out.println("URI:"+result.getId());
			System.out.println("Photo ID:"+result.getGphotoId());
			System.out.println("Title:"+result.getTitle());
			System.out.println("Summary:"+result.getSummary());
			System.out.println("Published Date:"+result.getPublishedDate());
			System.out.println("Updated Date:"+result.getUpdatedDate());
			System.out.println("Timestamp:"+result.getTimestamp());
			System.out.println("Height:"+result.getHeight());
			System.out.println("Width:"+result.getWidth());
			System.out.println("Size:"+result.getSize());
			System.out.println("Edit URL:"+result.getEditUrl());		
		}
	}
	
	@Test
	public void testParseEntry() throws IOException, XmlPullParserException {
		PhotoConverter converter = new PhotoConverter();
		
		HttpInputMessage inputMessage = 
				new MockHttpInputMessage(atomResource("createphoto").getInputStream());
		
		Photo result = converter.parseEntry(inputMessage);
		System.out.println("Title:"+result.getTitle());
		System.out.println("Summary:"+result.getSummary());		
	}
	
	@Test
	public void testSerializeFeed() throws XmlPullParserException, HttpMessageNotWritableException, IOException {
		PhotoConverter converter = new PhotoConverter();
		
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		
		PhotoFeed feed = new PhotoFeed();
		feed.setEntries(new ArrayList<Photo>());
		
		Photo photo = new Photo();
		photo.setTitle("plz-to-love-realcat.jpg");
		photo.setSummary("Real cat wants attention too.");
		feed.getEntries().add(photo);
		
		converter.serializeFeed(outputMessage, feed);
		
		System.out.println(outputMessage.getBodyAsString());
	}
	
	@Test
	public void testSerializeEntry() throws XmlPullParserException, HttpMessageNotWritableException, IOException {
		PhotoConverter converter = new PhotoConverter();
		
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
				
		Photo photo = new Photo();
		photo.setTitle("plz-to-love-realcat.jpg");
		photo.setSummary("Real cat wants attention too.");
				
		converter.serializeEntry(outputMessage, photo);
		
		System.out.println(outputMessage.getBodyAsString());
	}
	
	protected Resource atomResource(String filename) {
		return new ClassPathResource(filename + ".xml", getClass());
	}

}
