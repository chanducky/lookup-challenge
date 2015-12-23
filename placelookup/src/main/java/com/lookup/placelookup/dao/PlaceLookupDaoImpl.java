package com.lookup.placelookup.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.lookup.placelookup.model.Places;

@Repository(value="placeLookupDao")
public class PlaceLookupDaoImpl extends HibernateSessionDao implements IPlaceLookupDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Places> getAllPlaces() {
		Criteria crt = getSession().createCriteria(Places.class);
		return crt.list();
	}
	
	/* (non-Javadoc)
	 * @see com.lookup.placelookup.dao.IPlaceLookupDao#findPlaceByNameOrCategory(java.lang.String)
	 */
	@Cacheable(value = { "place" },key="#queryString")
	public List<Places> findPlaceByNameOrCategory(String queryString) {
		Criteria crt = getSession().createCriteria(Places.class);
		Disjunction disj = Restrictions.disjunction();
		disj.add(Restrictions.like("name",  queryString, MatchMode.ANYWHERE));
		disj.add(Restrictions.like("category", queryString, MatchMode.ANYWHERE));
		crt.add(disj);

		@SuppressWarnings("unchecked")
		List<Places> placesList = crt.list();

		return placesList;
	}

	/* (non-Javadoc)
	 * @see com.lookup.placelookup.dao.IPlaceLookupDao#getTotalPlacesCount()
	 */
	@Override
	@Cacheable(value = { "count" })
	public Integer getTotalPlacesCount() {
		Object countObj =  getSession().createCriteria(Places.class).setProjection(Projections.rowCount()).uniqueResult();

		if(countObj != null ){
			return	((Long)countObj).intValue();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.lookup.placelookup.dao.IPlaceLookupDao#clearCacheAll()
	 */
	@Caching(evict={ @CacheEvict(value="count", allEntries=true), @CacheEvict(value="place",allEntries=true)})
	public void clearCacheAll(){
		// clear all cached data
	}

	/* (non-Javadoc)
	 * @see com.lookup.placelookup.dao.IPlaceLookupDao#clearCacheCount()
	 */
	@CacheEvict(value = { "count" }, allEntries=true)
	public void clearCacheCount(){
		// clear all cached data of count 
	}

	/* (non-Javadoc)
	 * @see com.lookup.placelookup.dao.IPlaceLookupDao#clearCachePlaces()
	 */
	@CacheEvict(value = { "place" }, allEntries=true)
	public void clearCachePlaces(){
		// clear all cached data of places
	}
}
