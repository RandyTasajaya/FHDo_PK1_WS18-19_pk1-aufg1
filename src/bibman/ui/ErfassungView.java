package bibman.ui;

import bibman.BibEintrag;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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
     * Ich implementiere alles strikt gemäß Klassendiagramm auf dem Praktikum-Aufgabenblatt 11.
     *
     * Ich könnte diese showView()-Methode hier in der abstrakten Eltern-Klasse einfach leer lassen,
     * denn sie wird sowieso in den Kinder-Klassen überschrieben.
     *
     * Aber ich entschied mich dafür, hier
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

    public BibEintrag getEintrag() {
        return eintrag;
    }

    public void setEintrag(BibEintrag eintrag) {
        this.eintrag = eintrag;
    }

}
