package main;

import java.util.Map;

import main.structure.MyDataStructure;

public class SearchEngine {
	
	/**
	 * 
	 */
	Map<String,WebPage> webPages;
	
	/**
	 * 
	 */
	MyDataStructure keyWords;
	
	/**
	 * 
	 */
	String webPagesFileName;
	
	/**
	 * 
	 */
	String keyWordsFileName;
	
	public SearchEngine(String webPagesFileName, String keyWordsFileName){
		this.webPagesFileName = webPagesFileName;
		this.keyWordsFileName = keyWordsFileName;
	}

}
