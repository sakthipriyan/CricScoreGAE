package com.sakthipriyan.cricscore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sakthipriyan.cricscore.models.Match;
import com.sakthipriyan.cricscore.models.SimpleScore;
import com.sakthipriyan.cricscore.service.CricScoreService;
import com.sakthipriyan.cricscore.service.JSONHelperService;
import com.sakthipriyan.cricscore.utils.StringUtils;

public class CricScoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(CricScoreServlet.class.getName());
	private CricScoreService cricScoreService;	
	private JSONHelperService jsonHelperService;

	public CricScoreServlet() {
		cricScoreService = new CricScoreService();
		jsonHelperService = new JSONHelperService();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		String responseString = null;
		String paramId = (String) request.getParameter("id");
		String paramTS = (String) request.getParameter("ts");
		logger.fine("paramId: " + paramId + "paramTS: " + paramTS);
		
		if (paramId == null) {
			List<Match> matches = cricScoreService.getMatches();
			responseString = jsonHelperService.getMatchesJSON(matches);
		} else if (StringUtils.isNumericPlus(paramId)) {
			
			long timestamp = StringUtils.getLong(paramTS);
			String[] matchIds = paramId.split(" ");
			List<SimpleScore> scores = new ArrayList<SimpleScore>();
			for (String matchId : matchIds) {
				int id = Integer.parseInt(matchId);
				SimpleScore simpleScore = cricScoreService.getScore(id);
				System.out.println(simpleScore);
				if(timestamp == 0 || simpleScore.getTimestamp() > timestamp){
					scores.add(simpleScore);
				}
			}
			responseString = jsonHelperService.getSimpleScoresJSON(scores, System.currentTimeMillis());
			
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		if (responseString != null) {
			logger.finest("responseString: " + responseString);	
			response.setContentType("application/json");
			response.getWriter().println(responseString);
		}
	}
}