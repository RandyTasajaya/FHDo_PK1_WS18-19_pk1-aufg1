package bibman;

public class Webseite extends BibEintrag {

	private String url;

	public Webseite(String autor, String titel, int jahr, String url) {
		super(autor, titel, jahr);
		this.url = url;
	}
	
	public boolean isWebseite() {
		return true;
	}

	public boolean isBuch() {
		return false;
	}
	
	public void druckeEintrag() {
		int realId = getId() + 1;
		System.out.println("[ID " + realId + "] " + getAutor() + ": \"" + getTitel() + "\", " + getJahr() + 
				". URL: " + getUrl());
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}