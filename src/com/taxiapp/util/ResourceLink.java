package com.taxiapp.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourceLink {
	@XmlElement	
	public String Url;
	@XmlElement	
	public String Method;
	@XmlElement	
	public String Rel;

	public ResourceLink() {
		
	}
	
	public ResourceLink(String url, String method, String rel) {
		this.Url = url;
		this.Method = method;
		this.Rel = rel;
	}
}
