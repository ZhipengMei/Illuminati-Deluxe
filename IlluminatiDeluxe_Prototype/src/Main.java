
import java.io.File;

import Extension.ConfirmBox;

import Firebase.AccessFirebase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {
	
	public static Boolean isSplashLoaded = false;
	Stage window;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		
		
		
		
		Parent root = FXMLLoader.load(getClass().getResource("fxml/splashFX.fxml"));
		window.getIcons().add(new Image("file:appicon.png"));
		window.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.setTitle("Illuminati Deluxe");
//		stage.setMaximized(true);
//		stage.setFullScreen(true);
//		stage.setResizable(false);
		window.show();
		window.toFront();

	}
	
	private void closeProgram() {
		// TODO Auto-generated method stub
		Boolean answer = ConfirmBox.display("Title", "Sure you want to exist?");
		if(answer)
			window.close();
	}

	public static void main(String[] args) {		
		AccessFirebase.firebaseConfig();	//establish secure connection between this project and firebase
		
		MediaPlayer mediaplayer;
		String path = new File("support/sounds/Illuminati_Soundtrack.mp3").getAbsolutePath();
		Media sound = new Media(new File(path).toURI().toString());

		mediaplayer = new MediaPlayer(sound);
    	mediaplayer.setAutoPlay(true);
    	mediaplayer.setVolume(0.1);
    	mediaplayer.setOnEndOfMedia(new Runnable() {
    		public void run(){
    			mediaplayer.seek(Duration.ZERO);
    		}
    	});
    	
		launch(args);
	}

}





















//************* DO NOT DELETE THE FOLLOWING METHOD ********************************
//public class Main implements Runnable {
//	
//	@Override
//	public void run() {
////		AccessFirebase.insertFirebase();
//	}
//	
//	public static void main(String[] args) throws InterruptedException {
//		AccessFirebase.firebaseProjecAuth();
//		
//	    Thread t = new Thread(new Main(), "TestThread");
//	    t.start();
//	    t.join();
//	    Thread.sleep(10000);
//	    
//	    AccessFirebase.createUser();
//
//	}
//
//}
//************* DO NOT DELETE THE ABOVE METHOD ********************************


//public class Main  {
//	
//
//	public static void main(String[] args) {		
//		AccessFirebase.firebaseConfig();	//establish secure connection between this project and firebase
////	    AccessFirebase.validateUsername("Darrian","12sddsf34567");		//register user with a username
//	    AccessFirebase.userAuth("Darrian","12sddsf34567");
//	    
//	}
//
//}
