import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginmenuController {

    @FXML
    private AnchorPane rootloginpane;
	
    @FXML
    private AnchorPane signinrightpane;

    @FXML
    private Button playBtn;

    @FXML
    private AnchorPane signinleftpane;

    @FXML
    void playBtn_action(ActionEvent event) {
    	BorderPane pane = null;
    	try {
    		pane = FXMLLoader.load(getClass().getResource("playerScreenFX.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//override the previouse scene content with current border pane's content
    	rootloginpane.getChildren().setAll(pane); 
    }

}
