package com.sakthipriyan.cricscore.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author sakthipriyan
 *
 */
@Entity
public class SimpleScore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Key key; 
	@Transient
	private int id;
	private String simple;
	private String detail;
	private long timestamp;
	
	public SimpleScore(String simple, String detail, int id) {
		super();
		this.simple = simple;
		this.detail = detail;
		this.id = id;
		this.timestamp = System.currentTimeMillis();
		this.key = KeyFactory.createKey(SimpleScore.class.getSimpleName(), id);
	}

	public int getId() {
		return id;
	}
	
	public String getSimple() {
		return simple;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "SimpleScore [id=" + id + ", simple=" + simple + ", detail="
				+ detail + ", timestamp=" + timestamp + "]";
	}
	
	
}
