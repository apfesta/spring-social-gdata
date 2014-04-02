/**
 * 
 */
package org.springframework.social.gdata.api;

/**
 * Interface specifying a basic set of operations for interacting with the Gdata API.
 * Implemented by {@link GdataTemplate}. 
 * @author apfesta
 *
 */
public interface Gdata {
	
	/**
	 * Returns operations of the Gdata API dealing with Google Spreadsheets.
	 * Requires OAuth2 scopes: 
	 * https://spreadsheets.google.com/feeds
	 * https://docs.google.com/feeds
	 * 
	 * @return
	 */
	SpreadsheetOperations spreadsheetOperations();
	
	/**
	 * Returns operations of the Gdata API dealing with Google Picasa Web Albums.
	 * Requires OAuth2 scope: 
	 * http://picasaweb.google.com/data/
	 * 
	 * @return
	 */
	PicasawebOperations picasawebOperations();
	
	String getAccessToken();

}
