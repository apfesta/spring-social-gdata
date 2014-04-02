/**
 * 
 */
package com.sun.syndication.feed.module.gphoto.io;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jdom.Element;
import org.jdom.Namespace;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.gphoto.GphotoModule;
import com.sun.syndication.io.ModuleGenerator;

/**
 * @author apfesta
 *
 */
public class GphotoModuleGenerator implements ModuleGenerator {
	
	private static final Namespace GPHOTO_NS  = Namespace.getNamespace("gphoto", GphotoModule.URI);

	/* (non-Javadoc)
	 * @see com.sun.syndication.io.ModuleGenerator#getNamespaceUri()
	 */
	@Override
	public String getNamespaceUri() {
		return GphotoModule.URI;
	}
	
	private static final Set<Namespace> NAMESPACES;
	 
    static {
        Set<Namespace> nss = new HashSet<Namespace>();
        nss.add(GPHOTO_NS);
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
        root.addNamespaceDeclaration(GPHOTO_NS);
        
        GphotoModule fm = (GphotoModule) module;
        this.generateCommon(fm, element);
        this.generateUser(fm, element);
        this.generateAlbum(fm, element);
        this.generatePhoto(fm, element);
	}
	
	private void generateCommon(GphotoModule module, Element element) {
		if (module.getId() != null) {
            element.addContent(generateSimpleElement("id", module.getId()));
        }
		if (module.getAlbumId() != null) {
            element.addContent(generateSimpleElement("albumid", module.getAlbumId()));
        }
	}
	
	private void generateUser(GphotoModule module, Element element) {
		if (module.getMaxPhotosPerAlbum() != null) {
            element.addContent(generateSimpleElement("maxPhotosPerAlbum", module.getMaxPhotosPerAlbum().toString()));
        }
		if (module.getNickname() != null) {
            element.addContent(generateSimpleElement("nickname", module.getNickname()));
        }
		if (module.getQuotacurrent() != null) {
            element.addContent(generateSimpleElement("quotacurrent", module.getQuotacurrent().toString()));
        }
		if (module.getQuotalimit() != null) {
            element.addContent(generateSimpleElement("quotalimit", module.getQuotalimit().toString()));
        }
		if (module.getThumbnail() != null) {
            element.addContent(generateSimpleElement("thumbnail", module.getThumbnail()));
        }
		if (module.getUser() != null) {
            element.addContent(generateSimpleElement("user", module.getUser()));
        }
	}
	
	private void generateAlbum(GphotoModule module, Element element) {
		if (module.getAccess() != null) {
            element.addContent(generateSimpleElement("access", module.getAccess().value()));
        }
		if (module.getBytesUsed() != null) {
            element.addContent(generateSimpleElement("bytesUsed", module.getBytesUsed().toString()));
        }
		if (module.getLocation() != null) {
            element.addContent(generateSimpleElement("location", module.getLocation().toString()));
        }
		if (module.getNumphotos() != null) {
            element.addContent(generateSimpleElement("numphotos", module.getNumphotos().toString()));
        }
		if (module.getNumphotosremaining() != null) {
            element.addContent(generateSimpleElement("numphotosremaining", module.getNumphotosremaining().toString()));
        }
	}
	
	private void generatePhoto(GphotoModule module, Element element) {
		if (module.getChecksum() != null) {
            element.addContent(generateSimpleElement("checksum", module.getChecksum()));
        }
		if (module.getCommentCount() != null) {
            element.addContent(generateSimpleElement("commentCount", module.getCommentCount().toString()));
        }
		if (module.getHeight() != null) {
            element.addContent(generateSimpleElement("height", module.getHeight().toString()));
        }
		if (module.getRotation() != null) {
            element.addContent(generateSimpleElement("rotation", module.getRotation().toString()));
        }
		if (module.getSize() != null) {
            element.addContent(generateSimpleElement("size", module.getSize().toString()));
        }
		if (module.getTimestamp() != null) {
            element.addContent(generateSimpleElement("timestamp", module.getTimestamp().toString()));
        }
		if (module.getVideostatus() != null) {
            element.addContent(generateSimpleElement("videostatus", module.getVideostatus().value()));
        }
		if (module.getWidth() != null) {
            element.addContent(generateSimpleElement("width", module.getWidth().toString()));
        }
	}
	
	protected Element generateSimpleElement(String name, String value)  {
        Element element = new Element(name, GPHOTO_NS);
        element.addContent(value);
        return element;
    }

}
