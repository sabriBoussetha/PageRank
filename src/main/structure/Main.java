package main.structure;

import bing.DegreesSearchEngine;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabri on 30/12/16.
 */
public class Main {

    public static void main(String[] args) {
        DegreesSearchEngine searchEngine = new DegreesSearchEngine("resource/new.txt", "resource/index_small.txt");
        searchEngine.readWebPages();
        searchEngine.readKeyWords();
        List<String> wd = new ArrayList<String>();
        wd.add("informatique");
        searchEngine.getSearchSubGraph(wd);
    }

}
