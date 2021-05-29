package com.sid.moviecatalogservice.models;

import java.util.List;

public class UserRating {
	private String userId;
	private List<Rating> userRatings;

	public UserRating() {

	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserRating(List<Rating> userRatings) {
		this.userRatings = userRatings;
	}

	public List<Rating> getUserRatings() {
		return userRatings;
	}

	public void setUserRatings(List<Rating> userRatings) {
		this.userRatings = userRatings;
	}
}
