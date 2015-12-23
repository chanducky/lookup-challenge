package com.lookup.placelookup.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lookup.placelookup.model.Places;
import com.lookup.placelookup.service.IPlaceLookupService;


@Controller
@RequestMapping("/place")
public class PlaceLookupController {

	@Autowired
	IPlaceLookupService placeLookupService;

	/**
	 * @return ResponseEntity<List<Places>>
	 */
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Places>> getAllPlaces() {
		List<Places> placesList = null;
		placesList = placeLookupService.getAllPlaces();

		if(placesList.isEmpty()){
			return new ResponseEntity<List<Places>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Places>>(placesList, HttpStatus.OK);
	}

	/**
	 * @param query
	 * @return ResponseEntity<List<Places>>
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Places>> searchPlacesDetails(@RequestParam String query) {

		if(StringUtils.isEmpty(query)){
			throw new RuntimeException("Query parameter not found.");
		}
		List<Places> placesList = placeLookupService.findPlaceByNameOrCategory(query);

		if(placesList.isEmpty()){
			return new ResponseEntity<List<Places>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Places>>(placesList, HttpStatus.OK);
	}

	/**
	 * @return ResponseEntity<Integer>
	 */
	@ResponseBody 
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getTotalPlacesCount() {

		Integer placeCount = placeLookupService.getTotalPlacesCount();

		if(placeCount == 0 ){
			return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(placeCount, HttpStatus.OK);
	}

	/**
	 * @param cacheType
	 * @return ResponseEntity<String>
	 */
	@ResponseBody
	@RequestMapping(value = "/clearcache", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> clearCache(@RequestParam("type")String cacheType) {
		placeLookupService.clearCache(cacheType);
		String msg = "Cached Cleared !";
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	/**
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> errorHandler(Exception e) {
		System.out.println("In exception handler..");
		e.printStackTrace();
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
