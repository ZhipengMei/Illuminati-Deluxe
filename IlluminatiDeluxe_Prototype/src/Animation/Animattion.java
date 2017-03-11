package Animation;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animattion {

    public static RotateTransition createAnimation(ImageView imageView) {
    	RotateTransition animation = new RotateTransition(Duration.seconds(1), imageView);
        animation.setByAngle(180);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
        return animation;
    }
    
    /**
     *  Apply the Fade Transition to the node
     * @param e the node to which apply transition
     */
    public static void fadeTransition(Node e){
        FadeTransition x=new FadeTransition(new Duration(2000),e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }
    
}
