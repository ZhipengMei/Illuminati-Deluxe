
package fxml;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import Animation.Animattion;
import Firebase.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;


public class playerScreenController implements Initializable {

	// User singleton contains data from login menu
	User currentUser = User.getInstance(); //getting user singleton
	// Get a reference to the database
	final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
	final DatabaseReference ref = rootRef.child("profile").child(currentUser.getUID());
	final DatabaseReference imageRef = rootRef.child("profile").child(currentUser.getUID()).child("imageName");
	
	//pop up "game in progress" pane
    Stage popupStage = new Stage(StageStyle.TRANSPARENT);
    Boolean popupStageSeen = false;
	
    @FXML
    private AnchorPane root_anchorpane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private HBox hboxbar;
    @FXML
    private Pane channelPane;

    @FXML
    private Label ruletheworld;

    @FXML
    private ImageView profileimageview;

    @FXML
    private Label usernameLabel;

    @FXML
    private JFXHamburger hamburger;
    
    @FXML
    private GridPane channelGridpane;

    @FXML
    private Button channel1;

    @FXML
    private Button channel2;

    @FXML
    private Button channel4;

    @FXML
    private Button channel5;

    @FXML
    private Button channel7;

    @FXML
    private Button channel8;

    @FXML
    private Button channel3;

    @FXML
    private Button channel6;

    @FXML
    private Button channel9;


 
    
    
    
    
    //-------------------------------------------------------------------------
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getUserdata();
		try {
			AnchorPane apane = FXMLLoader.load(getClass().getResource("slideMenu.fxml"));
			drawer.setSidePane(apane);

			//hamburger menu animation
			HamburgerBasicCloseTransition burgerTask2 = new HamburgerBasicCloseTransition(hamburger);
			burgerTask2.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
				burgerTask2.setRate(burgerTask2.getRate() * -1);
				burgerTask2.play();
				
				if(drawer.isShown()) {
					drawer.close();
					System.out.println("inGamedrawer.close();");

				} else {
					drawer.open();
				}
			});			
		} catch (IOException e1) {
			e1.printStackTrace();
		}			
	}
	
	
	//obtain current user data from database
	public void getUserdata(){

		// Attach a listener to read the data at our posts reference
		//specifically query this user's name
		ref.addValueEventListener(new ValueEventListener() {

		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {	
		    	//deserialize data from JSON straight back into a Message object.
		    	//currentUser = dataSnapshot.getValue(User.class); // it works but messed up with setter later on
		    	System.out.println(dataSnapshot.getValue());
		    	currentUser.setName((String) dataSnapshot.child("name").getValue());
		    	currentUser.setWin((String) dataSnapshot.child("win").getValue());
		    	currentUser.setimageName((String) dataSnapshot.child("imageName").getValue());
		    	currentUser.setCreatedDate((String) dataSnapshot.child("createdDate").getValue());

		    	
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						//display username 
			    		usernameLabel.setText(currentUser.getName());
			    		//check image if available
			    		if(currentUser.getimageName().equals("null")){
//			    			System.out.println("image name is null");
			    		} else {
//			    			System.out.println("image name is not null");
			    			//set profile image here
			    	    	String path = new File("support/images/"+currentUser.getimageName()).getAbsolutePath();
			    			Image image = new Image(new File(path).toURI().toString());
			    	    	profileimageview.setImage(image);	//reassign image view with new image
			    		}
					}	
				});
//	    		ref.removeEventListener(this);
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		        System.out.println("The read failed: " + databaseError.getCode());
		    }
		});
	}// end getUserdata
	
	//-------------------------------------------------------------------------------------
	@FXML
    void channel1_Action(MouseEvent event) {
		contentForChannelAction("Channel1");
    }

	//794,563
	
	
	// all channel button action share same content
	private void contentForChannelAction(String channelName){
		currentUser.setCurrentChannel(channelName);
		System.out.println(currentUser.getCurrentChannel());
		System.out.println(currentUser.getimageName());
		
		inGame(); //loading animation
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
				Parent root = null;
				try {
					root = FXMLLoader.load(getClass().getResource("gameTableFX.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Stage gameTableStage = new Stage();
				Scene scene = new Scene(root);
				gameTableStage.setMaximized(true);
				gameTableStage.setResizable(false);
				
		        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		        //set Stage boundaries to visible bounds of the main screen
		        gameTableStage.setX(primaryScreenBounds.getMinX());
		        gameTableStage.setY(primaryScreenBounds.getMinY());
		        gameTableStage.setWidth(primaryScreenBounds.getWidth());
		        gameTableStage.setHeight(primaryScreenBounds.getHeight());
				
				scene.getStylesheets().add("DesignFX.css");
				gameTableStage.setScene(scene);
				gameTableStage.getIcons().add(new Image("file:appicon.png"));
				gameTableStage.setTitle("Illuminati Deluxe");
				gameTableStage.show();
				
			    // register listener for window close event
				gameTableStage.setOnCloseRequest(event -> {
			        // consume event
			        event.consume();
			        
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
					        // show close dialog
					        Alert alert = new Alert(AlertType.CONFIRMATION);
					        alert.setTitle("Close Confirmation");
					        alert.setHeaderText("Do you really want to quit?");
					        alert.initOwner(gameTableStage);

					        Optional<ButtonType> result = alert.showAndWait();
					        if (result.get() == ButtonType.OK){
//					            Platform.exit();
//					        	System.exit(0);
					        	gameTableStage.close(); //only close current window
					        	popupStage.close();     //close the loading pane
					        	root_anchorpane.setEffect(null);
					        }
						}	
					});
			    });
			}
		});
	}
	
	// in game progress loading animation
    private void inGame(){
    	
    	String path = new File("appicon.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
      
		ImageView imageView = new ImageView(image);
		RotateTransition animation = Animattion.createAnimation(imageView);

		root_anchorpane.setEffect(new GaussianBlur());
		
		VBox pauseRoot = new VBox(5);
		pauseRoot.getChildren().add(new Label("Game in progress"));
		pauseRoot.getChildren().add(imageView);
		
		pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
		pauseRoot.setAlignment(Pos.CENTER);
		pauseRoot.setPadding(new Insets(20));
		
		Stage primaryStage = (Stage) root_anchorpane.getScene().getWindow();
		// only allow to initOwner once
		if (popupStageSeen == false) {
			popupStage.initOwner(primaryStage);
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStageSeen = true;
		}

		popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
		popupStage.show();
    }
}
