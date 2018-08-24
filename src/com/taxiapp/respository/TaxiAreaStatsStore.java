package com.taxiapp.respository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.taxiapp.models.TaxiAreaStats;

public class TaxiAreaStatsStore {
	
	/*
	 * SQL TABLE STRUCTURE
	 * 
		DROP TABLE IF EXISTS `taxi_area_stats`;
		CREATE TABLE IF NOT EXISTS `taxi_area_stats` (
		  `id` int(11) NOT NULL,
		  `hour_block` int(11) NOT NULL,
		  `start_longitude` double NOT NULL,
		  `end_longitude` double NOT NULL,
		  `start_lattitude` double NOT NULL,
		  `end_lattitude` double NOT NULL,
		  `average_taxi_count` int(11) NOT NULL,
		  `average_time_interval` int(11) NOT NULL
		) ENGINE=MyISAM DEFAULT CHARSET=latin1;
		COMMIT;

	 */
	
	public double GetAverageTaxiCountByArea(double longitude, double lattitude, int hourOfDay) {
		double result = 0;
		Connection conn = DataConnection.GetConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement("SELECT average_taxi_count FROM taxi_area_stats WHERE start_longitude <= ? and ? <= end_longitude and start_lattitude <= ? and ? <= end_lattitude and hour_block = ?");
				stmt.setDouble(1, longitude);
				stmt.setDouble(2, lattitude);
				stmt.setInt(3, hourOfDay);
				
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					result = rs.getDouble(1);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public double GetAverageTimeIntervalByArea(double longitude, double lattitude, int hourOfDay) {
		double result = 0;
		Connection conn = DataConnection.GetConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement("SELECT average_time_interval FROM taxi_area_stats WHERE start_longitude <= ? and ? <= end_longitude and start_lattitude <= ? and ? <= end_lattitude and hour_block = ?");
				stmt.setDouble(1, longitude);
				stmt.setDouble(2, lattitude);
				stmt.setInt(3, hourOfDay);
				
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					result = rs.getDouble(1);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public ArrayList<TaxiAreaStats> GetAreasRanked(double longitude, double lattitude, int hourOfDay, int count) {
		ArrayList<TaxiAreaStats> list = new ArrayList<TaxiAreaStats>();

		Connection conn = DataConnection.GetConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement("SELECT start_longitude, end_longitude, start_lattitude, end_lattitude, average_taxi_count, average_time_interval FROM taxi_area_stats WHERE start_longitude <= ? and ? <= end_longitude and start_lattitude <= ? and ? <= end_lattitude and hour_block = ?");
				stmt.setDouble(1, longitude);
				stmt.setDouble(2, lattitude);
				stmt.setInt(3, hourOfDay);
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					TaxiAreaStats area = new TaxiAreaStats();
					area.AverageTaxiCount = rs.getDouble("average_taxi_count");
					area.AverageTimeInterval = rs.getDouble("average_time_interval");
					area.StartLongitude = rs.getDouble("start_longitude");
					area.EndLongitude = rs.getDouble("end_longitude");
					area.StartLongitude = rs.getDouble("start_lattitude");
					area.EndLattitude = rs.getDouble("end_lattitude");
					list.add(area);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return list;
	}
	
}
