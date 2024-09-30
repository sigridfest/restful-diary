package diaryProject.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App.
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
    Scene scene = new Scene(root);
    stage.setTitle("My diary");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}