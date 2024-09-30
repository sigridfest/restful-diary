package ui;

import diaryProject.core.Post;
import diaryProject.ui.AppTestController;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class AppTest extends ApplicationTest {
    private AppTestController controller;
    private Parent root;
   
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("App_test.fxml"));
        root = loader.load();
        this.controller = loader.getController();       
        controller.getDiary().addPost(new Post("Gikk på topptur og spiste pinnebrød.", LocalDate.parse("1998-01-01")));
        controller.getDiary().addPost(new Post("Spiste tapas med exphil-gruppa", LocalDate.parse("1997-01-10")));
        stage.setScene(new Scene(root));
        stage.show();
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
    public void testInitialize() {
        ListView<Post> view = lookup("#postsView").query();
        assertNotNull(view.getItems());
    }

    @Order(3)
    @Test
    public void testOnSearch() throws InterruptedException {
        TextArea searchField = lookup("#searchField").query();
        clickOn("#searchField").write("");
        clickOn("#searchButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#searchField").write("topptur");
        clickOn("#searchButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(controller.getPostsView().getItems().isEmpty());
        searchField.setText("");
        clickOn("#searchField").write("sksksksk");
        clickOn("#searchButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(controller.getPostsView().getItems().isEmpty());
        searchField.setText("");
        clickOn("#searchField").write("spiste");
        clickOn("#searchButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(2, controller.getPostsView().getItems().size());
        
    }

    @Order(4)
    @DisplayName("Test addButton")
    @Test
    public void testOnAdd() throws InterruptedException {
        clickOn("#searchField").write("");
        clickOn("#searchButton");
        WaitForAsyncUtils.waitForFxEvents();
        Scene startScene = Window.getWindows().get(0).getScene();
        clickOn("#add");
        WaitForAsyncUtils.waitForFxEvents();
        Scene addScene = Window.getWindows().get(0).getScene();
        assertNotEquals(addScene, startScene);
    }

    @Order(6)
    @Test
    public void testClearSearch(){
        clickOn("#searchField").write("Hei");
        clickOn("#searchButton");
        clickOn("#clearsearchButton");
        TextArea searchField = lookup("#searchField").query();
        assertTrue(searchField.getText().equals(""));
        TextArea entry = lookup("#entry").query();
        assertTrue(entry.getText().equals(""));
    }

    @Order(7)
    @Test
    public void testOnDelete() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#searchField").write("");
        clickOn("#searchButton");
        ListView<Post> view = lookup("#postsView").query();
        view.getSelectionModel().selectFirst();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#delete");
        WaitForAsyncUtils.waitForFxEvents();
        controller.getViewText().contains("was deleted");
        WaitForAsyncUtils.waitForFxEvents();
        view.getSelectionModel().selectFirst();
        clickOn("#delete");
        WaitForAsyncUtils.waitForFxEvents();
        controller.getViewText().contains("was deleted");

        assertTrue(view.getItems().isEmpty());
    }
}