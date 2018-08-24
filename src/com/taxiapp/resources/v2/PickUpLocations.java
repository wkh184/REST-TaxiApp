package com.taxiapp.resources.v2;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.taxiapp.models.PickUpLocationModel;
import com.taxiapp.models.PickUpLocationModel2;
import com.taxiapp.models.UserModel;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import com.taxiapp.services.PickUpLocationService;
import com.taxiapp.services.UserService;

@Path("v2/pickuplocations")
public class PickUpLocations {

	private UserService userService = new UserService();
	private PickUpLocationService service = new PickUpLocationService();

	@RolesAllowed({"user"})
	@POST
	@Consumes("application/json")
	public Response Add(PickUpLocationModel location) {
		boolean result = service.Add(location);
		return Response.ok(result).build();
	}

	// pickuplocations/1
	// *BONUS Get Pick up Location Taxi statistics (peak period of taxi appearing, average waiting time)
	@RolesAllowed({"user"})
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response Get(@PathParam("id") int id, final @Context HttpHeaders httpHeaders) {
		String userStr = "";
		List<String> userList = httpHeaders.getRequestHeader("UserID");
		if(!userList.isEmpty()) {
			userStr = userList.get(0);
		}
		int headerUserID = Integer.parseInt(userStr);
		UserModel user = userService.Get(headerUserID);
		if(user != null) {
			PickUpLocationModel2 location = service.GetWithStats(id);
			if (location != null)
				if(location.UserId == headerUserID) {
					return Response.ok(new GenericEntity<PickUpLocationModel2>(location) {}).build();
				}else {
					//Forbidden, cannot get under different user ID
					return Response.status(Response.Status.FORBIDDEN)
	                        .entity("ID requested does not belong to you.").build();				
				}
			else
				return Response.status(Response.Status.NOT_FOUND)
                        .entity("Requested location not found.").build();
		}else {
			//Not found, cannot get when user not found
			return Response.status(Response.Status.NOT_FOUND)
                    .entity("User ID not found.").build();						
		}
	}
}
