package bibman;

import java.io.OutputStream;

public class Webseite extends BibEintrag {

	private String url;

	public Webseite(Autor autor, String titel, int jahr, String url) {
		super(autor, titel, jahr);
		this.url = url;
	}
	
	public boolean isWebseite() {
		return true;
	}

	public boolean isBuch() {
		return false;
	}
	
	public void druckeEintrag(OutputStream stream) {
		int realId = getId() + 1;
		System.out.println("[ID " + realId + "] " + getAutor().getFullname() + ": \"" + getTitel() + "\", " + getJahr() + 
				". URL: " + getUrl());
	}

	@Override
	public String exportiereAlsCsv() {
		return super.exportiereAlsCsv() + ",,,," + url + "\n";
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
