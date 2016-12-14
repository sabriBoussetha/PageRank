package main;

public class Main {

	public static void main(String[] args) {
		SearchEngine searchEngine = new SearchEngine("resource/new.txt", "keyWordsFileName");
		searchEngine.readWebPages();
	}

}
