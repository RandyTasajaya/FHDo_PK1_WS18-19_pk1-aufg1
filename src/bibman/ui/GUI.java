package bibman.ui;

import bibman.BibEintrag;
import bibman.BibEintragComparator;
import bibman.BibManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class GUI extends Application {

    public static BibManager bibManager = new BibManager();
    private static ObjectProperty<BibManager> bibManagerProperty = new SimpleObjectProperty<>();

    private static ListProperty<BibEintrag> listProperty = new SimpleListProperty<>();

    @Override
    public void start(Stage primaryStage) {

        /*
         * For logging
         */
        bibManager.addObserver(new Observer() {

            public void log(BibManager manager) {
                File log = new File("log.txt");

                try(FileWriter fw = new FileWriter(log, true);
                    PrintWriter pw = new PrintWriter(fw)) {
                    log.createNewFile();

                    BibEintrag eintrag = manager.getEintrag(manager.getSize() - 1);

                    pw.write("[" + new Date() + "] Eintrag hinzugefügt: " +
                            "id=" + eintrag.getId() + ", " +
                            "autor=" + eintrag.getAutor().getFullname() + ", " +
                            "titel=" + eintrag.getTitel() + ", " +
                            "jahr=" + eintrag.getJahr() +
                            "\n");
                }
                catch (IOException e) {
                    DialogUtil.showErrorDialog("Logging - ERROR!", DialogUtil.printStackTrace_toString(e));
                    e.printStackTrace();
                }
            }

            @Override
            public void update(Observable o, Object arg) {
                log((BibManager)o);
            }
        });


        /*
         * For observing changes (of <wrapper> contents) of the <wrapper> object bibManager
         */
        bibManagerProperty.addListener(new ChangeListener<BibManager>() {
            @Override
            public void changed(ObservableValue<? extends BibManager> observable, BibManager oldValue, BibManager newValue) {
                newValue.getBibEintraege().sort(new BibEintragComparator());
                listProperty.set(FXCollections.observableArrayList(newValue.getBibEintraege()));
            }
        });


        /*
         * List of BibEintrag items
         */
        ListView<BibEintrag> listView = new ListView<>();
        listView.itemsProperty().bind(listProperty);


        /*
         * Menu bar
         */

        // Menu DATEI
        Menu datei = new Menu("Datei");

        MenuItem laden = new MenuItem("Laden");
        MenuItem speichern = new MenuItem("Speichern");
        MenuItem csvExport = new MenuItem("CSV-Export");
        MenuItem beenden = new MenuItem("Beenden");

        datei.getItems().addAll(laden,
                                speichern,
                                new SeparatorMenuItem(),
                                csvExport,
                                new SeparatorMenuItem(),
                                beenden);

        laden.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File("BibManager_GUI.ser");

                try(FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {

                    bibManager = (BibManager)ois.readObject();
                    BibEintrag.setIdHelper(bibManager.getIdHelperOfBibEintrag());

                    updateBibManager();

                    DialogUtil.showMessageDialog("Laden - ERFOLGREICH!", "Datei wurde geladen!");
                }
                catch (FileNotFoundException | ClassNotFoundException e) {
                    String message = "\n!!! LIES MICH !!!\n-----------------\nDieses Exception taucht bestimmt auf, denn sein Fall wird nicht kodiert, weil es eben von der Praktikumaufgabe nicht gefordert wird. ~Randy Tasajaya";
                    DialogUtil.showErrorDialog("Laden - ERROR!", message);
                    e.printStackTrace();
                }
                catch (IOException | ClassCastException e) {
                    DialogUtil.showErrorDialog("Laden - ERROR!", DialogUtil.printStackTrace_toString(e));
                    e.printStackTrace();
                }
            }
        });

        speichern.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (bibManager.getSize() == 0) {
                    DialogUtil.showErrorDialog("Speichern - ERROR!", "BibManager hat keine Einträge zu speichern!");
                } else {
                    File file = new File("BibManager_GUI.ser");

                    try (FileOutputStream fos = new FileOutputStream(file);
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                        oos.writeObject(bibManager);

                        DialogUtil.showMessageDialog("Speichern - Erfolgreich!", "Datei wurde gespeichert!");
                    }
                    catch (FileNotFoundException e) {
                        DialogUtil.showErrorDialog("Speichern - ERROR!", DialogUtil.printStackTrace_toString(e));
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        DialogUtil.showErrorDialog("Speichern - ERROR!", DialogUtil.printStackTrace_toString(e));
                        e.printStackTrace();
                    }
                }
            }
        });

        csvExport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                csvExportDialog();
            }
        });

        beenden.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Platform.exit();
                }
                catch (Exception e) {
                    DialogUtil.showErrorDialog("Beenden - ERROR!", DialogUtil.printStackTrace_toString(e));
                    e.printStackTrace();
                }
            }
        });


        // Menu EINTRAG
        Menu eintrag = new Menu("Eintrag");

        MenuItem buchHinzufuegen = new MenuItem("Buch hinzufügen");
        MenuItem artikelHinzufuegen = new MenuItem("Artikel hinzufügen");
        MenuItem webseiteHinzufuegen = new MenuItem("Webseite hinzufügen");

        eintrag.getItems().addAll(buchHinzufuegen,
                                  artikelHinzufuegen,
                                  webseiteHinzufuegen);

        buchHinzufuegen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BuchErfassungView buchErfassungView = new BuchErfassungView(null, null);
                buchErfassungView.showView();
            }
        });

        artikelHinzufuegen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArtikelErfassungView artikelErfassungView = new ArtikelErfassungView(null, null);
                artikelErfassungView.showView();
            }
        });

        webseiteHinzufuegen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WebseiteErfassungView webseiteErfassungView = new WebseiteErfassungView(null, null);
                webseiteErfassungView.showView();
            }
        });


        // Menu ANZEIGE
        Menu anzeige = new Menu("Anzeige");

        MenuItem berechneDurchschnittlichesErscheinungsjahr = new MenuItem("Berechne durchschnittliches Erscheinungsjahr");
        MenuItem sucheNeuestenEintrag = new MenuItem("Suche neuesten Eintrag");

        anzeige.getItems().addAll(berechneDurchschnittlichesErscheinungsjahr,
                                  sucheNeuestenEintrag);

        berechneDurchschnittlichesErscheinungsjahr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DialogUtil.showMessageDialog("Berechne durchschnittliches Erscheinungsjahr",
                        "Der Durschnitt der Erscheinungsjahre ist " +
                                (int)bibManager.berechneErscheinungsjahr() + ".");
            }
        });

        sucheNeuestenEintrag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DialogUtil.showMessageDialog("Suche neuesten Eintrag",
                            "Der neueste Eintrag ist: \n" +
                                    bibManager.sucheNeuestenEintrag().toString());
                }
                catch(IllegalStateException e) {
                    DialogUtil.showErrorDialog("Suche neuesten Eintrag - ERROR!", e.getMessage());
                }
            }
        });


        /**
         * Menu DEVELOPER
         *
         * It can be turned on/off from the code (yeah not the prettiest thing)
         *
         * by commenting (out) this line
         * {@code menuBar.getMenus().add(developer);}
         *
         * under
         * {@code // Menu bar COMPLETION}
         */
        Menu developer = new Menu("Developer");

        MenuItem refreshListView = new MenuItem("Force Refresh ListView");
        refreshListView.setAccelerator(KeyCombination.valueOf("Ctrl+R"));

        developer.getItems().addAll(refreshListView);

        refreshListView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listView.refresh();
            }
        });


        // Menu bar COMPLETION
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(datei, eintrag, anzeige);

        // Developer menu on/off
