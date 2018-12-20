package bibman;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class Artikel extends BibEintrag implements Primaerquelle, Serializable {

    private String zeitschrift;
    private int ausgabe;

    private static final long serialVersionUID = -3259921699269634794L;

    public Artikel(Autor autor, String titel, int jahr, String zeitschrift, int ausgabe) {
        super(autor, titel, jahr);
        this.zeitschrift = zeitschrift;
        this.ausgabe = ausgabe;
    }

    public boolean isWebseite() {
        return false;
    }

    public boolean isBuch() {
        return false;
    }

    public void druckeEintrag(OutputStream stream)
            throws IOException {
        int realId = getId() + 1;
        String str = "[ID " + realId + "] " + getAutor().getFullname() + ": \"" + getTitel() + "\". In: \"" +
                getZeitschrift() + "\" (Ausgabe " + getAusgabe() + "), " + getJahr() + "\n";

        OutputStreamWriter osw = new OutputStreamWriter(stream);
        osw.write(str);
        osw.flush();
    }

    public String erzeugeZitierschluessel() {
        String str = getAutor().getFullname().replaceAll("\\s+", "");
        return str + getJahr() + "-" + getAusgabe();
    }

    @Override
    public String exportiereAlsCsv() {
        return super.exportiereAlsCsv() + ",," + zeitschrift + "," + ausgabe + ",\n";
    }

    public String getZeitschrift() {
        return zeitschrift;
    }

    public void setZeitschrift(String zeitschrift) {
        this.zeitschrift = zeitschrift;
    }

    public int getAusgabe() {
        return ausgabe;
    }

    public void setAusgabe(int ausgabe) {
        this.ausgabe = ausgabe;
    }

}
