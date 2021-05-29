package com.sid.moviecatalogservice.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sid.moviecatalogservice.models.CatalogItem;
import com.sid.moviecatalogservice.models.Movie;
import com.sid.moviecatalogservice.models.Rating;
import com.sid.moviecatalogservice.models.UserRating;
import com.sid.moviecatalogservice.services.MovieInfo;
import com.sid.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	@RequestMapping("/{userId}")
//	@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
//		RestTemplate restTemplate = new RestTemplate();
		
		
		// get all rated movie IDs
		/*List<Rating> ratings = Arrays.asList(
		new Rating ("1234", 4),
		new Rating ("5678", 4)
		);*/
		
//		UserRating ratings = restTemplate.getForObject("http://localhost:5002/ratingsdata/users/"+userId,UserRating.class);
//		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId,UserRating.class);
//		UserRating ratings = getUserRating(userId);
		UserRating ratings = userRatingInfo.getUserRating(userId);
		
		/*return ratings.getUserRatings().stream()
				.map(rating -> {
			// for each movie ID, call movie info service and get details
			// synchronous way of creating object by fetching json data from REST API
//			Movie movie = restTemplate.getForObject("http://localhost:5001/movies/"+rating.getMovieId(), Movie.class);
//			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
//			
//			// put them all together
//			return new CatalogItem(movie.getName(), "Description", rating.getRating());
			return getCatalogItem(rating);
		})
		.collect(Collectors.toList());*/
		
		return ratings.getUserRatings().stream()
				.map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());
		
		/*return Collections.singletonList(
			new CatalogItem("Transformers","Test", 4)
		);*/
	}

	
	
	
	
	public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
		return Arrays.asList(new CatalogItem("No Movie", "", 0));
	}
	
	
}

//asynchronous way of creating object by fetching json data from REST API
	/*Movie movie = webClientBuilder.build()
		.get()
		.uri("http://localhost:5001/movies/"+rating.getMovieId())
		.retrieve()
		.bodyToMono(Movie.class)
		.block();*/