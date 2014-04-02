/**
 * 
 */
package com.sun.syndication.feed.module.gphoto.io;

import org.jdom.Element;
import org.jdom.Namespace;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.feed.module.gphoto.GphotoModule;
import com.sun.syndication.feed.module.gphoto.GphotoModuleImpl;
import com.sun.syndication.feed.module.gphoto.types.Access;
import com.sun.syndication.feed.module.gphoto.types.VideoStatus;
import com.sun.syndication.io.ModuleParser;

/**
 * @author apfesta
 *
 */
public class GphotoModuleParser implements ModuleParser {
	
	private static final Namespace GPHOTO_NS  = Namespace.getNamespace("gphoto", GphotoModule.URI);
	
	@Override
	public String getNamespaceUri() {
		return GphotoModule.URI;
	}

	@Override
	public Module parse(Element element) {
		boolean foundSomething = false;
		GphotoModuleImpl mod = new GphotoModuleImpl();
		
		if (parseCommon(element, mod)) {
			foundSomething = true;
		}
		if (parseUser(element, mod)) {
			foundSomething = true;
		}
		if (parseAlbum(element, mod)) {
			foundSomething = true;
		}
		if (parsePhoto(element, mod)) {
			foundSomething = true;
		}
		
		return (foundSomething) ? mod : null;
	}
	
	private boolean parseCommon(Element element, GphotoModuleImpl mod) {
		boolean foundSomething = false;
		Element e;
		
		e = element.getChild("id", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setId(e.getText());
		}
		e = element.getChild("albumid", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setAlbumId(e.getText());
		}
		
		return foundSomething;
	}
	
	private boolean parseUser(Element element, GphotoModuleImpl mod) {
		boolean foundSomething = false;
		Element e;
		
		e = element.getChild("maxPhotosPerAlbum", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setMaxPhotosPerAlbum(Integer.parseInt(e.getText()));
		}
		e = element.getChild("nickname", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setNickname(e.getText());
		}
		e = element.getChild("quotacurrent", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setQuotacurrent(Long.parseLong(e.getText()));
		}
		e = element.getChild("quotalimit", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setQuotalimit(Long.parseLong(e.getText()));
		}
		e = element.getChild("thumbnail", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setThumbnail(e.getText());
		}
		e = element.getChild("user", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setUser(e.getText());
		}
		
		return foundSomething;
	}
	
	private boolean parseAlbum(Element element, GphotoModuleImpl mod) {
		boolean foundSomething = false;
		Element e;
		
		e = element.getChild("access", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setAccess(Access.fromValue(e.getText()));
		}
		e = element.getChild("bytesUsed", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setBytesUsed(Integer.parseInt(e.getText()));
		}
		e = element.getChild("location", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setLocation(e.getText());
		}
		e = element.getChild("numphotos", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setNumphotos(Integer.parseInt(e.getText()));
		}
		e = element.getChild("numphotosremaining", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setNumphotosremaining(Integer.parseInt(e.getText()));
		}
		
		return foundSomething;
	}
	
	private boolean parsePhoto(Element element, GphotoModuleImpl mod) {
		boolean foundSomething = false;
		Element e;
		
		e = element.getChild("checksum", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setChecksum(e.getText());
		}
		e = element.getChild("commentCount", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setCommentCount(Integer.parseInt(e.getText()));
		}
		e = element.getChild("height", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setHeight(Long.parseLong(e.getText()));
		}
		e = element.getChild("rotation", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setRotation(Integer.parseInt(e.getText()));
		}
		e = element.getChild("size", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setSize(Long.parseLong(e.getText()));
		}
		e = element.getChild("timestamp", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setTimestamp(Long.parseLong(e.getText()));
		}
		e = element.getChild("videostatus", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setVideostatus(VideoStatus.fromValue(e.getText()));
		}
		e = element.getChild("width", GPHOTO_NS);
		if (e!=null) {
			foundSomething = true;
			mod.setWidth(Long.parseLong(e.getText()));
		}
		
		return foundSomething;
	}

}
