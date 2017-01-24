package main.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabri on 30/12/16.
 */
public class WebPage {

    /**
     * url of the page
     */
    public String url;

    /**
     * rank of page after classification
     */
    private double rank;

    /**
     * list of WebPages that mentioned this web page
     */
    List<WebPage> inputArc;

    /**
     * list of WebPages accessible from this web page
     */
    List<WebPage> outputArc;

    /**
     *
     * @param url
     */
    public WebPage(String url){
        this.url = url;
        inputArc = new ArrayList<WebPage>();
        outputArc = new ArrayList<WebPage>();
    }

    /**
     * 
     * @param webPage
     */
    public void addToMentionedIn(WebPage webPage){
        if(!inputArc.contains(webPage) && webPage != null)
            inputArc.add(webPage);
    }
    
    /**
     * 
     * @param webPage
     */
    public void addToOutputArc(WebPage webPage){
    	if(!outputArc.contains(webPage) && webPage != null)
            outputArc.add(webPage);
    }

    /**
     * 
     */
    public List<WebPage> getInputArc(){
    	return inputArc;
    }
    
    /**
     * 
     */
    public List<WebPage> getOutputArc(){
    	return outputArc;
    }
    
    /**
     * 
     * @param inputArc
     */
    public void setInputArc(List<WebPage> inputArc){
    	this.inputArc = inputArc;
    }
    
    /**
     * 
     * @param outputArc
     */
    public void setOutputArc(List<WebPage> outputArc){
    	this.outputArc = outputArc;
    }
    
    /**
     * 
     */
    public double getRank(){
    	return rank;
    }
    
    public void setRank(double rank){
    	this.rank = rank;
    }
    /**
     *
     */
    @Override
    public boolean equals(Object o){
        boolean result = false;
        if(o == null) result = false;
        else{
            WebPage webPage = (WebPage) o;
            if(this.url.equals(webPage.url)) //&& this.mentionedIn.equals(webPage.mentionedIn)){
            	//if(this.linksToPages.equals(webPage.linksToPages))
            		result = true;
            //}
            else
                result = false;
        }
        return result;
    }
}
