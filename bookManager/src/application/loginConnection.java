package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import controllers.AlertMessage;
import controllers.Database;
import controllers.MouseDrag;
import controllers.SelectForm;
import controllers.dbOp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class loginConnection implements Initializable {

	@FXML
	private Hyperlink A;

	@FXML
	private Hyperlink B;

	@FXML
	private Button close;

	@FXML
	private Button login;

	@FXML
	private AnchorPane loginPage;

	@FXML
	private AnchorPane mainPage;

	@FXML
	private Button minimize;

	@FXML
	private CheckBox passtrough;

	@FXML
	private PasswordField password;

	@FXML
	private AnchorPane registerPage;

	@FXML
	private PasswordField signPassword;

	@FXML
	private PasswordField signConfirm;

	@FXML
	private TextField signUsername;

	@FXML
	private Button signup;

	@FXML
	private TextField username;

	private AlertMessage alert = new AlertMessage();
	private MouseDrag mousedrag = new MouseDrag();
    private SelectForm form = new SelectForm();
	private Stage stage = new Stage();
	private boolean overrideValue;
    Image icon = null;
    FXMLLoader loader = null;

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private Statement statement;

	public void loginAction() {
		if (username.getText().isEmpty() || password.getText().isEmpty()) {
			errorMoment(() -> {
				Platform.runLater(() -> alert.errorm("Please fill all blank fields"));
			});
			return;
		}
		connect = Database.connectDB();
		try {
			prepare = connect.prepareStatement(dbOp.login);
			prepare.setString(1, username.getText());
			prepare.setString(2, password.getText());
			result = prepare.executeQuery();
			if (result.next()) {
				loadForm();
			} else {
				errorMoment(() -> {
					Platform.runLater(() -> alert.errorm("Incorrect Username/Password"));
				});
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	public void registerAction() {
	    String selectData = "SELECT * FROM users WHERE username = ?";

	    if (signUsername.getText().isEmpty() || signPassword.getText().isEmpty() || signConfirm.getText().isEmpty()) {
	        errorMoment(() -> {
	            Platform.runLater(() -> alert.errorm("Please fill all blank fields"));
	        });
	        return;
	    }

	    connect = Database.connectDB();

	    try {
	        prepare = connect.prepareStatement(selectData);
	        prepare.setString(1, signUsername.getText());
	        result = prepare.executeQuery();

	        if (result.next()) {
	            errorMoment(() -> {
	                Platform.runLater(() -> alert.errorm(signUsername.getText() + " user already exists"));
	            });
	        } else if (!signPassword.getText().equals(signConfirm.getText())) {
	            errorMoment(() -> {
	                Platform.runLater(() -> alert.errorm("Password does not match."));
	            });
	        } else {
	            prepare = connect.prepareStatement(dbOp.register);
	            prepare.setString(1, signUsername.getText());
	            prepare.setString(2, signPassword.getText());
	            prepare.executeUpdate();

	            alert.confirm("Registered successfully!");

	            loginPage.setVisible(true);
	            registerPage.setVisible(false);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	}


	public void passthrough() {
		try {
			statement = connect.createStatement();
			result = statement.executeQuery(dbOp.override);
			overrideValue = result.getBoolean("override");
			if (overrideValue) {
				loadForm();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadForm() {
		try {
            form.selectz("/design/Bookcase.png","/design/BookController.fxml", "/design/base.css");
            icon = form.getIcon();
            loader = form.getLoader();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mousedrag.enableDrag(root, stage);

            stage.initStyle(StageStyle.TRANSPARENT);
			stage.setTitle("Book Controller");
			stage.setResizable(false);
			stage.getIcons().add(icon);
			stage.setScene(scene);

			mainPage.getScene().getWindow().hide();
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchForm(ActionEvent event) {
		if (event.getSource() == A) {
			loginPage.setVisible(false);
			registerPage.setVisible(true);
		} else if (event.getSource() == B) {
			loginPage.setVisible(true);
			registerPage.setVisible(false);
		}
	}

	public void errorMoment(Runnable callback) {
	    String borderColor = "-fx-border-color: #FF0000";
	    String loginBackgroundColor = "-fx-background-color: #FF0000";
	    String gradientBorderColor = "-fx-border-color: linear-gradient(to left, #F63BFF, #3E56EB)";
	    String gradientBackgroundColor = "linear-gradient(to bottom right, #0abfa4, #00d079)";
	    
	    username.setStyle(borderColor);
	    password.setStyle(borderColor);
	    signUsername.setStyle(borderColor);
	    signPassword.setStyle(borderColor);
	    signConfirm.setStyle(borderColor);
	    login.setStyle(loginBackgroundColor);
	    signup.setStyle(loginBackgroundColor);
	    
	    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
	        username.setStyle(gradientBorderColor);
	        password.setStyle(gradientBorderColor);
	        signUsername.setStyle(gradientBorderColor);
	        signPassword.setStyle(gradientBorderColor);
	        signConfirm.setStyle(gradientBorderColor);
	        login.setStyle(gradientBackgroundColor);
	        signup.setStyle(gradientBackgroundColor);
	        if (callback != null) {
	            callback.run();
	        }
	    }));
	    timeline.play();
	}

	public void minimize() {
		Stage stage = (Stage) mainPage.getScene().getWindow();
		stage.setIconified(true);
	}

	public void close() {
		Platform.exit();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

}
