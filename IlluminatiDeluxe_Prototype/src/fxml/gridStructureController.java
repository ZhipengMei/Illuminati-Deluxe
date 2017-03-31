package fxml;

import com.sun.glass.events.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class gridStructureController {

    @FXML
    private GridPane playerGrid;

    @FXML
    void randomClick(MouseEvent event) {

    }

    @FXML
    void randomClicked(MouseEvent event) {
    	System.out.println("Test");
    }

}
