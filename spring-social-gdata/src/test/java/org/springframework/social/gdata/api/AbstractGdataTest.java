package org.springframework.social.gdata.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.social.gdata.api.impl.GdataTemplate;
import org.springframework.test.web.client.MockRestServiceServer;

public class AbstractGdataTest {

	protected GdataTemplate gdata;

	protected GdataTemplate appAuthGdata;
	
	protected MockRestServiceServer mockServer;
	
	protected MockRestServiceServer appAuthMockServer;
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
	static {
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	
	@Before
	public void setup() {
		gdata = new GdataTemplate("ACCESS_TOKEN");
		mockServer = MockRestServiceServer.createServer(gdata.getRestTemplate());
		appAuthGdata = new GdataTemplate("APP_ACCESS_TOKEN");
		appAuthMockServer = MockRestServiceServer.createServer(appAuthGdata.getRestTemplate());
	}
	
	protected Resource atomResource(String filename) {
		return new ClassPathResource(filename + ".xml", getClass());
	}
	
	protected static Date date(String formatted) {
		try {
			return dateFormat.parse(formatted);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
