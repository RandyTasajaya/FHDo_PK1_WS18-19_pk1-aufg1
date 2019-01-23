package bibman;

import java.io.*;
import java.util.*;

public class BibManager extends Observable implements Serializable {

    private List<BibEintrag> bibEintraege;
    private Map<Autor, Integer> autorsEintraege;

    private int idHelperOfBibEintrag;

    private static final long serialVersionUID = 7024505680571386182L;

    public BibManager() {
        bibEintraege = new ArrayList<>();
        autorsEintraege = new HashMap<>();
        idHelperOfBibEintrag = BibEintrag.getIdHelper();
    }

    public int getSize() {
        return bibEintraege.size();
    }

    public BibEintrag getEintrag(int index) {
        return bibEintraege.get(index);
    }

    public int getIdHelperOfBibEintrag() {
        return idHelperOfBibEintrag;
    }

    public Iterator<BibEintrag> iterator() {
        return bibEintraege.iterator();
    }

    public void hinzufuegen(BibEintrag eintrag)
            throws DoppelterBibEintragException {

        this.idHelperOfBibEintrag = BibEintrag.getIdHelper();

        if(bibEintraege.contains(eintrag))
            throw new DoppelterBibEintragException("Dieser Bibeintrag: \"" + eintrag.getTitel() +
                    "\" von " + eintrag.getAutor().getFullname() + " wurde schon mal registriert!");

        bibEintraege.add(eintrag);

        /*
         * Integration of Map<Autor, Integer> autorsEintraege
         */
        if(autorsEintraege.containsKey(eintrag.getAutor())) {
            autorsEintraege.put(eintrag.getAutor(), (autorsEintraege.get(eintrag.getAutor()))+1);
        } else {
            autorsEintraege.put(eintrag.getAutor(), 1);
        }

        /*
         * For logging
         */
        setChanged();
        notifyObservers();
    }

    public void druckeAlleEintraege() {
        if(bibEintraege.isEmpty()) {
            System.out.println("BibManager ist leer!");
        }
        else {
            bibEintraege.sort(new Comparator<BibEintrag>() {
                @Override
                public int compare(BibEintrag o1, BibEintrag o2) {
                    return o1.getAutor().getNachname().compareTo(o2.getAutor().getNachname());
                }
            });

            for(BibEintrag eintrag : bibEintraege) {
                try {
                    eintrag.druckeEintrag(System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (Map.Entry<Autor, Integer> entry : autorsEintraege.entrySet()) {
                System.out.print(entry.getKey().getFullname() + ": ");
                System.out.println(entry.getValue() + " Einträge");
            }
        }
    }

    public BibEintrag sucheNeuestenEintrag() {
        if(bibEintraege.isEmpty()) {
            throw new IllegalStateException("BibManager ist leer!");
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

            return bibEintrag;
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
            else continue; //The array "quellen" could have unfilled (==null) elements
        }
    }

    public int gibAnzahlEintraege(Autor autor) {
        return autorsEintraege.getOrDefault(autor, 0);
    }

    public void exportiereEintraegeAlsCsv(File datei)
            throws FileNotFoundException, IOException {

        try(FileWriter fw = new FileWriter(datei);
            PrintWriter pw = new PrintWriter(fw)) {

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
                file.write("ID,Vorname,Nachname,Titel,Jahr,Verlag,ISBN,Zeitschrift,Ausgabe,URL\n".getBytes());

                Iterator<BibEintrag> it = bibEintraege.iterator();
                while(it.hasNext()) {
                    BibEintrag eintrag = it.next();

                    String line = eintrag.exportiereAlsCsv().substring(67);

                    file.write(line.getBytes());
                }
            }
        }
    }

    public List<BibEintrag> getBibEintraege() {
        return bibEintraege;
    }

    public void setBibEintraege(List<BibEintrag> bibEintraege) {
        this.bibEintraege = bibEintraege;
    }

    public Map<Autor, Integer> getAutorsEintraege() {
        return autorsEintraege;
    }

    public void setAutorsEintraege(Map<Autor, Integer> autorsEintraege) {
        this.autorsEintraege = autorsEintraege;
    }

}
