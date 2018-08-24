package com.taxiapp.services;

import java.util.ArrayList;

import com.taxiapp.models.PagedPickUpLocationsModel;
import com.taxiapp.models.PickUpLocationLiteModel;
import com.taxiapp.models.PickUpLocationModel;
import com.taxiapp.models.PickUpLocationModel2;
import com.taxiapp.respository.PickUpLocationStore;
import com.taxiapp.util.Simulator;

public class PickUpLocationService {
	private PickUpLocationStore store = new PickUpLocationStore();

	public boolean Add(PickUpLocationModel location) {
		return store.Add(location);
	}

	public boolean Update(int id, PickUpLocationModel location) {	
		return store.Update(id, location);
	}

	public PagedPickUpLocationsModel GetPickUpLocationsByUser(int userId, int page, int pageSize) {
		PagedPickUpLocationsModel model = new PagedPickUpLocationsModel();

		// check if there is a next page
		// get total record count
		// get startFrom
		// if (startFrom + pageSize) < totalRecord count, there is a next page
		int totalRecordCount = store.GetLocationCountByUser(userId);
		int startFrom = (page - 1) * pageSize;
		if (startFrom + pageSize < totalRecordCount) {
			int nextPage = page + 1;
			model.Next = "/users/" + userId + "/pickuplocations?page=" + nextPage + "&pagesize=" + pageSize;
		}

		// check if there is a prev page
		if (page > 1) {
			int prevPage = page - 1;
			model.Prev = "/users/" + userId + "/pickuplocations?page=" + prevPage + "&pagesize=" + pageSize;
		}

		// get data
		model.Data = store.GetPickUpLocationsByUser(userId, page, pageSize);

		return model;
	}

	public void Delete(int id) {
		store.DeletePickUpLocation(id);
	}

	public PickUpLocationModel Get(int id) {
		return store.Get(id);
	}

	public PickUpLocationModel2 GetWithStats(int id) {
		// TODO return 1 user based on id, if not found, return null
		PickUpLocationModel oldPickupLocationModel = store.Get(id);
		PickUpLocationModel2 pickupLocationModel = new PickUpLocationModel2(oldPickupLocationModel);
		pickupLocationModel.AverageWaitingTimeMins = Simulator.simulateAverageWaitingTime();
		pickupLocationModel.PeakHourBlock = Simulator.simulatePeakHourBlock();
		return pickupLocationModel;
	}

	public ArrayList<PickUpLocationLiteModel> GetPickUpLocationsByUserLite(int userId) {
		ArrayList<PickUpLocationLiteModel> list = store.GetPickUpLocationsLite(userId);
		return list;
	}

}
