package bibman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Menu {

    private BibManager bibManager;

    public Menu(BibManager bibManager) {
        this.bibManager = bibManager;
    }

    public void run()
            throws DoppelterBibEintragException {

        final String VERLAG = "Default-Verlag";
        final String ISBN = "Default-ISBN";
        final String ZEITSCHRIFT = "Default-Zeitschrift";
        final int AUSGABE = -1;
        final String URL = "Default-URL";

        String welcomeMenu = "Bibliographie-Manager" +
                           "\n1. Buch hinzufügen" +
                           "\n2. Artikel hinzufügen" +
                           "\n3. Webseite hinzufügen" +
                           "\n4. Drucke alle Einträge" +
                           "\n5. Suche neuesten Eintrag" +
                           "\n6. Berechne durchschnittliches Erscheinungsjahr" +
                           "\n7. CSV-Export" +
                           "\n8. Speichern" +
                           "\n9. Laden" +
                           "\n10. Beenden" +
                           "\nBitte Aktion wählen: ";
        System.out.print(welcomeMenu);

        Scanner scanner = new Scanner(System.in);
        int intEingabe;

        String autorVorname, autorNachname, titel;
        int jahr;

        do {
            intEingabe = askMenuNumber(scanner, 0, 10, welcomeMenu);		// case 0 for debugging

            switch(intEingabe) {

                case 0: // Einträge von Autor
                    autorVorname = JOptionPane.showInputDialog(null, "Autors Vorname?");
                    if(autorVorname == null || autorVorname.replaceAll("\\s+", "").length() == 0) {
                        autorVorname = whenStringInDialogIsEmpty("Autors Vorname?");
                    }

                    autorNachname = JOptionPane.showInputDialog(null, "Autors Nachname?");
                    if(autorNachname == null || autorNachname.replaceAll("\\s+", "").length() == 0) {
                        autorNachname = whenStringInDialogIsEmpty("Autors Nachname?");
                    }

                    Autor autor = new Autor(autorVorname, autorNachname);

                    System.out.println("\n" + autor.getFullname() + " hat in diesem BibManager " +
                            bibManager.gibAnzahlEintraege(autor) +
                            (bibManager.gibAnzahlEintraege(autor) > 1 ? " Einträge." : " Eintrag."));


                    System.out.println("\nCase 0 for debugging purposes was done.");
                    System.out.print("\n" + welcomeMenu);
                    break;

                case 1: // Buch hinzufügen
                    autorVorname = JOptionPane.showInputDialog(null, "Autors Vorname?");
                    if(autorVorname == null || autorVorname.replaceAll("\\s+", "").length() == 0) {
                        autorVorname = whenStringInDialogIsEmpty("Autors Vorname?");
                    }

                    autorNachname = JOptionPane.showInputDialog(null, "Autors Nachname?");
                    if(autorNachname == null || autorNachname.replaceAll("\\s+", "").length() == 0) {
                        autorNachname = whenStringInDialogIsEmpty("Autors Nachname?");
                    }

                    titel = JOptionPane.showInputDialog(null, "Titel?");
                    if(titel == null || titel.replaceAll("\\s+", "").length() == 0) {
                        titel = whenStringInDialogIsEmpty("Titel?");
                    }

                    jahr = jahreseingabe(-1);

                    try {
                        bibManager.hinzufuegen(new Buch(new Autor(autorVorname, autorNachname), titel, jahr, VERLAG, ISBN));
                    }
                    catch(DoppelterBibEintragException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Meldung", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.print("\n" + welcomeMenu);
                    break;

                case 2: // Artikel hinzufügen
                    autorVorname = JOptionPane.showInputDialog(null, "Autors Vorname?");
                    if(autorVorname == null || autorVorname.replaceAll("\\s+", "").length() == 0) {
                        autorVorname = whenStringInDialogIsEmpty("Autors Vorname?");
                    }

                    autorNachname = JOptionPane.showInputDialog(null, "Autors Nachname?");
                    if(autorNachname == null || autorNachname.replaceAll("\\s+", "").length() == 0) {
                        autorNachname = whenStringInDialogIsEmpty("Autors Nachname?");
                    }

                    titel = JOptionPane.showInputDialog(null, "Titel?");
                    if(titel == null || titel.replaceAll("\\s+", "").length() == 0) {
                        titel = whenStringInDialogIsEmpty("Titel?");
                    }

                    jahr = jahreseingabe(-1);

                    try {
                        bibManager.hinzufuegen(new Artikel(new Autor(autorVorname, autorNachname), titel, jahr, ZEITSCHRIFT, AUSGABE));
                    }
                    catch(DoppelterBibEintragException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Meldung", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.print("\n" + welcomeMenu);
                    break;

                case 3: // Webseite hinzufügen
                    autorVorname = JOptionPane.showInputDialog(null, "Autors Vorname?");
                    if(autorVorname == null || autorVorname.replaceAll("\\s+", "").length() == 0) {
                        autorVorname = whenStringInDialogIsEmpty("Autors Vorname?");
                    }

                    autorNachname = JOptionPane.showInputDialog(null, "Autors Nachname?");
                    if(autorNachname == null || autorNachname.replaceAll("\\s+", "").length() == 0) {
                        autorNachname = whenStringInDialogIsEmpty("Autors Nachname?");
                    }

                    titel = JOptionPane.showInputDialog(null, "Titel?");
                    if(titel == null || titel.replaceAll("\\s+", "").length() == 0) {
                        titel = whenStringInDialogIsEmpty("Titel?");
                    }

                    jahr = jahreseingabe(-1);

                    try {
                        bibManager.hinzufuegen(new Webseite(new Autor(autorVorname, autorNachname), titel, jahr, URL));
                    }
                    catch(DoppelterBibEintragException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Meldung", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.print("\n" + welcomeMenu);
                    break;

                case 4: // Drucke alle Einträge
                    System.out.println();
                    bibManager.druckeAlleEintraege();
                    System.out.print("\n" + welcomeMenu);
                    break;

                case 5: // Suche neuesten Eintrag
                    System.out.println();
                    bibManager.sucheNeuestenEintrag();
                    System.out.print("\n" + welcomeMenu);
                    break;

                case 6: // Berechne durchschnittliches Erscheinungsjahr
                    System.out.println("\nDer Durschnitt der Erscheinungsjahre ist " +
                            (int)bibManager.berechneErscheinungsjahr() + ".");
                    System.out.print("\n" + welcomeMenu);
                    break;

                case 7: // CSV-Export
                    csvExportDialog();

                    System.out.println("\nCSV-Datei wurde erstellt!");
                    System.out.print("\n" + welcomeMenu);
                    break;

                case 8: // Speichern
                    if(bibManager.getSize() == 0) {
                        System.out.println("\nBibManager hat keine Einträge zu speichern!");
                        System.out.print("\n" + welcomeMenu);
                        break;
                    }
                    else {
                        File file = new File("BibManager.ser");

                        try(FileOutputStream fos = new FileOutputStream(file);
                            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                            oos.writeObject(bibManager);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        System.out.println("\nDatei wurde gespeichert!");
                        System.out.print("\n" + welcomeMenu);
                        break;
                    }

                case 9: // Laden
                    File file = new File("BibManager.ser");

                    try(FileInputStream fis = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(fis)) {

                        bibManager = (BibManager)ois.readObject();
                        BibEintrag.setIdHelper(bibManager.getSize());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    System.out.println("\nDatei wurde geladen! (Falls keine exception ausgelöst wurde!)");
                    System.out.print("\n" + welcomeMenu);
                    break;
            }
        }
        while(intEingabe != 10); // Beenden

        scanner.close();
    }

    public int askMenuNumber(Scanner sc, int minNum, int maxNum, String menu) {

        int menuNum = -1;

        try {
            menuNum = sc.nextInt();

            while(menuNum > maxNum || menuNum < minNum) {
                System.out.println("\nUngültige Zahl!");
                System.out.print("\n" + menu);
                menuNum = sc.nextInt();
            }
        }
        catch(InputMismatchException e) {
            System.out.println("\nBitte geben Sie eine Zahl ein!");
            System.out.print("\n" + menu);
            sc.nextLine();
        }

        return menuNum;
    }

    public String whenStringInDialogIsEmpty(String inputDialog) {
        JOptionPane.showMessageDialog(null, "Sie müssen etwas eingegeben.", "Meldung", JOptionPane.ERROR_MESSAGE);

        String result = JOptionPane.showInputDialog(null, inputDialog);
        if(result == null || result.replaceAll("\\s+", "").length() == 0) {
            result = whenStringInDialogIsEmpty(inputDialog);
        }
        return result;
    }

    public int jahreseingabe(int recursive) {
        if(recursive != -1) {
            return recursive;
        } else {
            try {
                recursive = Integer.parseInt(JOptionPane.showInputDialog(null, "Jahr?"));
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie gültige Zahl ein!", "Meldung", JOptionPane.ERROR_MESSAGE);
            }
            return jahreseingabe(recursive);
        }
    }

    public void csvExportDialog() {
        String dateiname = JOptionPane.showInputDialog(null, "Dateiname für das zu erstellende CSV-Datei?");
        if(dateiname == null || dateiname.replaceAll("\\s+", "").length() == 0) {
            dateiname = whenStringInDialogIsEmpty("Dateiname für das zu erstellende CSV-Datei?");
        }

        File csv = new File(dateiname + ".csv");

        try {
            if(!csv.createNewFile()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "Das CSV-Datei existiert bereits. Möchten Sie das existierende Datei überschreiben?",
                        "Meldung", JOptionPane.YES_NO_OPTION);

                switch(response) {

                    case JOptionPane.YES_OPTION:
                        csv.delete();
                        break;

                    case JOptionPane.NO_OPTION:
                    case JOptionPane.CLOSED_OPTION:
                        csvExportDialog();
                        return;
                }
            }
            else {
                csv.createNewFile();
            }
            bibManager.exportiereEintraegeAlsCsv(csv);
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Meldung", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Meldung", JOptionPane.ERROR_MESSAGE);
        }
    }

    public BibManager getBibManager() {
        return bibManager;
    }

    public void setBibManager(BibManager bibManager) {
        this.bibManager = bibManager;
    }

    public static void main(String[] args)
            throws DoppelterBibEintragException {

        Menu menu1 = new Menu(new BibManager());
        menu1.run();
    }

}
