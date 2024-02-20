package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

public class SelectForm {
	
    private Image icon;
    private FXMLLoader loader;
	
	public void selectz(String icons, String base) {
		try {
            icon = new Image(getClass().getResource(icons).toExternalForm());
            loader = new FXMLLoader(getClass().getResource(base));
            String css = getClass().getResource("/design/base.css").toExternalForm();
            
		} catch (Exception e) {e.printStackTrace();} 
	}
	
    public Image getIcon() {
        return icon;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
