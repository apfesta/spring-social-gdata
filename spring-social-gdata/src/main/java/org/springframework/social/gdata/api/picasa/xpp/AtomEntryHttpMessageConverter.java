package org.springframework.social.gdata.api.picasa.xpp;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.social.gdata.api.picasa.Album;
import org.springframework.social.gdata.api.picasa.BaseEntry;
import org.springframework.social.gdata.api.picasa.Photo;
import org.xmlpull.v1.XmlPullParserException;

/**
 * HttpMessageConverter implementation based off of XML Pull Parser
 * 
 * @author apfesta
 *
 * @param <F>
 * @param <E>
 */
public class AtomEntryHttpMessageConverter
		extends AbstractHttpMessageConverter<BaseEntry> {

	AlbumConverter albumConverter;
	PhotoConverter photoConverter;
	
	public AtomEntryHttpMessageConverter() {
		super(MediaType.APPLICATION_ATOM_XML);
		this.albumConverter = new AlbumConverter();
		this.photoConverter = new PhotoConverter();
	}

	@Override
	protected BaseEntry readInternal(
			Class<? extends BaseEntry> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		try {
			if (Album.class.isAssignableFrom(clazz)) {
				return albumConverter.parseEntry(inputMessage);
			} else if (Photo.class.isAssignableFrom(clazz)) {
				return photoConverter.parseEntry(inputMessage);
			} 
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}

	@Override
	protected void writeInternal(BaseEntry t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		try {
			if (t instanceof Album) {
				albumConverter.serializeEntry(outputMessage, (Album) t);
			} else if (t instanceof Photo) {
				photoConverter.serializeEntry(outputMessage, (Photo) t);
			} 
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return (Album.class.isAssignableFrom(clazz) || 
				Photo.class.isAssignableFrom(clazz));
	}
	
}
