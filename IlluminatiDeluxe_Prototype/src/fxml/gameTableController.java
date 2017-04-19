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

import org.json.JSONObject;

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
import javafx.scene.control.Labeled;
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
import javafx.scene.layout.GridPane;
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
    private ImageView diceImageview;    
    @FXML
    private ImageView diceImageview1;
    @FXML
    private ImageView diceImageview2;
    @FXML
    private ImageView diceImageview3;
    

    @FXML
    private AnchorPane anouncementPane;

    @FXML
    private Label anouncementLabel;
    
    @FXML
    private StackPane stackPane;
    
    @FXML
    private JFXHamburger inGamehamburger;

    @FXML
    private JFXDrawer inGamedrawer;
    
    @FXML
    private GridPane powerStructureGrid;

	Boolean attackBool = true;
	Boolean diceRolled = false;
	
	// create an array list
    ArrayList players = new ArrayList();
    ArrayList profileImages = new ArrayList();
    ArrayList names = new ArrayList();
    ArrayList diceImages = new ArrayList();
    
    //corner menu 
    CornerMenu cornerMenu;




    
    //javaFX's main for current scene
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadConsoleMenu();

		//display players profile
		getPlayersData();
		//store player imageview into arraylist
		profileImages.add(player1Image);
		profileImages.add(player2Image);
		names.add(player1Name);
		names.add(player2Name);
		diceImages.add(diceImageview1);
		diceImages.add(diceImageview2);
		diceImages.add(diceImageview3);
		
		//rotate imageview
        RotateTransition animation = Animattion.createAnimation(diceImageview);
        resizePowerStructureGrid();
       
		
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
    
    @FXML
    void mouseLeaveMenu(MouseEvent event) {
        if(cornerMenu != null){
    		cornerMenu.hide();	
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
// 		CornerMenu cornerMenu = new CornerMenu(CornerMenu.Location.BOTTOM_LEFT, stackPane, true)
 		cornerMenu = new CornerMenu(CornerMenu.Location.BOTTOM_LEFT, stackPane, true)
            .withAnimationInterpolation(null)
 		    .withAutoShowAndHide(true);
 		 
 		// add the menu items
 		cornerMenu.getItems().addAll(control, neutralize, destroy, defense, windowsMenuItem);
    }
    
    //not dynamic to display players icon hard code to 3, possible to display multiple players dynamically but requires more time
    public void getPlayersData(){
		//set current user's profile image here, player3 (self)
    	String path = new File("support/images/"+currentUser.getimageName()).getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		player3Image.setImage(image);	//reassign image view with new image
		player3Name.setText(currentUser.getName());
		
		//get player 1 and 2
		//database reference to specific "chat > channel name" node
		  final DatabaseReference playerRef = rootRef.child(currentUser.getCurrentChannel());
		  
		  // Attach a listener to read the data at our posts reference
		  playerRef.addValueEventListener(new ValueEventListener() {
	
			    @Override
			    public void onDataChange(DataSnapshot dataSnapshot) {
			    	players.clear();
			    	for (DataSnapshot playerSnapshot:dataSnapshot.getChildren()){	
			    		if(playerSnapshot.child("id").getValue().equals(currentUser.getUID())){
			    			//display current user's dice
			    		    if (playerSnapshot.hasChild("dice")) {
			    		    	parseImageView((String) playerSnapshot.child("dice").getValue(), (ImageView)diceImageview3);
			    		      }
			    		} else {		
			    			ArrayList player = new ArrayList();
			    			player.add((String) playerSnapshot.getKey());
			    			player.add((String) playerSnapshot.child("imageName").getValue());
			    			//check if the "dice" child exist
			    		    if (playerSnapshot.hasChild("dice")) {
				    			player.add((String) playerSnapshot.child("dice").getValue());
				    			System.out.println("dice name is:" + player.get(1));
			    		    }
			    			players.add(player);
			    		}//end else			    		
			    	}//end for
			    	
	    			Platform.runLater(new Runnable() {
						@Override
						public void run() {
					    	for(int i=0; i<players.size(); i++){
					    		ArrayList player = (ArrayList) players.get(i);			    		
					    		
			    				((Label) names.get(i)).setText((String)player.get(0)); //display username into a label
			    				
			    				//parsing profileimage name into path
			    				String path = new File("support/images/"+(String) player.get(1)).getAbsolutePath();
			    				Image image = new Image(new File(path).toURI().toString());
			    				((ImageView) profileImages.get(i)).setImage(image);
//			    		    	parseImageView((String) player.get(1), (ImageView)profileImages.get(i));
			    				
					    	}//end for
					    	//removing legacy data when player size shrinks
					    	if(players.size() == 1) {
			    				((Label) names.get(1)).setText(" ");
			    				((ImageView) diceImages.get(1)).setImage(null);
					    	}
						}	
					});
	    			
	    		    //this block will listen to any changes and display dice roll throughout the entire game
	    			

			    }
			    @Override
			    public void onCancelled(DatabaseError databaseError) {
			    	players.clear();
			    }
			});
    }
    

    @FXML
    void rollDiceAction(MouseEvent event) {
    	
    	if(diceRolled == false) {
    		//generate 2 random numbers between 1-6
        	final int[] dice = new Random().ints(1, 7).distinct().limit(2).toArray();
        	    		
			System.out.println("Rolling dice");
			String diceImageName = String.format("%s-%s.gif",dice[0],dice[1]);
//			String path = String.format("support/images/dice/%s",diceImageName);
//			String absolutePath = new File(path).getAbsolutePath();
//			Image image = new Image(new File(absolutePath).toURI().toString());
//			diceImageview3.setImage(image); //display the image
			
			diceRolled = true; //play only roll dice once each turn
		
			//upload the dice roll to firebase
    		final DatabaseReference ref = rootRef.child(currentUser.getCurrentChannel()).child(currentUser.getName()).child("dice");
    		ref.setValue(diceImageName, new DatabaseReference.CompletionListener() {
    		    @Override
    		    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
    		        if (databaseError != null) {
    		            System.out.println("Dice date could not be saved " + databaseError.getMessage());
    		        } else {
    		            System.out.println("Dice date saved successfully.");
    		        }
    		    }
    		}); //end upload dice data to database
    		
		}//end if
    }
    
