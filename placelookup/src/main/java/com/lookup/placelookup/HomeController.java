package com.lookup.placelookup;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/**
	 * @param locale
	 * @param model
	 * @return String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		final String uri = "http://localhost:8080/placelookup/api/place/";
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap> places = null;

		final HttpEntity<String> request = getBasicAuthDetail();
		final ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, request, List.class);
		places = response.getBody();

		// return places 
		model.addAttribute("places", places );

		return returnHome(locale, model);
	}

	/**
	 * @param query
	 * @param locale
	 * @param model
	 * @return String
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchPlaces(@RequestParam("searchTxt")String query,Locale locale, Model model) {
		logger.info("query = "+ query);
		String  uri = null;
		if(StringUtils.isEmpty(query)){
			uri = "http://localhost:8080/placelookup/api/place/";
		}else {
			query = query.trim();
			uri = "http://localhost:8080/placelookup/api/place/search?query="+query;
		}

		RestTemplate restTemplate = new RestTemplate();
		final HttpEntity<String> request = getBasicAuthDetail();
		final ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, request, List.class);
		List<LinkedHashMap> places = response.getBody();

		model.addAttribute("places", places);
		return returnHome(locale, model);
	}

	/**
	 * @param locale
	 * @param model
	 * @return String
	 */
	public String returnHome(Locale locale, Model model) {

		RestTemplate restTemplate = new RestTemplate();
		final String placeCountUri = "http://localhost:8080/placelookup/api/place/count";

		final HttpEntity<String> request = getBasicAuthDetail();
		final ResponseEntity<Integer> response = restTemplate.exchange(placeCountUri, HttpMethod.GET, request, Integer.class);
		Integer totalPlace = response.getBody();

		model.addAttribute("totalPlace", totalPlace );

		return "home";
	}

	/**
	 * @param model
	 * @param lat
	 * @param lng
	 * @return String
	 */
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String lookupmap(Model model, @RequestParam("lat")String lat, @RequestParam("lng")String lng) {
		System.out.println(" lat = " + lat );
		System.out.println(" lng " +  lng);

		model.addAttribute("lat", lat);
		model.addAttribute("lng",lng);

		return "lookupmap";
	}

	/**
	 * @return  HttpEntity<String>
	 */
	private HttpEntity<String> getBasicAuthDetail() {

		final String plainCreds = "chandu:chandu";
		final byte[] plainCredsBytes = plainCreds.getBytes();
		final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		final String base64Creds = new String(base64CredsBytes);

		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		final HttpEntity<String> request = new HttpEntity<String>(headers);
		return request;
	}
}
