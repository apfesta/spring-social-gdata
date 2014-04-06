package org.springframework.social.gdata.api.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.MultipartRelatedHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.social.gdata.api.Gdata;
import org.springframework.social.gdata.api.PicasawebOperations;
import org.springframework.social.gdata.api.SpreadsheetOperations;
import org.springframework.social.gdata.api.picasa.xpp.AtomFeedHttpMessageConverter;
import org.springframework.social.gdata.api.picasa.xpp.AtomEntryHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.HttpRequestDecorator;
import org.springframework.web.client.RestTemplate;

public class GdataTemplate extends AbstractOAuth2ApiBinding implements Gdata {
	
	private String accessToken;
	
	PicasawebOperations picasawebOperations;

	public GdataTemplate() {
		super();
		initialize();
	}

	public GdataTemplate(String accessToken) {
		super(accessToken);
		this.accessToken = accessToken;
		initialize();
	}
	
	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
		super.configureRestTemplate(restTemplate);
		restTemplate.getInterceptors().add(new GDataVersionRequestInterceptor());
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
		picasawebOperations = new PicasawebTemplate(getRestTemplate(), isAuthorized());
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
	
	class GDataVersionRequestInterceptor implements ClientHttpRequestInterceptor {

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
			protectedResourceRequest.getHeaders().set("GData-Version", "2");
			return execution.execute(protectedResourceRequest, body);
		}
		
	}
		
}
