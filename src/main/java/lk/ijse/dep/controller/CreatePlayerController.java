//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.Objects;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import lk.ijse.dep.util.DEPAlert;

public class CreatePlayerController {
    public JFXTextField txtName;
    public JFXButton btnPlay;
    public CubicCurve curve;

    public CreatePlayerController() {
    }

    public void btnPlayOnAction(ActionEvent actionEvent) throws IOException {
        String name = this.txtName.getText();
        if (name.isBlank()) {
            (new DEPAlert(AlertType.ERROR, "Error", "Empty Name", "Name can't be empty", new ButtonType[0])).show();
            this.txtName.requestFocus();
            this.txtName.selectAll();
        } else if (!name.matches("[A-Za-z ]+")) {
            (new DEPAlert(AlertType.WARNING, "Error", "Invalid Name", "Please enter a valid name", new ButtonType[0])).show();
            this.txtName.requestFocus();
            this.txtName.selectAll();
        } else {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/Board.fxml"));
            stage.setScene(new Scene((Parent)fxmlLoader.load()));
            ((BoardController)fxmlLoader.getController()).initData(name);
            stage.setResizable(false);
            stage.setTitle("Connect 4 Game - Player: " + name);
            stage.show();
            stage.centerOnScreen();
            this.btnPlay.getScene().getWindow().hide();
            Objects.requireNonNull(stage);
            Platform.runLater(stage::sizeToScene);
        }
    }

    public void rootOnMouseExited(MouseEvent mouseEvent) {
        this.curve.setControlX2(451.8468017578125D);
        this.curve.setControlY2(-36.0D);
    }

    public void rootOnMouseMove(MouseEvent mouseEvent) {
        this.curve.setControlX2(mouseEvent.getX());
    }
}
