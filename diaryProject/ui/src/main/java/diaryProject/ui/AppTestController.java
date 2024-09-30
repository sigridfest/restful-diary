package diaryProject.ui;

import diaryProject.core.Diary;
import diaryProject.core.Post;
import diaryProject.json.DiaryFileHandler;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * Copy of AppController with different file for testing.
 */
public class AppTestController {

  @FXML
  TextArea entry;
  
  @FXML
  TextArea searchField;

  @FXML
  Button add;
  
  @FXML
  Button searchButton;
  
  @FXML
  Button delete;

  @FXML
  Label view;

  @FXML
  ListView<Post> postsView;

  private Stage stage;
  private Scene scene;
  private Parent root;

  private Diary diary;
  private String fileName = "AppTest.json";
  private DiaryFileHandler fileHandler = new DiaryFileHandler(fileName);

  private void getInitialDiary() {
    diary = fileHandler.readDiaryFromFile();
    if (diary == null) {
      diary = new Diary();
    }
  }

  /**
   * Initializes appcontroller.
   */
  @FXML
  private void initialize() {
    getInitialDiary();
    updatePostsView();
    updateDiaryButtons();
    entry.setEditable(false);
    postsView.getSelectionModel().selectedItemProperty()
            .addListener((prop, oldValue, newValue) -> updateDiaryButtons());
  }

  /**
   * Disables delete button if the listview has no posts.
   */
  private void updateDiaryButtons() {
    boolean disable = postsView.getSelectionModel().getSelectedItem() == null;
    delete.setDisable(disable);
  }

  /**
   * Initializes new SubmitController.
   *
   * @param event ActionEvent add button is pushed
   *
   * @throws IOException may be thrown if Submit.fxml is not loaded properly.
   */
  @FXML
  public void onAdd(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = changeWindow(event, "Submit.fxml");

    SubmitController controller = fxmlLoader.getController();
    controller.initSubmit(diary); 
  }

  /**
   * Deletes the selected post when delete button is pushed.
   */
  @FXML
  private void onDelete() {
    Post post = (Post) postsView.getSelectionModel().getSelectedItem();
    diary.deletePost(post);
    updatePostsView();
    entry.clear();
    view.setText(post.toString() + " was deleted.");
    writeDiaryToFile();
  }

  /**
   * Searches for keyword(s) in posts.
   */
  @FXML
  public void onSearch() {
    String keyword = searchField.getText().toLowerCase();
    if (diary.getPosts().isEmpty()) {
      view.setText("Can not search in an empty diary.");
    } else if (diary.searchPost(keyword).isEmpty()) {
      view.setText("No search match for '" + keyword + "'.");
      postsView.getItems().clear();
    } else {
      view.setText("");
      entry.clear();
      postsView.getItems().clear();
      postsView.getItems().addAll(diary.searchPost(keyword));
    }
  }

  /**
   * Empties the searchfield.
   */
  @FXML
  public void onClearSearch() {
    entry.clear();
    view.setText("");
    String keyword = "";
    postsView.getItems().clear();
    postsView.getItems().addAll(diary.searchPost(keyword));
    searchField.clear();
  }

  /**
   * Opens post in entry field.
   *
   * @param arg0 MouseEvent post clicked
   */
  @FXML
  public void handleMouseClick(MouseEvent arg0) {
    Post post = postsView.getSelectionModel().getSelectedItem();
    if (post != null) {
      entry.setText(post.getEntry());
    }
  }

  /**
   * Updates the listview containing posts with the current posts in this.diary.
   */
  private void updatePostsView() {
    postsView.getItems().clear();
    postsView.getItems().addAll(diary.getPosts());
  }

  public void writeDiaryToFile() {
    fileHandler.writeDiaryToFile(this.diary);
  }

  /**
   * Loads new root and stage from file.
   *
   * @param event  Actionevent button clicked
   *
   * @param window String fxml source file to open
   *
   * @return fxmlLoader FXMLLoader with location set from file
   */
  private FXMLLoader changeWindow(ActionEvent event, String window) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource(window));
    root = fxmlLoader.load();
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    return fxmlLoader;
  }

  /**
   * Helper method for testing.
   *
   * @return text String from this.view
   */
  public String getViewText() {
    return view.getText();
  }

  /**
   * Helper method for testing.
   *
   * @return diary Diary from this.diary
   */
  public Diary getDiary() {
    return diary;
  }

  /**
   * Helper method for testing.
   *
   * @return listview Listview from this.postsview
   */
  public ListView<Post> getPostsView() {
    return postsView;
  }

}