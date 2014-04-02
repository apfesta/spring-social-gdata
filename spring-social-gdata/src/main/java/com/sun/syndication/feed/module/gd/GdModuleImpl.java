/**
 * 
 */
package com.sun.syndication.feed.module.gd;

import com.sun.syndication.feed.module.ModuleImpl;

/**
 * @author apfesta
 *
 */
public class GdModuleImpl extends ModuleImpl implements GdModule {
	
	private static final long serialVersionUID = 1L;
	
	private String etag;
	
	
	public GdModuleImpl() {
		super(GdModule.class, GdModule.URI);
	}

	/* (non-Javadoc)
	 * @see com.sun.syndication.feed.CopyFrom#getInterface()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Class getInterface() {
		return GdModule.class;
	}

	/* (non-Javadoc)
	 * @see com.sun.syndication.feed.CopyFrom#copyFrom(java.lang.Object)
	 */
	@Override
	public void copyFrom(Object obj) {
		GdModule module = (GdModule) obj;
		setEtag(module.getEtag());
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}


}
