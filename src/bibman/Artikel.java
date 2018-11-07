package bibman;

public class Artikel extends BibEintrag {

	private String zeitschrift;
	private int ausgabe;

	public Artikel(String autor, String titel, int jahr, String zeitschrift, int ausgabe) {
		super(autor, titel, jahr);
		this.zeitschrift = zeitschrift;
		this.ausgabe = ausgabe;
	}

	public String getZeitschrift() {
		return zeitschrift;
	}

	public void setZeitschrift(String zeitschrift) {
		this.zeitschrift = zeitschrift;
	}

	public int getAusgabe() {
		return ausgabe;
	}

	public void setAusgabe(int ausgabe) {
		this.ausgabe = ausgabe;
	}

	public void druckeEintrag() {
		System.out.println("[ID " + getId() + "] " + getAutor() + ": \"" + getTitel() + "\". In: \"" + 
				getZeitschrift() + "\" (Ausgabe " + getAusgabe() + "), " + getJahr());
	}
}