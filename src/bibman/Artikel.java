package bibman;

public class Artikel extends BibEintrag implements Primaerquelle {

	private String zeitschrift;
	private int ausgabe;

	public Artikel(Autor autor, String titel, int jahr, String zeitschrift, int ausgabe) {
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
		System.out.println("[ID " + realId + "] " + getAutor().getFullname() + ": \"" + getTitel() + "\". In: \"" + 
				getZeitschrift() + "\" (Ausgabe " + getAusgabe() + "), " + getJahr());
	}
	
	public String erzeugeZitierschluessel() {
		String str = getAutor().getFullname().replaceAll("\\s+", "");
		return str + getJahr() + "-" + getAusgabe();
	}

	@Override
	public String exportiereAlsCsv() {
		return super.exportiereAlsCsv() + ",," + zeitschrift + "," + ausgabe + ",\n";
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
