/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.http.converter.feed;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.jdom.JDOMException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.ParsingFeedException;
import com.sun.syndication.io.impl.Atom10Generator;
import com.sun.syndication.io.impl.Atom10Parser;

/**
 * Abstract base class for Atom and RSS Feed message converters, using the
 * <a href="http://rometools.org/">ROME tools</a> project.
 *
 * @author Arjen Poutsma
 * @since 3.0.2
 * @see AtomFeedHttpMessageConverter
 * @see RssChannelHttpMessageConverter
 */
public class AtomEntryHttpMessageConverter extends AbstractHttpMessageConverter<Entry> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public AtomEntryHttpMessageConverter() {
		super(new MediaType("application", "atom+xml"));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return Entry.class.isAssignableFrom(clazz);
	}

	@Override
	protected Entry readInternal(Class<? extends Entry> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		MediaType contentType = inputMessage.getHeaders().getContentType();
		Charset charset;
		if (contentType != null && contentType.getCharSet() != null) {
			charset = contentType.getCharSet();
		} else {
			charset = DEFAULT_CHARSET;
		}
		try {
			Reader reader = new InputStreamReader(inputMessage.getBody(), charset);
			return build(reader);
		}
		catch (FeedException ex) {
			throw new HttpMessageNotReadableException("Could not read WireFeed: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected void writeInternal(Entry entry, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		String wireFeedEncoding = DEFAULT_CHARSET.name();

		MediaType contentType = outputMessage.getHeaders().getContentType();
		if (contentType != null) {
			Charset wireFeedCharset = Charset.forName(wireFeedEncoding);
			contentType = new MediaType(contentType.getType(), contentType.getSubtype(), wireFeedCharset);
			outputMessage.getHeaders().setContentType(contentType);
		}
		
		try {
			Writer writer = new OutputStreamWriter(outputMessage.getBody(), wireFeedEncoding);
			output(entry, writer);
		}
		catch (FeedException ex) {
			throw new HttpMessageNotWritableException("Could not write WiredFeed: " + ex.getMessage(), ex);
		}
		
	}
	
	protected Entry build(Reader reader) throws IllegalArgumentException,FeedException {
    	try {
			return Atom10Parser.parseEntry(reader, null);
		} catch (JDOMException ex) {
			throw new ParsingFeedException("Invalid XML: " + ex.getMessage(), ex);
		} catch (IOException ex) {
			 throw new ParsingFeedException("Invalid XML",ex);
		}
    }
	
	protected void output(Entry entry, Writer writer) throws IllegalArgumentException,IOException, FeedException {
		Atom10Generator.serializeEntry(entry, writer);
	}

}
