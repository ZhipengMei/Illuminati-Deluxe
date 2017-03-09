package fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.UUID;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Firebase.AccessFirebase; //using method from another package
import fxml.splashController.SplashScreen;

import Firebase.User;


//public class loginmenuController implements Runnable  {
public class loginmenuController{// extends User {
	
//    static User currentUser = new User();

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private AnchorPane rootloginpane;
	
    @FXML
    private AnchorPane signinrightpane;

    @FXML
    private Button playBtn;

    @FXML
    private AnchorPane signinleftpane;
    
    @FXML
    void toCreateMenu_ACTION(MouseEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		    	toCreateAcctMenu();
			}	
		});
    }

    @FXML
    void playBtn_action(ActionEvent event) {
    	//System.out.println(usernameField.getText() + " " + passwordField.getText());

    	// Get a reference to the database
		final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
		final DatabaseReference ref = rootRef.child("profile");

		// Attach a listener to read the data at our posts reference
		//specifically query this user's name
		ref.orderByChild("name").equalTo(usernameField.getText().toLowerCase()).addValueEventListener(new ValueEventListener() {

		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {	
		    	//System.out.println(dataSnapshot);
		    	//iterate each node to get the children value
		    	for (DataSnapshot messageSnapshot:dataSnapshot.getChildren()){		    		
		    		//parse DataSnapshot to String
		    		String dataPassword = (String)messageSnapshot.child("password").getValue();
		    		//validate password 
		    		if (dataPassword.equals(passwordField.getText())){
		    			System.out.println("User found.");
		    			ref.removeEventListener(this); //disconnect the network listener
		    			
		    			//using runlater because in javaFX only the FX thread can modify the ui elements 
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
//								System.out.println(messageSnapshot.getValue());
								User currentUser = User.getInstance();
								currentUser.setUID(messageSnapshot.getKey());
//					    		System.out.println(currentUser.getUID());
								login();	//switch to another playersreen
							}	
						});
						
		    		} else {
		    			Platform.runLater(new Runnable() {
							@Override
							public void run() {
								takenAlert();
							}	
						});
		    		}
		    	}
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		        System.out.println("The read failed: " + databaseError.getCode());
		    }
		});

    }
    
    public void login(){
    	BorderPane pane = null;
    	try {
    		pane = FXMLLoader.load(getClass().getResource("playerScreenFX.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//override the previouse scene content with current border pane's content
    	rootloginpane.getChildren().setAll(pane); 
    }
    
    public void toCreateAcctMenu(){
    	AnchorPane apane = null;
    	try {
    		apane = FXMLLoader.load(getClass().getResource("createmenuFX.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//override the previouse scene content with current border pane's content
    	rootloginpane.getChildren().setAll(apane); 
    }
   
    
    public void takenAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert");
    	alert.setHeaderText("Password incorrect");
    	alert.setContentText("Username and password do not match.");
    	alert.showAndWait();
    }

 }

