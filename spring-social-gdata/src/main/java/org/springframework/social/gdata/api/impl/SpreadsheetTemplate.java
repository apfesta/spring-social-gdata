/**
 * 
 */
package org.springframework.social.gdata.api.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.social.gdata.api.SpreadsheetOperations;
import org.springframework.web.client.RestTemplate;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.IFeed;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

/**
 * {@link SpreadsheetOperations} implementation
 * 
 * @author apfesta
 *
 */
public class SpreadsheetTemplate extends AbstractGdataOperations implements SpreadsheetOperations {

	public SpreadsheetTemplate(RestTemplate restTemplate, boolean isAuthorized) {
		super(restTemplate, null, isAuthorized);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpreadsheetService getSpreadsheetService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpreadsheetEntry> getSpreadsheets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CellEntry> getCells(String cellFeedUrl) {
		// TODO Auto-generated method stub
		return null;
	}

//	public SpreadsheetTemplate(String accessToken, String applicationName, boolean isAuthorized) {
//		super(accessToken, applicationName, isAuthorized);
//	}
//
//	public SpreadsheetService getSpreadsheetService() {
//		return getService();
//	}
//
//	@Override
//	protected SpreadsheetService createGoogleService(String applicationName) {
//		return new SpreadsheetService(applicationName);
//	}
//	
//	protected <F extends IFeed> F getFeed(String url, Class<F> feedClass)  {
//		URL feedUrl=null;
//		try {
//			feedUrl = new URL(url);
//		} catch (MalformedURLException e) {
//			throw new RuntimeException(e);
//		}
//		try {
//			return getService().getFeed(feedUrl, feedClass);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} 
//	}
//	
//	protected SpreadsheetFeed getSpreadsheetFeed()  {
//		return getFeed("https://spreadsheets.google.com/feeds/spreadsheets/private/full", 
//				SpreadsheetFeed.class);
//	}
//	
//	public List<SpreadsheetEntry> getSpreadsheets() {
//		return getSpreadsheetFeed().getEntries();
//	}
//	
//	protected CellFeed getCellFeed(String cellFeedUrl)  {
//		return getFeed("cellFeedUrl", CellFeed.class);
//	}
//	
//	public List<CellEntry> getCells(String cellFeedUrl) {
//		return getCellFeed(cellFeedUrl).getEntries();
//	}

}
