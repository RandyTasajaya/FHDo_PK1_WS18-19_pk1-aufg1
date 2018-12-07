package bibman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.*;

public class BibManager implements Serializable {
	
	private List<BibEintrag> bibEintraege;
	private Map<Autor, Integer> autorsEintraege;
	
	private static final long serialVersionUID = 7024505680571386182L;
		
	public BibManager() {
		bibEintraege = new ArrayList<>();
		autorsEintraege = new HashMap<>();
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
	
	public void hinzufuegen(BibEintrag eintrag) 
		throws DoppelterBibEintragException {
		
		if(bibEintraege.contains(eintrag))
			throw new DoppelterBibEintragException("Dieser Bibeintrag: \"" + eintrag.getTitel() + 
					"\" von " + eintrag.getAutor().getFullname() + " wurde schon mal registriert!");
		
		bibEintraege.add(eintrag);
		
		if(autorsEintraege.containsKey(eintrag.getAutor())) {
			autorsEintraege.put(eintrag.getAutor(), (autorsEintraege.get(eintrag.getAutor()))+1);
		} else {
			autorsEintraege.put(eintrag.getAutor(), 1);
		}
	}
	
	public void druckeAlleEintraege() {
		if(bibEintraege.isEmpty()) {
			System.out.println("BibManager ist leer!");
			return;
		}
		else {
			Collections.sort(bibEintraege, new BibEintragComparator());

			for(BibEintrag eintrag : bibEintraege) {
				try {
					eintrag.druckeEintrag(System.out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sucheNeuestenEintrag() {
		if(bibEintraege.isEmpty()) {
			System.out.println("BibManager ist leer!");
			return;
		}
		else {
			BibEintrag bibEintrag = bibEintraege.get(0);

			Iterator<BibEintrag> it = bibEintraege.iterator();
			while(it.hasNext()) {
				BibEintrag nextBibEintrag = it.next();
				if(nextBibEintrag.getJahr() > bibEintrag.getJahr()) {
					bibEintrag = nextBibEintrag;
				}
			}

			System.out.print("Der neueste Eintrag: ");
			try {
				bibEintrag.druckeEintrag(System.out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	
	public int gibAnzahlEintraege(Autor autor) {
		return autorsEintraege.containsKey(autor) ? autorsEintraege.get(autor) : 0;
	}
	
	public void exportiereEintraegeAlsCsv(File datei)
			throws FileNotFoundException, IOException {
		
		try(FileWriter fw = new FileWriter(datei); PrintWriter pw = new PrintWriter(fw)) {
			
			if(bibEintraege.size() == 0)
				return;
			
			if(bibEintraege.size() == 1) {
				pw.write(bibEintraege.get(0).exportiereAlsCsv());
			}
			else {
				pw.write("ID,Vorname,Nachname,Titel,Jahr,Verlag,ISBN,Zeitschrift,Ausgabe,URL\n");

				Iterator<BibEintrag> it = bibEintraege.iterator();
				while(it.hasNext()) {
					BibEintrag eintrag = it.next();

					pw.write(eintrag.exportiereAlsCsv().substring(67));				
				}
			}
		}
	}
	
	public void exportiereEintraegeAlsCsvRaf(File datei)
			throws FileNotFoundException, IOException {
		
		try(RandomAccessFile file = new RandomAccessFile(datei, "rw")) {

			if(bibEintraege.size() == 0)
				return;
			
			if(bibEintraege.size() == 1) {
				file.write(bibEintraege.get(0).exportiereAlsCsv().getBytes());
			}
			else {
				file.write(new String("ID,Vorname,Nachname,Titel,Jahr,Verlag,ISBN,Zeitschrift,Ausgabe,URL\n").getBytes());
				
				Iterator<BibEintrag> it = bibEintraege.iterator();
				while(it.hasNext()) {
					BibEintrag eintrag = it.next();
					
					String line = eintrag.exportiereAlsCsv().substring(67);
					
					file.write(line.getBytes());				
				}
			}
		}
	}
	
}
