package com.taxiapp.models;

public class WaitingLocationModel {
	public double Longitude;
	public double Lattude;
	public double Distance;
	public int NumTaxi;
	public int WaitTime;
	
	public WaitingLocationModel(double longitude, double lattude) {
		super();
		this.Longitude = longitude;
		this.Lattude = lattude;
	}

	public WaitingLocationModel(double longitude, double lattude, int numTaxi, int waitTime, double distance) {
		super();
		Longitude = longitude;
		Lattude = lattude;
		NumTaxi = numTaxi;
		WaitTime = waitTime;
		this.Distance = distance;
	}		
}
