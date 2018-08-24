package com.taxiapp.models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PagedPickUpLocationsModel {
	@XmlElement(name = "PickUpLocations")
	public ArrayList<PickUpLocationModel> Data;
	
	@XmlElement
	public String Next;
	
	@XmlElement
	public String Prev;
	
	public PagedPickUpLocationsModel() {
		Data = new ArrayList<PickUpLocationModel>();
		Next = "";
		Prev = "";
	}
}
