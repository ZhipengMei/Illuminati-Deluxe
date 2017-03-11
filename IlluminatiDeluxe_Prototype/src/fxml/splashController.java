package fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.nio.file.Paths;

import com.sun.istack.internal.logging.Logger;

import Animation.Animattion;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class splashController implements Initializable {

	@FXML
	private StackPane rootPane;
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Splash screen");
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
						stage.getIcons().add(new Image("file:appicon.png"));
						stage.setTitle("Illuminati Deluxe");
						stage.show();

						rootPane.getScene().getWindow().hide(); //dismiss the previous pane
					}
					
				});


			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}

}
