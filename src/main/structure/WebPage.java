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
    public double rank;

    /**
     * list of WebPages that mentioned this web page
     */
    List<WebPage> mentionedIn;

    /**
     * list of WebPages accessible from this web page
     */
    List<WebPage> linksToPages;

    /**
     *
     * @param url
     */
    public WebPage(String url){
        this.url = url;
        mentionedIn = new ArrayList<WebPage>();
        linksToPages = new ArrayList<WebPage>();
    }

    /**
     *
     */
    public void addToMentionedIn(WebPage webPage){
        if(!mentionedIn.contains(webPage) && webPage != null)
            mentionedIn.add(webPage);
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
            if(this.url.equals(webPage.url) && this.mentionedIn.equals(webPage.mentionedIn))
                result = true;
            else
                result = false;
        }
        return result;
    }
}
