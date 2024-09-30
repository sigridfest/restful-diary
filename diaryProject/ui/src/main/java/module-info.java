module diaryProject.ui {
    requires diaryProject.core;
    requires diaryProject.json;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens diaryProject.ui to javafx.graphics, javafx.fxml;
}
