package main;

import java.util.List;

public class WebPage {
	
	/**
	 * url of the page
	 */
	String url;
	
	/**
	 * rank of page after classification
	 */
	double rank;
	
	/**
	 * list of WebPages that mentioned this web page
	 */
	List<WebPage> mentionedIn;
	
	/**
	 * list of WebPages accessible from this web page
	 */
	List<WebPage> linksToPages;
	
	public WebPage(String url){
		this.url = url;
	}
}
