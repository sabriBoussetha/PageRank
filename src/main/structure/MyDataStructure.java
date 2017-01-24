package main.structure;

/* Remplacer "VotreStructureDeDonnees" par votre classe ABR ou une de vos classes de hachage */
public class MyDataStructure extends HachageCollision<DataKeyWord,String>{


    // D'après le m=100 pour le petit index_small.txt
    public MyDataStructure(int m, int k) {
        super(m, k);
    }

    /**
     * Method which returns the data of key <s>
     * @param s The key searched
     * @return The data of key <s> if any, null otherwise
     */
    public DataKeyWord get(String s){
    	DataKeyWord data = new DataKeyWord(s);
    	DataKeyWord dataKeyWord = null;
        int location = h(data);
        if(hashTab.get(location).contains(data)){
        	for(DataKeyWord dataToFind : hashTab.get(location)){
        		if(dataToFind.keyWord.equals(s)){
        			return dataToFind;
        		}
        	}
        }
        return dataKeyWord;
    }

}
