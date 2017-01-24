package main.structure;

import java.util.ArrayList;
import java.util.List;

public class DataKeyWord extends GenericData<String>{

    public DataKeyWord(String data) {
		super(data);
		mentionedInPages = new ArrayList<>();
		keyWord = data;
	}

	/**
     *
     */
    String keyWord;
    /**
     *
     */
    private List<String> mentionedInPages;
    
    /**
     * 
     */
    public List<String> getWebpages(){
    	return mentionedInPages;
    }
}