//    this method will listen to any changes and display dice roll throughout the entire game
//    public void diceRollListener(){
//    	//database reference to specific "chat > channel name" node
//		  final DatabaseReference d = rootRef.child(currentUser.getCurrentChannel());
//		  
//		  // Attach a listener to read the data at our posts reference
//		  playerRef.addValueEventListener(new ValueEventListener() {
//	
//			    @Override
//			    public void onDataChange(DataSnapshot dataSnapshot) {
//			    	players.clear();
//			    	for (DataSnapshot playerSnapshot:dataSnapshot.getChildren()){	
////			    		if(playerSnapshot.child("id").getValue().equals(currentUser.getUID())){
////			    			//do nothing
////			    		} else {		
////			    			ArrayList player = new ArrayList();
////			    			player.add((String) playerSnapshot.getKey());
////			    			player.add((String) playerSnapshot.child("imageName").getValue());
////			    			players.add(player);
////			    		}//end else
//			    		
//			    	}//end for
//			    	
//	    			Platform.runLater(new Runnable() {
//						@Override
//						public void run() {
//					
//						}	
//					});
//			    }
//			    @Override
//			    public void onCancelled(DatabaseError databaseError) {
//
//			    }
//			});//end listener
//    }
    
    public void resizePowerStructureGrid(){
//    	powerStructureGrid
    	
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			Stage stage = (Stage) powerStructureGrid.getScene().getWindow();
    	        System.out.println("after stageH " + stage.getWidth());
    	        System.out.println("after stageH " + stage.getHeight());
    	        powerStructureGrid.setMinSize(stage.getWidth(), stage.getHeight()- 200);
    		}	
		});

    	

    }
    
    
    //this method will parse imageView with a image name
    public void parseImageView(String imageName, ImageView imageview) {
		//parsing dice image name into path	
		String path = String.format("support/images/dice/%s",imageName);
		String absolutePath = new File(path).getAbsolutePath();
		Image image = new Image(new File(absolutePath).toURI().toString());
		imageview.setImage(image); //display the image
    }
   

	
}


//Platform.runLater(new Runnable() {
//@Override
//public void run() {
//	if(player2Name.getText().equals(" ")){
//		player2Name.setText((String)playerSnapshot.getKey());
//		String path = new File("support/images/"+(String) playerSnapshot.child("imageName").getValue()).getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
//		player2Image.setImage(image);	
//	}//end if
//}	
//});
//
//Platform.runLater(new Runnable() {
//@Override
//public void run() {
//	if(player2Name.getText().equals(" ") == false && player2Name.getText().equals((String)playerSnapshot.getKey()) == false){
//		player1Name.setText((String)playerSnapshot.getKey());
//		String path = new File("support/images/"+(String) playerSnapshot.child("imageName").getValue()).getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
//		player1Image.setImage(image);						    			
//	}				    				
//}	
//});
