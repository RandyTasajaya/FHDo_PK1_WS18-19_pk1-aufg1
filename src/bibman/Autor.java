package bibman;

public class Autor {

	private String vorname, nachname;
	
	public Autor(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public String getFullname() {
		return vorname + " " + nachname;
	}
	
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
}