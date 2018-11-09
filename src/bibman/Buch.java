package bibman;

public class Buch extends BibEintrag implements Primaerquelle {

	private String verlag;
	private String isbn;

	public Buch(String autor, String titel, int jahr, String verlag, String isbn) {
		super(autor, titel, jahr);
		this.verlag = verlag;
		this.isbn = isbn;
	}
	
	public boolean isWebseite() {
		return false;
	}
	
	public boolean isBuch() {
		return true;
	}
	
	public void druckeEintrag() {
		int realId = getId() + 1;
		System.out.println("[ID " + realId + "] " + getAutor() + ": \"" + getTitel() + "\". " + getVerlag()
				+ ", " + getJahr() + " (ISBN: " + getIsbn() + ")");
	}
	
	public String erzeugeZitierschluessel() {
		String str = getAutor().replaceAll("\\s+", "");
		return str + getJahr();
	}

	public String getVerlag() {
		return verlag;
	}

	public void setVerlag(String verlag) {
		this.verlag = verlag;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}