/**
 * 
 */
package org.springframework.social.gdata.api.impl;

import static org.springframework.http.HttpMethod.POST;

import java.util.Arrays;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.gdata.api.ApiEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author apfesta
 *
 */
public abstract class AbstractGdataOperations {
	
	protected final RestTemplate restTemplate;
	protected final ChunkedRestTemplate chunkedRestTemplate;
	private final boolean isAuthorized;
	
	public AbstractGdataOperations(RestTemplate restTemplate, 
			ChunkedRestTemplate chunkedRestTemplate, boolean isAuthorized) {
		super();
		this.restTemplate = restTemplate;
		this.chunkedRestTemplate = chunkedRestTemplate;
		this.isAuthorized = isAuthorized;
	}

	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException("gdata");
		}
	}
	
	protected <T> T getEntity(String url, Class<T> type) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_ATOM_XML));
		
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		
		return restTemplate.exchange(url, HttpMethod.GET, entity, type).getBody();
	}
	
	protected <T extends ApiEntity> T saveEntity(String url, T entity) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_ATOM_XML);
		
		HttpMethod method;
		if (entity.getId()!=null) {
			method = HttpMethod.PUT;
			headers.set("If-Match", entity.getEtag());
		} else {
			method = HttpMethod.POST;
		}
				
		@SuppressWarnings("unchecked")
		ResponseEntity<T> response = 
				restTemplate.exchange(url, method, 
						new HttpEntity<T>(entity, headers), 
						(Class<T>)entity.getClass());
		return response.getBody();
	}
	
	protected <T extends ApiEntity> void deleteEntity(String url, T entity) {
		HttpHeaders headers = new HttpHeaders();
				
		HttpMethod method = HttpMethod.DELETE;
		if (entity.getEtag()!=null) {
			headers.set("If-Match", entity.getEtag());
		} else {
			headers.set("If-Match", "*");
		}
		
						
		@SuppressWarnings({ "unchecked", "unused" })
		ResponseEntity<T> response = 
				restTemplate.exchange(url, method, 
						new HttpEntity<T>(headers), 
						(Class<T>)entity.getClass());
	}
	
	protected <T> T uploadEntity(String url, T metadata, Resource resource, MediaType resourceType, Class<T>clazz) {
		
		MultiValueMap<String, HttpEntity<?>> parts = new LinkedMultiValueMap<String, HttpEntity<?>>();
		
		//Part 1 - metadata
		HttpHeaders metadataHeaders = new HttpHeaders();
		metadataHeaders.setContentType(MediaType.APPLICATION_ATOM_XML);
		HttpEntity<T> metadataEntity = new HttpEntity<T>(metadata, metadataHeaders);
		parts.add("metadata", metadataEntity);
		
		//Part 2 - file
		HttpHeaders fileHeaders = new HttpHeaders();
		fileHeaders.setContentType(resourceType);
		HttpEntity<Resource> sample_file = new HttpEntity<Resource>(
		      resource, fileHeaders);
		parts.add("file", sample_file);
		 
		//Full Multi-part request
		HttpHeaders headers = chunkedRestTemplate.httpHeaders();
		headers.setContentType(MediaType.parseMediaType("multipart/related"));
		//headers.add("Content-type", "multipart/related");
		HttpEntity<MultiValueMap<String, HttpEntity<?>>> ereq = 
				new HttpEntity<MultiValueMap<String, HttpEntity<?>>>(
		                parts, headers);
				
		ResponseEntity<T> response = chunkedRestTemplate.restTemplate().
				exchange(url, POST, ereq, clazz);
		return response.getBody();

	}
	
	protected <T> T upload(String url, Resource resource, MediaType resourceType, Class<T> clazz) {
		HttpHeaders fileHeaders = chunkedRestTemplate.httpHeaders();
		fileHeaders.setContentType(resourceType);
		if (resource.getFilename()!=null) {
			fileHeaders.add("Slug", resource.getFilename());
		}
		HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(
		      resource, fileHeaders);
		
		ResponseEntity<T> response = chunkedRestTemplate.restTemplate().
				exchange(url, POST, httpEntity, clazz);
		return response.getBody();
	}
	
}
