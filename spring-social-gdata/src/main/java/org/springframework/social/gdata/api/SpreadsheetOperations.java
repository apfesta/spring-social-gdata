/**
 * 
 */
package org.springframework.social.gdata.api;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

/**
 * @author apfesta
 *
 */
public interface SpreadsheetOperations {
	
	public SpreadsheetService getSpreadsheetService(String applicationName);

}
