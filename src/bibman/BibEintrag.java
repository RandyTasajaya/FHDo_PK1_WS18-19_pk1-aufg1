package bibman;

import java.time.LocalDate;

public abstract class BibEintrag {
	
	private int id;			// {readOnly} = keine setId-Methode
	private String autor;
	private String titel;
	private int jahr;

	private static int idHelper;

	public BibEintrag(String autor, String titel, int jahr) {
		id = ++idHelper;
		this.autor = autor;
		this.titel = titel;
		this.jahr = jahr;
	}

	public int getId() {
		return id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
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

	public int berechneAlter() {
		LocalDate currentDate = LocalDate.now();
		return currentDate.getYear() - jahr;
	}

	public abstract void druckeEintrag();
}