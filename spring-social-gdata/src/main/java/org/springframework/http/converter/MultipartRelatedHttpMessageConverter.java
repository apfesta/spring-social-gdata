package org.springframework.http.converter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

public class MultipartRelatedHttpMessageConverter implements HttpMessageConverter<MultiValueMap<String, ?>>{

	private List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
	
	public static final MediaType MULTIPART_RELATED = MediaType.parseMediaType("multipart/related");
	
	private List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();

	
	public MultipartRelatedHttpMessageConverter() {
		this.supportedMediaTypes.add(MULTIPART_RELATED);
		
		this.partConverters.add(new ByteArrayHttpMessageConverter());
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		this.partConverters.add(stringHttpMessageConverter);
		this.partConverters.add(new ResourceHttpMessageConverter());
	}
	
	/**
	 * Set the message body converters to use. These converters are used to convert objects to MIME parts.
	 */
	public final void setPartConverters(List<HttpMessageConverter<?>> partConverters) {
		Assert.notEmpty(partConverters, "'partConverters' must not be empty");
		this.partConverters = partConverters;
	}

	/**
	 * Add a message body converter. Such a converters is used to convert objects to MIME parts.
	 */
	public final void addPartConverter(HttpMessageConverter<?> partConverter) {
		Assert.notNull(partConverter, "'partConverter' must not be NULL");
		this.partConverters.add(partConverter);
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if (!MultiValueMap.class.isAssignableFrom(clazz)) {
			return false;
		}
		if (mediaType == null || MediaType.ALL.equals(mediaType)) {
			return true;
		}
		for (MediaType supportedMediaType : getSupportedMediaTypes()) {
			if (supportedMediaType.isCompatibleWith(mediaType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Collections.unmodifiableList(this.supportedMediaTypes);
	}

	@Override
	public MultiValueMap<String, ?> read(
			Class<? extends MultiValueMap<String, ?>> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(MultiValueMap<String, ?> map, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		if (!isMultipart(map, contentType)) {
			//writeForm((MultiValueMap<String, String>) map, contentType, outputMessage);
		}
		else {
			writeMultipart((MultiValueMap<String, Object>) map, outputMessage);
		}
	}
	
	private boolean isMultipart(MultiValueMap<String, ?> map, MediaType contentType) {
		if (contentType != null) {
			return MULTIPART_RELATED.equals(contentType);
		}
		return false;
	}
	
	private void writeMultipart(MultiValueMap<String, Object> parts, HttpOutputMessage outputMessage)
			throws IOException {
		byte[] boundary = generateMultipartBoundary();

		Map<String, String> parameters = Collections.singletonMap("boundary", new String(boundary, "US-ASCII"));
		MediaType contentType = new MediaType(MULTIPART_RELATED, parameters);
		outputMessage.getHeaders().setContentType(contentType);

		writeParts(outputMessage.getBody(), parts, boundary);
		writeEnd(boundary, outputMessage.getBody());
	}
	
	private void writeParts(OutputStream os, MultiValueMap<String, Object> parts, byte[] boundary) throws IOException {
		for (Map.Entry<String, List<Object>> entry : parts.entrySet()) {
			String name = entry.getKey();
			for (Object part : entry.getValue()) {
				if (part != null) {
					writeBoundary(boundary, os);
					HttpEntity<?> entity = getEntity(part);
					writePart(name, entity, os);
					writeNewLine(os);
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HttpEntity getEntity(Object part) {
		if (part instanceof HttpEntity) {
			return (HttpEntity) part;
		}
		else {
			return new HttpEntity(part);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void writePart(String name, HttpEntity partEntity, OutputStream os) throws IOException {
		Object partBody = partEntity.getBody();
		Class<?> partType = partBody.getClass();
		HttpHeaders partHeaders = partEntity.getHeaders();
		MediaType partContentType = partHeaders.getContentType();
		for (HttpMessageConverter messageConverter : partConverters) {
			if (messageConverter.canWrite(partType, partContentType)) {
				HttpOutputMessage multipartOutputMessage = new MultipartHttpOutputMessage(os);
				if (!partHeaders.isEmpty()) {
					multipartOutputMessage.getHeaders().putAll(partHeaders);
				}
				messageConverter.write(partBody, partContentType, multipartOutputMessage);
				return;
			}
		}
		throw new HttpMessageNotWritableException(
				"Could not write request: no suitable HttpMessageConverter found for request type [" +
						partType.getName() + "]");
	}

	private void writeBoundary(byte[] boundary, OutputStream os) throws IOException {
		os.write('-');
		os.write('-');
		os.write(boundary);
		writeNewLine(os);
	}
	
	private void writeEnd(byte[] boundary, OutputStream os) throws IOException {
		os.write('-');
		os.write('-');
		os.write(boundary);
		os.write('-');
		os.write('-');
		writeNewLine(os);
	}

	private void writeNewLine(OutputStream os) throws IOException {
		os.write('\r');
		os.write('\n');
	}
	
	protected byte[] generateMultipartBoundary() {
		return "END_OF_PART".getBytes();
	}
	
	
	/**
	 * Implementation of {@link org.springframework.http.HttpOutputMessage} used for writing multipart data.
	 */
	private class MultipartHttpOutputMessage implements HttpOutputMessage {

		private final HttpHeaders headers = new HttpHeaders();

		private final OutputStream os;

		private boolean headersWritten = false;

		public MultipartHttpOutputMessage(OutputStream os) {
			this.os = os;
		}

		public HttpHeaders getHeaders() {
			return headersWritten ? HttpHeaders.readOnlyHttpHeaders(headers) : this.headers;
		}

		public OutputStream getBody() throws IOException {
			writeHeaders();
			return this.os;
		}

		private void writeHeaders() throws IOException {
			if (!this.headersWritten) {
				for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
					byte[] headerName = getAsciiBytes(entry.getKey());
					for (String headerValueString : entry.getValue()) {
						byte[] headerValue = getAsciiBytes(headerValueString);
						os.write(headerName);
						os.write(':');
						os.write(' ');
						os.write(headerValue);
						writeNewLine(os);
					}
				}
				writeNewLine(os);
				this.headersWritten = true;
			}
		}

		protected byte[] getAsciiBytes(String name) {
			try {
				return name.getBytes("US-ASCII");
			}
			catch (UnsupportedEncodingException ex) {
				// should not happen, US-ASCII is always supported
				throw new IllegalStateException(ex);
			}
		}
	}
}
