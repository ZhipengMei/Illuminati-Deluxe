package fxml;
	
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
	import javafx.scene.input.MouseEvent;
	import javafx.scene.layout.AnchorPane;
	import javafx.scene.layout.FlowPane;
	import java.io.File;
import java.net.URL;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
	import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
	import javafx.animation.TranslateTransition;
	import javafx.application.Platform;

import javafx.event.EventHandler;
	import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
	import javafx.scene.layout.HBox;
	import javafx.scene.layout.Pane;
	import javafx.scene.layout.StackPane;
	import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
	
	import Extension.Chat;

	public class inGameSlideMenuController implements Initializable {
		

	    @FXML
	    private Button uncontrolledLeftBtn;
	    @FXML
	    private StackPane freeStackRoot;
	    
	    @FXML
	    private StackPane specialCardStack;
	
//	    @FXML
//	    private AnchorPane uncontrolledCardsPane;
//	    
//	    @FXML
//	    private AnchorPane specialCardPane;
	    
	    @FXML
	    private AnchorPane chatPane;
		
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
		    Chat chat = new Chat(chatScrollPane, flowPaneInScroll, inGameChatTextField, inGameChatSendBtn);
		    chat.sendMessage();
	    }
	
	    @FXML
	    void onEnter(ActionEvent event) {
		    Chat chat = new Chat(chatScrollPane, flowPaneInScroll, inGameChatTextField, inGameChatSendBtn);
		    chat.sendMessage();
	    }
	    
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

	    // moving the card in untrolled group
	    @FXML
	    void uncontrolledGroupRightArrow(MouseEvent event) {
	    	System.out.println("left arrow clicked");

//	    	uncontrolledGroupRightArrow(groupImgContainer);
	    }
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

	    @FXML
	    void uncontrolledGroupLeftArrow(MouseEvent event) {
//	    	uncontrolledGroupLeftArrow(specialImgContainer);
	   
	    }
	    
	    
	    
	    // --- Carosel Begins --- 
	    // Width and height of image in pixels
	    private final double IMG_WIDTH = 200;
	    private final double IMG_HEIGHT = 150;

	    private final int NUM_OF_IMGS = 5;
	    private final int SLIDE_FREQ = 3; // in secs
	    
	    
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// disable chatbox horizontal scroll bar 
			chatScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);



	        //chat box
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
				    Chat chat = new Chat(chatScrollPane, flowPaneInScroll, inGameChatTextField, inGameChatSendBtn);
					chat.receiveMessage();	
				}	
			});
			
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
			
			
		}// end intialize
		
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
		
		HBox groupImgContainer = new HBox(); //for uncontrolled group
		HBox specialImgContainer = new HBox(); //for special cards

		//show new draw card free onto screen
		public void loadUncontrolledGroup(){
			Pane clipPane = new Pane();
			clipPane.setMaxSize(IMG_WIDTH, IMG_HEIGHT);
	        clipPane.setClip(new Rectangle(IMG_WIDTH, IMG_HEIGHT));

//    		System.out.println("inside here run cards draw");
//    		ImageView freecardImage = new ImageView();				
//			//set free card image here
//	    	String path = new File("support/images/cards/Illuminati/"+cardName).getAbsolutePath();
//	    	System.out.println(path);
//			Image image = new Image(new File(path).toURI().toString());
//			freecardImage.setImage(image);	//reassign image view with new image
//			freecardImage.setFitHeight(130);
//			freecardImage.setPreserveRatio(true);
//
//			groupImgContainer.getChildren().add(freecardImage);
//			System.out.println("groupImg size: - > " + groupImgContainer.getChildren().size());
//    			
//	        clipPane.getChildren().add(groupImgContainer);
//	    	        
//	        Platform.runLater(new Runnable() {
//	        	@Override
//        		public void run() {
//	        		if(freeStackRoot == null){
//	        			System.out.println("freeStackRoot -> "+ freeStackRoot.getChildren());
//	        			freeStackRoot = new StackPane();
//	        		}else{
//	        			freeStackRoot.getChildren().add(clipPane);
//	        		}
//	        	}	
//			});
				
		        //image view	        
		    	String path = new File("support/images/cards/group/"+"airlines.jpg").getAbsolutePath();
		    	String path3 = new File("support/images/cards/group/"+"pentagon.jpg").getAbsolutePath();
		    	String path1 = new File("support/images/cards/group/"+"apathy.jpg").getAbsolutePath();
		    	String path2 = new File("support/images/cards/group/"+"junkmail.jpg").getAbsolutePath();

				ImageView imgGreen = new ImageView();
				ImageView imgBlue = new ImageView();
				ImageView imgRose = new ImageView();
				ImageView imgGold = new ImageView();

				
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
	  			
	  			Image image3 = new Image(new File(path3).toURI().toString());
	  			imgGold.setImage(image3);	//reassign image view with new image
	  			imgGold.setFitHeight(130);
	  			imgGold.setPreserveRatio(true);


		        
	  			groupImgContainer.getChildren().addAll(imgGreen, imgGold, imgBlue, imgRose);
		        clipPane.getChildren().add(groupImgContainer);
		        freeStackRoot.getChildren().add(clipPane);
		        startAnimation(groupImgContainer);

	  }
	  
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
 
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

		public void loadSpecialCard() {
			Pane clipPane = new Pane();
	        // To center the slide show incase maximized
	        clipPane.setMaxSize(IMG_WIDTH, IMG_HEIGHT);
	        clipPane.setClip(new Rectangle(IMG_WIDTH, IMG_HEIGHT));

//	        HBox imgContainer2 = new HBox();
	        //image view	        
	    	String path = new File("support/images/cards/group/"+"whitecollarcrime.jpg").getAbsolutePath();
	    	String path1 = new File("support/images/cards/group/"+"secretsmanwasnotmeantoknow1.jpg").getAbsolutePath();
	    	String path2 = new File("support/images/cards/group/"+"whisperingcampaign.jpg").getAbsolutePath();
	    	String path3 = new File("support/images/cards/group/"+"mediacampaign.jpg").getAbsolutePath();

			ImageView imgGreen = new ImageView();
			ImageView imgBlue = new ImageView();
			ImageView imgRose = new ImageView();
			ImageView imgRose2 = new ImageView();


			
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
  			
  			Image image3 = new Image(new File(path3).toURI().toString());
  			imgRose2.setImage(image3);	//reassign image view with new image
  			imgRose2.setFitHeight(130);
  			imgRose2.setPreserveRatio(true);

	        
  			specialImgContainer.getChildren().addAll(imgGreen, imgBlue, imgRose, imgRose2);
	        clipPane.getChildren().add(specialImgContainer);
	        specialCardStack.getChildren().add(clipPane);

	        startAnimation(specialImgContainer);
		}
		
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

		//start animation
	    private void startAnimation(final HBox hbox) {
	        //error occured on (ActionEvent t) line
	        //slide action
	        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
//	            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
	            TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), hbox);
	            trans.setByX(-IMG_WIDTH);
//	            trans.setByX(-140);
	            trans.setInterpolator(Interpolator.EASE_BOTH);
	            trans.play();
	        };
	        //eventHandler
	        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
	            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
	            trans.setByX((NUM_OF_IMGS - 1) * IMG_WIDTH);
//	            trans.setByX((NUM_OF_IMGS - 1) * 140);
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

	        anim.setCycleCount(Animation.INDEFINITE);
	        anim.playFromStart();
		}
	    
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

	   
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

	    double uncontrolledGroupSlideCount = 0;
	    public void uncontrolledGroupRightArrow(final HBox hbox){
	    	if ((hbox.getChildren().size()-1) * -140 <=  uncontrolledGroupSlideCount){
	    		uncontrolledGroupSlideCount -= 190;
		    	hbox.setTranslateX(uncontrolledGroupSlideCount);
	    	}
	    	
	    }
	    
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

	    public void uncontrolledGroupLeftArrow(final HBox hbox){
	    	if (uncontrolledGroupSlideCount != 0) {
	    		uncontrolledGroupSlideCount += 190;
		    	hbox.setTranslateX(uncontrolledGroupSlideCount);
	    	}	    	
	    }
	    
	    
	    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

	    
	    
	    
	}
	
	
	

