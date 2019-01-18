package bibman.ui;

import bibman.Webseite;
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

public class WebseiteErfassungView extends ErfassungView {

    private Webseite webseite;

    public WebseiteErfassungView(Stage parentStage, Webseite webseite) {
        super(parentStage, webseite);
        this.webseite = (Webseite)super.getEintrag();
    }

    public void showView() {
        Label titel = new Label("Titel:");
        Label autor = new Label("Autor:");
        Label jahr = new Label("Jahr:");
        Label url = new Label("URL:");

        TextField titelTF = new TextField();
        TextField autorTF = new TextField();
        TextField jahrTF = new TextField();
        TextField urlTF = new TextField();

        if(webseite != null) {
            titelTF.appendText(webseite.getTitel());
            autorTF.appendText(webseite.getAutor().getFullname());
            jahrTF.appendText(String.valueOf(webseite.getJahr()));
            urlTF.appendText(webseite.getUrl());
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
        gridPane.add(url, 0, 3);
        gridPane.add(urlTF, 1, 3);

        GridPane.setHalignment(titel, HPos.RIGHT);
        GridPane.setHalignment(autor, HPos.RIGHT);
        GridPane.setHalignment(jahr, HPos.RIGHT);
        GridPane.setHalignment(url, HPos.RIGHT);

        TextField[] allTextFields = {titelTF, autorTF, jahrTF, urlTF};
        for (TextField tf : allTextFields) {
            tf.setMaxWidth(Double.MAX_VALUE);
        }

        GridPane.setHgrow(titelTF, Priority.ALWAYS);
        GridPane.setHgrow(autorTF, Priority.ALWAYS);
        GridPane.setHgrow(jahrTF, Priority.ALWAYS);
        GridPane.setHgrow(urlTF, Priority.ALWAYS);

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

        this.setTitle("Erfassung einer Webseite");
        this.setScene(scene);
        this.show();
    }

    public Webseite getWebseite() {
        return webseite;
    }

    public void setWebseite(Webseite webseite) {
        this.webseite = webseite;
    }

}
