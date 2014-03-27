package org.springframework.social.gdata.api.impl;

import org.springframework.social.ApiBinding;
import org.springframework.social.gdata.api.Gdata;
import org.springframework.social.gdata.api.PicasawebOperations;
import org.springframework.social.gdata.api.SpreadsheetOperations;

public class GdataTemplate implements Gdata, ApiBinding {
	
	private String accessToken;
	private String applicationName;
	
	private SpreadsheetOperations spreadsheetOperations;
	private PicasawebOperations picasawebOperations;

	public GdataTemplate(String accessToken, String applicationName) {
		super();
		this.accessToken = accessToken;
		this.applicationName = applicationName;
	}
	
	public boolean isAuthorized() {
		return accessToken != null;
	}

	public SpreadsheetOperations spreadsheetOperations() {
		if (spreadsheetOperations==null) {
			spreadsheetOperations = new SpreadsheetTemplate(accessToken, applicationName, isAuthorized());
		}
		return spreadsheetOperations;
	}

	public PicasawebOperations picasawebOperations() {
		if (picasawebOperations==null) {
			picasawebOperations = new PicasawebTemplate(accessToken, applicationName, isAuthorized());
		}
		return picasawebOperations;
	}
	
}
