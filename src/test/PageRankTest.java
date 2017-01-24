package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import bing.DegreesSearchEngine;
import google.PageRankSearchEngine;
import main.structure.DataKeyWord;
import main.structure.WebPage;
import yahoo.NormalizedDegreesSearchEngine;

public class PageRankTest {
	
	static DegreesSearchEngine bing;
	
	/* (Remarque) Méthode exécutée avant la série de tests (évite de le répéter dans chaque test) */
	@BeforeClass
	public static void init(){
		bing = new DegreesSearchEngine("data/new.txt", "data/index_small.txt");
		bing.readWebPages();
		bing.readKeyWords();
	}
	
	@Test
	public void readWebPagesFile(){
		
		HashMap<String, WebPage> hm = bing.getWebpages();
		
		// TODO Le prof n'ignore pas le fichier error ???!!! 
		assertTrue(hm.containsKey("purl.org/metadata/dublin_core_elements"));
		assertTrue(hm.containsKey("plus.franceculture.fr/partenaires/avignon-et-pays-de-vaucluse"));
		assertTrue(hm.containsKey("www.dokuwiki.org/fr:namespaces"));
		assertTrue(hm.containsKey("www.facebook.com/login.php?next=https%3A%2F%2Fwww.facebook.com%2Fhome.php"));
		
		WebPage wp = hm.get("twitter.com/amoreeedignus/status/511925269805170688/photo/1");
		
		assertTrue(wp != null);
		
		assertTrue(wp.getInputArc().contains(new WebPage("twitter.com/univavignon")));
		assertEquals(1, wp.getInputArc().size());
		assertEquals(0, wp.getOutputArc().size());
		/* Remarque : l'arc entrant est : ceri.univ-avignon.fr/ */
		
		wp = hm.get("plus.google.com/100566320038879692337");
		
		assertTrue(wp != null);
		
		assertTrue(wp.getOutputArc().contains(new WebPage("fonts.gstatic.com/robots.txt")));
		assertEquals(1, wp.getInputArc().size());
		assertEquals(16, wp.getOutputArc().size());
		/* Remarques : 
		 * L'arc entrant est www.univ-avignon.fr/
		 * Les arcs sortants sont :
		 * 	    schema.org/robots.txt
				fonts.gstatic.com/robots.txt
				www.google.fr/robots.txt
				news.google.fr/robots.txt
				mail.google.com/robots.txt
				drive.google.com/robots.txt
				translate.google.fr/robots.txt
				books.google.fr/robots.txt
				www.blogger.com/robots.txt
				video.google.fr/robots.txt
				www.google.com/robots.txt
				history.google.com/robots.txt
				apis.google.com/robots.txt
				profiles.google.com/robots.txt
				csi.gstatic.com/robots.txt
				talkgadget.google.com/robots.txt
		 */
		
		wp = hm.get("www.univ-avignon.fr/fr/formations/choix/fiche/diplome/licence-informatique-1/presentation.html");
		
		assertTrue(wp != null);
		assertEquals(11, wp.getOutputArc().size());
		/* Remarque : Les arcs sortant sont :
		 * www-test.univ-avignon.fr/robots.txt
		 * ceri.univ-avignon.fr/fr/informations-utiles/envoyerami.html?tipUrl=http%3A%2F%2Fwww-test.univ-avignon.fr%2Ffr%2Flicence-informatique.html
		 * ceri.univ-avignon.fr/fr/informations-utiles/envoyerami.96d531d.delayed
		 * www-test.univ-avignon.fr/fr/print/licence-informatique.html
		 * ceri.univ-avignon.fr/fr/mini-site/ceri/etudes/master-informatique/parcours-informatique-sante.html
		 * ceri.univ-avignon.fr/fr/informations-utiles/envoyerami.html?tipUrl=http%3A%2F%2Fwww-test.univ-avignon.fr%2Ffr%2Fmaster-informatique.html
		 * ceri.univ-avignon.fr/fr/informations-utiles/envoyerami.9706c90.delayed
		 * www-test.univ-avignon.fr/fr/print/master-informatique.html
		 * ceri.univ-avignon.fr/fr/informations-utiles/envoyerami.html?tipUrl=http%3A%2F%2Fwww-test.univ-avignon.fr%2Ffr%2Fdoctorat-informatique.html
		 * ceri.univ-avignon.fr/fr/informations-utiles/envoyerami.972c86d.delayed
		 * www-test.univ-avignon.fr/fr/print/doctorat-informatique.html
		 */
		
	}
	
