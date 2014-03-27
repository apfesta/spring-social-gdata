/**
 * 
 */
package org.springframework.social.gdata.api.impl;

import org.springframework.social.gdata.api.SpreadsheetOperations;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

/**
 * {@link SpreadsheetOperations} implementation
 * 
 * @author apfesta
 *
 */
public class SpreadsheetTemplate extends AbstractGdataOperations<SpreadsheetService> implements SpreadsheetOperations {

	public SpreadsheetTemplate(String accessToken, String applicationName, boolean isAuthorized) {
		super(accessToken, applicationName, isAuthorized);
	}

	public SpreadsheetService getSpreadsheetService(String applicationName) {
		return getService();
	}

	@Override
	protected SpreadsheetService createGoogleService(String applicationName) {
		return new SpreadsheetService(applicationName);
	}

}
