package Extension;

import java.io.File;
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

import Firebase.Message;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Chat {

    private ScrollPane chatScrollPane;

    private FlowPane flowPaneInScroll;

    private TextField inGameChatTextField;
    
    private Button inGameChatSendBtn;
    
    public Chat(ScrollPane chatScrollPane, FlowPane flowPaneInScroll, TextField inGameChatTextField, Button inGameChatSendBtn) {
    	this.chatScrollPane = chatScrollPane; 
    	this.flowPaneInScroll = flowPaneInScroll;
    	this.inGameChatTextField = inGameChatTextField;
    	this.inGameChatSendBtn = inGameChatSendBtn;    	
    }
	
	 // User singleton contains data from login menu
	User currentUser = User.getInstance(); //getting user singleton
	
	// Get a reference to the database
	final static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
	
	// messages arraylist to store individual message
//	ArrayList<Message> messages = new ArrayList<Message>();
	//create message object
	Message message = new Message();
	
	//animation to scroll chat history to the bottom
	static void slowScrollToBottom(ScrollPane scrollPane) {
	    Animation animation = new Timeline(
	        new KeyFrame(Duration.seconds(2),
	            new KeyValue(scrollPane.vvalueProperty(), 1)));
	    animation.play();
	}
	
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
				}	
			}	
		});
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
	  
  
	  
	  
	  
	  //-----------------------------------------------------------------------------------------------------------------------
	  //receive message is officially working, one message at a t
	  public void receiveMessage(){
		  
		  //database reference to specific "chat > channel name" node
		  final DatabaseReference ref = rootRef.child("Chat").child(currentUser.getCurrentChannel());
		  final DatabaseReference receiveMessageRef = rootRef.child("Chat").child(currentUser.getCurrentChannel());
		  
		  // Attach a listener to read the data at our posts reference
		  receiveMessageRef.orderByChild("timeStamp").addChildEventListener(new ChildEventListener() {
			    @Override
			    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

			    	String name = (String) dataSnapshot.child("name").getValue();
			    	String textMessage = (String) dataSnapshot.child("message").getValue();
			    	
			    	System.out.println(name + " : " + textMessage);
			    	
//		    		message.setName(name);
//		    		message.setMessage(textMessage);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
//							displayMessage(message.getName(), message.getMessage());
							displayMessage(name, textMessage);					

						}	
					});

			    }

			    @Override
			    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

			    @Override
			    public void onChildRemoved(DataSnapshot dataSnapshot) {}

			    @Override
			    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

			    @Override
			    public void onCancelled(DatabaseError databaseError) {}
			});
	  }// end receive message
	  
	  
	  
//  //receive message is officially working, load multiple messages at once
//  public void receiveMessage(){
//	  
//	  //database reference to specific "chat > channel name" node
//	  final DatabaseReference ref = rootRef.child("Chat").child(currentUser.getCurrentChannel());
//	  final DatabaseReference receiveMessageRef = rootRef.child("Chat").child(currentUser.getCurrentChannel());
//	  
//	  // Attach a listener to read the data at our posts reference
//	  receiveMessageRef.addValueEventListener(new ValueEventListener() {
//
//		    @Override
//		    public void onDataChange(DataSnapshot dataSnapshot) {
//				messages.clear(); //reset messages arraylist to empty
//
//		    	for (DataSnapshot messageSnapshot:dataSnapshot.getChildren()){	
//		    		// parse json data into local vars
//			    	String name = (String) messageSnapshot.child("name").getValue();
//			    	String textMessage = (String) messageSnapshot.child("message").getValue();
//			    	String timeStamp = (String) messageSnapshot.child("timeStamp").getValue();
//			    	
//			    	//convert string back to Date
//			    	try{
//			    	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
//			    	    Date parsedDate = dateFormat.parse(timeStamp);
//			    	    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
//				    	
//				    	//create text object
//			    		Message message = new Message();
//			    		//feed setter data
//			    		message.setDateTime(timestamp);
//			    		message.setName(name);
//			    		message.setMessage(textMessage);
//			    		
//			    		messages.add(message); // add individual message into messages arraylist
//
//			    	}catch(Exception e){//this generic but you can control another types of exception
//			    		//look the origin of excption 
//			    		e.printStackTrace();
//			    	}
//		    	}//end for
//		    	
//		    	Collections.sort(messages); //sort by timeStamp
//		    	
//				Platform.runLater(new Runnable() {
//					@Override
//					public void run() {
//						for (int i = 0; i < messages.size(); i++) {
//							System.out.println(i);
//							//System.out.println(messages.get(i));
//							//display messages onto screen
////							System.out.println(messages.get(i).getName() + ": " + messages.get(i).getMessage());
////							System.out.println("flowPaneInScroll.getWidth() " + flowPaneInScroll.getLayoutBounds().getWidth());
//							displayMessage(messages.get(i).getName(), messages.get(i).getMessage());
//
//						}						
//					}	
//				});
//		    }
//		    @Override
//		    public void onCancelled(DatabaseError databaseError) {}
//		});
//  }// end receive message
  
  
  //show message onto chat box
  public void displayMessage(String name, String text){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (text.equals("")){
					//do nothing if textfield is empty
				} else{
					String message = name + ": " + text;
			    	Label textLabel = new Label(message);
			    	textLabel.setMinWidth(flowPaneInScroll.getPrefWidth());
			    	textLabel.setMaxWidth(flowPaneInScroll.getPrefWidth());
			    	textLabel.setWrapText(true);	//multi line text
			    	flowPaneInScroll.getChildren().addAll(textLabel); //addAll allow to add multiple Nodes
			    	inGameChatTextField.setText("");
			    	slowScrollToBottom(chatScrollPane);
	
				}	
			}	
		});
  }



}
