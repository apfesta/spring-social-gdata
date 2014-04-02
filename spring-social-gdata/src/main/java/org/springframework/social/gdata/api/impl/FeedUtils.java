package org.springframework.social.gdata.api.impl;

import com.sun.syndication.feed.module.Extendable;
import com.sun.syndication.feed.module.Module;

public class FeedUtils {

	@SuppressWarnings("unchecked")
	public static <M extends Module> M getModule(Extendable element, String uri) {
		return (M) element.getModule(uri);
	}
	
}
