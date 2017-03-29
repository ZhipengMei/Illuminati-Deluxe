package fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
				Thread.sleep(1500); //1.5 second
//				Thread.sleep(100); //skip login 
//				//DO NOT DELETE, ORIGINAL CODE
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
						
					    // register listener for window close event
					    stage.setOnCloseRequest(event -> {
					        // consume event
					        event.consume();
					        
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
							        // show close dialog
							        Alert alert = new Alert(AlertType.CONFIRMATION);
							        alert.setTitle("Close Confirmation");
							        alert.setHeaderText("Do you really want to quit?");
							        alert.initOwner( stage);

							        Optional<ButtonType> result = alert.showAndWait();
							        if (result.get() == ButtonType.OK){
//							            Platform.exit();
							            System.exit(0); //terminate process
							        }
								}	
							});
					    });
						rootPane.getScene().getWindow().hide(); //dismiss the previous pane
					}
					
				});
//----------------------------------------------------------------------------
//			// skip login, faster to game table screen	
//				Platform.runLater(new Runnable() {
//
//					@Override
//					public void run() {
//						Parent root = null;
//						try {
//							root = FXMLLoader.load(getClass().getResource("gameTableFX.fxml"));
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//						
//						Stage stage = new Stage();
//						Scene scene = new Scene(root);
//						stage.setMaximized(true);
//						stage.setResizable(false);
//						
//				        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//
//				        //set Stage boundaries to visible bounds of the main screen
//				        stage.setX(primaryScreenBounds.getMinX());
//				        stage.setY(primaryScreenBounds.getMinY());
//				        stage.setWidth(primaryScreenBounds.getWidth());
//				        stage.setHeight(primaryScreenBounds.getHeight());
//						
//						scene.getStylesheets().add("DesignFX.css");
//						stage.setScene(scene);
//						stage.getIcons().add(new Image("file:appicon.png"));
//						stage.setTitle("Illuminati Deluxe");
//						stage.show();		
//						
//						rootPane.getScene().getWindow().hide(); //dismiss the previous pane
//					}
//					
//				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}

}
