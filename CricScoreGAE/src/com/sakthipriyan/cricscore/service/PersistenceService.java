package com.sakthipriyan.cricscore.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.datastore.KeyFactory;
import com.sakthipriyan.cricscore.models.SimpleScore;

public class PersistenceService {
	
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
		score.setId(id);
		em.close();
		return score;
	}
	
	public void updateSimpleScore(final SimpleScore score){
		EntityManager em = EMF.get().createEntityManager();
		em.merge(score);
		em.close();
	}
	
	public void insertSimpleScore(final SimpleScore score){
		EntityManager em = EMF.get().createEntityManager();
		em.persist(score);
		em.close();
	}
		
}
