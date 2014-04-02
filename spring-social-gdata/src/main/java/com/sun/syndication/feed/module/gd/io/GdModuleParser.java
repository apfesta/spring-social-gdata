package com.sun.syndication.feed.module.gd.io;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.gd.GdModule;
import com.sun.syndication.feed.module.gd.GdModuleImpl;
import com.sun.syndication.io.ModuleParser;

public class GdModuleParser implements ModuleParser {
	
	private static final Namespace GD_NS  = Namespace.getNamespace("gd", GdModule.URI);
	
	@Override
	public String getNamespaceUri() {
		return GdModule.URI;
	}

	@Override
	public Module parse(Element element) {
		boolean foundSomething = false;
		GdModuleImpl mod = new GdModuleImpl();
		
		Attribute a = element.getAttribute("etag", GD_NS);
		if (a!=null) {
			foundSomething = true;
			mod.setEtag(a.getValue());
		}
		
		return (foundSomething) ? mod : null;
	}
	
}
