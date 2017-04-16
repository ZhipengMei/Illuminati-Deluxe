package fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.UUID;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;

import Animation.Animattion;
import Firebase.Message;
import Firebase.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import jfxtras.scene.menu.CornerMenu;


public class gameTableController extends Message implements Initializable  {

	 // User singleton contains data from login menu
	User currentUser = User.getInstance(); //getting user singleton
	
	// Get a reference to the database
	final static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//	final DatabaseReference ref = rootRef.child("profile").child(currentUser.getUID());
//	final DatabaseReference imageRef = rootRef.child("profile").child(currentUser.getUID()).child("imageName");
	
	//---in game profile ----
	 @FXML
	 private ImageView player1Image;

    @FXML
    private Label player1Name;
    
    @FXML
    private ImageView player2Image;

    @FXML
    private Label player2Name;
    
    @FXML
    private ImageView player3Image;

    @FXML
    private Label player3Name;
	
	//------
	
	
    @FXML
    private AnchorPane gameTableMainMenu;
    
    @FXML
    private JFXDrawer diceDrawer;
    
    @FXML
    private ImageView diceImageview;
    
    @FXML
    private StackPane stackPane;
    
    @FXML
    private JFXHamburger inGamehamburger;

    @FXML
    private JFXDrawer inGamedrawer;

	Boolean attackBool = true;
	Boolean diceRolled = false;
    
    //javaFX's main for current scene
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadConsoleMenu();
		loadDicePane();	


		
		
		//rotate imageview
        RotateTransition animation = Animattion.createAnimation(diceImageview);
		
      
	}
	
	public void loadDicePane(){
		try {
			AnchorPane apane = FXMLLoader.load(getClass().getResource("rollDiceFXMl.fxml"));
			diceDrawer.setSidePane(apane);

			diceImageview.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
//				if(diceRolled == false) {
					if(diceDrawer.isShown()) {
						diceDrawer.close();
					} else {
						diceDrawer.open();
					}
//				}

			});			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//display players profile
		getPlayersDate();
	}
	
	//console menu
	public void loadConsoleMenu(){
		try {
			AnchorPane apane = FXMLLoader.load(getClass().getResource("inGameSlideMenu.fxml"));
			inGamedrawer.setSidePane(apane);

			//hamburger menu animation
			HamburgerNextArrowBasicTransition burgerTask2 = new HamburgerNextArrowBasicTransition(inGamehamburger);
			burgerTask2.setRate(-1);
			inGamehamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
				burgerTask2.setRate(burgerTask2.getRate() * -1);
				burgerTask2.play();
				if(inGamedrawer.isShown()) {
					inGamedrawer.close();
				} else {
					inGamedrawer.open();
				}
			});			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
  

    @FXML
    void mouseEnterMenuShow(MouseEvent event) {
    	if(diceRolled == true){
    		actionMenu();	
    	}    	
    }
    
   
    
    public void actionMenu(){
    	MenuItem control = new MenuItem("Attack to Control",
                new ImageView(new Image("file:support/images/ingame/control.png")));
        MenuItem neutralize = new MenuItem("Attack to Neutralize",
                new ImageView(new Image("file:support/images/ingame/neutralize.png")));
        MenuItem destroy = new MenuItem("Attack to Destroy",
                new ImageView(new Image("file:support/images/ingame/destroy.png")));
        MenuItem defense = new MenuItem("Defense an attack",
                new ImageView(new Image("file:support/images/ingame/defense.png")));
        MenuItem windowsMenuItem = new MenuItem("windowsMenuItem",
                new ImageView(new Image("file:support/images/ingame/control.png")));
        
        control.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
               	 if(attackBool == true){
                   	 System.out.println("control");
               	 }
                }
            });

        neutralize.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
           	 
              	 System.out.println("googleMenuItem");
              	 attackBool = false;

            }
        });

 		// corner menu
 		// position a corner menu in the top left corner, that initially is not visible
 		CornerMenu cornerMenu = new CornerMenu(CornerMenu.Location.BOTTOM_LEFT, stackPane, true)
 		    .withAnimationInterpolation(null)
 		    .withAutoShowAndHide(true);
 		 
 		// add the menu items
 		cornerMenu.getItems().addAll(control, neutralize, destroy, defense, windowsMenuItem);
    }
    
    public void getPlayersDate(){
		//set current user's profile image here
    	String path = new File("support/images/"+currentUser.getimageName()).getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		player3Image.setImage(image);	//reassign image view with new image
		player3Name.setText(currentUser.getName());
		
//		//get other players profile
//		ref.addListenerForSingleValueEvent(new ValueEventListener() {
//		    @Override
//		    public void onDataChange(DataSnapshot dataSnapshot) {
//		        long numChildren = dataSnapshot.getChildrenCount();
//		        System.out.println(count.get() + " == " + numChildren);
//		    }
//
//		    @Override
//		    public void onCancelled(DatabaseError databaseError) {}
//		});
    }
    

	
}
