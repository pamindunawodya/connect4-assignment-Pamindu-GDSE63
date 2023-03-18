//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXButton;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lk.ijse.dep.service.AiPlayer;
import lk.ijse.dep.service.Board;
import lk.ijse.dep.service.BoardImpl;
import lk.ijse.dep.service.BoardUI;
import lk.ijse.dep.service.HumanPlayer;
import lk.ijse.dep.service.Piece;
import lk.ijse.dep.service.Player;
import lk.ijse.dep.service.Winner;

public class BoardController implements BoardUI {
    private static final int RADIUS = 42;
    public Label lblStatus;
    public Group grpCols;
    public AnchorPane root;
    public Pane pneOver;
    public JFXButton btnPlayAgain;
    private String playerName;
    private boolean isAiPlaying;
    private boolean isGameOver;
    private Player humanPlayer;
    private Player aiPlayer;

    public BoardController() {
    }

    private void initializeGame() {
        Board newBoard = new BoardImpl(this);
        this.humanPlayer = new HumanPlayer(newBoard);
        this.aiPlayer = new AiPlayer(newBoard);
    }

    public void initialize() {
        this.initializeGame();
        this.grpCols.getChildren().stream().map((n) -> {
            return (VBox)n;
        }).forEach((vbox) -> {
            vbox.setOnMouseClicked((mouseEvent) -> {
                this.colOnClick(vbox);
            });
        });
    }

    private void colOnClick(VBox col) {
        if (!this.isAiPlaying && !this.isGameOver) {
            this.humanPlayer.movePiece(this.grpCols.getChildren().indexOf(col));
        }

    }

    public void initData(String playerName) {
        this.playerName = playerName;
    }

    public void update(int col, boolean isHuman) {
        if (!this.isGameOver) {
            VBox vCol = (VBox)this.grpCols.lookup("#col" + col);
            if (vCol.getChildren().size() == 5) {
                throw new RuntimeException("Double check your logic, no space available within the column: " + col);
            } else {
                if (!isHuman) {
                    vCol.getStyleClass().add("col-ai");
                }

                Circle circle = new Circle(42.0D);
                circle.getStyleClass().add(isHuman ? "circle-human" : "circle-ai");
                vCol.getChildren().add(0, circle);
                if (vCol.getChildren().size() == 5) {
                    vCol.getStyleClass().add("col-filled");
                }

                TranslateTransition tt = new TranslateTransition(Duration.millis(250.0D), circle);
                tt.setFromY(-50.0D);
                tt.setToY(circle.getLayoutY());
                tt.playFromStart();
                this.lblStatus.getStyleClass().clear();
                this.lblStatus.getStyleClass().add(isHuman ? "ai" : "human");
                KeyFrame delayFrame;
                if (isHuman) {
                    this.isAiPlaying = true;
                    this.grpCols.getChildren().stream().map((n) -> {
                        return (VBox)n;
                    }).forEach((vbox) -> {
                        vbox.getStyleClass().remove("col-human");
                    });
                    delayFrame = new KeyFrame(Duration.millis(300.0D), (actionEvent) -> {
                        if (!this.isGameOver) {
                            this.lblStatus.setText("Wait, AI is playing");
                        }

                    }, new KeyValue[0]);
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5D), (actionEvent) -> {
                        if (!this.isGameOver) {
                            this.aiPlayer.movePiece(-1);
                        }

                    }, new KeyValue[0]);
                    (new Timeline(new KeyFrame[]{delayFrame, keyFrame})).playFromStart();
                } else {
                    delayFrame = new KeyFrame(Duration.millis(300.0D), (actionEvent) -> {
                        this.grpCols.getChildren().stream().map((n) -> {
                            return (VBox)n;
                        }).forEach((vbox) -> {
                            vbox.getStyleClass().remove("col-ai");
                            vbox.getStyleClass().add("col-human");
                        });
                    }, new KeyValue[0]);
                    (new Timeline(new KeyFrame[]{delayFrame})).playFromStart();
                    this.isAiPlaying = false;
                    this.lblStatus.setText(this.playerName + ", it is your turn now!");
                }

            }
        }
    }

    public void notifyWinner(Winner winner) {
        this.isGameOver = true;
        this.lblStatus.getStyleClass().clear();
        this.lblStatus.getStyleClass().add("final");
        switch(winner.getWinningPiece()) {
            case BLUE:
                this.lblStatus.setText(String.format("%s, you have won the game !", this.playerName));
                break;
            case GREEN:
                this.lblStatus.setText("Game is over, AI has won the game !");
                break;
            case EMPTY:
                this.lblStatus.setText("Game is tied !");
        }

        if (winner.getWinningPiece() != Piece.EMPTY) {
            VBox vCol = (VBox)this.grpCols.lookup("#col" + winner.getCol1());
            Rectangle rect = new Rectangle((double)(winner.getCol2() - winner.getCol1() + 1) * vCol.getWidth(), (double)((winner.getRow2() - winner.getRow1() + 1) * 88));
            rect.setId("rectOverlay");
            this.root.getChildren().add(rect);
            rect.setLayoutX(vCol.localToScene(0.0D, 0.0D).getX());
            rect.setLayoutY(vCol.localToScene(0.0D, 0.0D).getY() + (double)((4 - winner.getRow2()) * 88));
            rect.getStyleClass().add("winning-rect");
        }

        this.pneOver.setVisible(true);
        this.pneOver.toFront();
        JFXButton var10000 = this.btnPlayAgain;
        Objects.requireNonNull(var10000);
        Platform.runLater(var10000::requestFocus);
    }

    public void btnPlayAgainOnAction(ActionEvent actionEvent) {
        this.initializeGame();
        this.isAiPlaying = false;
        this.isGameOver = false;
        this.pneOver.setVisible(false);
        this.lblStatus.getStyleClass().clear();
        this.lblStatus.setText("LET'S PLAY !");
        this.grpCols.getChildren().stream().map((n) -> {
            return (VBox)n;
        }).forEach((vbox) -> {
            vbox.getChildren().clear();
            vbox.getStyleClass().remove("col-ai");
            vbox.getStyleClass().remove("col-filled");
            vbox.getStyleClass().add("col-human");
        });
        this.root.getChildren().remove(this.root.lookup("#rectOverlay"));
    }
}
