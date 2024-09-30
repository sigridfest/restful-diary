package diaryProject.ui;

import diaryProject.core.Diary;
import diaryProject.core.Post;
import diaryProject.json.DiaryFileHandler;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Copy of SubmitController with file functionality for testing.
 */
public class SubmitTestController {

  @FXML
  TextArea entry;
  
  @FXML
  TextField date;

  @FXML
  Label view;

  @FXML
  Button submit;

  private Stage stage;
  private Scene scene;
  private Parent root;
  private Diary diary;

  public void initSubmit(Diary diary) {
    this.diary = diary;
  }

  /**
   * Saves new post to file and clears inputfields.
   */
  @FXML
  private void onSubmit() {
    String date = this.date.getText();
    String entry = this.entry.getText();
    try {
      LocalDate localDate = LocalDate.parse(date);
      Post post = new Post(entry, localDate);
      diary.addPost(post);
      writeDiaryToFile();
      view.setText(post + " was added.");
      clearFields();
    } catch (DateTimeParseException e) {
      view.setText("Date must be on the format YYYY-MM-DD.");
    } catch (IllegalArgumentException e) {
      if (e.getMessage().contains("Entry")) {
        view.setText("Entry cannot be empty.");
      }
      if (e.getMessage().contains("future")) {
        view.setText("Date cannot be in the future.");
      }
      if (e.getMessage().contains("exits")) {
        view.setText("Couldn't add post because a post with this date already exists.");
      }
    } catch (Exception e) {
      e.getCause().printStackTrace();
    }
  }

  /**
   * Writes to different file for testing.
   */
  public void writeDiaryToFile() {
    String fileName = "SubmitTest.json";
    DiaryFileHandler fileHandler = new DiaryFileHandler(fileName);
    fileHandler.writeDiaryToFile(this.diary);
  }

  
  /**
   * Returns to original App stage.
   *
   * @param event ActionEvent back button clicked
   */
  @FXML
  public void onBack(ActionEvent event) throws IOException {
    clearFields();
    root = FXMLLoader.load(getClass().getResource("App.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Used to clear textfields on submission.
   */
  public void clearFields() {
    date.clear();
    entry.clear();
  }

}
