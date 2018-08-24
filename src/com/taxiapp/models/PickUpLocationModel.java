package com.taxiapp.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PickUpLocationModel {
	@XmlElement
	public int Id;

	@XmlElement
	public int UserId;

	@XmlElement
	public double Lattitude;

	@XmlElement
	public double Longitude;

	@XmlElement
	public java.util.Date DateAdded;

	@XmlElement
	public String Category;

	@XmlElement
	public String LocationName;

	
	@XmlTransient
	private java.sql.Date SQLDate;

	public PickUpLocationModel() {
		super();
	}

	public PickUpLocationModel(int id, int userId, double lattitude, double longitude, String locationName,
			java.sql.Date sqlDate, String category) {
		super();
		Id = id;
		UserId = userId;
		Lattitude = lattitude;
		Longitude = longitude;
		LocationName = locationName;
		SQLDate = sqlDate;
		DateAdded = new java.util.Date(sqlDate.getTime());
		Category = category;
	}	

	@JsonIgnore
	public java.sql.Date getSQLDate() {
		SQLDate = new java.sql.Date(DateAdded.getTime());
		return SQLDate;
	}

	@JsonIgnore
	public void setSQLDate(java.sql.Date sqlDate) {
		SQLDate = sqlDate;
		DateAdded = new java.util.Date(sqlDate.getTime());
	}
}
