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
import org.springframework.social.gdata.api.picasa.Comment;
import org.springframework.social.gdata.api.picasa.CommentFeed;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author apfesta
 *
 */
public class CommentConverterTest {
	
	@Test
	public void testParseFeed() throws IOException, XmlPullParserException {
		CommentConverter converter = new CommentConverter();
		
		HttpInputMessage inputMessage = 
				new MockHttpInputMessage(atomResource("commentfeed").getInputStream());
		
		CommentFeed feed = converter.parseFeed(inputMessage);
		System.out.println("Feed title:"+feed.getTitle());
		System.out.println("Feed user:"+feed.getGphotoUser());
		System.out.println("Feed nickname:"+feed.getGphotoNickname());
		System.out.println("Feed Author Name:"+feed.getAuthor().getName());
		System.out.println("Feed Author URI:"+feed.getAuthor().getUri());
		for (Comment result: feed.getEntries()) {
			System.out.println("Etag:"+result.getEtag());
			System.out.println("URI:"+result.getId());
			System.out.println("Photo ID:"+result.getGphotoId());
			System.out.println("Title:"+result.getTitle());
			System.out.println("Summary:"+result.getSummary());
			System.out.println("Content:"+result.getContent());
			System.out.println("Published Date:"+result.getPublishedDate());
			System.out.println("Updated Date:"+result.getUpdatedDate());
			System.out.println("Edit URL:"+result.getEditUrl());	
		}
	}
	
	@Test
	public void testParseEntry() throws IOException, XmlPullParserException {
		CommentConverter converter = new CommentConverter();
		
		HttpInputMessage inputMessage = 
				new MockHttpInputMessage(atomResource("createcomment").getInputStream());
		
		Comment result = converter.parseEntry(inputMessage);
		System.out.println("Content:"+result.getContent());		
	}
	
	@Test
	public void testSerializeFeed() throws XmlPullParserException, HttpMessageNotWritableException, IOException {
		CommentConverter converter = new CommentConverter();
		
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		
		CommentFeed feed = new CommentFeed();
		feed.setEntries(new ArrayList<Comment>());
		
		Comment comment = new Comment();
		comment.setContent("great photo!");
		feed.getEntries().add(comment);
		
		converter.serializeFeed(outputMessage, feed);
		
		System.out.println(outputMessage.getBodyAsString());
	}
	
	@Test
	public void testSerializeEntry() throws XmlPullParserException, HttpMessageNotWritableException, IOException {
		CommentConverter converter = new CommentConverter();
		
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
				
		Comment comment = new Comment();
		comment.setContent("great photo!");
				
		converter.serializeEntry(outputMessage, comment);
		
		System.out.println(outputMessage.getBodyAsString());
	}
	
	protected Resource atomResource(String filename) {
		return new ClassPathResource(filename + ".xml", getClass());
	}

}
