package com.taxiapp.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.time.Instant;

import com.taxiapp.models.TaxiAreaStats;
import com.taxiapp.models.TaxiLocationModel;
import com.taxiapp.respository.TaxiAreaStatsStore;
import com.taxiapp.respository.TaxiLocationsStore;

public class TaxiLocationService {
	private TaxiLocationsStore store = new TaxiLocationsStore();
	private TaxiAreaStatsStore statStore = new TaxiAreaStatsStore();
	
	public void Add(TaxiLocationModel taxiLocation) {
		store.Add(taxiLocation);
	}
	
	public double GetAverageTaxiCountByArea(double longitude, double lattitude, int hourofday) {
		double averageTaxiCount = 0;
		averageTaxiCount = statStore.GetAverageTaxiCountByArea(longitude, lattitude, hourofday);
		return averageTaxiCount;
	}

	public double GetAverageTimeIntervalByArea(double longitude, double lattitude, int hourofday) {
		double averageTimeInterval = 0;
		averageTimeInterval = statStore.GetAverageTimeIntervalByArea(longitude, lattitude, hourofday);
		return averageTimeInterval;
	}
	
	public ArrayList<TaxiAreaStats> GetAreaRanked(double longitude, double lattitude, int count, int hourofday) {
		ArrayList<TaxiAreaStats> list = new ArrayList<TaxiAreaStats>();
		
		return list;
	}
}
