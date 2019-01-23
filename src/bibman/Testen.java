package bibman;

import java.io.*;
import java.util.Iterator;

public class Testen {

    public static void main(String[] args)
            throws DoppelterBibEintragException {

        Buch buch1 = new Buch(new Autor("Conrad", "Barski"),
                          "Land of Lisp",
                          2010,
                        "No Starch Press",
                          "9781593272814");

        Artikel artikel1 = new Artikel(new Autor("Donald E.", "Knuth"),
                                   "Computer programming as an art",
                                   1974,
                               "Communications of the ACM",
                                12);

        Webseite webseite1 = new Webseite(new Autor("Christian", "Ullenbloom"),
                                      "Java ist auch eine Insel",
                                      2016,
                                       "http://openbook.rheinwerk-verlag.de/javainsel/");

        BibManager manager1 = new BibManager();

        manager1.hinzufuegen(buch1);
        manager1.hinzufuegen(artikel1);
        manager1.hinzufuegen(webseite1);

        manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"),
                                  "Buch Titel",
                                  2018,
                                "Verlagsname",
                                  "ISBN-Zahlen"));

        manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"),
                                  "Buch Titel - 2",
                                  2018,
                                "Verlagsname",
                                  "ISBN-Zahlen"));

        //----------------------------------------------------------------------------------------

        manager1.druckeAlleEintraege();

        //----------------------------------------------------------------------------------------
        System.out.println();
        //----------------------------------------------------------------------------------------

        System.out.println("Der neueste Eintrag ist: \n" +
                manager1.sucheNeuestenEintrag().toString());

        System.out.println("Der Durschnitt der Erscheinungsjahre ist " +
                (int)manager1.berechneErscheinungsjahr() + ".");

        //----------------------------------------------------------------------------------------
        System.out.println();
        //----------------------------------------------------------------------------------------

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

        //----------------------------------------------------------------------------------------
        System.out.println();
        //----------------------------------------------------------------------------------------

        System.out.println("- Anzahl Eintraege Autor (Object Autor aufgerufen durch Methode) " +
                "\n     -->" +
                manager1.getEintrag(3).getAutor().getFullname() + ": " +
                manager1.gibAnzahlEintraege(manager1.getEintrag(3).getAutor()));

        System.out.println();

        System.out.println("- Anzahl Eintraege Autor (Object Autor aufgerufen durch"
                + "\n     neue Erstellung von Object Autor (explizites Nameneingabe)) " +
                "\n     -->" +
                manager1.getEintrag(3).getAutor().getFullname() + ": " +
                manager1.gibAnzahlEintraege(new Autor("Vorname", "Nachname")));

        System.out.println();

        System.out.println("- Anzahl Eintraege Autor (Object Autor aufgerufen durch"
                + "\n     neue Erstellung von Object Autor (explizites Nameneingabe)) " +
                "\n     -->" +
                "Randy Tasajaya: " +
                manager1.gibAnzahlEintraege(new Autor("Randy", "Tasajaya")));

        //----------------------------------------------------------------------------------------
        System.out.println();
        //----------------------------------------------------------------------------------------

        try {
            manager1.hinzufuegen(new Buch(new Autor("Vorname", "Nachname"),
                                      "Buch Titel",
                                      2018,
                                     "Verlagsname",
                                      "ISBN-Zahlen"));
        }
        catch(DoppelterBibEintragException e) {
            System.out.println("Exception für doppelten BibEintrag aufgefangen.");
        }

        //----------------------------------------------------------------------------------------
        System.out.println();
        //----------------------------------------------------------------------------------------

        File csv = new File("BibManager_Testen.csv");
        try {
            csv.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("A CSV file (" + csv.getName() + ") was created!");

//      // Für BibManager mit nur einem Element:
//		BibManager manager2 = new BibManager();
//		manager2.hinzufuegen(buch1);

        try {
            manager1.exportiereEintraegeAlsCsvRaf(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------
        System.out.println();
        //----------------------------------------------------------------------------------------

        File file = new File("BibManager_Testen.ser");

        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            oos.writeObject(manager1);

            Object readObject = ois.readObject();
            BibManager bibManagerToBePrinted = (BibManager)readObject;

            System.out.println("Object BibManager read from .ser file: " + bibManagerToBePrinted);

        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------------------
        System.out.println();
        //----------------------------------------------------------------------------------------

//        // Using a deprecated method
//        new Buch().setId();

        //----------------------------------------------------------------------------------------

//        // Calling a function with -null- as argument
//        manager1.hinzufuegen(null);

        /*
         * One developer could think,
         * does everything, and I mean really everything, need to be coded
         * with NullPointerException in mind?
         *
         * Sure, there are a (whole!) lot of cases where it's necessary.
         * But that'd also mean a lot of unnecessary works.
         * Probably it just depends on the software (as a whole) that is being developed.
         */

        //----------------------------------------------------------------------------------------


    }
}
