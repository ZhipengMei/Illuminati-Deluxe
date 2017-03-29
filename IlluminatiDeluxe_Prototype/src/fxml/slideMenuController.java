package fxml;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Animation.Animattion;
import Firebase.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class slideMenuController {

    @FXML
    private AnchorPane slideMenu_root_anchorPane;

    @FXML
    private Button logoutBtn;
    
    @FXML
    void logoutClicked(MouseEvent event) {
    	System.out.println("logout btn clicked");
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				AnchorPane loginScreen = null;
				try {
					loginScreen = FXMLLoader.load(getClass().getResource("loginmenu.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	//override the current stage with new login stage
			    Stage stage = (Stage) slideMenu_root_anchorPane.getScene().getWindow();
				Scene scene = new Scene(loginScreen);
				stage.getIcons().add(new Image("file:appicon.png"));
				stage.setTitle("Illuminati Deluxe");	
				scene.getStylesheets().add("DesignFX.css");
		        stage.setScene(scene);
		        Animattion.fadeTransition(loginScreen);				
			}
		});
    }

    @FXML
    void AnonymousClicked(MouseEvent event) {
//    	String path = new File("support/images/Anonymous.png").getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
		storeImage("Anonymous.png");
//    	profileimageview.setImage(image);	//reassign image view with new image
//    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void CoinClicked(MouseEvent event) {
//    	String path = new File("support/images/Coins.png").getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
		storeImage("Coins.png");
//    	profileimageview.setImage(image);	//reassign image view with new image
//    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void FortuneTellerClicked(MouseEvent event) {
//    	String path = new File("support/images/FortuneTeller.png").getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
		storeImage("FortuneTeller.png");
//    	profileimageview.setImage(image);	//reassign image view with new image
//    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void GhostClicked(MouseEvent event) {
//    	String path = new File("support/images/Ghost.png").getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
		storeImage("Ghost.png");
//    	profileimageview.setImage(image);	//reassign image view with new image
//    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void WitchClicked(MouseEvent event) {
//    	String path = new File("support/images/Witch.png").getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
		storeImage("Witch.png");
//    	profileimageview.setImage(image);	//reassign image view with new image
//    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }
    
    @FXML
    void alienClicked(MouseEvent event) {
    	//get the alien image
//    	String path = new File("support/images/Alien.png").getAbsolutePath();
//		Image image = new Image(new File(path).toURI().toString());
		storeImage("Alien.png"); //save image name to database
//    	profileimageview.setImage(image);	//reassign image view with new image
//    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

	// User singleton contains data from login menu
	User currentUser = User.getInstance(); //getting user singleton
	// Get a reference to the database
	final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
	final DatabaseReference ref = rootRef.child("profile").child(currentUser.getUID());
	final DatabaseReference imageRef = rootRef.child("profile").child(currentUser.getUID()).child("imageName");
	
	// Since profile image is given/limited, we can cheat without using cloud storage
	// Tip: Store file locally attached with file name and obtain file path when needed
	public void storeImage(String imageName){
		currentUser.setimageName(imageName);
		
		//upload image name to database
		imageRef.setValue(imageName, new DatabaseReference.CompletionListener() {
		    @Override
		    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
		        if (databaseError != null) {
		            System.out.println("Image data could not be saved " + databaseError.getMessage());
		        } else {
		            System.out.println("Image data saved successfully.");
		        }
		    }
		});
	} //end storeImage
	
	
	//------------------------------------------------------
}
