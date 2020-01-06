package ehu.isad;


import ehu.isad.kud.NagusiKud;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

  private Scene eNagusi;

  private Parent nagusiUI;

  private Stage stage;

  private NagusiKud nagusiKud;

  @Override
  public void start(Stage primaryStage) throws Exception {

    stage = primaryStage;
    pantailakKargatu();


    eNagusi = new Scene(nagusiUI, 800, 500);
    stage.setTitle("Azterketa 2019/2020 Ekaitz Hurtado");
    stage.setScene(eNagusi);
    stage.show();

    // Pantailaren erdian kokatzeko
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
  }

  private void pantailakKargatu() throws IOException {

    FXMLLoader loaderKautotu = new FXMLLoader(getClass().getResource("/view/nagusi.fxml"));
    nagusiUI = (Parent) loaderKautotu.load();
    nagusiKud = loaderKautotu.getController();
    nagusiKud.setMainApp(this);

  }


  public static void main(String[] args) {
    launch(args);
  }
}
