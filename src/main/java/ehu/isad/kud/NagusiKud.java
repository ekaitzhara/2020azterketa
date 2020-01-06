package ehu.isad.kud;

import ehu.isad.Main;
import ehu.isad.db.DatuakDBKud;
import ehu.isad.model.TaulaDatu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.*;

public class NagusiKud  implements Initializable {

    private Main mainApp;

    // Taulako elementuak

    @FXML
    private TableView<TaulaDatu> tbData;

    @FXML
    private TableColumn<TaulaDatu, Integer> id;

    @FXML
    private TableColumn<TaulaDatu, String> path;

    @FXML
    private TableColumn<TaulaDatu, String> content;

    @FXML
    private TableColumn<TaulaDatu, Date> date;

    @FXML
    private TableColumn<TaulaDatu, Image> irudia;

    // Taulako datuak gordetzeko modeloa
    private ObservableList<TaulaDatu> taulaModels = FXCollections.observableArrayList(
            DatuakDBKud.getInstantzia().emanDatuak()
          );

    @FXML
    private Label proba = new Label();


    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Taulako edozer editagarri izateko, taula editagarria izan behar da
        tbData.setEditable(true);
        // Zutabeen definizioak -> izenak modeloaren izen berdin-berdina izan behar du, eta bertan get metodoa eduki
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        path.setCellValueFactory(new PropertyValueFactory<>("filename"));
        content.setCellValueFactory(new PropertyValueFactory<>("value"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Content editagarri egiteko
        Callback<TableColumn<TaulaDatu, String>, TableCell<TaulaDatu, String>> defaultTextFieldCellFactory
                = TextFieldTableCell.<TaulaDatu>forTableColumn();

        content.setCellFactory(col -> {
            TableCell<TaulaDatu, String> cell = defaultTextFieldCellFactory.call(col);
            cell.itemProperty().addListener((obs, oldValue, newValue) -> {
                TableRow row = cell.getTableRow();
                if (row == null) {
                    cell.setEditable(false);
                } else {
                    TaulaDatu item = (TaulaDatu) cell.getTableRow().getItem();
                    if (item == null) {
                        cell.setEditable(false);
                    } else {
                        cell.setEditable(true);
                    }
                }
            });
            return cell ;
        });

        content.setOnEditCommit(
                t -> t.getTableView().getItems().get(t.getTablePosition().getRow())
                        .setValue(t.getNewValue())
        );

        // ARGAZKIA
        irudia.setCellValueFactory(new PropertyValueFactory<TaulaDatu, Image>("irudia"));
        irudia.setCellFactory(p -> new TableCell<>() {
            public void updateItem(Image image, boolean empty) {
                if (image != null && !empty){
                    final ImageView imageview = new ImageView();
                    imageview.setFitHeight(50);
                    imageview.setFitWidth(100);
                    imageview.setImage(image);
                    setGraphic(imageview);
                    setAlignment(Pos.CENTER);
                    // tbData.refresh();
                }else{
                    setGraphic(null);
                    setText(null);
                }
            };
        });

        // Datuak taulan sartu
        tbData.setItems(taulaModels);

        proba.setText(DatuakDBKud.getInstantzia().probaMysql());

    }

    @FXML
    public void txertatuKlik(ActionEvent actionEvent) {
        // fitxategi bat deskargatzeko url bat emanez
        String filename = "captcha"+(int)(Math.random()*((1000000000)+1))+".png";
//        String irudiak_src = "/home/ekaitzhara/Documentos/UNI/InformazioSistemenAnalisia/3_partziala/2020azterketa_EkaitzHurtado/src/main/resources/irudiak/";
//        String irudiak_build = "/home/ekaitzhara/Documentos/UNI/InformazioSistemenAnalisia/3_partziala/2020azterketa_EkaitzHurtado/build/resources/main/irudiak/";
        String azterketarakoKarpeta = "/home/ekaitzhara/Documentos/UNI/InformazioSistemenAnalisia/3_partziala/irudiak_azterketa2020/";
        azterketarakoKarpeta=azterketarakoKarpeta.replace('/',File.separatorChar);

//        downloadFromUrl(filename, irudiak_src);
//        downloadFromUrl(filename, irudiak_build);
        downloadFromUrl(filename, azterketarakoKarpeta);

        long l = new Date().getTime();
        DatuakDBKud.getInstantzia().sartuBerria(filename, l);
        taulaModels.add(new TaulaDatu(DatuakDBKud.getInstantzia().emanId(filename, l), filename, null, String.valueOf(l)));
    }

    private void downloadFromUrl(String filename, String destiny) {
        try (
                BufferedInputStream inputStream = new BufferedInputStream(new URL("http://45.32.169.98/captcha.php").openStream());
//                FileOutputStream fileOS = new FileOutputStream("irudiak/captcha"+ (int)Math.random() +".png")) {
                FileOutputStream fileOS = new FileOutputStream(destiny +filename)) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
            System.out.println("Captcha deskargatu da");
        } catch (IOException e) {
            // handles IO exceptions
            e.printStackTrace();
        }
    }


    @FXML
    public void gordeKlik(ActionEvent actionEvent) {
        ArrayList<TaulaDatu> datuak = new ArrayList<>();
        Iterator it = taulaModels.iterator();
        while (it.hasNext())
            datuak.add((TaulaDatu) it.next());
        DatuakDBKud.getInstantzia().eguneratuContentGuztiak(datuak);
    }


}
