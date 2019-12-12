package ehu.isad;

import com.flickr4java.flickr.FlickrException;
import ehu.isad.flickr.FlickrAPI;
import ehu.isad.flickr.FlickrSortu;
import ehu.isad.flickrKud.*;
import ehu.isad.model.ListaBildumak;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

  public String username;
  public String usingApi;

  private Scene eKautoketa;
  private Scene eAccessTokenLortu;
  private Scene pantailaNagusia;
  private Scene argazkiaIgo;
  private Scene bildumaSortu;
  private Scene uploadError;

  private Parent kautotuUI;
  private Parent pantailaNagusiUI;
  private Parent kautotuFlickrUI;
  private Parent argazkiaIgoUI;
  private Parent bildumaSortuUI;
  private Parent uploadErrorUI;
  private Parent setPropErrorUI;

  private Stage stage;

  private KautotuKud kautotuKud;
  private PantailaNagusiKud pantailaNagusiKud;
  private KautotuFlickrKud kautotuFlickrKud;
  private ArgazkiaIgoKud argazkiaIgoKud;
  private BildumaSortuKud bildumaSortuKud;
  private UploadErrorKud uploadErrorKud;
  private SetPropErrorKud setPropErrorKud;

  private static String hizkuntza = "eu";
  private static String hizkuntzHerrialdea = "ES";

  @Override
  public void start(Stage primaryStage) throws Exception {

    stage = primaryStage;
    pantailakKargatu();


    eKautoketa = new Scene(kautotuUI, 500, 300);
    eAccessTokenLortu = new Scene(kautotuFlickrUI);
    pantailaNagusia = new Scene(pantailaNagusiUI);
    argazkiaIgo = new Scene(argazkiaIgoUI, 450, 450);
    bildumaSortu = new Scene(bildumaSortuUI, 450, 450);
    uploadError = new Scene(uploadErrorUI, 450, 450);

    stage.setTitle("DASI APP Argazki Backup");
    stage.setScene(eKautoketa);
    stage.show();
  }

  private void pantailakKargatu() throws IOException {

    // Hemen aldatu ahal da hizkuntza
    Locale locale = new Locale(hizkuntza,hizkuntzHerrialdea);
    ResourceBundle bundle = ResourceBundle.getBundle("UIResources", locale);

    FXMLLoader loaderKautotu = new FXMLLoader(getClass().getResource("/view/kautotu.fxml"), bundle);
    kautotuUI = (Parent) loaderKautotu.load();
    kautotuKud = loaderKautotu.getController();
    kautotuKud.setMainApp(this);

    FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/view/pantailaNagusia.fxml"), bundle);
    pantailaNagusiUI = (Parent) loaderMain.load();
    pantailaNagusiKud = loaderMain.getController();
    pantailaNagusiKud.setMainApp(this);

    FXMLLoader loaderKautotuFlickr = new FXMLLoader(getClass().getResource("/view/kautotuFlickr.fxml"), bundle);
    kautotuFlickrUI = (Parent) loaderKautotuFlickr.load();
    kautotuFlickrKud = loaderKautotuFlickr.getController();
    kautotuFlickrKud.setMainApp(this);

    FXMLLoader loaderArgazkiaIgo = new FXMLLoader(getClass().getResource("/view/argazkiaIgo.fxml"), bundle);
    argazkiaIgoUI = (Parent) loaderArgazkiaIgo.load();
    argazkiaIgoKud = loaderArgazkiaIgo.getController();
    argazkiaIgoKud.setMainApp(this);

    FXMLLoader loaderBildumaSortu = new FXMLLoader(getClass().getResource("/view/bildumaSortu.fxml"), bundle);
    bildumaSortuUI = (Parent) loaderBildumaSortu.load();
    bildumaSortuKud = loaderBildumaSortu.getController();
    bildumaSortuKud.setMainApp(this);

    FXMLLoader loaderUploadError = new FXMLLoader(getClass().getResource("/view/error/uploadError.fxml"), bundle);
    uploadErrorUI = (Parent) loaderUploadError.load();
    uploadErrorKud = loaderUploadError.getController();
    uploadErrorKud.setMainApp(this);

    FXMLLoader loaderSetPropErrorKud = new FXMLLoader(getClass().getResource("/view/error/setPropError.fxml"), bundle);
    setPropErrorUI = (Parent) loaderSetPropErrorKud.load();
    setPropErrorKud = loaderSetPropErrorKud.getController();
    setPropErrorKud.setMainApp(this);


  }


  public static void main(String[] args) {
    launch(args);
  }

  public void pantailaNagusiaErakutsi() {
    if (!Laguntzaile.emanSetupPropStatus()) {
      stage.setTitle("DasiAPP Main Page");
      pantailaNagusiKud.jarriErabiltzaileIzena();
      ListaBildumak.getNireBilduma().listaBeteDBrekin();
      //pantailaNagusiKud.syncEgin();
      pantailaNagusiKud.sartuBildumakListan();
      pantailaNagusiKud.sartuDatuakTaulan();
      stage.setScene(pantailaNagusia);
      stage.show();

      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
      stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    } else
      setupPropertiesError();
  }

  public void kautoketaraEraman() {
    stage.setScene(eKautoketa);
    stage.show();
  }

  public void kautotuFlickrErakutsi(String zerbitzua) {
    if (!Laguntzaile.emanSetupPropStatus()) {
      kautotuFlickrKud.gordeURL();
      kautotuFlickrKud.gordeZerbitzua(zerbitzua);
      stage.setScene(eAccessTokenLortu);
      stage.show();
    } else
      setupPropertiesError();
  }

  public void bildumaSortuErakutsi(){

    stage.setTitle("Bilduma sortu");
    stage.setScene(bildumaSortu);
    stage.show();
  }

  public void argazkiaIgoErakutsi(){
    stage.setTitle("Argazkia igo");
    stage.setScene(argazkiaIgo);
    stage.show();
  }

  public void erroreaBistaratu(String erroreMota) throws IOException {

    if(erroreMota.equals("UploadError")) {
      stage.setTitle("Upload Error");
      stage.setScene(uploadError);
      stage.show();
    }
  }

  public void hizkuntzaAldatu(String hizkuntzBerria, String herrialdeBerria) throws Exception {
    hizkuntza = hizkuntzBerria;
    hizkuntzHerrialdea = herrialdeBerria;
    this.start(stage);
  }

  public void logoutAktibatu() {
    kautotuKud.logoutAktibatu();
  }


  public void syncEginLehenAldia() {
    pantailaNagusiKud.jarriErabiltzaileIzena();
    try {
      pantailaNagusiKud.hartuEtaGordeDatuakFlickr();
    } catch (FlickrException e) {
      e.printStackTrace();
    }
    // ListaBildumako datuak DBra sartu, lehen aldia delako
    ListaBildumak.getNireBilduma().sartuDatuakDBra();
    pantailaNagusiKud.sartuBildumakListan();
    pantailaNagusiKud.sartuDatuakTaulan();

    stage.setScene(pantailaNagusia);
    stage.show();

    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
  }

  public void setupPropertiesError() {
    stage.setScene(new Scene(setPropErrorUI, 400, 210));
    stage.show();

    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    stage.setResizable(false);
  }

  public void itxi() {
    stage.close();
  }
}
