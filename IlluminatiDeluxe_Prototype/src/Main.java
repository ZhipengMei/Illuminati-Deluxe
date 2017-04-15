
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








/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//import java.util.ArrayList;
//import java.util.List;
//import javafx.animation.Interpolator;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.animation.TranslateTransition;
//import javafx.application.Application;
//import static javafx.application.Application.launch;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//import javafx.util.Duration;
////
//////public class ImageSlide extends Application {
////public class Main extends Application {
////
////    // Width and height of image in pixels
////    private final double IMG_WIDTH = 600;
////    private final double IMG_HEIGHT = 600;
////
////    private final int NUM_OF_IMGS = 3;
////    private final int SLIDE_FREQ = 4; // in secs
////
////    @Override
////    public void start(Stage stage) throws Exception {
////        //root code
////        StackPane freeStackRoot = new StackPane();
////
////        Pane clipPane = new Pane();
////        // To center the slide show incase maximized
////        clipPane.setMaxSize(IMG_WIDTH, IMG_HEIGHT);
////        clipPane.setClip(new Rectangle(IMG_WIDTH, IMG_HEIGHT));
////
////        HBox imgContainer = new HBox();
////        //image view
////        ImageView imgGreen = new ImageView("https://www.w3schools.com/css/trolltunga.jpg");
////        ImageView imgBlue = new ImageView("https://s3-us-west-1.amazonaws.com/powr/defaults/image-slider2.jpg");
////        ImageView imgRose = new ImageView("https://3.bp.blogspot.com/-W__wiaHUjwI/Vt3Grd8df0I/AAAAAAAAA78/7xqUNj8ujtY/s1600/image02.png");
////        //resize images
////        imgGreen.setFitWidth(600);
////        imgBlue.setFitWidth(600);
////        imgRose.setFitWidth(600);
////        //preserve the width:height ratio
////        imgGreen.setPreserveRatio(true);
////        imgBlue.setPreserveRatio(true);
////        imgRose.setPreserveRatio(true);
////
////        
////        imgContainer.getChildren().addAll(imgBlue, imgRose, imgGreen);
////        clipPane.getChildren().add(imgContainer);
////        root.getChildren().add(clipPane);
////
////        Scene scene = new Scene(root, IMG_WIDTH, IMG_HEIGHT);
////        stage.setTitle("Image Slider");
////        stage.setScene(scene);
////        startAnimation(imgContainer);
////        stage.show();
////    }
////
////    //start animation
////    private void startAnimation(final HBox hbox) {
////        //error occured on (ActionEvent t) line
////        //slide action
////        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
////            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
////            trans.setByX(-IMG_WIDTH);
////            trans.setInterpolator(Interpolator.EASE_BOTH);
////            trans.play();
////        };
////        //eventHandler
////        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
////            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
////            trans.setByX((NUM_OF_IMGS - 1) * IMG_WIDTH);
////            trans.setInterpolator(Interpolator.EASE_BOTH);
////            trans.play();
////        };
////
////        List<KeyFrame> keyFrames = new ArrayList<>();
////        for (int i = 1; i <= NUM_OF_IMGS; i++) {
////            if (i == NUM_OF_IMGS) {
////                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
////            } else {
////                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
////            }
////        }
//////add timeLine
////        Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[NUM_OF_IMGS]));
////
////        anim.setCycleCount(Timeline.INDEFINITE);
////        anim.playFromStart();
////    }
////
////    //call main function
////    public static void main(String[] args) {
////        launch(args);
////    }
////}