//        menuBar.getMenus().add(developer);


        /*
         * BORDER PANE for the master-layout
         */
        BorderPane borderPane = new BorderPane(listView);
        borderPane.setTop(menuBar);


        /*
         * COMPLETION
         */
        Scene scene = new Scene(borderPane);

        primaryStage.setHeight(480);
        primaryStage.setWidth(750);

        primaryStage.setTitle("Bib-Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void csvExportDialog() {
        String dateiname = DialogUtil.showInputDialog("CSV-Export", "Dateiname für die zu erstellende CSV-Datei:");

        if(dateiname.equals("26071994-CANCEL")) {
            return;
        }

        if(dateiname.replaceAll("\\s+", "").length() == 0) {
            DialogUtil.showErrorDialog("CSV-Export - ERROR!", "Sie müssen für den Dateinamen etwas eingeben!");
            csvExportDialog();
            return; // to avoid unneccesary or even faulty recursive
        }

        File csv = new File(dateiname + ".csv");

        try {
            if(!csv.createNewFile()) {
                boolean response = DialogUtil.showConfirmDialog("CSV-Export", "Die CSV-Datei existiert bereits. Möchten Sie die existierende Datei überschreiben?");

                if(response) {
                    csv.delete();
                } else {
                    csvExportDialog();
                    return; // to avoid unneccesary or even faulty recursive
                }
            }
            else {
                csv.createNewFile();
            }
            bibManager.exportiereEintraegeAlsCsv(csv);
        }
        catch (FileNotFoundException e) {
            DialogUtil.showErrorDialog("CSV-Export - ERROR!", DialogUtil.printStackTrace_toString(e));
            e.printStackTrace();
        }
        catch (IOException e) {
            DialogUtil.showErrorDialog("CSV-Export - ERROR!", DialogUtil.printStackTrace_toString(e));
            e.printStackTrace();
        }
    }

    public static void updateBibManager() {

        BibManager newBibManager = new BibManager();

        newBibManager.setBibEintraege(bibManager.getBibEintraege());
        newBibManager.setAutorsEintraege(bibManager.getAutorsEintraege());

        /*
         * For logging (that is gone if already implemented before, because it's implemented as anonymous class)
         */
        newBibManager.addObserver(new Observer() {

            public void log(BibManager manager) {
                File log = new File("log.txt");

                try(FileWriter fw = new FileWriter(log, true);
                    PrintWriter pw = new PrintWriter(fw)) {
                    log.createNewFile();

                    BibEintrag eintrag = manager.getEintrag(manager.getSize() - 1);

                    pw.write("[" + new Date() + "] Eintrag hinzugefügt: " +
                            "id=" + eintrag.getId() + ", " +
                            "autor=" + eintrag.getAutor().getFullname() + ", " +
                            "titel=" + eintrag.getTitel() + ", " +
                            "jahr=" + eintrag.getJahr() +
                            "\n");
                }
                catch (IOException e) {
                    DialogUtil.showErrorDialog("Logging - ERROR!", DialogUtil.printStackTrace_toString(e));
                    e.printStackTrace();
                }
            }

            @Override
            public void update(Observable o, Object arg) {
                log((BibManager)o);
            }
        });

        bibManager = newBibManager;
        bibManagerProperty.set(bibManager);
    }

}
