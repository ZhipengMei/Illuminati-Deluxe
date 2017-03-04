
import Firebase.AccessFirebase; //using method from another package

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	public static Boolean isSplashLoaded = false;

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("splashFX.fxml"));
		
		stage.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	    stage.toFront();

	}
	
	public static void main(String[] args) {		
//		AccessFirebase.firebaseConfig();	//establish secure connection between this project and firebase
//	//    AccessFirebase.validateUsername("Darrian","12sddsf34567");		//register user with a username
//	    AccessFirebase.userAuth("Darrian","12sddsf34567");
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
