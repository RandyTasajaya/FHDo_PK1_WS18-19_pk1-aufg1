package bibman;

public class BibliographieManager {

	public static void main(String[] args) {
		
		Buch buch1 = new Buch("Conrad Barski", "Land of Lisp", 2010, "No Starch Press", "9781593272814");
		Artikel artikel1 = new Artikel("Donald E. Knuth", "Computer programming as an art", 1974, "Communications of the ACM", 12);
		Webseite webseite1 = new Webseite("Christian Ullenbloom", "Java ist auch eine Insel", 2016, "http://openbook.rheinwerk-verlag.de/javainsel/");
		
		buch1.druckeEintrag();
		artikel1.druckeEintrag();
		webseite1.druckeEintrag();
		
		System.out.println("\n\"" + buch1.getTitel() + "\" wurde vor " + buch1.berechneAlter() + 
				" Jahren veröffentlicht.");
	}
}