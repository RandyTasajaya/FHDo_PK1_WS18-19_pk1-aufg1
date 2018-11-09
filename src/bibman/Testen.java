package bibman;

public class Testen {

	public static void main(String[] args) {
		
		Buch buch1 = new Buch("Conrad Barski", "Land of Lisp", 2010, "No Starch Press", "9781593272814");
		Artikel artikel1 = new Artikel("Donald E. Knuth", "Computer programming as an art", 1974, "Communications of the ACM", 12);
		Webseite webseite1 = new Webseite("Christian Ullenbloom", "Java ist auch eine Insel", 2016, "http://openbook.rheinwerk-verlag.de/javainsel/");
		
		buch1.druckeEintrag();
		artikel1.druckeEintrag();
		webseite1.druckeEintrag();
		
		System.out.println("\n\"" + buch1.getTitel() + "\" wurde vor " + buch1.berechneAlter() + 
				" Jahren veröffentlicht.");
		
		System.out.println("\n" + buch1.erzeugeZitierschluessel());
		System.out.println(artikel1.erzeugeZitierschluessel() + "\n");

		
		BibManager manager1 = new BibManager(4);
		
		manager1.hinzufuegen(buch1);
		manager1.hinzufuegen(artikel1);
		manager1.hinzufuegen(webseite1);
		manager1.hinzufuegen(new Buch("Vorname Nachname", "Buch Titel", 2018, "Verlag", "ISBN-Zahlen"));
		
		manager1.druckeAlleEintraege();
		System.out.println("\nDer neueste Eintrag ist vom Jahr " + manager1.sucheNeuestenEintrag() + ".");
		System.out.println("Der Durschnitt der Erscheinungsjahre ist " + (int)manager1.berechneErscheinungsjahr() + ".\n");
				
		//Mit ArrayList besser? Aber Aufgabe sagt nur "Array".
		Primaerquelle[] quellen = new Primaerquelle[manager1.getIndex()];
		
		int j = 0;
		for(int i = 0; i < quellen.length; i++) {
			if(j < manager1.getIndex()) { 
				if(!manager1.getEintrag(j).isWebseite()) {
					if(manager1.getEintrag(j).isBuch()) {
						quellen[i] = (Buch)manager1.getEintrag(j);
					} else {
						quellen[i] = (Artikel)manager1.getEintrag(j);
					}
				} else {
					i--;
				}
				j++;
			} else {
				break;
			}
		}
		
		manager1.druckeZitierschluessel(quellen);
	}
}