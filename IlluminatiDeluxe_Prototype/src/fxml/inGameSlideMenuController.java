package fxml;
	
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
	import javafx.scene.control.TextField;
	import javafx.scene.input.MouseEvent;
	import javafx.scene.layout.AnchorPane;
	import javafx.scene.layout.FlowPane;
	import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.ResourceBundle;
	
	import fxml.splashController.SplashScreen;
	import javafx.animation.Interpolator;
	import javafx.animation.KeyFrame;
	import javafx.animation.Timeline;
	import javafx.animation.TranslateTransition;
	import javafx.application.Application;
import javafx.application.Platform;

import static javafx.application.Application.launch;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
	import javafx.scene.layout.HBox;
	import javafx.scene.layout.Pane;
	import javafx.scene.layout.StackPane;
	import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
	import javafx.util.Duration;
	
	public class inGameSlideMenuController implements Initializable {
	
	    @FXML
	    private StackPane freeStackRoot;
	    
	    @FXML
	    private StackPane specialCardStack;
	
	    @FXML
	    private AnchorPane uncontrolledCardsPane;
	    
	    @FXML
	    private AnchorPane specialCardPane;
		
	    @FXML
	    private ScrollPane chatScrollPane;
	
	    @FXML
	    private FlowPane flowPaneInScroll;
	
	    @FXML
	    private TextField inGameChatTextField;
	
	    @FXML
	    private Button inGameChatSendBtn;
	    
	    @FXML
	    private Label uncontrolledGroupLabel;
	    
	    @FXML
	    private Label specialCardLabel;

	
	    @FXML
	    void inGameChatSend_ACTION(MouseEvent event) {
	
	    }
	
	    @FXML
	    void onEnter(ActionEvent event) {
	
	    }
	    
	    
	    
	    // --- Carosel Begins --- 
	    // Width and height of image in pixels
	    private final double IMG_WIDTH = 470;
	    private final double IMG_HEIGHT = 150;

	    private final int NUM_OF_IMGS = 3;
	    private final int SLIDE_FREQ = 4; // in secs
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			uncontrolledGroupLabel.setFont(Font.font(null, FontWeight.BOLD, 15));
			specialCardLabel.setFont(Font.font(null, FontWeight.BOLD, 15));

			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					loadUncontrolledGroup();					
				}
			});
			
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					loadSpecialCard();					
				}
			});
			
			
		}
		
		
        HBox imgContainer = new HBox();
		public void loadUncontrolledGroup() {
			Pane clipPane = new Pane();
	        // To center the slide show incase maximized
	        clipPane.setMaxSize(IMG_WIDTH, IMG_HEIGHT);
	        clipPane.setClip(new Rectangle(IMG_WIDTH, IMG_HEIGHT));

//	        HBox imgContainer = new HBox();
	        //image view	        
	    	String path = new File("support/images/cards/"+"airlines.jpg").getAbsolutePath();
	    	String path1 = new File("support/images/cards/"+"apathy.jpg").getAbsolutePath();
	    	String path2 = new File("support/images/cards/"+"assassination.jpg").getAbsolutePath();

			ImageView imgGreen = new ImageView();
			ImageView imgBlue = new ImageView();
			ImageView imgRose = new ImageView();

			
  			Image image = new Image(new File(path).toURI().toString());
  			imgGreen.setImage(image);	//reassign image view with new image
  			imgGreen.setFitHeight(130);
  			imgGreen.setPreserveRatio(true);
  			
  			Image image1 = new Image(new File(path1).toURI().toString());
  			imgBlue.setImage(image1);	//reassign image view with new image
  			imgBlue.setFitHeight(130);
  			imgBlue.setPreserveRatio(true);
  			
  			Image image2 = new Image(new File(path2).toURI().toString());
  			imgRose.setImage(image2);	//reassign image view with new image
  			imgRose.setFitHeight(130);
  			imgRose.setPreserveRatio(true);

	        
	        imgContainer.getChildren().addAll(imgGreen, imgBlue, imgRose);
	        clipPane.getChildren().add(imgContainer);
	        freeStackRoot.getChildren().add(clipPane);

//	        startAnimation(imgContainer);
		}
		
		public void loadSpecialCard() {
			Pane clipPane = new Pane();
	        // To center the slide show incase maximized
	        clipPane.setMaxSize(IMG_WIDTH, IMG_HEIGHT);
	        clipPane.setClip(new Rectangle(IMG_WIDTH, IMG_HEIGHT));

	        HBox imgContainer2 = new HBox();
	        //image view	        
	    	String path = new File("support/images/cards/"+"barcodes.jpg").getAbsolutePath();
	    	String path1 = new File("support/images/cards/"+"bigmedia.jpg").getAbsolutePath();
	    	String path2 = new File("support/images/cards/"+"bigmedicine.jpg").getAbsolutePath();

			ImageView imgGreen = new ImageView();
			ImageView imgBlue = new ImageView();
			ImageView imgRose = new ImageView();

			
  			Image image = new Image(new File(path).toURI().toString());
  			imgGreen.setImage(image);	//reassign image view with new image
  			imgGreen.setFitHeight(130);
  			imgGreen.setPreserveRatio(true);
  			
  			Image image1 = new Image(new File(path1).toURI().toString());
  			imgBlue.setImage(image1);	//reassign image view with new image
  			imgBlue.setFitHeight(130);
  			imgBlue.setPreserveRatio(true);
  			
  			Image image2 = new Image(new File(path2).toURI().toString());
  			imgRose.setImage(image2);	//reassign image view with new image
  			imgRose.setFitHeight(130);
  			imgRose.setPreserveRatio(true);

	        
	        imgContainer2.getChildren().addAll(imgGreen, imgBlue, imgRose);
	        clipPane.getChildren().add(imgContainer2);
	        specialCardStack.getChildren().add(clipPane);

	        startAnimation(imgContainer2);
		}
		
		//start animation
	    private void startAnimation(final HBox hbox) {
	        //error occured on (ActionEvent t) line
	        //slide action
	        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
//	            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
	            TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), hbox);
//	            trans.setByX(-IMG_WIDTH);
	            trans.setByX(-140);
	            trans.setInterpolator(Interpolator.EASE_BOTH);
	            trans.play();
	        };
	        //eventHandler
	        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
	            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
//	            trans.setByX((NUM_OF_IMGS - 1) * IMG_WIDTH);
	            trans.setByX((NUM_OF_IMGS - 1) * 140);
	            trans.setInterpolator(Interpolator.EASE_BOTH);
	            trans.play();
	        };

	        List<KeyFrame> keyFrames = new ArrayList<>();
	        for (int i = 1; i <= NUM_OF_IMGS; i++) {
	            if (i == NUM_OF_IMGS) {
	                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
	            } else {
	                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
	            }
	        }
	//add timeLine
	        Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[NUM_OF_IMGS]));

	        anim.setCycleCount(Timeline.INDEFINITE);
	        anim.playFromStart();
		}
	    
	    
	    // moving the card in untrolled group
	    @FXML
	    void uncontrolledGroupRightArrow(MouseEvent event) {	    	
	    	uncontrolledGroupRightArrow(imgContainer);
	    }
	    
	    @FXML
	    void uncontrolledGroupLeftArrow(MouseEvent event) {
	    	uncontrolledGroupLeftArrow(imgContainer);
	    }

	    public void uncontrolledGroupRightArrow(final HBox hbox){
	    	hbox.setTranslateX(-140);
	    }
	    public void uncontrolledGroupLeftArrow(final HBox hbox){
	    	hbox.setTranslateX(+140);
	    }
	        
	
	}
	
	
	

