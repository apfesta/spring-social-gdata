package org.springframework.social.gdata.api.impl;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.MultipartRelatedHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.social.gdata.api.Gdata;
import org.springframework.social.gdata.api.PicasawebOperations;
import org.springframework.social.gdata.api.SpreadsheetOperations;
import org.springframework.social.gdata.api.picasa.xpp.AtomEntryHttpMessageConverter;
import org.springframework.social.gdata.api.picasa.xpp.AtomFeedHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.web.client.RestTemplate;

public class GdataTemplate extends AbstractOAuth2ApiBinding implements Gdata {
	
	private String accessToken;
	
	private final ChunkedRestTemplate chunkedRestTemplate;
	
	PicasawebOperations picasawebOperations;

	public GdataTemplate() {
		super();
		this.chunkedRestTemplate = new ChunkedRestTemplate();
		this.chunkedRestTemplate.restTemplate().setMessageConverters(getMessageConverters());
		initialize();
	}

	public GdataTemplate(String accessToken) {
		super(accessToken);
		this.accessToken = accessToken;
		this.chunkedRestTemplate = createChunkedRestTemplate(accessToken, 
				OAuth2Version.BEARER);
		this.chunkedRestTemplate.restTemplate().setMessageConverters(getMessageConverters());
		initialize();
	}
	
	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
		super.configureRestTemplate(restTemplate);
		
//		//Factory to ensure we don't buffer the transfer
//		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//		requestFactory.setBufferRequestBody(false);
//		restTemplate.setRequestFactory(requestFactory);
		
		//You cannot add interceptors here bceause it will break the chunked transfer
//		restTemplate.getInterceptors().add(new GDataVersionRequestInterceptor());
	}

	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
		messageConverters.add(new AtomEntryHttpMessageConverter());
		messageConverters.add(new AtomFeedHttpMessageConverter());
		messageConverters.add(new ResourceHttpMessageConverter());
		messageConverters.add(getMultipartRelatedMessageConverter());
		return messageConverters;
	}
	
	protected MultipartRelatedHttpMessageConverter getMultipartRelatedMessageConverter() {
		MultipartRelatedHttpMessageConverter converter = new MultipartRelatedHttpMessageConverter();
		converter.addPartConverter(new AtomEntryHttpMessageConverter());
		return converter;
	}

	private void initialize() {
		picasawebOperations = new PicasawebTemplate(getRestTemplate(), 			
				chunkedRestTemplate, isAuthorized());
	}

	@Override
	public SpreadsheetOperations spreadsheetOperations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PicasawebOperations picasawebOperations() {
		return picasawebOperations;
	}
	
	@Override
	public String getAccessToken() {
		return accessToken;
	}
	
	private ChunkedRestTemplate createChunkedRestTemplate(String accessToken, OAuth2Version version) {
		ChunkedRestTemplate client = new ChunkedRestTemplate();
		client.addHeader("Authorization", version.getAuthorizationHeaderValue(accessToken));
		client.addHeader("GData-Version", "2");
		return client;
	}
	
//	class GDataVersionRequestInterceptor implements ClientHttpRequestInterceptor {
//
//		@Override
//		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
//				ClientHttpRequestExecution execution) throws IOException {
//			HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
//			protectedResourceRequest.getHeaders().set("GData-Version", "2");
//			return execution.execute(protectedResourceRequest, body);
//		}
//		
//	}
		
}
