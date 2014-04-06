package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.social.gdata.api.picasa.AlbumFeed;
import org.springframework.social.gdata.api.picasa.BaseFeed;
import org.springframework.social.gdata.api.picasa.PhotoFeed;
import org.xmlpull.v1.XmlPullParserException;

/**
 * HttpMessageConverter implementation based off of XML Pull Parser
 * 
 * @author apfesta
 *
 * @param <F>
 * @param <E>
 */
public class AtomFeedHttpMessageConverter
		extends AbstractHttpMessageConverter<BaseFeed<?>> {

	AlbumConverter albumConverter;
	PhotoConverter photoConverter;
	
	public AtomFeedHttpMessageConverter() {
		super(MediaType.APPLICATION_ATOM_XML);
		this.albumConverter = new AlbumConverter();
		this.photoConverter = new PhotoConverter();
	}

	@Override
	protected BaseFeed<?> readInternal(
			Class<? extends BaseFeed<?>> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		try {
			if (AlbumFeed.class.isAssignableFrom(clazz)) {
				return albumConverter.parseFeed(inputMessage);
			} else if (PhotoFeed.class.isAssignableFrom(clazz)) {
				return photoConverter.parseFeed(inputMessage);
			} 
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}

	@Override
	protected void writeInternal(BaseFeed<?> t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		try {
			if (t instanceof AlbumFeed) {
				albumConverter.serializeFeed(outputMessage, (AlbumFeed) t);
			} else if (t instanceof PhotoFeed) {
				photoConverter.serializeFeed(outputMessage, (PhotoFeed) t);
			} 
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return (AlbumFeed.class.isAssignableFrom(clazz) || 
				PhotoFeed.class.isAssignableFrom(clazz));
	}
	
}
