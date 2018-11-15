package bibman;

import java.util.Iterator;

public class Testen {

	public static void main(String[] args) {
		
		Buch buch1 = new Buch(new Autor("Conrad", " Barski"), "Land of Lisp", 2010, 
				"No Starch Press", "9781593272814");
		Artikel artikel1 = new Artikel(new Autor("Donald E.", "Knuth"), "Computer programming as an art", 1974, 
				"Communications of the ACM", 12);
		Webseite webseite1 = new Webseite(new Autor("Christian", "Ullenbloom"), "Java ist auch eine Insel", 2016, 
				"http://openbook.rheinwerk-verlag.de/javainsel/");
		
		/*buch1.druckeEintrag();
		artikel1.druckeEintrag();
		webseite1.druckeEintrag();
		
		System.out.println("\n\"" + buch1.getTitel() + "\" wurde vor " + buch1.berechneAlter() + 
				" Jahren veröffentlicht.");
		
		System.out.println("\n" + buch1.erzeugeZitierschluessel());
		System.out.println(artikel1.erzeugeZitierschluessel() + "\n");*/

		
		
		BibManager manager1 = new BibManager();
		
		manager1.hinzufuegen(buch1);
		manager1.hinzufuegen(artikel1);
		manager1.hinzufuegen(webseite1);
		manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"), "Buch Titel", 2018, "Verlagsname", "ISBN-Zahlen"));
		manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"), "Buch Titel - 2", 2018, "Verlagsname", "ISBN-Zahlen"));
		
		manager1.druckeAlleEintraege();
		
		System.out.println("\nDer neueste Eintrag ist vom Jahr " + manager1.sucheNeuestenEintrag() + ".");
		System.out.println("Der Durschnitt der Erscheinungsjahre ist " +
				(int)manager1.berechneErscheinungsjahr() + ".\n");
				
		
		//Mit ArrayList besser? Aber Aufgabe sagt nur "Array".
		Primaerquelle[] quellen = new Primaerquelle[manager1.getSize()];

		Iterator<BibEintrag> it = manager1.getBibEintraege().iterator();

		for(int i = 0; i < quellen.length; i++) {
			if(it.hasNext()) {
				BibEintrag eintrag = it.next();
				if(!eintrag.isWebseite()) {
					quellen[i] = eintrag.isBuch() ? (Buch)eintrag : (Artikel)eintrag;
				} else {
					i--;
				}
			} else {
				break;
			}
		}
		
		manager1.druckeZitierschluessel(quellen);
		
		
		System.out.println("\nAnzahl Eintraege Autor (Object Autor aufgerufen durch Methode) " + 
				manager1.getEintrag(3).getAutor().getFullname() + ": " + 
				manager1.gibAnzahlEintraege(manager1.getEintrag(3).getAutor()));
		
		System.out.println("Anzahl Eintraege Autor (Object Autor aufgerufen durch explizites Nameneingabe) " + 
				manager1.getEintrag(3).getAutor().getFullname() + ": " + 
				manager1.gibAnzahlEintraege(new Autor("Vorname", "Nachname")));
		
		System.out.println("Anzahl Eintraege Autor (Object Autor aufgerufen durch explizites Nameneingabe) " + 
				manager1.getEintrag(2).getAutor().getFullname() + ": " + 
				manager1.gibAnzahlEintraege(new Autor("Christian", "Ullenbloom")));
		
		
	}
}