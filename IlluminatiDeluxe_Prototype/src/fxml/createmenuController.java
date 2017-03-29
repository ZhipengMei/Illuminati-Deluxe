package fxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

import Firebase.AccessFirebase;

public class createmenuController {
	
    @FXML
    private AnchorPane rootpanecreatemenu;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordfield;

    @FXML
    private TextField passwordfieldconfirm;

    @FXML
    void backbtn_ACTION(MouseEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				backtoLoginMenu();
			}	
		});
    }

    @FXML
    void createbutton_ACTION(MouseEvent event) {
    	System.out.println(passwordfield.getText());
    	System.out.println(passwordfieldconfirm.getText());

    	if (passwordfield.getText().equals(passwordfieldconfirm.getText())){
    		AccessFirebase.createUser(usernameField.getText().toLowerCase(), passwordfieldconfirm.getText());
    		
    		availableAlert();
    	} else {
    		passwordAlert();
    	}

    }
    
    public void availableAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Hi");
    	alert.setHeaderText("Username is available");
    	alert.setContentText("Your account is being set up right now. You may sign in afterward.");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    // ... user chose OK
    		backtoLoginMenu();
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    	}
    }
    
    public void passwordAlert(){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert");
    	alert.setHeaderText("Password incorrect");
    	alert.setContentText("Passwords do not match.");
    	alert.showAndWait();
    }
    
    public void backtoLoginMenu(){
    	AnchorPane pane = null;
    	try {
    		pane = FXMLLoader.load(getClass().getResource("loginmenu.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//override the previouse scene content with current border pane's content
    	rootpanecreatemenu.getChildren().setAll(pane); 
    }

}
