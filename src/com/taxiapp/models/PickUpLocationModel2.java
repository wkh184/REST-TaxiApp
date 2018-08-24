package com.taxiapp.models;

import java.sql.Date;

public class PickUpLocationModel2 extends PickUpLocationModel{
	public int PeakHourBlock;
	public int AverageWaitingTimeMins;
	
	public PickUpLocationModel2(int id, int userId, double lattitude, double longitude, String locationName,
			java.sql.Date sqlDate, String category, int peakHourBlock, int averageWaitingTimeMins) {
		super(id, userId, lattitude, longitude, locationName, sqlDate, category);
		PeakHourBlock = peakHourBlock;
		AverageWaitingTimeMins = averageWaitingTimeMins;
	}

	public PickUpLocationModel2(PickUpLocationModel oldPickupLocationModel) {
		super(oldPickupLocationModel.Id, oldPickupLocationModel.UserId, oldPickupLocationModel.Lattitude, 
				oldPickupLocationModel.Longitude, oldPickupLocationModel.LocationName, 
				oldPickupLocationModel.getSQLDate(), oldPickupLocationModel.Category);		
		PeakHourBlock = 0;
		AverageWaitingTimeMins = 0;
	}
}
