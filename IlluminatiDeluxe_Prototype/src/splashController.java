import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.istack.internal.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class splashController implements Initializable {

	@FXML
	private StackPane rootPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new SplashScreen().start();
	}
	
	class SplashScreen extends Thread {
		
		@Override
		public void run() {
			try {
				Thread.sleep(1500);
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("loginmenu.fxml"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						Stage stage = new Stage();

						Scene scene = new Scene(root);
						scene.getStylesheets().add("DesignFX.css");
						stage.setScene(scene);
						
//						stage.setMaximized(true);
//						stage.setFullScreen(true);
//						stage.setResizable(false);
						
//						Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
//						stage.setX(bounds.getWidth() - bounds.getWidth()/2);
//						stage.setY(bounds.getHeight() - bounds.getHeight()/2);
						
						stage.show();
						
						rootPane.getScene().getWindow().hide();
					}
					
				});


			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}

}
