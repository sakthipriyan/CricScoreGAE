package com.sakthipriyan.cricscore.models;


public class Match{
	private String teamOne;
	private String teamTwo;
	private int matchId;
	
	public Match(){
	}
	
	public Match(String teamOne, String teamTwo, int matchId) {
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.matchId = matchId;
	}
	
	public String getTeamOne() {
		return teamOne;
	}
	public String getTeamTwo() {
		return teamTwo;
	}
	public int getMatchId() {
		return matchId;
	}

	@Override
	public String toString() {
		return "Match [teamOne=" + teamOne + ", teamTwo=" + teamTwo
				+ ", matchId=" + matchId + "]";
	}
	
	
	
}