package bing;

import java.util.Collections;
import java.util.Comparator;
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

        }
    }

    @Override
    public List<WebPage> search(List<String> keyWords){
        for(String keyWord : keyWords){
            List<WebPage> serchResult = getSearchSubGraph(keyWords);
            // compute rank
            computeRank(serchResult, keyWords);
            Collections.sort(serchResult, new Comparator<WebPage>() {
                @Override
                public int compare(WebPage o1, WebPage o2) {
                    if(o1.rank > o2.rank)
                        return 1;
                    else
                        if(o1.rank < o2.rank)
                            return -1;
                        else
                            return 0;
                }
            });
        }
        return null;
    }
}
