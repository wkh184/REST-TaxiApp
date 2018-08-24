package com.taxiapp.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestResult {
	@XmlElement
	public boolean Result; // true for successful login, false for failed login
	@XmlElement
	public String Message = ""; // message for failed logins
}
