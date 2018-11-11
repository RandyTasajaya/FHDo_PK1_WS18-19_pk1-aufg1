package bibman;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class Menu {

	private BibManager bibManager;
	
	public Menu(BibManager bibManager) {
		this.bibManager = bibManager;
	}
	
	public void run() {
		
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
			intEingabe = scanner.nextInt();
			
			switch(intEingabe) {
			
			case 1:
				autorVorname = JOptionPane.showInputDialog(null, "Autors Vorname?");
				autorNachname = JOptionPane.showInputDialog(null, "Autors Nachname?");
				titel = JOptionPane.showInputDialog(null, "Titel?");
				jahr = Integer.parseInt(JOptionPane.showInputDialog(null, "Jahr?"));
				
				bibManager.hinzufuegen(new Buch(new Autor(autorVorname, autorNachname), titel, jahr, VERLAG, ISBN));
				System.out.print("\n" + welcomeMenu);
				break;
				
			case 2:
				autorVorname = JOptionPane.showInputDialog(null, "Autors Vorname?");
				autorNachname = JOptionPane.showInputDialog(null, "Autors Nachname?");
				titel = JOptionPane.showInputDialog(null, "Titel?");
				jahr = Integer.parseInt(JOptionPane.showInputDialog(null, "Jahr?"));
				
				bibManager.hinzufuegen(new Artikel(new Autor(autorVorname, autorNachname), titel, jahr, ZEITSCHRIFT, AUSGABE));
				System.out.print("\n" + welcomeMenu);
				break;
				
			case 3:
				autorVorname = JOptionPane.showInputDialog(null, "Autors Vorname?");
				autorNachname = JOptionPane.showInputDialog(null, "Autors Nachname?");
				titel = JOptionPane.showInputDialog(null, "Titel?");
				jahr = Integer.parseInt(JOptionPane.showInputDialog(null, "Jahr?"));
				
				bibManager.hinzufuegen(new Webseite(new Autor(autorVorname, autorNachname), titel, jahr, URL));
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
	
	public BibManager getBibManager() {
		return bibManager;
	}
	
	public void setBibManager(BibManager bibManager) {
		this.bibManager = bibManager;
	}
	
	public static void main(String[] args) {
		
		Menu menu1 = new Menu(new BibManager());
		menu1.run();
	}
}