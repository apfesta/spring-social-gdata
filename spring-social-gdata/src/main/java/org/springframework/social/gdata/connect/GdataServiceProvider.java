/**
 * 
 */
package org.springframework.social.gdata.connect;

import org.springframework.social.gdata.api.Gdata;
import org.springframework.social.gdata.api.impl.GdataTemplate;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleServiceProvider;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * Gdata ServiceProvider implementation.
 * @author apfesta
 *
 */
public class GdataServiceProvider extends AbstractOAuth2ServiceProvider<Gdata> {

	String applicationName;
	GoogleServiceProvider delegate;
	
	public GdataServiceProvider(String clientId, String clientSecret, String applicationName) {
		this(new GoogleServiceProvider(clientId, clientSecret), applicationName);
	}
	
	GdataServiceProvider(GoogleServiceProvider delegate, String applicationName) {
		super(delegate.getOAuthOperations());
		this.delegate = delegate;
		this.applicationName = applicationName;
	}
	
	@Override
	public Gdata getApi(String accessToken) {
		return new GdataTemplate(accessToken, applicationName);
	}
	
	/**
	 * Get access to Google+ API
	 * 
	 * @param accessToken
	 * @return
	 */
	public Google getGoogleApi(String accessToken) {
		return delegate.getApi(accessToken);
	}

}
