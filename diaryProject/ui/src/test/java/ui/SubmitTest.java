package ui;

import diaryProject.core.Diary;
import diaryProject.core.Post;
import diaryProject.ui.SubmitTestController;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class SubmitTest extends ApplicationTest {
    private SubmitTestController controller;
    private Parent root;
    private Diary diary;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Submit_test.fxml"));
        root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
        diary = new Diary();
        Post post = new Post("I dag var det styremøte med badkom", LocalDate.parse("2005-02-04"));
        diary.addPost(post);
        controller.initSubmit(diary);
    }

    public Parent getRootNode() {
        return root;
    }

    @Order(1)
    @Test
    public void testController_initial() {
        assertNotNull(this.controller);
    }

    @Order(2)
    @Test
    public void testAddPost(){
        clickOn("#entry").write("yohooo bra dag");
        clickOn("#date").write("2007-01-01");
        clickOn("#submit");
        Label view = lookup("#view").query();

        assertTrue(view.getText().contains("was added."));
        TextArea entry = lookup("#entry").query();
        assertTrue(entry.getText().isEmpty());
    }
    @Order(3)
    @Test
    public void testAddIllegalPost() {
        TextField date = lookup("#date").query();
        clickOn("#entry").write("yohooo bra dag");
        clickOn("#date").write("2005-02-04");
        clickOn("#submit");
        assertEquals(diary.getPosts().size(), 1);
        date.setText("");
        clickOn("#entry").write("");
        clickOn("#date").write("2005-02-04");
        clickOn("#submit");
        Label view = lookup("#view").query();
        assertEquals(diary.getPosts().size(), 1);
        date.clear();
        clickOn("#date").write("2003.01.01");
        clickOn("#submit");
        assertTrue(view.getText().contains("Date must be on the format YYYY-MM-DD"));
    }

}