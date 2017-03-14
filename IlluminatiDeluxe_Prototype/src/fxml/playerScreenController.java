package fxml;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Firebase.User;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class playerScreenController implements Initializable {

	User currentUser = User.getInstance(); //getting user singleton
	// Get a reference to the database
	final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
	final DatabaseReference ref = rootRef.child("profile").child(currentUser.getUID());
	final DatabaseReference imageRef = rootRef.child("profile").child(currentUser.getUID()).child("imageName");

	
    @FXML
    private StackPane profilechooseStackpane;
    
    @FXML
    private GridPane channelGridpane;
    
    @FXML
    private Button channel1;

    @FXML
    private Button channel2;

    @FXML
    private Button channel3;

    @FXML
    private Button channel4;

    @FXML
    private Button channel5;

    @FXML
    private Button channel6;
    
    @FXML
    private Label ruletheworld;
    
    @FXML
    private BorderPane rootpane;
    
    @FXML
    private HBox hboxbar;
    
    @FXML
    private GridPane profilechoosepane;
    
    @FXML
    private ImageView profileimageview;
    
    @FXML
    private Label usernameLabel;
    
    @FXML
    void uploadnewprofileimage(MouseEvent event) { 
    	profilechoosepane.setVisible(true);
    	
    }

    @FXML
    void AnonymousClicked(MouseEvent event) {
    	String path = new File("support/images/Anonymous.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		storeImage("Anonymous.png");
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void CoinClicked(MouseEvent event) {
    	String path = new File("support/images/Coins.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		storeImage("Coins.png");
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void FortuneTellerClicked(MouseEvent event) {
    	String path = new File("support/images/FortuneTeller.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		storeImage("FortuneTeller.png");
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void GhostClicked(MouseEvent event) {
    	String path = new File("support/images/Ghost.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		storeImage("Ghost.png");
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void WitchClicked(MouseEvent event) {
    	String path = new File("support/images/Witch.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		storeImage("Witch.png");
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }
    
    @FXML
    void alienClicked(MouseEvent event) {
    	//get the alien image
    	String path = new File("support/images/Alien.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
		storeImage("Alien.png"); //save image name to database
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }
    
	
	public void initialize(URL location, ResourceBundle rb) {
		System.out.println("player screen");
		playerScreen();
	}
	
	public void playerScreen() {
		profilechoosepane.setVisible(false);
		getUserdata();
	}
	

	public void getUserdata(){

		// Attach a listener to read the data at our posts reference
		//specifically query this user's name
		ref.addValueEventListener(new ValueEventListener() {

		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {	
		    	currentUser = dataSnapshot.getValue(User.class);     
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
	    		ref.removeEventListener(this);
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		        System.out.println("The read failed: " + databaseError.getCode());
		    }
		});
	}// end getUserdata
	
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
	
	
	@FXML
    void channel1_Action(MouseEvent event) {
		System.out.println("im in action 1 ");
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Parent root = null;
				try {
					root = FXMLLoader.load(getClass().getResource("gameTableFX.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setMaximized(true);
				stage.setResizable(false);
				
		        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		        //set Stage boundaries to visible bounds of the main screen
		        stage.setX(primaryScreenBounds.getMinX());
		        stage.setY(primaryScreenBounds.getMinY());
		        stage.setWidth(primaryScreenBounds.getWidth());
		        stage.setHeight(primaryScreenBounds.getHeight());
				
				scene.getStylesheets().add("DesignFX.css");
				stage.setScene(scene);
				stage.getIcons().add(new Image("file:appicon.png"));
				stage.setTitle("Illuminati Deluxe");
				stage.show();				
			}
			
		});
		
    }

}  











    
    
    
    