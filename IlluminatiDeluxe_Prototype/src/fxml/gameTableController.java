package fxml;

import fxml.inGameSlideMenuController;
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
import java.util.TreeSet;
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
import Extension.Player;
import Firebase.Message;
import Firebase.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.DoubleExpression;
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
	
    ArrayList<Player> players = new ArrayList<Player>(); //store all players' info
    ArrayList<ImageView> profileImages = new ArrayList<ImageView>(); 
    ArrayList<Label> names = new ArrayList<Label>();
    ArrayList<ImageView> diceImages = new ArrayList<ImageView>();
    ArrayList<Integer> diceVal = new ArrayList<Integer>();
    ArrayList<String> playersOrder = new ArrayList<String>();


    //corner menu 
    CornerMenu cornerMenu;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    //javaFX's main for current scene
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadConsoleMenu();

		//store player imageview into arraylist
		profileImages.add(player1Image);
		profileImages.add(player2Image);
		profileImages.add(player3Image);
		names.add(player1Name);
		names.add(player2Name);
		names.add(player3Name);
		diceImages.add(diceImageview1);
		diceImages.add(diceImageview2);
		diceImages.add(diceImageview3);    
        
        setProfileDataNull(); //hide profile image at the very beginning
        
		//rotate imageview
		Animattion.createAnimation(diceImageview);
        resizePowerStructureGrid();
        
		//display players profile
		getPlayersData();

	} //end initialize
	
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void setProfileDataNull(){		
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			player1Image.setImage(null);
    	        player2Image.setImage(null);
    	        player3Image.setImage(null);
    	        player1Name.setText(null);
    	        player2Name.setText(null);
    	        player3Name.setText(null);
    		}	
		});        
	}
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
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
	
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @FXML
    void mouseEnterMenuShow(MouseEvent event) {
    		actionMenu();	
    }
    
   
    
    public void actionMenu(){
        MenuItem rollDice = new MenuItem("Roll Dice",
                new ImageView(new Image("file:support/images/ingameMenu/dice.png")));
    	MenuItem control = new MenuItem("Attack to Control",
                new ImageView(new Image("file:support/images/ingameMenu/control.png")));
        MenuItem neutralize = new MenuItem("Attack to Neutralize",
                new ImageView(new Image("file:support/images/ingameMenu/neutralize.png")));
        MenuItem destroy = new MenuItem("Attack to Destroy",
                new ImageView(new Image("file:support/images/ingameMenu/destroy.png")));
        MenuItem cancel = new MenuItem("Cancel",
                new ImageView(new Image("file:support/images/ingameMenu/cancel.png")));
        MenuItem moveMoney = new MenuItem("Transfer Money",
                new ImageView(new Image("file:support/images/ingameMenu/money.png")));
        MenuItem pass = new MenuItem("Pass",
                new ImageView(new Image("file:support/images/ingameMenu/pass.png")));        
        MenuItem draw = new MenuItem("Draw a card",
                new ImageView(new Image("file:support/images/ingameMenu/draw.png")));        
        
        rollDice.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	rollingDice();
            }
        });
        
        draw.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	
            	inGameSlideMenuController drawCard = new inGameSlideMenuController();
            	drawCard.loadUncontrolledGroup("airlines.jpg");
