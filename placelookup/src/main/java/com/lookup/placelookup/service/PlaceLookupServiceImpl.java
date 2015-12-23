package com.lookup.placelookup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lookup.placelookup.dao.IPlaceLookupDao;
import com.lookup.placelookup.model.Places;

@Service(value="placeLookupService")
@Transactional(propagation=Propagation.REQUIRED)
public class PlaceLookupServiceImpl implements IPlaceLookupService{

	@Autowired
	IPlaceLookupDao placeLookupDao;

	@Override
	public List<Places> getAllPlaces(){
		return placeLookupDao.getAllPlaces();
	}
	
	@Override
	public List<Places> findPlaceByNameOrCategory(String queryString){
		
		if(StringUtils.isEmpty(queryString)){
			return null;
		}else{
			queryString = queryString.trim();
		}
		return placeLookupDao.findPlaceByNameOrCategory(queryString);
	}
	
	public Integer getTotalPlacesCount(){
		return  placeLookupDao.getTotalPlacesCount();
	}
	
	@Override
	public void clearCache(String cacheType) {
		if("count".equalsIgnoreCase(cacheType)){
			placeLookupDao.clearCacheCount();
		}else if("place".equalsIgnoreCase(cacheType)){
			placeLookupDao.clearCachePlaces();;
		}else{
			placeLookupDao.clearCacheAll();
		}
	}
}
