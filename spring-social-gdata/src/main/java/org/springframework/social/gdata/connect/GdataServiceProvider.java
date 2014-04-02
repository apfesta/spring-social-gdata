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

	GoogleServiceProvider delegate;
	
	public GdataServiceProvider(String clientId, String clientSecret) {
		this(new GoogleServiceProvider(clientId, clientSecret));
	}
	
	GdataServiceProvider(GoogleServiceProvider delegate) {
		super(delegate.getOAuthOperations());
		this.delegate = delegate;
	}
	
	@Override
	public Gdata getApi(String accessToken) {
		return new GdataTemplate(accessToken);
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
