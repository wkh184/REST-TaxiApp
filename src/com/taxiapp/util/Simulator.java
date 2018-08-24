package com.taxiapp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.taxiapp.models.WaitingLocationModel;
import com.taxiapp.models.TaxiAreaStats;

public class Simulator {
    private static Random mRandom = new Random(1984);
//    double latMin = 103.608656;
//    double latMax = 104.095625;
//    double longMin = 1.226108;
//    double longMax = 1.470211;

    public static int simulateAverageWaitingTime() {
		 Random rand = new Random();
		 int pickedNumber = rand.nextInt(60);
		 return pickedNumber;
	}
	
	public static int simulatePeakHourBlock() {
		 Random rand = new Random();
		 int pickedNumber = rand.nextInt(23);
		 return pickedNumber;
	}

	private static WaitingLocationModel position(double longMin, double longMax, double latMin, double latMax) {
        return new WaitingLocationModel(random(longMin, longMax), random(latMin, latMax));
    }

	private static double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }

	public static int SimulateAverageTaxiCountByArea() {
		 Random rand = new Random();
		 int pickedNumber = rand.nextInt(99);
		 return pickedNumber;
	}
	
	public static Collection<WaitingLocationModel> GetBestLocations(double longitude, double latitude, int radius, int count) {
		double longMin = longitude - (radius * 0.01);
		double longMax = longitude + (radius * 0.01);
		double latMin = latitude  - (radius * 0.01);
		double latMax = latitude  + (radius * 0.01);
        Map<Double, WaitingLocationModel> sortLocations = null;
        Map<Double, WaitingLocationModel> unsortLocations = new HashMap<>();
		for(int i = 0; i < count; i ++) {
			WaitingLocationModel location = position(longMin, longMax, latMin, latMax);
			location.NumTaxi = SimulateAverageTaxiCountByArea();
			location.WaitTime = simulateAverageWaitingTime();
			double diffDistance = DistanceCalculator.distance(location.Lattude,location.Longitude,
					latitude, longitude, "K"); 
			location.Distance = diffDistance;
            unsortLocations.put(diffDistance, location);			
		}
        sortLocations = new TreeMap<Double, WaitingLocationModel>(unsortLocations);
		return sortLocations.values();
	}
	

}
