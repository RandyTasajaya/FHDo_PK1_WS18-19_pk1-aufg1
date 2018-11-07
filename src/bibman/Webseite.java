package bibman;

public class Webseite extends BibEintrag {

	private String url;

	public Webseite(String autor, String titel, int jahr, String url) {
		super(autor, titel, jahr);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void druckeEintrag() {
		System.out.println("[ID " + getId() + "] " + getAutor() + ": \"" + getTitel() + "\", " + getJahr() + 
				". URL: " + getUrl());
	}
}