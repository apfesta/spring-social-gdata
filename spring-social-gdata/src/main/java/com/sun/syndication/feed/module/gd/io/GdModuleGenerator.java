package com.sun.syndication.feed.module.gd.io;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.gd.GdModule;
import com.sun.syndication.io.ModuleGenerator;

public class GdModuleGenerator implements ModuleGenerator {
	
	private static final Namespace GD_NS  = Namespace.getNamespace("gd", GdModule.URI);

	/* (non-Javadoc)
	 * @see com.sun.syndication.io.ModuleGenerator#getNamespaceUri()
	 */
	@Override
	public String getNamespaceUri() {
		return GdModule.URI;
	}
	
	private static final Set<Namespace> NAMESPACES;
	 
    static {
        Set<Namespace> nss = new HashSet<Namespace>();
        nss.add(GD_NS);
        NAMESPACES = Collections.unmodifiableSet(nss);
    }
 
  
	/* (non-Javadoc)
	 * @see com.sun.syndication.io.ModuleGenerator#getNamespaces()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Set getNamespaces() {
		return NAMESPACES;
	}

	/* (non-Javadoc)
	 * @see com.sun.syndication.io.ModuleGenerator#generate(com.sun.syndication.feed.module.Module, org.jdom.Element)
	 */
	@Override
	public void generate(Module module, Element element) {
		// this is not necessary, it is done to avoid the namespace definition in every item.
        Element root = element;
        while (root.getParent()!=null && root.getParent() instanceof Element) {
            root = (Element) element.getParent();
        }
        root.addNamespaceDeclaration(GD_NS);
        
        GdModule fm = (GdModule) module;
        if (fm.getEtag() != null) {
        	element.setAttribute(generateAttribute("etag", fm.getEtag()));
        }
	}
	
	protected Attribute generateAttribute(String name, String value) {
		return new Attribute(name, value, GD_NS);
	}
}
