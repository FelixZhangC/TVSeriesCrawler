package com.seriesguide;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class SeriesSpider {
	private String url;
	private String keyword;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	// 抓取页面信息
	public String getHTMLDocument() {
		String content=null;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		
		try
		{
			HttpGet httpget=new HttpGet(this.getUrl()+"/?s="+this.getKeyword());
			RequestConfig config=RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
			httpget.setConfig(config);
			httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
					+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			CloseableHttpResponse response=httpclient.execute(httpget);
			try
			{
				HttpEntity entity=response.getEntity();
				if(entity!=null)
				{
					content=EntityUtils.toString(entity);
				}
			}finally {
				response.close();
			}
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try
			{
				httpclient.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	
	//Jsoup解析
	public LinkedHashMap<String, String> analyzeHTMLDocument(String html,String searchword) {
		Document document=Jsoup.parse(html);
		LinkedHashMap<String,String> hashtable=new LinkedHashMap<String,String>();
		Elements ele=document.select(".archive_title");
		Elements eles=ele.select("h2");
		for(Element link : eles)
		{
			Pattern psplit=Pattern.compile("/");
			Pattern psearchword=Pattern.compile(searchword);
			String title=psplit.split(link.text())[0];
			Matcher m=psearchword.matcher(title);
			if (m.find())
			{
				hashtable.put(title,link.select("a").attr("href"));
			}
		}
		return  hashtable;
	}
	
}
