package bibman;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class BibEintrag implements CsvExportable, Serializable {

    private int id;			// {readOnly} = keine setId-Methode
    private Autor autor;
    private String titel;
    private int jahr;

    private static int idHelper;

    private static final long serialVersionUID = -1013508665712177163L;

    public BibEintrag(Autor autor, String titel, int jahr) {
        id = idHelper++;
        this.autor = autor;
        this.titel = titel;
        this.jahr = jahr;
    }

    public BibEintrag() {
    }

    public abstract boolean isWebseite();
    public abstract boolean isBuch();
    public abstract void druckeEintrag(OutputStream stream) throws IOException;

    @Override
    public abstract String toString();

    public String exportiereAlsCsv() {
        return "ID,Vorname,Nachname,Titel,Jahr,Verlag,ISBN,Zeitschrift,Ausgabe,URL\n" +
                id + "," + autor.getVorname() + "," + autor.getNachname() + "," + titel + "," + jahr + ",";
    }

    public int berechneAlter() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - jahr;
    }

    @Override
    public boolean equals(Object obj) {
        BibEintrag other = (BibEintrag) obj;
        return autor.equals(other.autor) && titel.equals(other.titel) && jahr == other.jahr;
    }

    public static int getIdHelper() {
        return idHelper;
    }

    public static void setIdHelper(int idHelper) {
        BibEintrag.idHelper = idHelper;
    }

    public int getId() {
        return id;
    }

    /*
     * Diese Methode wird hier nur gemacht,
     * um (nach Praktikumsufgabe) die Java-Beans-Konvention einzuhalten
     * --> daher das "@Deprecated", um eine Warnung hervorzuheben.
     *
     * In diesem von mir entwickelten Code soll "id" nur automatisch durch "idHelper" arbeiten.
     */
    @Deprecated
    public void setId(int id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

}
