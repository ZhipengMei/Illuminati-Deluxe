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
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		
				
		Parent root = FXMLLoader.load(getClass().getResource("fxml/splashFX.fxml"));
		primaryStage.getIcons().add(new Image("file:appicon.png"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Illuminati Deluxe");
//		stage.setMaximized(true);
//		stage.setFullScreen(true);
//		stage.setResizable(false);
		primaryStage.show();
		primaryStage.toFront();

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


//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.TreeMap;
//public class Main {
//	public static void main(String[] args) {
//		
////	    ArrayList<Integer> diceVal = new ArrayList<Integer>();
////		TreeMap<Integer, String> diceValMap = new TreeMap<Integer, String>();  
////
////	    diceVal.add(11);
////	    diceVal.add(14);
////	    diceVal.add(5);
////	    
////	    diceValMap.put( 11, "AA");
////	    diceValMap.put(5,"BB");
////	    diceValMap.put(14,"CC");
////	    
////		int largest = Collections.max(diceVal);
////		System.out.println(largest);
////		
////		String name = diceValMap.get(largest);
////		System.out.println(name);
//		
//		
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		list.add(3);
//		list.add(7);
//		list.add(3);
//		list.add(9);
//		list.add(3);
//		
//		
//		for(int i:list){
////			System.out.println(i + " is repeated: " + moreThanOnce(list, i));
//			if(moreThanOnce(list, i) == true){
//				System.out.println("my love wil not go in bro");
//				break;
//			}
//		}
//	}
//	
//	public static boolean moreThanOnce(ArrayList<Integer> list, int searched) 
//	{
//	    int numCount = 0;
//
//	    for (int thisNum : list) {
//	        if (thisNum == searched) numCount++;
//	    }
//
//	    return numCount > 1;
//	}
//}
