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
	
	// create an array list
    ArrayList<ArrayList<String>> players = new ArrayList<ArrayList<String>>();
    ArrayList<ImageView> profileImages = new ArrayList<ImageView>();
    ArrayList<Label> names = new ArrayList<Label>();
    ArrayList<ImageView> diceImages = new ArrayList<ImageView>();
    ArrayList<Integer> diceVal = new ArrayList<Integer>();
	TreeMap<Integer, String> diceValMap = new TreeMap<Integer, String>(); 
	TreeSet diceValSet = new TreeSet(); 
    ArrayList<String> playersOrder = new ArrayList<String>();



    
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
		Animattion.createAnimation(diceImageview);
        resizePowerStructureGrid();
        
        //hide all dice roll image view at first
        diceImageview1.setVisible(false);
        diceImageview2.setVisible(false);
        diceImageview3.setVisible(false);
       
		
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
// 		CornerMenu cornerMenu = new CornerMenu(CornerMenu.Location.BOTTOM_LEFT, stackPane, true)
 		cornerMenu = new CornerMenu(CornerMenu.Location.BOTTOM_LEFT, stackPane, true)
            .withAnimationInterpolation(null)
 		    .withAutoShowAndHide(true);
 		 
 		// add the menu items
 		cornerMenu.getItems().addAll(rollDice, draw, control, neutralize, destroy, moveMoney, pass, cancel);
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
			    			playersOrder.add(currentUser.getName());
			    			
			    			//display current user's dice
			    		    if (playerSnapshot.hasChild("dice")) {
			    		    	parseImageView((String) playerSnapshot.child("dice").getValue(), (ImageView)diceImageview3);
				    		    if (playerSnapshot.hasChild("diceVal")) {
//				    		    	System.out.println((long) playerSnapshot.child("diceVal").getValue());
				    		    	Integer result = (int) (long) playerSnapshot.child("diceVal").getValue();

//				    		    	int result = ((DoubleExpression) playerSnapshot.child("diceVal").getValue()).intValue();
				    		    	diceValMap.put(result,currentUser.getName());		 	    		    	
				    		    	diceVal.add(result); //add dice value into arraylist for compa	
				    		    }				    		    
			    		    }
			    		    if (playerSnapshot.hasChild("announcement")) {
			    		    	Platform.runLater(new Runnable() {
			    		    		@Override
			    		    		public void run() {
				    	    			anouncementLabel.setText((String) playerSnapshot.child("announcement").getValue());
			    		    		}	
			    				});
//		    	    			anouncementLabel.setText((String) playerSnapshot.child("announcement").getValue());
			    		    }
			    		    
			    		} else {		
			    			ArrayList<String> player = new ArrayList<String>();
			    			player.add((String) playerSnapshot.getKey());
			    			player.add((String) playerSnapshot.child("imageName").getValue());
		    				playersOrder.add((String) playerSnapshot.getKey());
			    			
			    			//check if the "dice" child exist
			    		    if (playerSnapshot.hasChild("dice")) {
				    			player.add((String) playerSnapshot.child("dice").getValue());
				    		    if (playerSnapshot.hasChild("diceVal")) {
				    		    	Integer result = (int) (long) playerSnapshot.child("diceVal").getValue();

//				    		    	int result = ((DoubleExpression) playerSnapshot.child("diceVal").getValue()).intValue();
				    		    	diceValMap.put(result,currentUser.getName());	
				    		    	diceValSet.add(result);
				    		    	diceVal.add(result); //add dice value into arraylist for compa
				    		    }			    		    	
			    		    }
			    			players.add(player);
			    		}//end else			    		
			    	}//end for
			    	
	    			Platform.runLater(new Runnable() {
						@Override
						public void run() {
					    	for(int i=0; i<players.size(); i++){
					    		ArrayList<?> player = (ArrayList<?>) players.get(i);			    		
					    		
			    				((Label) names.get(i)).setText((String)player.get(0)); //display username into a label
			    				
			    				//parsing profileimage name into path
			    				String path = new File("support/images/"+(String) player.get(1)).getAbsolutePath();
			    				Image image = new Image(new File(path).toURI().toString());
			    				((ImageView) profileImages.get(i)).setImage(image);
			    				
			    				//display dice image
			    				if (player.size() > 2 ) {
				    		    	parseImageView((String) player.get(2), (ImageView)diceImages.get(i));
				    		    }
			    				
					    	}//end for
					    	//removing legacy data when player size shrinks
					    	if(players.size() == 1) {
			    				((Label) names.get(1)).setText(" ");
			    				((ImageView) diceImages.get(1)).setImage(null);
					    	}
					    	
					    	System.out.println("diceValMap: "+diceValMap.size());
					    	for(int i =0;i<diceVal.size();i++){
					    		System.out.println(diceVal.get(i));
					    	}
					    	
//					    	//determining the highest dice roll
//					    	if(diceImageview3.isVisible() != false && diceImageview2.isVisible() != false && diceImageview1.isVisible() != false) {
//					    		System.out.println("diceVal.size(): "+diceVal.size());
//					    		if(diceVal.size() == 3){
//					    			System.out.println("diceVal.get(0): " + diceVal.get(0));
//					    			System.out.println("diceVal.get(1): " + diceVal.get(1));
//					    			System.out.println("diceVal.get(2): " + diceVal.get(2));
//						    		//cannot detect highest dice roll
//						    		if(diceVal.get(0) == diceVal.get(1) || diceVal.get(0) == diceVal.get(2) || diceVal.get(2) == diceVal.get(1)){
//					    				diceVal.clear(); //erase dice value array
//					    				
//					    				//hide images
//					    		    	Platform.runLater(new Runnable() {
//					    		    		@Override
//					    		    		public void run() {
//					    		    	        diceImageview1.setVisible(false);
//					    		    	        diceImageview2.setVisible(false);
//					    		    	        diceImageview3.setVisible(false);						    		    		}	
//					    				});
//					    		    	
//							        	//remove dice value from database
//						    			for(int i=0; i<playersOrder.size();i++){						    										    				
//								    		rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(i)).child("diceVal").removeValue();
//								    		rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(i)).child("announcement").setValue("Re-Roll Dice again to start the game !!!");
//						    			}
//
//						    		} else {
//						    			//detect highest dice roll value
//							    		long largest = Collections.max(diceVal);
//							    		String name = diceValMap.get(largest);
//							    		String anouncementText = "\name please draw a card !!!";
////				    	    			anouncementLabel.setText(anouncementText);
//				    	    			
//				    	    			//first draw announcement
//						    			for(int i=0; i<playersOrder.size();i++){
//								    		rootRef.child(currentUser.getCurrentChannel()).child(playersOrder.get(i)).child("announcement").setValue(anouncementText);
//						    			}
//						    		}//end else
//					    		}
//					    	} //end if
					    						    	
						}	
					});
	    
			    }
			    @Override
			    public void onCancelled(DatabaseError databaseError) {
			    	players.clear();
			    }
			});
    }// end get player data
    
    public void rollingDice(){
    	if(diceRolled == false) {
    		//generate 2 random numbers between 1-6
        	final int[] dice = new Random().ints(1, 7).distinct().limit(2).toArray();
        	    		
			System.out.println("Rolling dice");
			String diceImageName = String.format("%s-%s.gif",dice[0],dice[1]);

//			diceRolled = true; //play only roll dice once each turn
		
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
			anouncementLabel.setText("Waiting for other players !!!");
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
    
    
    //this method will parse imageView with a image name
    public void parseImageView(String imageName, ImageView imageview) {
		//parsing dice image name into path	
		String path = String.format("support/images/dice/%s",imageName);
		String absolutePath = new File(path).getAbsolutePath();
		Image image = new Image(new File(absolutePath).toURI().toString());
		imageview.setImage(image); //display the image  
		imageview.setVisible(true);
//    	Platform.runLater(new Runnable() {
//    		@Override
//    		public void run() {
//  		
//    		}	
//		});
    }
   

	
}

