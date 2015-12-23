package com.lookup.placelookup.dao;

import java.util.List;

import com.lookup.placelookup.model.Places;

public interface IPlaceLookupDao {

	/**
	 * @return List<Places>
	 */
	public List<Places> getAllPlaces();
	
	/**
	 * @param queryString
	 * @return List<Places> 
	 */
	public List<Places>  findPlaceByNameOrCategory(String queryString);

	/**
	 * @return Integer
	 */
	public Integer getTotalPlacesCount();

	/**
	 * Method clear all the caches
	 */
	public void clearCacheAll();

	/**
	 * Method clear cache of count
	 */
	public void clearCacheCount();

	/**
	 * Method clear cache of places
	 */
	public void clearCachePlaces();

}
