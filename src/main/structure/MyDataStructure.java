package main.structure;

/* Remplacer "VotreStructureDeDonnees" par votre classe ABR ou une de vos classes de hachage */
public class MyDataStructure extends HachageCollision<GenericData<String>,String>{

	
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
			return null;
	 }
	
}
