/**
 * 
 */
package org.springframework.social.gdata.api;

import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;

/**
 * @author apfesta
 *
 */
public interface SpreadsheetOperations {
	
	public SpreadsheetService getSpreadsheetService();
	
	public List<SpreadsheetEntry> getSpreadsheets();
	
	public List<CellEntry> getCells(String cellFeedUrl);

}
