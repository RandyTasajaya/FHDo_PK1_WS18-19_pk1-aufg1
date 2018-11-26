package bibman;

import java.time.LocalDate;

public abstract class BibEintrag implements CsvExportable {
	
	private int id;			// {readOnly} = keine setId-Methode
	private Autor autor;
	private String titel;
	private int jahr;

	private static int idHelper;

	public BibEintrag(Autor autor, String titel, int jahr) {
		id = idHelper++;
		this.autor = autor;
		this.titel = titel;
		this.jahr = jahr;
	}

	public abstract boolean isWebseite();
	public abstract boolean isBuch();
	public abstract void druckeEintrag();
	
	public String exportiereAlsCsv() {
		return "ID,Vorname,Nachname,Titel,Jahr,Verlag,ISBN,Zeitschrift,Ausgabe,URL\n" +
				id + "," + autor.getVorname() + "," + autor.getNachname() + "," + 
				titel + "," + jahr + ",";
	}

	public int berechneAlter() {
		LocalDate currentDate = LocalDate.now();
		return currentDate.getYear() - jahr;
	}
	
	@Override
	public boolean equals(Object obj) {
		BibEintrag other = (BibEintrag) obj;
		return autor.equals(other.autor) && titel.equals(other.titel) && jahr == other.jahr;
	}
	
	public int getId() {
		return id;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}
	
}
