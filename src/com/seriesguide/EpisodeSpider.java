package com.seriesguide;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EpisodeSpider {
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getHTMLDocument() {
		String content=null;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		try
		{
			HttpGet httpget=new HttpGet(this.getUrl());
			RequestConfig config=RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
			httpget.setConfig(config);
			//System.out.println(this.getUrl()+"/?s="+this.getKeyword());
			httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
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
	
	public LinkedHashMap<String, String> analyzeHTMLDocument(String html)
	{
		Pattern p1=Pattern.compile("\\d{1,2}¼¯");
		Pattern p2=Pattern.compile("720P");
		Document document=Jsoup.parse(html);
		LinkedHashMap<String,String> hashtable=new LinkedHashMap<String,String>();
		Elements eles=document.select("a");
		int i=1;
		for(Element ele : eles)
		{
			Matcher m1=p1.matcher(ele.text());
			Matcher m2=p2.matcher(ele.text());
			if(m1.find() && !m2.find())
			{
				if(i>9)
				{
					hashtable.put("Episode"+i,ele.attr("href"));
				}
				else
				{
					hashtable.put("Episode"+"0"+i,ele.attr("href"));
				}
				i++;
			}
		}
		return  hashtable;
	}
}
