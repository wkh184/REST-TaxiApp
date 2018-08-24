package com.taxiapp.services;

import java.net.URI;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import com.taxiapp.models.RequestResult;
import com.taxiapp.models.UserModel;
import com.taxiapp.respository.UserStore;

public class UserService {
	private UserStore store = new UserStore();

	public ArrayList<UserModel> GetAll() {
		// TODO get all from database, return array list of users
		return store.GetAll();
	}
	public boolean Add(UserModel user) {
		// TODO add user, check for duplicate username, return true or false for success
		if (!store.IsUserNameExists(user.Username))
			return store.Add(user);
		else
			return false;
	}

	public UserModel Get(int id) {
		// TODO return 1 user based on id, if not found, return null
		return store.Get(id);
	}

	public UserModel GetByUsername(String userName) {
		// TODO Auto-generated method stub
		return store.Get(userName);
	}

	public RequestResult Reset(String username) {
		RequestResult result = new RequestResult();
		UserModel user = store.Get(username);
		user.FailedLoginCount = 0;
		user.LastFailedLoginDate = null;
		result.Result = true;
		store.Update(user);		
		return result;
	}
	public RequestResult Authenticate(String username, String password) {
		RequestResult result = new RequestResult();

		// retrieve user by username
		UserModel user = store.Get(username);

		if (user != null) {
			// get difference between 2 dates
			// get unix time stamp

			// short hand if else
			/*
			long lastFailedLoginTime = user.LastFailedLoginDate != null ? (long)Math.ceil(user.LastFailedLoginDate.getTime()) : 0;
			 */
			long lastFailedLoginTime = 0;
			if (user.LastFailedLoginDate != null) {
				lastFailedLoginTime = (long)Math.ceil(user.LastFailedLoginDate.getTime());
			}
			long nowTime = Instant.now().toEpochMilli();
			int minutesDifference = (int)Math.ceil((nowTime - lastFailedLoginTime)/(1000*60));

			if (user.FailedLoginCount < 3 || minutesDifference >= 30) {
				// if time difference of last_failed_login_date and now >= 30 minutes or failed_login_count < 3 continue

				if (lastFailedLoginTime > 0 && minutesDifference >= 30) {
					user.FailedLoginCount = 0;
					user.LastFailedLoginDate = null;
				}

				if (user.Password.equals(password)) {
					// compare password

					// if password is same 
					user.FailedLoginCount = 0;
					user.LastFailedLoginDate = null;
					// set failed_login_count = 0
					result.Result = true;
					// return true
					store.Update(user);
				} else {				
					result.Result = false;
					result.Message = "Wrong Password";
					// else increment failed_login_count by 1
					user.FailedLoginCount++;

					// if failed_login_count >= 3 set last_failed_login_date to now
					if (user.FailedLoginCount >= 3) {
						user.LastFailedLoginDate = new Date(Instant.now().toEpochMilli());
						result.Message = "Account locked for 30 minutes due to 3 failed attempts";
					}

					store.Update(user);
				}
				// return false

				// else
			} else {
				result.Result = false;
				result.Message = "User locked for 30 minutes due to 3 failed logins";
				// return false
			}	
		} else {
			result.Result = false;
			result.Message = "Invalid user";
		}
		return result;
	}

	public boolean UserRolesExists(String username, String password, Set<String> roles) {
		UserModel user = store.Get(username, password);
		if (user != null)
			return (roles.contains(user.Role));
		else
			return false;
	}

	public URI buildURI(int id) {
		UriBuilder builder = UriBuilder.fromPath("/users/{id}");
		builder.scheme("http")
		       .host("{hostname}");
		UriBuilder clone = builder.clone();
		URI uri = clone.build("localhost:8080", id);
		return uri;
	}
}
