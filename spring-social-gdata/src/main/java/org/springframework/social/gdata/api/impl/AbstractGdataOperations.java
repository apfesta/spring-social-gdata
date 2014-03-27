/**
 * 
 */
package org.springframework.social.gdata.api.impl;

import org.springframework.social.MissingAuthorizationException;

import com.google.gdata.client.GoogleService;

/**
 * @author apfesta
 *
 */
public abstract class AbstractGdataOperations<G extends GoogleService> {
	
	private G service;
	private final boolean isAuthorized;
	
	public AbstractGdataOperations(String accessToken, String applicationName, boolean isAuthorized) {
		super();
		this.service = createGoogleService(applicationName);
		setAccessToken(accessToken);
		this.isAuthorized = isAuthorized;
	}

	protected void setAccessToken(String accessToken) {
		service.setHeader("Authorization", "Bearer " + accessToken);
	}
	
	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException("gdata");
		}
	}
	
	abstract G createGoogleService(String applicationName);

	public G getService() {
		return service;
	}

}
