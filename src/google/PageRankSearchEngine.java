package google;

import java.util.List;
import main.structure.SearchEngine;
import main.structure.WebPage;

/**
 * Created by sabri on 30/12/16.
 */
public class PageRankSearchEngine extends SearchEngine{

    public PageRankSearchEngine(String webPagesFileName, String keyWordsFileName) {
        super(webPagesFileName, keyWordsFileName);
    }

    @Override
    public void computeRank(List<WebPage> webPages, List<String> keyWords) {

    }

    @Override
    public List<WebPage> search(List<String> keyWords) {
        return null;
    }
}
