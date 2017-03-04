import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

public class playerScreenController implements Initializable {

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
    void uploadnewprofileimage(MouseEvent event) { 
    	profilechoosepane.setVisible(true);
    	
    	//allow user to change profile image choose from folder
//    	 FileChooser fileChooser = new FileChooser();
//         
//         //Set extension filter
//         FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
//         FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
//         fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
//           
//         //Show open file dialog
//         File file = fileChooser.showOpenDialog(null);
//                    
//         try {
//             BufferedImage bufferedImage = ImageIO.read(file);
//             Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//             profileimageview.setImage(image);
//         } catch (IOException ex) {
//             Logger.getLogger(playerScreenController.class.getName()).log(Level.SEVERE, null, ex);
//         }
    }

    @FXML
    void AnonymousClicked(MouseEvent event) {
    	String path = new File("support/images/Anonymous.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void CoinClicked(MouseEvent event) {
    	String path = new File("support/images/Coins.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void FortuneTellerClicked(MouseEvent event) {
    	String path = new File("support/images/FortuneTeller.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void GhostClicked(MouseEvent event) {
    	String path = new File("support/images/Ghost.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }

    @FXML
    void WitchClicked(MouseEvent event) {
    	String path = new File("support/images/Witch.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }
    
    @FXML
    void alienClicked(MouseEvent event) {
    	//get the alien image
    	String path = new File("support/images/Alien.png").getAbsolutePath();
		Image image = new Image(new File(path).toURI().toString());
    	profileimageview.setImage(image);	//reassign image view with new image
    	profilechoosepane.setVisible(false); //dismiss profilechoosepane
    }
    
	public void initialize(URL location, ResourceBundle rb) {
		System.out.println("player screen");
		playerScreen();
	}
	
	public void playerScreen() {
//		profilechoosepane.managedProperty().bind(profilechoosepane.visibleProperty());
		profilechoosepane.setVisible(false);
	}

}
