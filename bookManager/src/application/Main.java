package application;
	
import controllers.MouseDrag;
import controllers.SelectForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        SelectForm form = new SelectForm();
        Image icon = null;
        FXMLLoader loader = null;
        try {
            MouseDrag mousedrag = new MouseDrag();
            form.selectz("/design/Bookcase.png","/design/base.fxml");
            icon = form.getIcon();
            loader = form.getLoader();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mousedrag.enableDrag(root, stage);

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setResizable(false);
            stage.getIcons().add(icon);
            stage.setTitle("Book Manager");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {e.printStackTrace();}
    }
}
