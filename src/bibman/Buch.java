package bibman;

public class Buch extends BibEintrag {

	private String verlag;
	private String isbn;

	public Buch(String autor, String titel, int jahr, String verlag, String isbn) {
		super(autor, titel, jahr);
		this.verlag = verlag;
		this.isbn = isbn;
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

	public void druckeEintrag() {
		System.out.println("[ID " + getId() + "] " + getAutor() + ": \"" + getTitel() + "\". " + getVerlag()
				+ ", " + getJahr() + " (ISBN: " + getIsbn() + ")");
	}
}