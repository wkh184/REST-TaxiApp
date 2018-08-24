package com.taxiapp.respository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.taxiapp.models.PickUpLocationLiteModel;
import com.taxiapp.models.PickUpLocationModel;
import com.taxiapp.util.ResourceLink;

public class PickUpLocationStore {

	public boolean Add(PickUpLocationModel location) {
		boolean result = false;

		Connection conn = DataConnection.GetConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_pickup_locations (user_id, lattitude, longitude, location_name, date_added, category) VALUES (?,?,?,?,?,?)");
			stmt.setInt(1, location.UserId);
			stmt.setDouble(2, location.Lattitude);
			stmt.setDouble(3, location.Longitude);
			stmt.setString(4, location.LocationName);
			java.sql.Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			stmt.setTimestamp(5, currentTime);
			stmt.setString(6, location.Category);

			stmt.execute();

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean Update(int id, PickUpLocationModel location) {
		boolean result = false;

		Connection conn = DataConnection.GetConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE user_pickup_locations SET user_id = ?, lattitude = ?, longitude = ?, location_name = ?, category = ? WHERE id = ?");

			stmt.setInt(1, location.UserId);
			stmt.setDouble(2, location.Lattitude);
			stmt.setDouble(3, location.Longitude);
			stmt.setString(4, location.LocationName);
			stmt.setString(5, location.Category);
			stmt.setInt(6, id);

			stmt.execute();

			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();			
		}

		return result;
	}

	public ArrayList<PickUpLocationModel> GetPickUpLocationsByUser(int user_id, int page, int pageSize) {
		ArrayList<PickUpLocationModel> locations = new ArrayList<PickUpLocationModel>();

		int startFrom = (page - 1) * pageSize;

		Connection conn = DataConnection.GetConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_pickup_locations WHERE user_id = ? LIMIT ?,?");
			stmt.setInt(1, user_id);
			stmt.setInt(2, startFrom);
			stmt.setInt(3, pageSize);

			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				PickUpLocationModel location = new PickUpLocationModel();
				location.Id = result.getInt("id");
				location.UserId = result.getInt("user_id");
				location.Lattitude = result.getDouble("lattitude");
				location.Longitude = result.getDouble("longitude");
				location.LocationName = result.getString("location_name");
				location.setSQLDate(result.getDate("date_added"));
				location.Category = result.getString("category");

				locations.add(location);
			}

		} catch (Exception ex) {
			ex.printStackTrace();			

		}

		return locations;
	}

	public int GetLocationCountByUser(int userId) {
		int count = 0;

		Connection conn = DataConnection.GetConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM user_pickup_locations WHERE user_id = ?");
			stmt.setInt(1, userId);

			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				count = result.getInt(1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();			

		}

		return count;
	}

	public void DeletePickUpLocation(int id) {
		Connection conn = DataConnection.GetConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM user_pickup_locations WHERE id = ?");
			stmt.setInt(1, id);
			stmt.execute();
		} catch(Exception ex) {
			ex.printStackTrace();			

		}
	}

	public PickUpLocationModel Get(int id) {
		PickUpLocationModel location = null;

		Connection conn = DataConnection.GetConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_pickup_locations WHERE id = ?");
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				location = new PickUpLocationModel();
				location.Id = result.getInt("id");
				location.UserId = result.getInt("user_id");
				location.Lattitude = result.getDouble("lattitude");
				location.Longitude = result.getDouble("longitude");
				location.LocationName = result.getString("location_name");
				location.setSQLDate(result.getDate("date_added"));
				location.Category = result.getString("category");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return location;
	}

	public ArrayList<PickUpLocationLiteModel> GetPickUpLocationsLite(int userId) {
		ArrayList<PickUpLocationLiteModel> list = new ArrayList<PickUpLocationLiteModel>();
		Connection conn = DataConnection.GetConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_pickup_locations WHERE user_id = ?");
			stmt.setInt(1, userId);

			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				PickUpLocationLiteModel location = new PickUpLocationLiteModel();
				location.Category = result.getString("category");
				location.LocationName = result.getString("location_name");
				int id = result.getInt("id");
				location.Uri = new ResourceLink("pickuplocations/" + id, "GET", "Pick Up Locations Details");
				list.add(location);
			}

		} catch (Exception ex) {
			ex.printStackTrace();			

		}
		return list;
	}
}
