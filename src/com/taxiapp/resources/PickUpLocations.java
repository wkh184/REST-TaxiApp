package com.taxiapp.resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.taxiapp.models.PagedPickUpLocationsModel;
import com.taxiapp.models.PickUpLocationLiteModel;
import com.taxiapp.models.PickUpLocationModel;
import com.taxiapp.models.RequestResult;
import com.taxiapp.models.UserModel;
import com.taxiapp.services.PickUpLocationService;
import com.taxiapp.services.UserService;

@Path("pickuplocations")
public class PickUpLocations {

	private UserService userService = new UserService();
	private PickUpLocationService service = new PickUpLocationService();

	@RolesAllowed({"user"})
	@POST
	@Consumes("application/json")
	public Response Add(PickUpLocationModel location, final @Context HttpHeaders httpHeaders) {
		String userStr = "";
		List<String> userList = httpHeaders.getRequestHeader("UserID");
		if(!userList.isEmpty()) {
			userStr = userList.get(0);
		}
		UserModel user = userService.Get(Integer.parseInt(userStr));
		if(user != null) {
			if(location.UserId == user.UserId) {
				boolean result = service.Add(location);
				return Response.ok(result).build();
			}else {
				//Forbidden, cannot add under different user ID
				RequestResult result = new RequestResult();
				result.Result = false;
				result.Message = "Requested ID not belonging to you.";
				return Response.status(Response.Status.FORBIDDEN).entity(new GenericEntity<RequestResult>(result) {}).build();
			}
		}else {
			//Not found, cannot add when user not found
			RequestResult result = new RequestResult();
			result.Result = false;
			result.Message = "Requested ID not found.";
			return Response.status(Response.Status.NOT_FOUND).entity(new GenericEntity<RequestResult>(result) {}).build();
		}
	}

	// pickuplocations/1
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
			PickUpLocationModel location = service.Get(id);
			if (location != null)
				if(location.UserId == headerUserID) {
					return Response.ok(new GenericEntity<PickUpLocationModel>(location) {}).build();
				}else {
					//Forbidden, cannot get under different user ID
					RequestResult result = new RequestResult();
					result.Result = false;
					result.Message = "Requested ID not belonging to you.";
					return Response.status(Response.Status.FORBIDDEN).entity(new GenericEntity<RequestResult>(result) {}).build();
				}
			else
				return Response.noContent().build();
		}else {
			//Not found, cannot get when user not found
			RequestResult result = new RequestResult();
			result.Result = false;
			result.Message = "Requested ID not found.";
			return Response.status(Response.Status.NOT_FOUND).entity(new GenericEntity<RequestResult>(result) {}).build();
		}
	}

	// pickuplocations/1
	@RolesAllowed({"user"})
	@PUT
	@Path("{id}")
	@Consumes("application/json")
	public Response Update(@PathParam("id") int id, PickUpLocationModel location
			, final @Context HttpHeaders httpHeaders) {
		String userStr = "";
		List<String> userList = httpHeaders.getRequestHeader("UserID");
		if(!userList.isEmpty()) {
			userStr = userList.get(0);
		}
		int headerUserID = Integer.parseInt(userStr);
		UserModel user = userService.Get(headerUserID);
		if(user != null) {
			PickUpLocationModel locationRead = service.Get(id);
			if(locationRead.UserId == headerUserID) {
				boolean result = service.Update(id, location);
				return Response.ok(result).build();			
			}else {
				//Forbidden, cannot update, not belonging to correct user
				RequestResult result = new RequestResult();
				result.Result = false;
				result.Message = "Requested ID not belonging to you.";
				return Response.status(Response.Status.FORBIDDEN).entity(new GenericEntity<RequestResult>(result) {}).build();
			}
		}else {
			//Not found, cannot update when user not found
			RequestResult result = new RequestResult();
			result.Result = false;
			result.Message = "Requested ID not found.";
			return Response.status(Response.Status.NOT_FOUND).entity(new GenericEntity<RequestResult>(result) {}).build();
		}
	}

	// pickuplocations/1
	@RolesAllowed({"user"})
	@DELETE
	@Path("{id}")
	public Response Delete(@PathParam("id") int id, final @Context HttpHeaders httpHeaders) {
		String userStr = "";
		List<String> userList = httpHeaders.getRequestHeader("UserID");
		if(!userList.isEmpty()) {
			userStr = userList.get(0);
		}
		int headerUserID = Integer.parseInt(userStr);
		UserModel user = userService.Get(headerUserID);
		if(user != null) {
			PickUpLocationModel locationRead = service.Get(id);
			if(locationRead.UserId == headerUserID) {
				service.Delete(id);
				return Response.ok().build();
			}else {
				//Forbidden, cannot delete, not belonging to correct user
				RequestResult result = new RequestResult();
				result.Result = false;
				result.Message = "Requested ID not belonging to you.";
				return Response.status(Response.Status.FORBIDDEN).entity(new GenericEntity<RequestResult>(result) {}).build();
			}
		}else {
			//Not found, cannot delete when user not found
			RequestResult result = new RequestResult();
			result.Result = false;
			result.Message = "Requested ID not found.";
			return Response.status(Response.Status.NOT_FOUND).entity(new GenericEntity<RequestResult>(result) {}).build();
		}
	}

	// paginated
	// /1/allpickuplocations?page=1&pagesize=10
	@RolesAllowed({"user"})
	@GET
	@Path("{user_id}/allpickuplocations")
	@Produces({"application/json;qs=0.9","application/xml;qs=0.1"}) // content negotiation
	public Response GetAllByUser(@PathParam("user_id") int userId, 
			@QueryParam("page") int pageNumber, 
			@QueryParam("pagesize") int pageSize) {

		PagedPickUpLocationsModel locations = service.GetPickUpLocationsByUser(userId, pageNumber, pageSize);

		return Response.ok().entity(new GenericEntity<PagedPickUpLocationsModel>(locations) {}).build();
	}

	// paginated
	// /1/allpickuplocationslite
	@RolesAllowed({"user"})
	@GET
	@Path("{user_id}/allpickuplocationslite")
	@Produces({"application/json;qs=0.9","application/xml;qs=0.1"}) // content negotiation
	public Response GetAllByUserLite(@PathParam("user_id") int userId) {
		ArrayList<PickUpLocationLiteModel> locations = service.GetPickUpLocationsByUserLite(userId);
		return Response.ok().entity(new GenericEntity<ArrayList<PickUpLocationLiteModel>>(locations) {}).build();
	}
}
