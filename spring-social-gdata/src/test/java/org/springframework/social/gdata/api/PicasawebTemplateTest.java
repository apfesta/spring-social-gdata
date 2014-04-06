package org.springframework.social.gdata.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_ATOM_XML;
import static org.springframework.http.MediaType.APPLICATION_ATOM_XML_VALUE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.social.gdata.api.picasa.Access;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.Photo;

public class PicasawebTemplateTest extends AbstractGdataTest {

	@Test
	public void getAlbumsByUserId() {
		mockServer
			.expect(requestTo("https://picasaweb.google.com/data/feed/api/user/12345?kind=album"))
			.andExpect(method(GET))
			.andRespond(withSuccess(atomResource("userfeed"), APPLICATION_ATOM_XML));
		
		List<Album> albums = gdata.picasawebOperations().getAlbums("12345");
		assertAlbums(albums);
	}
	
	@Test
	public void getPhotosByUserIdAlbumId() {
		mockServer
			.expect(requestTo("https://picasaweb.google.com/data/feed/api/user/12345/albumid/54321?kind=photo"))
			.andExpect(method(GET))
			.andRespond(withSuccess(atomResource("albumfeed"), APPLICATION_ATOM_XML));
		
		List<Photo> photos = gdata.picasawebOperations().getPhotos("12345", "54321");
		assertPhotos(photos);
	}
	
	@Test
	public void addAlbumByUserId() {
		mockServer
		.expect(requestTo("https://picasaweb.google.com/data/feed/api/user/12345"))
		.andExpect(method(POST))
		.andExpect(content().contentType(APPLICATION_ATOM_XML_VALUE))
		.andRespond(withSuccess(atomResource("createalbum"), APPLICATION_ATOM_XML));
		
		Album album = new Album();
		album.setTitle("Trip To Italy");
		album.setSummary("This was the recent trip I took to Italy.");
		album.setLocation("Italy");
		album.setAccess(Access.PUBLIC);
		album.setTimestamp(new Date(1152255600000L));
		album.setKeywords(new String[]{"italy", "vacation"});
		
		album = gdata.picasawebOperations().addAlbum("12345", album);
		
		assertEquals("Trip To Italy", album.getTitle());
		assertEquals("This was the recent trip I took to Italy.", album.getSummary());
		assertEquals("Italy", album.getLocation());
		assertEquals(Access.PUBLIC, album.getAccess());
		assertEquals(new Date(1152255600000L), album.getTimestamp());
	}
	
	@Test
	public void addPhotosByUserIdAlbumId() throws IOException, URISyntaxException {		
		mockServer
		.expect(requestTo("https://picasaweb.google.com/data/feed/api/user/12345/albumid/54321"))
		.andExpect(method(POST))
		.andRespond(withSuccess(atomResource("createphoto"), APPLICATION_ATOM_XML));
		
		Photo photo = new Photo();
		photo.setTitle("plz-to-love-realcat.jpg");
		photo.setSummary("Real cat wants attention too.");
		
		Resource resource = new ByteArrayResource("test content".getBytes());		
				
		photo = gdata.picasawebOperations().addPhoto("12345", "54321", photo, 
				resource, MediaType.IMAGE_JPEG);
		
		assertEquals("plz-to-love-realcat.jpg", photo.getTitle());
		assertEquals("Real cat wants attention too.", photo.getSummary());
	}
	
	private void assertAlbums(List<Album> albums) {
		assertNotNull(albums);
		Album album = albums.get(0);
		assertNotNull(album);
		assertEquals("albumID",album.getGphotoId());
		assertEquals("lolcats",album.getTitle());
		assertEquals("Hilarious Felines",album.getSummary());
		assertEquals(1, album.getNumOfPhotos().intValue());
		assertEquals("https://picasaweb.google.com/data/entry/api/user/liz/albumid/albumID",album.getId());
	}
	
	private void assertPhotos(List<Photo> photos) {
		assertNotNull(photos);
		Photo photo = photos.get(0);
		assertEquals("photoID",photo.getGphotoId());
		assertEquals("invisible_bike.jpg",photo.getTitle());
		assertEquals("Bike",photo.getSummary());
		assertEquals(410, photo.getWidth().intValue());
		assertEquals(295, photo.getHeight().intValue());
		assertEquals(23044, photo.getSize().intValue());
	}
	
}
