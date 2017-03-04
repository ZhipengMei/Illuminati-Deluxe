import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.istack.internal.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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
				Thread.sleep(3000);
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("messageFX.fxml"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						Stage stage = new Stage();
						stage.setScene(new Scene(root));
						stage.show();
						
						rootPane.getScene().getWindow().hide();

//			            Stage primaryStage = (Stage)rootPane.getScene().getWindow();
////
////			            primaryStage.hide();
//			            primaryStage.close();
					}
					
				});


			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}

}
