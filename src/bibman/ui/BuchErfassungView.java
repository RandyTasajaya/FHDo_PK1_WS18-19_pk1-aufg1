package bibman.ui;

import bibman.Buch;
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

public class BuchErfassungView extends ErfassungView {

    private Buch buch;

    public BuchErfassungView(Stage parentStage, Buch buch) {
        super(parentStage, buch);
        this.buch = (Buch)super.getEintrag();
    }

    @Override
    public void showView() {
        Label titel = new Label("Titel:");
        Label autor = new Label("Autor:");
        Label jahr = new Label("Jahr:");
        Label verlag = new Label("Verlag:");
        Label isbn = new Label("ISBN:");

        TextField titelTF = new TextField();
        TextField autorTF = new TextField();
        TextField jahrTF = new TextField();
        TextField verlagTF = new TextField();
        TextField isbnTF = new TextField();

        if(buch != null) {
            titelTF.appendText(buch.getTitel());
            autorTF.appendText(buch.getAutor().getFullname());
            jahrTF.appendText(String.valueOf(buch.getJahr()));
            verlagTF.appendText(buch.getVerlag());
            isbnTF.appendText(buch.getIsbn());
        }

        Button ok = new Button("OK");
        Button abbrechen = new Button("Abbrechen");

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean inputResult = okButtonPressed(titelTF.getText(), autorTF.getText(), jahrTF.getText(), "buch");

                if(inputResult) {
                    BuchErfassungView.this.buch = (Buch)BuchErfassungView.super.getEintrag();

                    // Gemäß vorherigen Praktika
                    buch.setVerlag("Default-Verlag");
                    buch.setIsbn("Default-ISBN");

                    try {
                        GUI.bibManager.hinzufuegen(buch);

                        GUI.updateBibManager();

                        DialogUtil.showMessageDialog("Buch hinzufügen - ERFOLGREICH!", "Das Buch wurde hinzugefügt!");
                        close();
                    }
                    catch (DoppelterBibEintragException e) {
                        DialogUtil.showErrorDialog("Buch hinzufügen - ERROR!", e.getMessage());
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
        gridPane.add(verlag, 0, 3);
        gridPane.add(verlagTF, 1, 3);
        gridPane.add(isbn, 0, 4);
        gridPane.add(isbnTF, 1, 4);

        GridPane.setHalignment(titel, HPos.RIGHT);
        GridPane.setHalignment(autor, HPos.RIGHT);
        GridPane.setHalignment(jahr, HPos.RIGHT);
        GridPane.setHalignment(verlag, HPos.RIGHT);
        GridPane.setHalignment(isbn, HPos.RIGHT);

        TextField[] allTextFields = {titelTF, autorTF, jahrTF, verlagTF, isbnTF};
        for (TextField tf : allTextFields) {
            tf.setMaxWidth(Double.MAX_VALUE);
        }

        GridPane.setHgrow(titelTF, Priority.ALWAYS);
        GridPane.setHgrow(autorTF, Priority.ALWAYS);
        GridPane.setHgrow(jahrTF, Priority.ALWAYS);
        GridPane.setHgrow(verlagTF, Priority.ALWAYS);
        GridPane.setHgrow(isbnTF, Priority.ALWAYS);

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

        this.setTitle("Erfassung eines Buches");
        this.setScene(scene);
        this.show();
    }

    public Buch getBuch() {
        return buch;
    }

    public void setBuch(Buch buch) {
        this.buch = buch;
    }

}
