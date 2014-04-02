/**
 * 
 */
package org.springframework.social.gdata.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.gdata.api.Gdata;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @author apfesta
 *
 */
public class GdataConnectionFactory extends OAuth2ConnectionFactory<Gdata> {

	public GdataConnectionFactory(String clientId, String clientSecret) {
		super("gdata", new GdataServiceProvider(clientId, clientSecret), 
				new GdataAdapter());
	}

	@Override
	protected String extractProviderUserId(AccessGrant accessGrant) {
		Google google = this.getGdataServiceProvider().getGoogleApi(accessGrant.getAccessToken());
		GoogleUserInfo userInfo = google.userOperations().getUserInfo();
		return userInfo.getId();
	}
	
	private GdataServiceProvider getGdataServiceProvider() {
		return (GdataServiceProvider) this.getServiceProvider();
	}
	
}
