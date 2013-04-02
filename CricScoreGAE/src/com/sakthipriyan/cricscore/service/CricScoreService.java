package com.sakthipriyan.cricscore.service;

import java.util.List;

import com.sakthipriyan.cricscore.models.Match;
import com.sakthipriyan.cricscore.models.SimpleScore;

public class CricScoreService {

	private FetchDataService fetchDataService;
	private ObjectGeneratorService objectGeneratorService;
	private PersistenceService persistenceService;
	private MemCacheService memCacheService;

	public CricScoreService() {
		this.fetchDataService = new FetchDataService();
		this.objectGeneratorService = new ObjectGeneratorService();
		this.persistenceService = new PersistenceService();
		this.memCacheService = new MemCacheService();
	}

	public List<Match> getMatches() {
		String livescore = getMatchesFromLocalOrRemote();
		return objectGeneratorService.getMatches(livescore);
	}

	public SimpleScore getScore(int id) {

		SimpleScore score = memCacheService.getSimpleScore(id);

		if (score == null) {
			score = persistenceService.findSimpleScore(id);
			if (score == null) {
				score = fetchSimpleScore(id);
				persistenceService.insertSimpleScore(score);
			} else {
				if (!score.getDetail().toLowerCase().contains("match over")) {
					SimpleScore newScore = fetchSimpleScore(id);
					if (!score.getSimple().equals(newScore.getSimple())
							|| !score.getDetail().equals(newScore.getDetail())) {
						persistenceService.updateSimpleScore(newScore);
						score = newScore;
					}

				}
			}

			memCacheService.putSimpleScore(score);
		}
		return score;
	}

	private SimpleScore fetchSimpleScore(int id) {
		String detail = fetchDataService.getScore(id);
		String livescore = getMatchesFromLocalOrRemote();
		return objectGeneratorService.getScore(detail, livescore, id);
	}

	private String getMatchesFromLocalOrRemote() {
		String livescore = memCacheService.getLiveScore();
		if (livescore == null) {
			livescore = fetchDataService.getMatches();
			memCacheService.setLiveScore(livescore);
		}
		return livescore;
	}

}
