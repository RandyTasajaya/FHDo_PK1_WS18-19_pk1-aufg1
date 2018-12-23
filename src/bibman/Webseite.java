package bibman;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class Webseite extends BibEintrag implements Serializable {

    private String url;

    private static final long serialVersionUID = -1966401721518351934L;

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

    public void druckeEintrag(OutputStream stream)
            throws IOException {
        int realId = getId() + 1;
        String str = "[ID " + realId + "] " + getAutor().getFullname() + ": \"" + getTitel() + "\", " + getJahr() + ". URL: " + getUrl() + "\n";

        OutputStreamWriter osw = new OutputStreamWriter(stream);
        osw.write(str);
        osw.flush();
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
