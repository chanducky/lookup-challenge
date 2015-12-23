package com.lookup.placelookup.service;

import java.util.List;

import com.lookup.placelookup.model.Places;

/**
 * @author anshu
 *
 */
public interface IPlaceLookupService {

	/**
	 * @return List<Places>
	 */
	public List<Places> getAllPlaces();
	
	/**
	 * @param queryString
	 * @return List<Places>
	 */
	public List<Places> findPlaceByNameOrCategory(String queryString);

	/**
	 * @return Integer
	 */
	public Integer getTotalPlacesCount();
	
	/**
	 * @param cacheType
	 */
	public void clearCache(String cacheType);
	
}
