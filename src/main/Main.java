package main;

public class Main {

	public static void main(String[] args) {
		SearchEngine searchEngine = new SearchEngine("resource/new.txt", "resource/index_small.txt");
		searchEngine.readWebPages();
		searchEngine.readKeyWords();
	}

}
