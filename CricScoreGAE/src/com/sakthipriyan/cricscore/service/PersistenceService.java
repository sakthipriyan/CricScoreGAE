package com.sakthipriyan.cricscore.service;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.datastore.KeyFactory;
import com.sakthipriyan.cricscore.models.SimpleScore;

public class PersistenceService {
	
	private static final Logger logger = Logger.getLogger(PersistenceService.class
			.getName());
	
	public static final class EMF {
	    private static final EntityManagerFactory emfInstance =
	        Persistence.createEntityManagerFactory("transactions-optional");
	    private EMF() {}
	    public static EntityManagerFactory get() {
	        return emfInstance;
	    }
	}
	
	public SimpleScore findSimpleScore(int id){
		EntityManager em = EMF.get().createEntityManager();
		SimpleScore score = em.find(SimpleScore.class, KeyFactory.createKey(
				SimpleScore.class.getSimpleName(), id));
		if(score != null){
			score.setId(id);	
		}
		em.close();
		logger.info("SimpleScore for match id " + id + " is " + score);
		return score;
	}
	
	public void updateSimpleScore(final SimpleScore score){
		if(score == null){
			logger.warning("Invalid simple score to update");
			return;
		}
		EntityManager em = EMF.get().createEntityManager();
		em.merge(score);
		em.close();
		logger.info("Updated " + score);
	}
	
	public void insertSimpleScore(final SimpleScore score){
		if(score == null){
			logger.warning("Invalid simple score to insert");
			return;
		}
		EntityManager em = EMF.get().createEntityManager();
		em.persist(score);
		em.close();
		logger.info("Inserted " + score);
	}
		
}
