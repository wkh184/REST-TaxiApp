package com.taxiapp.models;

import javax.xml.bind.annotation.XmlElement;

import com.taxiapp.util.ResourceLink;

public class PickUpLocationLiteModel {
	@XmlElement
	public String Category;

	@XmlElement
	public String LocationName;
	
	@XmlElement
	public ResourceLink Uri;
	

}
