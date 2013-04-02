package com.sakthipriyan.cricscore.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sakthipriyan.cricscore.models.Match;
import com.sakthipriyan.cricscore.models.SimpleScore;
import com.sakthipriyan.cricscore.utils.StringUtils;

public class ObjectGeneratorService {

	private static final Logger logger = Logger.getLogger(ObjectGeneratorService.class
			.getName());

	public List<Match> getMatches(String xmlRecords) {

		List<Match> matches = new ArrayList<Match>();
		try {

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("item");

			for (int i = 0; i < nodes.getLength(); i++) {

				Element element = (Element) nodes.item(i);
				NodeList name = element.getElementsByTagName("guid");
				Element line = (Element) name.item(0);
				int matchId = Integer.parseInt(StringUtils
						.getOnlyNumbers(getCharacterDataFromElement(line)));
				NodeList title = element.getElementsByTagName("title");
				line = (Element) title.item(0);
				String detail = getCharacterDataFromElement(line);
				if (detail.contains("Under-19s") || detail.contains("College")
						|| detail.contains("School")
						|| StringUtils.getSpaceCount(detail) > 10) {
					// continue;
					;
				}
				String[] teams = detail.split(" v ");
				Match match = new Match(StringUtils.getNonNumeric(teams[0])
						.trim(), StringUtils.getNonNumeric(teams[1]).trim(),
						matchId);
				matches.add(match);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.fine("Number of matches retrieved " + matches.size());
		return matches;
	}

	

	public SimpleScore getScore(String detail, String livescore, int id) {
		String simple = getSimpleString(livescore, id);
		return new SimpleScore(simple, detail, id);
	};

	private String getSimpleString(String livescore, int id) {
		String detail = null;
		try {

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(livescore));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("item");

			for (int i = 0; i < nodes.getLength(); i++) {

				Element element = (Element) nodes.item(i);
				NodeList name = element.getElementsByTagName("guid");
				Element line = (Element) name.item(0);
				int matchId = Integer.parseInt(StringUtils
						.getOnlyNumbers(getCharacterDataFromElement(line)));
				if (matchId != id) {
					continue;
				}
				NodeList title = element.getElementsByTagName("title");
				line = (Element) title.item(0);
				detail = getCharacterDataFromElement(line);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return detail;
	}

	private String getCharacterDataFromElement(Element element) {
		Node child = element.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData().trim().replaceAll("\\s+", " ");
		}
		return "";
	}
}
