package bibman;

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
						   "\n7. Beenden" +
						   "\nBitte Aktion wählen: ";
		System.out.print(welcomeMenu);
		
		Scanner scanner = new Scanner(System.in);
		int intEingabe;
		
		String autorVorname, autorNachname, titel;
		int jahr;
		
		do {
			intEingabe = askMenuNumber(scanner, 0, 7, welcomeMenu);		// case 0 for debugging 
			
			switch(intEingabe) {
			
			case 0:
				// TO-DO
				
				System.out.println("\nCase 0 for debugging purposes was done.");
				System.out.print("\n" + welcomeMenu);
				break;
			
			case 1:
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
				
				jahr = jahreseingabe();
				
				try {
					bibManager.hinzufuegen(new Buch(new Autor(autorVorname, autorNachname), titel, jahr, VERLAG, ISBN));
				}
				catch(DoppelterBibEintragException e) {
					JOptionPane.showMessageDialog(null, "\nDieser Bibeintrag: \"" + titel + "\" von " + 
							autorVorname + " " + autorNachname + " wurde schon mal registriert!", 
							"Meldung", JOptionPane.ERROR_MESSAGE);
				}
				
				System.out.print("\n" + welcomeMenu);
				break;
				
			case 2:
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
				
				jahr = jahreseingabe();
				
				try {
					bibManager.hinzufuegen(new Artikel(new Autor(autorVorname, autorNachname), titel, jahr, ZEITSCHRIFT, AUSGABE));
				}
				catch(DoppelterBibEintragException e) {
					JOptionPane.showMessageDialog(null, "\nDieser Bibeintrag: \"" + titel + "\" von " + 
							autorVorname + " " + autorNachname + " wurde schon mal registriert!", 
							"Meldung", JOptionPane.ERROR_MESSAGE);
				}
				
				System.out.print("\n" + welcomeMenu);
				break;
				
			case 3:
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
				
				jahr = jahreseingabe();
				
				try {
					bibManager.hinzufuegen(new Webseite(new Autor(autorVorname, autorNachname), titel, jahr, URL));
				}
				catch(DoppelterBibEintragException e) {
					JOptionPane.showMessageDialog(null, "\nDieser Bibeintrag: \"" + titel + "\" von " + 
							autorVorname + " " + autorNachname + " wurde schon mal registriert!", 
							"Meldung", JOptionPane.ERROR_MESSAGE);
				}
				
				System.out.print("\n" + welcomeMenu);
				break;
				
			case 4:
				System.out.println();
				bibManager.druckeAlleEintraege();
				System.out.print("\n" + welcomeMenu);
				break;
				
			case 5:
				System.out.println("\nDer neueste Eintrag ist vom Jahr " + bibManager.sucheNeuestenEintrag() + ".");
				System.out.print("\n" + welcomeMenu);
				break;
				
			case 6:
				System.out.println("\nDer Durschnitt der Erscheinungsjahre ist " +
						(int)bibManager.berechneErscheinungsjahr() + ".");
				System.out.print("\n" + welcomeMenu);
				break;
			}
		}
		while(intEingabe != 7);
		
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
		JOptionPane.showMessageDialog(null, "Sie haben gerade noch nichts eingegeben.", "Meldung", JOptionPane.ERROR_MESSAGE);
		
		String result = JOptionPane.showInputDialog(null, inputDialog);
		if(result == null || result.replaceAll("\\s+", "").length() == 0) {
			result = whenStringInDialogIsEmpty(inputDialog);
		}
		return result;
	}
	
	public int jahreseingabe() {
		int jahr = -1;
		try {
			jahr = Integer.parseInt(JOptionPane.showInputDialog(null, "Jahr?"));
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Bitte geben Sie gültige Zahl ein!", "Meldung", JOptionPane.ERROR_MESSAGE);
			jahreseingabe();
		}
		return jahr;
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
