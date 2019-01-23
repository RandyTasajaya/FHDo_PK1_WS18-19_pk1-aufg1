package bibman.ui;

import bibman.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ErfassungView extends Stage {

    private BibEintrag eintrag;

    public ErfassungView(Stage parentStage, BibEintrag eintrag) {
        this.eintrag = eintrag;

        this.initOwner(parentStage);
        this.initModality(Modality.WINDOW_MODAL);
    }

    /*
     * Ich implementiere alle Aufgaben in Praktikum 11
     * strikt gemäß Klassendiagramm auf dem Praktikum-Aufgabenblatt 11,
     * was z.B. dazu führt,
     * dass die vorgestellte Lösung nicht unbedingt die von mir gedachte günstigste Lösung ist.
     *
     * Zum Beispiel,
     *
     * ich könnte diese showView()-Methode hier in der abstrakten Eltern-Klasse einfach leer lassen,
     * denn sie wird sowieso in den Kinder-Klassen überschrieben
     *
     * oder,
     *
     * ich könnte in dieser abstrakten Eltern-Klasse
     * weitere Attribute und Methoden definieren,
     * um redudanten Code zu vermeiden.
     *
     * Aufgrund der Einschränkung des genannten Klassendiagramms entschied ich mich dafür,
     * hier in dieser showView()-Methode,
     * die Gemeinsamkeiten aller Kinder-Klassen zusammenzufassen,
     * um z.B. zu betonen, dass, in diesem Programm,
     * alle möglichen Arten von Bibliothek-Eintrag immer die Attribute Titel, Autor und Jahr haben sollen.
     */
    public void showView() {
        Label titel = new Label("Titel:");
        Label autor = new Label("Autor:");
        Label jahr = new Label("Jahr:");

        TextField titelTF = new TextField();
        TextField autorTF = new TextField();
        TextField jahrTF = new TextField();

        if(eintrag != null) {
            titelTF.appendText(eintrag.getTitel());
            autorTF.appendText(eintrag.getAutor().getFullname());
            jahrTF.appendText(String.valueOf(eintrag.getJahr()));
        }

        Button ok = new Button("OK");
        Button abbrechen = new Button("Abbrechen");

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean inputResult = okButtonPressed(titelTF.getText(), autorTF.getText(), jahrTF.getText(), "typeOfBibEintrag");

                if(inputResult) {
                    // 1. Initialize child-BibEintrag object.
                    // 2. Assign all its specialized properties (specialized from the parent class).
                    // 3. Add this child-BibEintrag object to the (static) field BibManager of the GUI class/JavaFX-application.
                    // 4. updateBibManager() so that this change is "observed".
                    // 5. Show confirmation dialog.
                }
            }
        });

        abbrechen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });


        /*
         * GRID PANE for everything above the buttons
         */
        GridPane gridPane = new GridPane();
        gridPane.add(titel, 0, 0);
        gridPane.add(titelTF, 1, 0);
        gridPane.add(autor, 0, 1);
        gridPane.add(autorTF, 1, 1);
        gridPane.add(jahr, 0, 2);
        gridPane.add(jahrTF, 1, 2);

        GridPane.setHalignment(titel, HPos.RIGHT);
        GridPane.setHalignment(autor, HPos.RIGHT);
        GridPane.setHalignment(jahr, HPos.RIGHT);

        TextField[] allTextFields = {titelTF, autorTF, jahrTF};
        for (TextField tf : allTextFields) {
            tf.setMaxWidth(Double.MAX_VALUE);
        }

        GridPane.setHgrow(titelTF, Priority.ALWAYS);
        GridPane.setHgrow(autorTF, Priority.ALWAYS);
        GridPane.setHgrow(jahrTF, Priority.ALWAYS);

        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(0, 0, 0, 40));

        /*
         * BUTTONS
         */
        FlowPane buttons = new FlowPane();
        buttons.getChildren().addAll(ok, abbrechen);

        buttons.setOrientation(Orientation.HORIZONTAL);
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(20);
        buttons.setPadding(new Insets(10));

        /*
         * BORDER PANE for the master-layout
         */
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttons);

        borderPane.setPadding(new Insets(30, 10, 10, 10));

        /*
         * COMPLETION
         */
        Scene scene = new Scene(borderPane);

        this.setHeight(480);
        this.setWidth(750);

        this.setTitle("Erfassung eines BibEintrags");
        this.setScene(scene);
        this.show();
    }

    public boolean okButtonPressed(String titel, String autor, String jahrStillString, String typeOfBibEintrag) {

        if(titel.replaceAll("\\s+", "").length() == 0) {
            DialogUtil.showErrorDialog("Eingabe - ERROR!", "ERROR! Sie müssen für den Titel etwas eingeben!");
            return false;
        } else {
            if(autor.replaceAll("\\s+", "").length() == 0) {
                DialogUtil.showErrorDialog("Eingabe - ERROR!", "ERROR! Sie müssen für den Autornamen etwas eingeben!");
                return false;
            } else {
                try {
                    int jahr = Integer.parseInt(jahrStillString);

                    if(typeOfBibEintrag.equals("buch")) setEintrag(new Buch());
                    if(typeOfBibEintrag.equals("artikel")) setEintrag(new Artikel());
                    if(typeOfBibEintrag.equals("webseite")) setEintrag(new Webseite());

                    eintrag.setTitel(titel);
                    eintrag.setJahr(jahr);

                    String[] autorsNameSplitted = autor.split("\\s+");
                    String vorname, nachname;

                    if(autorsNameSplitted.length == 1) {
                        vorname = nachname = autorsNameSplitted[0];
                    }
                    else {
                        vorname = "";
                        for (int i = 0; i < autorsNameSplitted.length - 1; i++) {
                            vorname = vorname.concat(autorsNameSplitted[i] + " ");
                        }
                        vorname = vorname.substring(0, vorname.length() - 1); // delete last space

                        nachname = autorsNameSplitted[autorsNameSplitted.length - 1];
                    }

                    eintrag.setAutor(new Autor(vorname, nachname));

                    return true;
                }
                catch (NumberFormatException e) {
                    DialogUtil.showErrorDialog("Eingabe - ERROR!", "ERROR! Sie müssen für das Jahr gültige Zahl eingeben!");
                }
            }
        }
        return false;
    }

    public BibEintrag getEintrag() {
        return eintrag;
    }

    public void setEintrag(BibEintrag eintrag) {
        this.eintrag = eintrag;
    }

}
