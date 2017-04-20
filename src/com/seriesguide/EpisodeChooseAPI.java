package com.seriesguide;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class EpisodeChooseAPI {
	private LinkedHashMap<String, String> EpisodeLink;
	
	public EpisodeChooseAPI(String url)
	{
		EpisodeSpider es=new EpisodeSpider();
		es.setUrl(url);
		String con=es.getHTMLDocument();
		EpisodeLink=es.analyzeHTMLDocument(con);
		//return EpisodeLink;
	}
	
	public LinkedHashMap<String, String> getEpisodeList()
	{
		return EpisodeLink;
	}
	public int getSize()
	{
		return EpisodeLink.size();
	}
}
