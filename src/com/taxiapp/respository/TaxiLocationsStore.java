package com.taxiapp.respository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.taxiapp.models.TaxiLocationModel;

public class TaxiLocationsStore {
	
	public void Add(TaxiLocationModel taxiLocation) {
		Connection conn = DataConnection.GetConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO taxi_movement_data (longitude, lattitude, tracked_datetime) VALUES (?,?,?)");
				stmt.setDouble(1, taxiLocation.Longitude);
				stmt.setDouble(2, taxiLocation.Lattitude);

				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				stmt.setString(3, (taxiLocation.TrackedDatetime != null ? sdf.format(taxiLocation.TrackedDatetime) : null));
				stmt.execute();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
