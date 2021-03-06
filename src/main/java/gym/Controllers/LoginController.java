package gym.Controllers;

/**
 * Sample Skeleton for 'Untitled' Controller Class
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gym.database.DAOAdmin;
import gym.database.DBSingleton;
import gym.model.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
   private Admin admin = null;

    @FXML
    private Label loginlabel;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;
    @FXML
    private Circle con_circle;

    @FXML
    private Label status;

    @FXML
    void onClick(ActionEvent event) {

        new Thread(() -> {
            admin = new DAOAdmin().findById(0);
        }).start();

        while (admin == null)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        if (username.getText().equals(admin.getUsername()) && password.getText().equals(admin.getPassword())) {
            System.out.println("logged in !!!");
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/main.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loginlabel.setStyle("-fx-background-color: red");
            loginlabel.setText("Invalid Credentials");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            if (DBSingleton.getInstance().getConnection().isClosed()) {
                con_circle.fillProperty().setValue(Paint.valueOf("red"));
                status.setText("Offline ");
            } else {
                con_circle.fillProperty().setValue(Paint.valueOf("green"));
                status.setText("Online ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

