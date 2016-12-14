package main.structure;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 
 * @author Sabri
 *
 */
public class HachageCollision<T extends GenericData<E>,E> extends Hachage<T,E>{
 	
	/**
	 * coef used to calculate hashCode
	 */
	public int a;
	
	/**
	 * Structure for hash table 
	 */
	List<ArrayList<T>> hashTab;
	
	/**
	 * 
	 * @param m
	 * @param a
	 */
	public HachageCollision(int m, int a)
	{
		this.m = m;
		this.a = a;
		hashTab = new ArrayList<ArrayList<T>>(m);
		
		for(int i=0; i<m; ++i){
			hashTab.add(new ArrayList<T>());
		}
	}
	
	/**
	 * 
	 */
	public List<ArrayList<T>> getHashTab(){
		return hashTab;
	}
	
	/**
	 * 
	 */
	public String getListsSize()
	{
		int subArrayLength=0,tmpValue;
		Map<Integer, Integer> l = new HashMap<Integer,Integer>();

		for(int i=0; i<hashTab.size(); i++){
			if(!hashTab.get(i).isEmpty()){
				subArrayLength = hashTab.get(i).size();
				if(l.containsKey(subArrayLength))
				{
					tmpValue = l.get(subArrayLength);
					tmpValue += 1;
					l.put(subArrayLength, tmpValue);
				}
				else
				{
					l.put(subArrayLength, 1);
				}
			}
		}
		StringBuilder s = new StringBuilder();
		
		s.append( m-getNbNoneElements(l) + " entrée(s) contenant 0 élément(s)\n");
		for( Integer key : l.keySet())
			s.append(l.get(key) + " entrée(s) contenant " + key + " élément(s)\n");
		return s.toString();
	}
	
	private int getNbNoneElements(Map<Integer, Integer> map){
		int result = 0;
		for(int i : map.keySet()){
			result += map.get(i);
		}
		return result;
	}
	
	@Override
	public void add(T data) {
		int location = h(data);
		if(hashTab.get(location).isEmpty()){
			hashTab.set(location,new ArrayList<T> (Arrays.asList(data)));			
		}
		else{
			if(!hashTab.get(location).contains(data)){
				hashTab.get(location).add(data);
			}
			else
				System.out.println("Already exists");
		}
	}

	@Override
	public boolean recherche(T data) {
		int location = h(data);
		boolean result;
		if(hashTab.get(location).isEmpty())
			result = false;
		else{
			if(hashTab.get(location).contains(data))
				result = true;
			else
				result = false;
		}
		return result;
	}

	@Override
	public int h(T data) {
		int x = 0;
		Character character;
		for(int i=0; i<data.cle.length(); ++i){
			character = new Character(data.cle.charAt(i));
			x+=Math.pow(a, i)*character.hashCode();
		}
		return (x%m);
	}

}
