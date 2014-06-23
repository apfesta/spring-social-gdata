package org.springframework.social.gdata.api.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * This creates a wrapped RestTemplate and a wrapped HttpHeaders object so we
 * can override the default buffering of the original RestTmplate.  This 
 * helps us to "chunk" our requests for larger uploads.
 * 
 * @author apfesta
 *
 */
public class ChunkedRestTemplate {
	
	private RestTemplate restTemplate;
	private HttpHeaders httpHeaders;
	

	public ChunkedRestTemplate() {
		super();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setBufferRequestBody(false);
				
		restTemplate = new RestTemplate(requestFactory);
		
		httpHeaders =  new HttpHeaders();
	}
	
	public RestTemplate restTemplate() {
		return restTemplate;
	}
	
	public HttpHeaders httpHeaders() {
		HttpHeaders copy =  new HttpHeaders();
		copy.putAll(this.httpHeaders);
		return copy;
	}

	public void addHeader(String headerName, String headerValue) {
		httpHeaders.add(headerName, headerValue);
	}
	
}
