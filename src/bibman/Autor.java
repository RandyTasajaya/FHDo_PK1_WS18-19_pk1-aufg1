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
	
	@Override
	public boolean equals(Object obj) {
		Autor other = (Autor)obj;
		return vorname == other.vorname && nachname == other.nachname;
	}
	
	@Override
	public int hashCode() {
		String str = vorname + nachname;
		return str.hashCode();
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