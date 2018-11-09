package bibman;

public class Artikel extends BibEintrag implements Primaerquelle {

	private String zeitschrift;
	private int ausgabe;

	public Artikel(String autor, String titel, int jahr, String zeitschrift, int ausgabe) {
		super(autor, titel, jahr);
		this.zeitschrift = zeitschrift;
		this.ausgabe = ausgabe;
	}
	
	public boolean isWebseite() {
		return false;
	}
	
	public boolean isBuch() {
		return false;
	}
	
	public void druckeEintrag() {
		int realId = getId() + 1;
		System.out.println("[ID " + realId + "] " + getAutor() + ": \"" + getTitel() + "\". In: \"" + 
				getZeitschrift() + "\" (Ausgabe " + getAusgabe() + "), " + getJahr());
	}
	
	public String erzeugeZitierschluessel() {
		String str = getAutor().replaceAll("\\s+", "");
		return str + getJahr() + "-" + getAusgabe();
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
}