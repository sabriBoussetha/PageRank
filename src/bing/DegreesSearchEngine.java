package bing;

import java.util.List;
import main.structure.SearchEngine;
import main.structure.WebPage;

/**
 * Created by sabri on 30/12/16.
 */
public class DegreesSearchEngine extends SearchEngine{

    public DegreesSearchEngine(String webPagesFileName, String keyWordsFileName){
        super(webPagesFileName,keyWordsFileName);
    }

    @Override
    public void computeRank(List<WebPage> webPages, List<String> keyWords){
        for(WebPage webPage : webPages){
        	webPage.setRank(webPage.getInputArc().size());
        }
    }
}
