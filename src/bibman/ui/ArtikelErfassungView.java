package bibman.ui;

import bibman.Artikel;
import bibman.DoppelterBibEintragException;
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
import javafx.stage.Stage;

public class ArtikelErfassungView extends ErfassungView {

    private Artikel artikel;

    public ArtikelErfassungView(Stage parentStage, Artikel artikel) {
        super(parentStage, artikel);
        this.artikel = (Artikel)super.getEintrag();
    }

    @Override
    public void showView() {
        Label titel = new Label("Titel:");
        Label autor = new Label("Autor:");
        Label jahr = new Label("Jahr:");
        Label zeitschrift = new Label("Zeitschrift:");
        Label ausgabe = new Label("Ausgabe:");

        TextField titelTF = new TextField();
        TextField autorTF = new TextField();
        TextField jahrTF = new TextField();
        TextField zeitschriftTF = new TextField();
        TextField ausgabeTF = new TextField();

        if(artikel != null) {
            titelTF.appendText(artikel.getTitel());
            autorTF.appendText(artikel.getAutor().getFullname());
            jahrTF.appendText(String.valueOf(artikel.getJahr()));
            zeitschriftTF.appendText(artikel.getZeitschrift());
            ausgabeTF.appendText(String.valueOf(artikel.getAusgabe()));
        }

        Button ok = new Button("OK");
        Button abbrechen = new Button("Abbrechen");

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean inputResult = okButtonPressed(titelTF.getText(), autorTF.getText(), jahrTF.getText(), "artikel");

                if(inputResult) {
                    ArtikelErfassungView.this.artikel = (Artikel)ArtikelErfassungView.super.getEintrag();

                    // Gemäß vorherigen Praktika
                    artikel.setZeitschrift("Default-Zeitschrift");
                    artikel.setAusgabe(-1);

                    try {
                        GUI.bibManager.hinzufuegen(artikel);

                        GUI.updateBibManager();

                        DialogUtil.showMessageDialog("Artikel hinzufügen - ERFOLGREICH!", "Der Artikel wurde hinzugefügt!");
                        close();
                    }
                    catch (DoppelterBibEintragException e) {
                        DialogUtil.showErrorDialog("Artikel hinzufügen - ERROR!", e.getMessage());
                        close();
                    }
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
        gridPane.add(zeitschrift, 0, 3);
        gridPane.add(zeitschriftTF, 1, 3);
        gridPane.add(ausgabe, 0, 4);
        gridPane.add(ausgabeTF, 1, 4);

        GridPane.setHalignment(titel, HPos.RIGHT);
        GridPane.setHalignment(autor, HPos.RIGHT);
        GridPane.setHalignment(jahr, HPos.RIGHT);
        GridPane.setHalignment(zeitschrift, HPos.RIGHT);
        GridPane.setHalignment(ausgabe, HPos.RIGHT);

        TextField[] allTextFields = {titelTF, autorTF, jahrTF, zeitschriftTF, ausgabeTF};
        for (TextField tf : allTextFields) {
            tf.setMaxWidth(Double.MAX_VALUE);
        }

        GridPane.setHgrow(titelTF, Priority.ALWAYS);
        GridPane.setHgrow(autorTF, Priority.ALWAYS);
        GridPane.setHgrow(jahrTF, Priority.ALWAYS);
        GridPane.setHgrow(zeitschriftTF, Priority.ALWAYS);
        GridPane.setHgrow(ausgabeTF, Priority.ALWAYS);

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

        this.setTitle("Erfassung eines Artikels");
        this.setScene(scene);
        this.show();
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

}
