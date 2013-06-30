package com.sakthipriyan.cricscore.service;

import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.sakthipriyan.cricscore.models.Match;
import com.sakthipriyan.cricscore.models.SimpleScore;

public class JSONHelperService {

	private static final Logger logger = Logger.getLogger(JSONHelperService.class
			.getName());

	public String getMatchesJSON(List<Match> matches) {
		JSONArray array = new JSONArray();
		try {
			for (Match match : matches) {
				JSONObject object = new JSONObject();
				object.put("id", match.getMatchId());
				object.put("t1", match.getTeamOne());
				object.put("t2", match.getTeamTwo());
				array.put(object);
			}
		} catch (JSONException e) {
			logger.severe(e.getMessage());
		}

		return array.toString();
	}

	public String getSimpleScoresJSON(List<SimpleScore> simpleScores,
			long timestamp) {
		JSONArray array = new JSONArray();
		try {
			
			for (SimpleScore simpleScore : simpleScores) {
				JSONObject scoreObject = new JSONObject();
				scoreObject.put("id", simpleScore.getId());
				scoreObject.put("si", simpleScore.getSimple());
				scoreObject.put("de", simpleScore.getDetail());
				array.put(scoreObject);
			}
		} catch (JSONException e) {
			logger.severe(e.getMessage());
		}
		return array.toString();
	}
	
}