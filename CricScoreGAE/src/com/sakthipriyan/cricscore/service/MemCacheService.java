package com.sakthipriyan.cricscore.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.sakthipriyan.cricscore.models.SimpleScore;


public class MemCacheService {
	
	
	
	private static final Logger logger = Logger.getLogger(MemCacheService.class.getName());
	
	private static final String LIVESCORE  = "LIVESCORE";
	private static final int TIMEOUT_SECONDS = 5;
	
	private Cache cache;
	
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public MemCacheService(){
        try {
    		Map properties = new HashMap();
    		properties.put(GCacheFactory.EXPIRATION_DELTA, TIMEOUT_SECONDS);
            cache = CacheManager.getInstance().getCacheFactory().createCache(properties);
        } catch (CacheException e) {
        	logger.severe(e.getMessage());
        }

	}
	
	public SimpleScore getSimpleScore(int key){
		return (SimpleScore) cache.get(key);
	}
	
	public void putSimpleScore(SimpleScore score){
		if(score == null){
			logger.warning("SimpleScore is null");
			return;
		}
	    cache.put(score.getId(), score);
	}
	
	public String getLiveScore(){
		return (String)cache.get(LIVESCORE);
	}
	
	public void setLiveScore(String livescore){
		if(livescore == null){
			logger.warning("Livescore is null");
			return;
		}
		cache.put(LIVESCORE, livescore);
	}
}
