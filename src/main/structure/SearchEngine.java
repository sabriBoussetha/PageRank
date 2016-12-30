package main.structure;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.structure.MyDataStructure;

/**
 * Created by sabri on 30/12/16.
 */
public abstract class SearchEngine {

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

    /**
     *
     * @param webPagesFileName
     * @param keyWordsFileName
     */
    public SearchEngine(String webPagesFileName, String keyWordsFileName){
        this.webPagesFileName = webPagesFileName;
        this.keyWordsFileName = keyWordsFileName;
        webPages = new HashMap<String, WebPage>();
    }

    /**
     *
     */
    public void readWebPages(){
        String currentString;
        try (BufferedReader br = new BufferedReader(new FileReader(webPagesFileName))) {
            currentString = br.readLine();
            String url, url2, from;
            while((currentString = br.readLine()) != null){
                url = currentString.split("\t")[7].split("//")[1];
                url2 = currentString.split("\t")[8];
                if(!url2.equals(""))
                    url2 = url2.substring(2);

                from = currentString.split("\t")[9].split("from")[1];
                WebPage webPageFrom = null;
                if(from.length() > 2){
                    from = from.substring(0,from.length()-1).split("//")[1];
                    webPageFrom = new WebPage(from);
                }

                if(!webPages.containsKey(url)){
                    WebPage webPage = new WebPage(url);
                    webPage.addToMentionedIn(webPageFrom);
                    webPages.put(url, webPage);
                }
                else{
                    webPages.get(url).addToMentionedIn(webPageFrom);
                }
                if(!url.equals(url2)){
                    if(!webPages.containsKey(url2)){
                        if(!url2.equals("")){
                            WebPage webPage2 = new WebPage(url2);
                            webPage2.addToMentionedIn(webPageFrom);
                            webPages.put(url2, webPage2);
                        }
                    }
                    else{
                        webPages.get(url2).addToMentionedIn(webPageFrom);
                    }
                }
            }
            System.out.println(webPages.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void readKeyWords(){
        keyWords = new MyDataStructure(100, 3);
        String currentString;
        try(BufferedReader br = new BufferedReader(new FileReader(keyWordsFileName))){
            while((currentString = br.readLine()) != null){
                String keyWord = currentString.split("^\t")[0];
                if(!keyWord.equals(""))
                    keyWords.add(new GenericData<String>(currentString.split("^\t")[0]));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param list
     * @return
     */
    public List<WebPage> getSearchSubGraph(List<String> list){
        List<WebPage> subGraph = new ArrayList<WebPage>();
        String[] line;
        String link;
        for(String keyWord : list){
            if(keyWords.recherche(new GenericData<String>(keyWord))){
                Path path = Paths.get(keyWordsFileName);
                try{
                    List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
                    System.out.println(lines.indexOf(keyWord));
                    for (int i = lines.indexOf(keyWord)+1; i < lines.size(); i++) {
                        if(!lines.get(i).contains("\t"))
                            break;
                        line = lines.get(i).split("\t");
                        if(line.length>1) {
                            link = line[1];
                            if (link.contains(" ") && webPages.containsKey(link.split(" ")[1])) {
                                subGraph.add(new WebPage(link));
                            }
                        }
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println(subGraph.size());
        return subGraph;
    }

    public abstract void computeRank(List<WebPage> webPages, List<String> keyWords);

    public abstract List<WebPage> search(List<String> keyWords);
}
