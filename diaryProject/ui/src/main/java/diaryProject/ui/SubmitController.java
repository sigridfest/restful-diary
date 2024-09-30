package diaryProject.ui;

import diaryProject.core.Diary;
import diaryProject.core.Post;
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
 * Contoller for window when New post is pressed.
 */
public class SubmitController {

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
  private RemoteDiaryAccess remoteDiaryAccess;
  private boolean validDate;
  private boolean validEntry;
  
  
  /**
   *  Creates listeners on date- and entry-field to give direct user feedback when format is wrong.
   */
  @FXML
  private void initialize() {
    validPost();
    this.date.textProperty().addListener((prop, oldValue, newValue) -> {
      view.setText("");
      validateDate();
      validateEntry();
    });
    this.entry.textProperty().addListener((prop, oldValue, newValue) -> {
      view.setText("");
      validateEntry();
      validateDate();
    });
  }
  
  /**
   * Enables the submit button if both date and entry is on valid format, disables it if not.
   */
  private void validPost() {
    submit.setDisable(!(validDate && validEntry));
  }
  
  /**
   * Validates a date.
   */
  private void validateDate() {
    try {
      LocalDate.parse(date.getText());
      validDate = true;
      validPost();
    } catch (DateTimeParseException e) {
      if (view.getText().equals("")) {
        view.setText("Date must be on the format YYYY-MM-DD");
        validDate = false;
        validPost();
      }
    }
  }

  /**
   * Validates an entry.
   */
  private void validateEntry() {
    if (view.getText().equals("")) {
      if (entry.getText().equals("")) {
        view.setText("Entry cannot be empty.");
        validEntry = false;
        validPost();
      } else {
        view.setText("");
        validEntry = true;
        validPost();
      }
    }
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
      remoteDiaryAccess.addPost(post);
      clearFields();
      view.setText(post + " was added.");
    } catch (DateTimeParseException e) {
      view.setText("Date must be on the format YYYY-MM-DD.");
    } catch (IllegalArgumentException e) {
      if (e.getMessage().contains("Entry")) {
        view.setText("Entry cannot be empty.");
      }
      if (e.getMessage().contains("future")) {
        view.setText("Date cannot be in the future.");
      }
      if (e.getMessage().contains("exists")) {
        view.setText("Couldn't add post, date already exists.");
      }
    } catch (Exception e) {
      e.getCause().printStackTrace();
    }
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

  public void setRemoteDiaryAccess(RemoteDiaryAccess remoteDiaryAccess) {
    this.remoteDiaryAccess = remoteDiaryAccess;
  }

  
  public void initSubmit(Diary diary) {
    this.diary = diary;
  }

}
