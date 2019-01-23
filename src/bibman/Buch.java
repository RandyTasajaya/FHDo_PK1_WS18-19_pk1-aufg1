package bibman;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class Buch extends BibEintrag implements Primaerquelle, Serializable {

    private String verlag;
    private String isbn;

    private static final long serialVersionUID = -3935378397240751260L;

    public Buch(Autor autor, String titel, int jahr, String verlag, String isbn) {
        super(autor, titel, jahr);
        this.verlag = verlag;
        this.isbn = isbn;
    }

    public Buch() {
        super();
    }

    public boolean isWebseite() {
        return false;
    }

    public boolean isBuch() {
        return true;
    }

    public void druckeEintrag(OutputStream stream)
            throws IOException {
        int realId = getId() + 1;
        String str = "[ID " + realId + "] " + getAutor().getFullname() + ": \"" + getTitel() + "\". " + getVerlag() + ", " + getJahr() + " (ISBN: " + getIsbn() + ")" + "\n";

        OutputStreamWriter osw = new OutputStreamWriter(stream);
        osw.write(str);
        osw.flush();
    }

    @Override
    public String toString() {
        int realId = getId() + 1;

        return "[ID " + realId + "] " + getAutor().getFullname() + ": \"" + getTitel() + "\". " + getVerlag() + ", " + getJahr() + " (ISBN: " + getIsbn() + ")";
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
