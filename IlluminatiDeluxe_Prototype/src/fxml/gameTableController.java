package fxml;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				if (inGameChatTextField.getText().equals("")){
					//do nothing if textfield is empty
				} else{
			    	Label textLabel = new Label(inGameChatTextField.getText());
			    	textLabel.setMinWidth(flowPaneInScroll.getWidth());
			    	textLabel.setMaxWidth(flowPaneInScroll.getWidth());
			    	textLabel.setWrapText(true);	//multi line text
			    	flowPaneInScroll.getChildren().addAll(textLabel); //addAll allow to add multiple Nodes
			    	inGameChatTextField.setText("");	
			    	
			    	slowScrollToBottom(chatScrollPane);
				}
			
			}	
		});
    }

    @FXML
    void inGameChatSend_ACTION(MouseEvent event) {	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (inGameChatTextField.getText().equals("")){
					//do nothing if textfield is empty
				} else{
			    	Label textLabel = new Label(inGameChatTextField.getText());
			    	textLabel.setMinWidth(flowPaneInScroll.getWidth());
			    	textLabel.setMaxWidth(flowPaneInScroll.getWidth());
			    	textLabel.setWrapText(true);	//multi line text
			    	flowPaneInScroll.getChildren().addAll(textLabel); //addAll allow to add multiple Nodes
			    	inGameChatTextField.setText("");
			    	slowScrollToBottom(chatScrollPane);

				}	
			}	
		});
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    
		
//		Platform.runLater(new Runnable() {
//			@Override
//			public void run() {
//				
//			}	
//		});

	}

	//animation to scroll chat history to the bottom
	static void slowScrollToBottom(ScrollPane scrollPane) {
	    Animation animation = new Timeline(
	        new KeyFrame(Duration.seconds(2),
	            new KeyValue(scrollPane.vvalueProperty(), 1)));
	    animation.play();
	}
	
	
}
