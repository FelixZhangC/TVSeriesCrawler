package com.seriesguide;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class WebChooseAPI {
	private LinkedHashMap<String, String> chooseLink;
	public WebChooseAPI(String url,String keyword)
		{
			SeriesSpider ss=new SeriesSpider();
			ss.setUrl(url);
			ss.setKeyword(keyword);
			String con=ss.getHTMLDocument();
			chooseLink=ss.analyzeHTMLDocument(con,ss.getKeyword());
			
			//return chooseLink;
		}
	public LinkedHashMap<String, String> getSeasonList()
	{
		return chooseLink;
	}
	public int getSize()
	{
		return chooseLink.size();
	}
}
