package bibman;

import java.io.IOException;

public class BibManagerArray {

    private BibEintrag[] bibEintraege;

    public static byte index;

    public BibManagerArray(int zahlEintraege) {
        bibEintraege = new BibEintrag[zahlEintraege];
    }

    public int getIndex() {
        return index;
    }

    public BibEintrag[] getBibEintraege() {
        return bibEintraege;
    }

    public BibEintrag getEintrag(int index) {
        return bibEintraege[index];
    }

    public void hinzufuegen(BibEintrag eintrag) {
        if(bibEintraege[bibEintraege.length-1] != null) {
            System.out.println("BibEintrag ist bereits voll!");
        } else {
            bibEintraege[index++] = eintrag;
        }
    }

    public void druckeAlleEintraege() {
        for(BibEintrag eintrag : bibEintraege) {
            try {
                eintrag.druckeEintrag(System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int sucheNeuestenEintrag() {
        int result = bibEintraege[0].getJahr();

        for(int i = 0; i < bibEintraege.length; i++) {
            if(bibEintraege[i].getJahr() > result) {
                result = bibEintraege[i].getJahr();
            }
        }
        return result;
    }

    public double berechneErscheinungsjahr() {
        if(bibEintraege.length == 0) {
            return 0.0;
        } else {
            int total = 0;
            for(BibEintrag eintrag : bibEintraege) {
                total += eintrag.getJahr();
            }
            return total / bibEintraege.length;
        }
    }

    public void druckeZitierschluessel(Primaerquelle[] quellen) {
        /* Die Praktikumsaufgabe für diese Methode verstehe ich nicht ganz.
         *
         * Meiner Meinung nach macht es mehr Sinn und ist auch geschickter, wenn,
         * wenn diese Methode aufgerufen wird,
         * ohne dass Parameter gebraucht werden muss,
         * direkt alle Zitierschlüssel von kompatiblen Objekten im BibManagerArray ausgedruckt werden.
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