//            	//action enable only if player's turn
//            	if(players.get(0).getTurn() == currentUser.getName()){
//            		System.out.println("\nDraw a card");
//            		//TODO
//            		
//            	}
            }
        });
        
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
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	if(cornerMenu != null){
            		cornerMenu.hide();	
                 }
            }
        });


 		// corner menu
 		// position a corner menu in the top left corner, that initially is not visible
 		cornerMenu = new CornerMenu(CornerMenu.Location.BOTTOM_LEFT, stackPane, true)
            .withAnimationInterpolation(null)
 		    .withAutoShowAndHide(true);
 		 
 		// add the menu items
 		cornerMenu.getItems().addAll(rollDice, draw, control, neutralize, destroy, moveMoney, pass, cancel);
    }
    
    
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    
    //not dynamic to display players icon hard code to 3, possible to display multiple players dynamically but requires more time
    public void getPlayersData(){
		//database reference to specific "chat > channel name" node
		  final DatabaseReference playerRef = rootRef.child(currentUser.getCurrentChannel());
		  
		  // Attach a listener to read the data at our posts reference
		  playerRef.addValueEventListener(new ValueEventListener() {
	
			    @Override
			    public void onDataChange(DataSnapshot dataSnapshot) {
			    	//clear out reusable tools
			    	players.clear();
			    	diceVal.clear();
			    	playersOrder.clear();
			    	setProfileDataNull();
			    	
			    	for (DataSnapshot playerSnapshot:dataSnapshot.getChildren()){	
			    		
			    		Player player = new Player(); //creating new player object
			    		player.setName((String) playerSnapshot.getKey());
			    		player.setimageName((String) playerSnapshot.child("imageName").getValue());
			    		
	    				playersOrder.add((String) playerSnapshot.getKey()); //setup an order for take turn later on
		    			
		    			//check if the "dice" child exist
		    		    if (playerSnapshot.hasChild("dice")) {		    		    
		    		    	player.setDice((String) playerSnapshot.child("dice").getValue());		
		    		    	
		    		    	String path = new File("support/images/"+ player.getDice()).getAbsolutePath();
	        				Image image = new Image(new File(path).toURI().toString());
	        				diceImageview1.setImage(image); //display the image  
		    		    }
		    		    if (playerSnapshot.hasChild("diceVal")) {
		    		    	Integer result = (int) (long) playerSnapshot.child("diceVal").getValue();
		    		    	player.setDiceVal(result);
		    		    	diceVal.add(result); //add dice value into arraylist for compa
		    		    }
		    		    if (playerSnapshot.hasChild("announcement")) {
		    		    	player.setAnnouncement((String) playerSnapshot.child("announcement").getValue());
		    		    }
		    		    if (playerSnapshot.hasChild("turn")) {
		    		    	player.setTurn((String) playerSnapshot.child("turn").getValue());
		    		    }
		    			players.add(player);
			    	}//end for
			    	
			    	
			    	assignProfileData();
			    	compareDiceValue();
			    }
			    @Override
			    public void onCancelled(DatabaseError databaseError) {
			    	players.clear();
			    	diceVal.clear();
			    	playersOrder.clear();
			    }
			});
    }// end get player data
    
    
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
    
    public void assignProfileData(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
		    	for(int i=0; i<players.size(); i++){
		    		//getting the appropriate player's info
		    		Player player = players.get(i);
		    		
		    		//parsing name to label
		    		((Label) names.get(i)).setText(player.getName());
    				
		    		//parsing profileimage 
    		    	parseImageView((String) player.getimageName(), (ImageView)profileImages.get(i), "profile");

    		    	if( player.getDice() != null) {
        		    	parseImageView((String) player.getDice(), (ImageView)diceImages.get(i), "dice");
    		    	}else {
    		    		((ImageView)diceImages.get(i)).setImage(null);
    		    	}
    		    	if( player.getAnnouncement() != null) {
    		    		((Label) anouncementLabel).setText(player.getAnnouncement());
    		    	}
		    	}//end for						    	
			}	
		});
    }
    
    
    
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
    
    public void compareDiceValue(){
    	Boolean repeatedDiceRoll = false;
    	//making sure there is enough player for the game to begin
    	if(players.size() > 2){
        		if(diceVal.size() > 2){    
        			//make sure no repeated dice roll 
        			for(int i:diceVal){
        				if(moreThanOnce(diceVal, i) == true){
        					repeatedDiceRoll = true;
        					diceVal.clear(); //erase dice value array

        					//remove dice value from database
        	    			for(int j=0; j<playersOrder.size();j++){	
        	    				rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(j)).child("diceVal").removeValue();
        			    		rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(j)).child("dice").removeValue();
        			    		rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(j)).child("announcement").setValue("Re-Roll Dice again to start the game !!!");
        	    			}        	    			
        	    			diceRolled = true; //enable to roll dice again
        	    			break;
        				}
        			}//end for

        			if(repeatedDiceRoll == false){
    	    			//detect highest dice roll value
    		    		int largest = Collections.max(diceVal);
    		    		Player maxPlayer = Collections.max(players);
    		    		String name = maxPlayer.getName();
    		    		System.out.println(name+" has the highest dice roll");
    		    		String anouncementText = name+" has the highest dice roll, " +name+" please draw a card !!!";
    	    			
    	    			//first draw announcement
    	    			for(int i=0; i<playersOrder.size();i++){
    			    		rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(i)).child("announcement").setValue(anouncementText);
    			    		rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(i)).child("turn").setValue(name);
    	    			}
    	    		}//end if
        	} //end if
    	}    
    }
    
    
    
    
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    public void rollingDice(){
    	if(diceRolled == false) {
    		//generate 2 random numbers between 1-6
        	final int[] dice = new Random().ints(1, 7).distinct().limit(2).toArray();
        	    		
			System.out.println("Rolling dice");
			String diceImageName = String.format("%s-%s.gif",dice[0],dice[1]);

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
    		rootRef.child(currentUser.getCurrentChannel()).child(currentUser.getName()).child("diceVal").setValue(dice[0] + dice[1]);    	    	
    		rootRef.child(currentUser.getCurrentChannel()).child(currentUser.getName()).child("announcement").setValue("Waiting for other players !!!");
	
		}//end if
    }
    
    public void resizePowerStructureGrid(){
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			Stage stage = (Stage) powerStructureGrid.getScene().getWindow();
    	        powerStructureGrid.setMinSize(stage.getWidth(), stage.getHeight()- 200);
    		}	
		});

    }
    
    
    
    
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------   
    //this method will parse imageView with a image name
    public void parseImageView(String imageName, ImageView imageview, String imageType) {
		
    	if(imageType.equals("profile")){
    		String path = new File("support/images/"+ imageName).getAbsolutePath();
    		Image image = new Image(new File(path).toURI().toString());
    		imageview.setImage(image); //display the image  
    		imageview.setVisible(true);	
    	} else if(imageType.equals("dice")){
    		String path = new File("support/images/dice/"+ imageName).getAbsolutePath();
    		Image image = new Image(new File(path).toURI().toString());
    		imageview.setImage(image); //display the image  
    		imageview.setVisible(true);	
    	}
    	
    	
		
		
		
		
//    	Platform.runLater(new Runnable() {
//    		@Override
//    		public void run() {    			
//    		}	
//		});
    }
   
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//check if number appeared in arraylist more than once
	public static boolean moreThanOnce(ArrayList<Integer> list, int searched) {
	    int numCount = 0;

	    for (int thisNum : list) {
	        if (thisNum == searched) numCount++;
	    }

	    return numCount > 1;
	}
    
    
    
}