	@Test
	public void readKeyWordsFile(){
	
		DataKeyWord informelles = bing.getKeyWords().get("informelles");
		
		assertTrue(informelles.getWebpages().contains("arcmc.hypotheses.org/category/methodologie.html"));
		assertTrue(informelles.getWebpages().contains("pock.hypotheses.org/204.html"));
		assertTrue(informelles.getWebpages().contains("www.umrespace.org/TES_2014-04.htm"));
	
		assertEquals(10, informelles.getWebpages().size());
		/* Remarques : Les pages contenues mentionnant informelles sont :
		 *  arcmc.hypotheses.org/category/methodologie.html
		 	arcmc.hypotheses.org/tag/archives-de-la-recherche-et-des-chercheurs.html
		 	pock.hypotheses.org/148.html
		 	pock.hypotheses.org/204.html
		 	pock.hypotheses.org/author/dacos.html
		 	pock.hypotheses.org/category/actualites-news.html
		 	pock.hypotheses.org/category/actualites.html
		 	pock.hypotheses.org/date/2013/09.html
		 	pock.hypotheses.org/index.html
		 */
		
	}
	
	@Test
	public void createSubGraph(){
		
		List<String> l_wp = new ArrayList<>();
		l_wp.add("informatique");
		List<WebPage> graph = bing.getSearchSubGraph(l_wp);
		
		assertEquals(832, graph.size());
		
		WebPage wp = graph.get(graph.indexOf(new WebPage("www.univ-avignon.fr/fr/formations/choix/fiche/diplome/licence-informatique-1/presentation.html")));
		
		assertTrue(wp != null);
		
		assertEquals(4, wp.getOutputArc().size());
		/* Remarque : Les arcs sortant sont :
		 * www-test.univ-avignon.fr/fr/print/licence-informatique.html
		 * ceri.univ-avignon.fr/fr/mini-site/ceri/etudes/master-informatique/parcours-informatique-sante.html
		 * www-test.univ-avignon.fr/fr/print/master-informatique.html
		 * www-test.univ-avignon.fr/fr/print/doctorat-informatique.html
		 */
		
	}
	
	@Test
	public void degreesSearchEngine(){

		List<String> l_wp = new ArrayList<>();
		l_wp.add("informatique");
		List<WebPage> searchResults = bing.search(l_wp);
		
		WebPage wp = searchResults.get(searchResults.indexOf(new WebPage("plus.franceculture.fr/partenaires/unisciel2679.html")));
		assertEquals(0, wp.getRank(), 1E-5);
		
		wp = searchResults.get(searchResults.indexOf(new WebPage("www.sncf.com/fr/services/services-en-gare.html")));
		assertEquals(1, wp.getRank(), 1E-5);	
		
	}
	
	@Test
	public void normalizedDegreesSearchEngine(){
		
		NormalizedDegreesSearchEngine yahoo = new NormalizedDegreesSearchEngine("data/new.txt", "data/index_small.txt");
		yahoo.readWebPages();
		yahoo.readKeyWords();
		
		List<String> l_wp = new ArrayList<>();
		l_wp.add("informatique");
		List<WebPage> searchResults = yahoo.search(l_wp);

		WebPage wp = searchResults.get(searchResults.indexOf(new WebPage("www.univ-avignon.fr/fr/international/index.html")));
		assertEquals(0.25, wp.getRank(), 1E-5);

		wp = searchResults.get(searchResults.indexOf(new WebPage("www-test.univ-avignon.fr/fr/print/master-informatique.html")));
		assertEquals(0.0909090, wp.getRank(), 1E-5);		
		
	}
	
	@Test
	public void pageRankSearchEngine(){
		
		PageRankSearchEngine google = new PageRankSearchEngine("data/new.txt", "data/index_small.txt", 0.05, 2);
		google.readWebPages();
		google.readKeyWords();

		List<String> l_wp = new ArrayList<>();
		l_wp.add("informatique");
		List<WebPage> searchResults = google.search(l_wp);
		
		WebPage wp = searchResults.get(searchResults.indexOf(new WebPage("www.geo.univ-avignon.fr/Actualites.htm")));
		assertEquals(3.0837200097078293E-6, wp.getRank(), 1E-10);
		
		wp = searchResults.get(searchResults.indexOf(new WebPage("anlea.org/formation-lea/presentation-formation-LEA.html")));
		assertEquals(1.6564363558617048E-6, wp.getRank(), 1E-10);
		
		wp = searchResults.get(searchResults.indexOf(new WebPage("www.planete-sciences.org/robot/indexa275.html")));
		assertEquals(4.4873480260724234E-7, wp.getRank(), 1E-10);
				
	}

	
//	@Test
//	public void fichierIndexComplet(){
//		//Test utilisant le fichier "index.txt"
//	}
//		
}
