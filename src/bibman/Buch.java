package bibman;

import java.io.OutputStream;

public class Buch extends BibEintrag implements Primaerquelle {

	private String verlag;
	private String isbn;

	public Buch(Autor autor, String titel, int jahr, String verlag, String isbn) {
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
	
	public void druckeEintrag(OutputStream stream) {
		int realId = getId() + 1;
		System.out.println("[ID " + realId + "] " + getAutor().getFullname() + ": \"" + getTitel() + "\". " + getVerlag()
				+ ", " + getJahr() + " (ISBN: " + getIsbn() + ")");
	}
	
	public String erzeugeZitierschluessel() {
		String str = getAutor().getFullname().replaceAll("\\s+", "");
		return str + getJahr();
	}
	
	@Override
	public String exportiereAlsCsv() {
		return super.exportiereAlsCsv() + verlag + "," + isbn + "," + ",,\n";
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
