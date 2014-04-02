/**
 * 
 */
package com.sun.syndication.feed.module.gd;

import com.sun.syndication.feed.module.Module;

/**
 * @author apfesta
 *
 */
public interface GdModule extends Module {
	
	public static final String URI = "http://schemas.google.com/g/2005";
	
	public void setEtag(String etag);
	
	public String getEtag();

}
