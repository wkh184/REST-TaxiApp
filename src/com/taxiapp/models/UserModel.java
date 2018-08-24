package com.taxiapp.models;

import java.sql.Date;

public class UserModel {
	public int UserId;
	public String Username;
	public String Password;
	public String FirstName;
	public String LastName;
	public Date LastFailedLoginDate;
	public int FailedLoginCount;
	public String Role;
}
