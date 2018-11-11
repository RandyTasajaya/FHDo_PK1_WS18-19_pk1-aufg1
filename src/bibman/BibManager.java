package bibman;

import java.util.*;

public class BibManager {
	
	private List<BibEintrag> bibEintraege;
		
	public BibManager() {
		bibEintraege = new ArrayList<>();
	}
	
	public int getSize() {
		return bibEintraege.size();
	}
	
	public List<BibEintrag> getBibEintraege() {
		return bibEintraege;
	}
	
	public BibEintrag getEintrag(int index) {
		return bibEintraege.get(index);
	}
	
	public void hinzufuegen(BibEintrag eintrag) {
		bibEintraege.add(eintrag);
	}
	
	public void druckeAlleEintraege() {
		for(BibEintrag eintrag : bibEintraege) {
			eintrag.druckeEintrag();
		}
	}
	
	public int sucheNeuestenEintrag() {
		int result = bibEintraege.get(0).getJahr();
		
		Iterator<BibEintrag> it = bibEintraege.iterator();
		while(it.hasNext()) {
			BibEintrag eintrag = it.next();
			if(eintrag.getJahr() > result) {
				result = eintrag.getJahr();
			}
		}
		
		return result;
	}
	
	public double berechneErscheinungsjahr() {
		if(bibEintraege.isEmpty()) {
			return 0.0;
		} else {
			int total = 0;
			
			Iterator<BibEintrag> it = bibEintraege.iterator();
			while(it.hasNext()) {
				BibEintrag eintrag = it.next();
				total += eintrag.getJahr();
			}
			
			return total / bibEintraege.size();
		}
	}
	
	public void druckeZitierschluessel(Primaerquelle[] quellen) {
		/* Die Praktikumsaufgabe für diese Methode verstehe ich nicht ganz.
		 * 
		 * Meiner Meinung nach macht es mehr Sinn und ist auch geschickter, wenn,
		 * wenn diese Methode aufgerufen wird,
		 * ohne dass Parameter gebraucht werden muss,
		 * direkt alle Zitierschlüssel von kompatiblen Objekten im BibManager ausgedruckt werden.
		 * 
		 * Die Implementierung erfolgt dann direkt hier innerhalb der Methode,
		 * und zwar für alle Zitierschlüssel von kompatiblen Objekten hier im BibManager.
		 * 
		 * Stattdessen fordert die Praktikumsaufgabe auf, dass diese Methode ein Parameter braucht,
		 * und zwar ein Array vom Typ Primaerquelle,
		 * was ja dazu führt, dass beim Aufruf dieser Methode dieses Array für den Parameter erst definiert werden muss.
		 * 
		 * Meine Lösung folgt der Aufforderung der Praktikumsaufgabe.
		 */
		for(Primaerquelle quelle : quellen) {
			if(quelle != null) System.out.println(quelle.erzeugeZitierschluessel());
			else continue;
		}
	}
}