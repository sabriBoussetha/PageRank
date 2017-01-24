package main.structure;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import main.structure.MyDataStructure;

/**
 * Created by sabri on 30/12/16.
 */
public abstract class SearchEngine {

    /**
     *
     */
    private HashMap<String,WebPage> webPages;

    /**
     *
     */
    private MyDataStructure keyWords;

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(webPagesFileName), "UTF8"))) {
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
	                    	if(!url2.equals(""))
	                    		webPages.get(url2).addToMentionedIn(webPageFrom);
	                    }
	                }
	                
	                if(webPageFrom != null){
	                	if(webPages.containsKey(from)){
	                		webPages.get(from).addToOutputArc(new WebPage(url));
	                		if(!url2.equals(""))
	                			webPages.get(from).addToOutputArc(new WebPage(url2));
	                	}else{
	                		webPageFrom.addToOutputArc(new WebPage(url));
	                		if(!url2.equals(""))
	                			webPageFrom.addToOutputArc(new WebPage(url2));
	                		webPages.put(from, webPageFrom);
	                	}
	                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */    
	public void readKeyWords(){
		  keyWords = new MyDataStructure(100, 3);
		  String[] line;
		  String link, currentKeyWord;
		  int currentIndex = -1;
		  Path path = Paths.get(keyWordsFileName);
		  try{
		      List<String> lines = Files.readAllLines(path,  StandardCharsets.UTF_8);
		      List<DataKeyWord> dataKeyWordList = new ArrayList<DataKeyWord>();
		      for (int i = 0; i < lines.size(); i++) {
		          if(!lines.get(i).contains("\t")){
			  currentIndex++;
			  currentKeyWord = lines.get(i);
			  dataKeyWordList.add(new DataKeyWord(currentKeyWord));
		  }
		  else{
		      line = lines.get(i).split("\t");
		      if(line.length>1) {
		          link = line[1];
		          if (link.contains(" ") && webPages.containsKey(link.split(" ")[1])) {
		        	  dataKeyWordList.get(currentIndex).getWebpages().add(link.split(" ")[1]);
		          }
		              
		          }
		      }
		
		  }
		  for(DataKeyWord dataKeyWord : dataKeyWordList)
			  keyWords.add(dataKeyWord);
		  
		  }catch(IOException e){
		      e.printStackTrace();
		  }
	}
    

    /**
     *
     * @param list
     * @return
     */
	public List<WebPage> getSearchSubGraph(List<String> list){
		List<WebPage> subGraph = new ArrayList<>();
		for(String keyWord : list){
			DataKeyWord dataKeyWord = keyWords.get(keyWord);
			for(String foundIn : dataKeyWord.getWebpages()){
				WebPage webPage = webPages.get(foundIn);
				subGraph.add(getWebPageForSubGraph(webPage, dataKeyWord));
			}
		}
		return subGraph;
	}
	
	/**
	 * 
	 * @param webPage
	 * @param dataKeyWord
	 * @return
	 */
    private WebPage getWebPageForSubGraph(WebPage webPage, DataKeyWord dataKeyWord) {
    	WebPage webPageForSubGraph = new WebPage(webPage.url);
    	
    	for(WebPage inputArc : webPage.getInputArc()){
    		if(dataKeyWord.getWebpages().contains(inputArc.url)){
    			webPageForSubGraph.addToMentionedIn(inputArc);
    		}
    	}
    	
    	for(WebPage outputArc : webPage.getOutputArc()){
    		if(dataKeyWord.getWebpages().contains(outputArc.url)){
    			webPageForSubGraph.addToOutputArc(outputArc);
    		}
    	}
		return webPageForSubGraph;
	}

	/**
     * 
     * @return
     */
    public HashMap<String,WebPage> getWebpages(){
    	return webPages;
    }
    
    /**
     * 
     * @return
     */
    public MyDataStructure getKeyWords(){
    	return keyWords;
    }

    public abstract void computeRank(List<WebPage> webPages, List<String> keyWords);

    /**
     * 
     * @param keyWords
     * @return
     */
    public List<WebPage> search(List<String> keyWords) {
    	List<WebPage> searchResult = null;
        for(String keyWord : keyWords){
        	searchResult = getSearchSubGraph(keyWords);
            // compute rank
            computeRank(searchResult, keyWords);
            Collections.sort(searchResult, new Comparator<WebPage>() {
                @Override
                public int compare(WebPage o1, WebPage o2) {
                    if(o1.getRank() > o2.getRank())
                        return 1;
                    else
                        if(o1.getRank() < o2.getRank())
                            return -1;
                        else
                            return 0;
                }
            });
        }
        return searchResult;
    }
}
