package google;

import java.util.List;
import main.structure.SearchEngine;
import main.structure.WebPage;

/**
 * Created by sabri on 30/12/16.
 */
public class PageRankSearchEngine extends SearchEngine{
	
	double [][]probabilityMatrix;
	
	double p;
	
	int n;

	/**
	 * 
	 * @param webPagesFileName
	 * @param keyWordsFileName
	 * @param p
	 * @param n
	 */
    public PageRankSearchEngine(String webPagesFileName, String keyWordsFileName, double p, int n) {
        super(webPagesFileName, keyWordsFileName);
        this.p = p;
        this.n = n;
    }

    @Override
    public void computeRank(List<WebPage> webPages, List<String> keyWords) {
    	probabilityMatrix = new double[webPages.size()][webPages.size()];
    	
    	for(int i = 0; i < webPages.size(); ++i){
    		for(int j = 0; j < webPages.size(); ++j){
    			probabilityMatrix[i][j] =  0;
    		}
    	} 
    	
    	
    	for(WebPage webPage : webPages){
    		webPage.setRank(0.0);
    	}
    	webPages.get(0).setRank(1.0);
    	
    	for(int j = 0; j < webPages.size(); ++j){
    			int inputArcNb = webPages.get(j).getInputArc().size();
    			List<WebPage> inputArc = webPages.get(j).getInputArc();
    			for(WebPage inputArcPage : inputArc){
    				if(inputArcNb > 0){
	    				probabilityMatrix[j][webPages.indexOf(new WebPage(inputArcPage.url))] = ((double)1/(double)inputArcNb);
    				}
    			}
    	} 
    	
    	for(int i = 0; i < webPages.size(); ++i){
    		for(int j = 0; j < webPages.size(); ++j){
    			probabilityMatrix[i][j]= ((1-p) * probabilityMatrix[i][j]) + (p/(double)webPages.size());
    		}
    	}
    	
	    double[][] result = probabilityMatrix;
	    for(int k = 0; k < n; ++k){
	    	result = matrixMultiplication(result,probabilityMatrix);
	    }
	    
    	for(int i = 0; i < webPages.size(); ++i){
    		double pageRank = vectorMatrixMultiplication(probabilityMatrix, webPages, i);
    		webPages.get(i).setRank(pageRank);
    	}
    }
    
    /**
     * Method used to multiplicate vector of probabily with the matrix  
     * 
     * @param probabilityMatrix2
     * @param webPages
     * @param column
     * @return
     */
    private double vectorMatrixMultiplication(double[][] probabilityMatrix2, List<WebPage> webPages, int column) {
    	double score = 0;
    	for(int i = 0; i < probabilityMatrix2.length; ++i){
    		score += probabilityMatrix2[i][column] * webPages.get(i).getRank();
    	}
		return score;
	}

    /**
     * 
     * @param resultMatrix
     * @param probabilityMatrix2
     * @return
     */
	private double[][] matrixMultiplication(double[][] resultMatrix, double[][] probabilityMatrix2){
		double[][] result = new double[resultMatrix.length][resultMatrix.length];
		int sum = 0; 
    	for(int i = 0; i < resultMatrix.length; ++i){
    		for(int j = 0; j < resultMatrix.length; ++j){
    			sum = 0;
    			for(int k = 0; k < resultMatrix.length; ++k){
    				sum += resultMatrix[i][k] * probabilityMatrix2[k][j];
    			}
    			result[i][j] = sum;
    		}
    	}
    	return result;
    }
}
