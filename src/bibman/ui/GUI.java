package bibman.ui;

import bibman.Artikel;
import bibman.Autor;
import bibman.Buch;
import bibman.Webseite;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(new Scene(new Group()));
        primaryStage.centerOnScreen();
        primaryStage.show();

        Buch buch1 = new Buch(new Autor("Conrad", "Barski"),
                          "Land of Lisp",
                          2010,
                        "No Starch Press",
                          "9781593272814");

        Artikel artikel1 = new Artikel(new Autor("Donald E.", "Knuth"),
                                   "Computer programming as an art",
                                   1974,
                               "Communications of the ACM",
                                12);

        Webseite webseite1 = new Webseite(new Autor("Christian", "Ullenbloom"),
                                      "Java ist auch eine Insel",
                                      2016,
                                       "http://openbook.rheinwerk-verlag.de/javainsel/");

        Buch buch2 = null;
        Artikel artikel2 = null;
        Webseite webseite2 = null;

        ErfassungView buchErfassungView1 = new BuchErfassungView(primaryStage, buch1);
        buchErfassungView1.showView();

        ErfassungView artikelErfassungView1 = new ArtikelErfassungView(primaryStage, artikel1);
        artikelErfassungView1.showView();

        ErfassungView webseiteErfassungView1 = new WebseiteErfassungView(primaryStage, webseite1);
        webseiteErfassungView1.showView();

        /*
         * Zum Testen wenn das Objekt null ist
         */
        ErfassungView buchErfassungView2 = new BuchErfassungView(primaryStage, buch2);
        buchErfassungView2.showView();

        ErfassungView artikelErfassungView2 = new ArtikelErfassungView(primaryStage, artikel2);
        artikelErfassungView2.showView();

        ErfassungView webseiteErfassungView2 = new WebseiteErfassungView(primaryStage, webseite2);
        webseiteErfassungView2.showView();
    }

}
