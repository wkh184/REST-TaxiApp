package com.taxiapp.resources;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import com.taxiapp.models.WaitingLocationModel;
import com.taxiapp.models.PagedPickUpLocationsModel;
import com.taxiapp.models.PickUpLocationModel;
import com.taxiapp.models.TaxiAreaStats;
import com.taxiapp.models.TaxiLocationModel;
import com.taxiapp.models.UserModel;
import com.taxiapp.services.TaxiLocationService;
import com.taxiapp.util.Simulator;

@Path("taxilocations")
public class TaxiLocations {
	
	private TaxiLocationService service = new TaxiLocationService();

	@RolesAllowed({"admin"})
	@POST
	@Consumes("application/json")
	public Response Add(TaxiLocationModel taxiLocation) {
		// TODO add taxi location model
		service.Add(taxiLocation);
		return Response.ok().build();
	}
	
	@GET
	@Path("appearance-probability")
	public Response GetAppearanceProbability(
			@QueryParam("longitude") double longitude, 
			@QueryParam("lattitude") double lattitude, 
			@QueryParam("hourofday") int hourofday) {
		// TODO return average number of taxis within a geographical plot in an hour block of the day 
		
		// or simply give me a random number :P
//		double result = service.GetAverageTaxiCountByArea(longitude, lattitude, hourofday);
		int result = Simulator.SimulateAverageTaxiCountByArea();
		return Response.ok(result).build();
	}
	
	@GET
	@Path("estimated-waiting")
	public Response GetEstimatedWaitingTime(
			@QueryParam("longitude") double longitude, 
			@QueryParam("lattitude") double lattitude, 
			@QueryParam("hourofday") int hourofday) {

		// TODO return average time interval between taxis within a geographical plot in an hour block of the day 
//		double result = service.GetAverageTimeIntervalByArea(longitude, lattitude, hourofday);
		// or simply give me a random number :P
		int result = Simulator.simulateAverageWaitingTime();
		return Response.ok(result).build();
	}
	
	@GET
	@Path("next-best-locations")
	@Produces("application/json")
	public Response GetNextBestLocations(
			@QueryParam("longitude") double longitude, 
			@QueryParam("lattitude") double latitude, 
			@QueryParam("radius") int radius,
			@QueryParam("hourofday") int hourofday,
			@QueryParam("count") int count) {
		// TODO return top {count} geographical plots in descending order of average number of taxis, average time interval
		// get the plots around the person's location, if count <=9, but if count > 9, the number of plots to get increases
		/*
		 *     [ ][ ][ ]
		 *     [ ][x][ ]
		 *     [ ][ ][ ]
		 * 
		 */
		// return the count number of plots around person order by average number of taxis, average time interval
		
		
		// or simply give me SOME random co-ordinates :P		
		Collection<WaitingLocationModel> locations = Simulator.GetBestLocations(longitude, latitude, radius, count);
		return Response.ok().entity(new GenericEntity<Collection<WaitingLocationModel>>(locations) {}).build();
	}
	
	// question 2F - given me some random co-ordinates with random numbers inside
}
