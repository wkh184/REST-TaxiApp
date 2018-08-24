package com.taxiapp.resources;

import com.taxiapp.models.RequestResult;
import com.taxiapp.models.PagedPickUpLocationsModel;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.taxiapp.services.PickUpLocationService;
import com.taxiapp.services.UserService;
import com.taxiapp.models.UserModel;

@Path("users")
public class Users {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private UserService service = new UserService();

	// users/authenticate
	@PermitAll
	@POST
	@Path("/authenticate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response AuthenticateUser(UserModel user) {
		RequestResult result = service.Authenticate(user.Username, user.Password);
		return Response.ok().entity(new GenericEntity<RequestResult>(result) {}).build();
	}

	// users/reset
	@PermitAll
	@POST
	@Path("/reset")
	@Consumes("application/json")
	@Produces("application/json")
	public Response ResetUser(UserModel user) {
		RequestResult result = service.Reset(user.Username);
		return Response.ok().entity(new GenericEntity<RequestResult>(result) {}).build();
	}

	@RolesAllowed("admin")
	@POST
	@Consumes("application/json")
	public Response Add(UserModel user) {
		// TODO Add user model to database
		String userName = user.Username;
		UserModel userDB = service.GetByUsername(userName);
		if(userDB == null) { // username not found, can insert
			boolean result = service.Add(user);
			return Response.ok(result).build();
		}else {
			return Response.status(Response.Status.CONFLICT)
                    .entity("User name exists.").build();
		}
	}

	@RolesAllowed("admin")
	@GET
	@Produces("application/json")
	public Response GetAll() {
		// TODO get from database all users
		ArrayList<UserModel> users = service.GetAll();
		return Response.ok().entity(new GenericEntity<ArrayList<UserModel>>(users) {}).build();
	}


	@RolesAllowed({"admin","user"})
	// /users/1
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response Get(@PathParam("id") int id, final @Context HttpHeaders httpHeaders) {
		String userStr = "";
		String roleStr = "";
		List<String> roleList = httpHeaders.getRequestHeader("Role");
		if(!roleList.isEmpty()) {
			roleStr = roleList.get(0);
		}
		UserModel user = service.Get(id);
		//admin can query all ID
		if("admin".equals(roleStr)) {
			if (user != null) {
				return Response.ok().entity(new GenericEntity<UserModel>(user) {}).build();
			}else {
            	RequestResult result = new RequestResult();
            	result.Result = false;
            	result.Message = "Requested ID not found.";
				return Response.status(Response.Status.NOT_FOUND).entity(new GenericEntity<RequestResult>(result) {}).build();
			}
		}else { // normal user, can only query own ID
			List<String> userList = httpHeaders.getRequestHeader("User");
			if(!userList.isEmpty()) {
				userStr = userList.get(0);
			}
			if (user != null) {
				if((user.Username).equals(userStr)) {
					return Response.ok().entity(new GenericEntity<UserModel>(user) {}).build();
				}else {
	            	RequestResult result = new RequestResult();
	            	result.Result = false;
	            	result.Message = "Requested ID does not belong to you.";
					return Response.status(Response.Status.FORBIDDEN).entity(new GenericEntity<RequestResult>(result) {}).build();
				}
			}else {
            	RequestResult result = new RequestResult();
            	result.Result = false;
            	result.Message = "Requested ID not found.";
				return Response.status(Response.Status.FORBIDDEN).entity(new GenericEntity<RequestResult>(result) {}).build();
			}
		}
	}




}
