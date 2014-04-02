package org.springframework.http.converter;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.feed.AtomEntryHttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;

public class MultipartRelatedHttpMessageConverterTest {

	@Ignore
	@Test
	public void test() {
		
		MultipartRelatedHttpMessageConverter converter = new MultipartRelatedHttpMessageConverter();
		converter.addPartConverter(new AtomEntryHttpMessageConverter());
		
		Resource resource = new FileSystemResource(new File("/home/apfesta/Desktop/kl_75.png"));
		
		Entry entry = new Entry();
		entry.setTitle("kl_75.png");
		entry.setSummary(new Content());
		entry.getSummary().setValue("KickballLegends Logo");
		
		MultiValueMap<String, HttpEntity<?>> parts = getParts(entry, resource, MediaType.IMAGE_JPEG);
		
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		try {
			converter.write(parts, MultipartRelatedHttpMessageConverter.MULTIPART_RELATED, outputMessage);
		} catch (HttpMessageNotWritableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(outputMessage.getHeaders());
		System.out.println(outputMessage.getBodyAsString());
		
	}
	
	public <T> MultiValueMap<String, HttpEntity<?>> getParts(T entity, Resource resource, MediaType resourceType) {
		MultiValueMap<String, HttpEntity<?>> parts = new LinkedMultiValueMap<String, HttpEntity<?>>();
		
		//Part 1 - metadata
		HttpHeaders metadataHeaders = new HttpHeaders();
		metadataHeaders.setContentType(MediaType.APPLICATION_ATOM_XML);
		HttpEntity<T> metadataEntity = new HttpEntity<T>(entity, metadataHeaders);
		parts.add("metadata", metadataEntity);
		
		//Part 2 - file
		HttpHeaders fileHeaders = new HttpHeaders();
		fileHeaders.setContentType(resourceType);
		HttpEntity<Resource> sample_file = new HttpEntity<Resource>(
		      resource, fileHeaders);
		parts.add("file", sample_file);
		 
		return parts;
	}

}
