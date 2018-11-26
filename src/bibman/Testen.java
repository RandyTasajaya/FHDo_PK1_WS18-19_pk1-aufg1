package bibman;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Testen {

	public static void main(String[] args) 
		throws DoppelterBibEintragException {
		
		Buch buch1 = new Buch(new Autor("Conrad", "Barski"), "Land of Lisp", 2010, 
				"No Starch Press", "9781593272814");
		Artikel artikel1 = new Artikel(new Autor("Donald E.", "Knuth"), "Computer programming as an art", 1974, 
				"Communications of the ACM", 12);
		Webseite webseite1 = new Webseite(new Autor("Christian", "Ullenbloom"), "Java ist auch eine Insel", 2016, 
				"http://openbook.rheinwerk-verlag.de/javainsel/");
		
		BibManager manager1 = new BibManager();
		
		manager1.hinzufuegen(buch1);
		manager1.hinzufuegen(artikel1);
		manager1.hinzufuegen(webseite1);
		manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"), "Buch Titel", 2018, "Verlagsname", "ISBN-Zahlen"));
		manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"), "Buch Titel - 2", 2018, "Verlagsname", "ISBN-Zahlen"));
		
		manager1.druckeAlleEintraege();
		
		System.out.println();
		manager1.sucheNeuestenEintrag();
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
		
		System.out.println("Anzahl Eintraege Autor (Object Autor aufgerufen durch "
				+ "\n    neue Erstellung von Object Autor (explizites Nameneingabe)) " + 
				manager1.getEintrag(3).getAutor().getFullname() + ": " + 
				manager1.gibAnzahlEintraege(new Autor("Conrad", "Barski")));
		
		System.out.println("Anzahl Eintraege Autor (Object Autor aufgerufen durch "
				+ "\n    neue Erstellung von Object Autor (explizites Nameneingabe)) " + 
				"Randy Tasajaya : " + 
				manager1.gibAnzahlEintraege(new Autor("Randy", "Tasajaya")));
		
		
		System.out.println();
		try {
			manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"), "Buch Titel", 2018, "Verlagsname", "ISBN-Zahlen"));
		}
		catch(DoppelterBibEintragException e) {
			System.out.println("Exception aufgefangen.");
		}
		
		
		System.out.println();
		File csv = new File("BibManager.csv");
		try {
			csv.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("A CSV file (" + csv.getName() + ") was created!");
		
		// Für BibManager mit nur einem Element:
		/*BibManager manager2 = new BibManager();
		manager2.hinzufuegen(buch1);*/

		try {
			manager1.exportiereEintraegeAlsCsv(csv);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		System.out.println();
		
	}
}
