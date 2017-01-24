package yahoo;

import java.util.List;
import main.structure.SearchEngine;
import main.structure.WebPage;

/**
 * Created by sabri on 30/12/16.
 */
public class NormalizedDegreesSearchEngine extends SearchEngine{

    public NormalizedDegreesSearchEngine(String webPagesFileName, String keyWordsFileName) {
        super(webPagesFileName, keyWordsFileName);
    }

    @Override
    public void computeRank(List<WebPage> webPages, List<String> keyWords) {
    	double rank = 0;
    	for (WebPage webPage : webPages) {
			List<WebPage> inputArc = webPage.getInputArc();
			for (WebPage inputArcWebPage : inputArc) {
				WebPage page = this.getWebpages().get(inputArcWebPage.url);
				rank += ((double)1/(double)page.getOutputArc().size());
			}
			webPage.setRank(rank);
			rank = 0;
		}
    }
}
