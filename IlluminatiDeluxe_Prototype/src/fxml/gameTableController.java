package fxml;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.UUID;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Firebase.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class gameTableController implements Initializable {

	 // User singleton contains data from login menu
	User currentUser = User.getInstance(); //getting user singleton
	// Get a reference to the database
	final static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
	
	// online Chat is using "Fan Out" design pattern
	//current channel
//	private String channelName = currentUser.getCurrentChannel();

	
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private TextField inGameChatTextField;

    @FXML
    private Button inGameChatSendBtn;
    
    @FXML
    private FlowPane flowPaneInScroll;
    
    @FXML
    private ScrollPane chatScrollPane;
    
    // chat send button can be click or press enter
    @FXML
    void onEnter(ActionEvent event) {
    	sendMessage();
    }

    @FXML
    void inGameChatSend_ACTION(MouseEvent event) {	
    	sendMessage();
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	//animation to scroll chat history to the bottom
	static void slowScrollToBottom(ScrollPane scrollPane) {
	    Animation animation = new Timeline(
	        new KeyFrame(Duration.seconds(2),
	            new KeyValue(scrollPane.vvalueProperty(), 1)));
	    animation.play();
	}
	
  //generate a chat channel
  public static void chatChannel(String channelName, String textMessage) {
		User currentUser = User.getInstance();
	  	String name = currentUser.getName(); //get current username
		name = name.toLowerCase(); //convert name to lowercase

		//create date
		DateFormat textTimeStamp = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Date dateobj = new Date();

		//create map to put data into a package
		TreeMap<String,String> messageMap = new TreeMap<String,String>();  
		messageMap.put("name", name);
		messageMap.put("message", textMessage);
		messageMap.put("timeStamp", textTimeStamp.format(dateobj));

		// generate a random message id
		String messageID = UUID.randomUUID().toString();

    	//create database reference for the user
		final DatabaseReference messageRef = rootRef.child("Chat").child(channelName).child(messageID);
		
		messageRef.setValue(messageMap, new DatabaseReference.CompletionListener() {
		    @Override
		    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
		        if (databaseError != null) {
		            System.out.println("Message could not be send. " + databaseError.getMessage());
		        } else {
		            System.out.println("Message Sent Successfully.");
		        }
		    }
		});
	}//end chatChannel()
  
  //send message to database
  public void sendMessage(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (inGameChatTextField.getText().equals("")){
					//do nothing if textfield is empty
				} else{
			    	// upload new message to database
					chatChannel(currentUser.getCurrentChannel(), inGameChatTextField.getText());
			    	inGameChatTextField.setText("");
					receiveMessage();
				}	
			}	
		});
  }
 
  //receive message 
  public void receiveMessage(){
	  final DatabaseReference ref = rootRef.child("Chat").child(currentUser.getCurrentChannel());
	  final DatabaseReference receiveMessageRef = rootRef.child("Chat").child(currentUser.getCurrentChannel());
	  
	  // Attach a listener to read the data at our posts reference
	  receiveMessageRef.addValueEventListener(new ValueEventListener() {

		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {	
		    	for (DataSnapshot messageSnapshot:dataSnapshot.getChildren()){	
		    		System.out.println(messageSnapshot);
		    		
		    		// TO DO create a message object and reaarange by date
		    		
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
//					    	String name = (String) dataSnapshot.child("name").getValue();
//					    	String message = (String) dataSnapshot.child("message").getValue();
//					    	String timeStamp = (String) dataSnapshot.child("timeStamp").getValue();
//					    	System.out.println(name + " "+message +" "+timeStamp);
//							displayMessage(name, message, timeStamp);
						}	
					});
		    		
		    	}

		    	

	    		//ref.removeEventListener(this);
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		        System.out.println("Receive message failed: " + databaseError.getCode());
		    }
		});
  }
  
  //show message onto chat box
  public void displayMessage(String name, String text, String date){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (text.equals("")){
					//do nothing if textfield is empty
				} else{
					String message = name + ": " + text;
			    	Label textLabel = new Label(message);
			    	textLabel.setMinWidth(flowPaneInScroll.getWidth());
			    	textLabel.setMaxWidth(flowPaneInScroll.getWidth());
			    	textLabel.setWrapText(true);	//multi line text
			    	flowPaneInScroll.getChildren().addAll(textLabel); //addAll allow to add multiple Nodes
			    	inGameChatTextField.setText("");
			    	slowScrollToBottom(chatScrollPane);
	
			    	// upload new message to database
					chatChannel(currentUser.getCurrentChannel(), textLabel.getText());
				}	
			}	
		});
  }
  
  
	
}
