package lk.ijse.mental_health_therapy_center_system.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NavigationUtil {

    private static final String BASE =
            "/lk/ijse/mental_health_therapy_center_system/view/";

    public static void navigate(MouseEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationUtil.class.getResource(BASE + fxmlFile));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.centerOnScreen();
            newStage.setOpacity(0);
            newStage.show();

            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO,
                    new KeyValue(newStage.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.millis(300), new KeyValue(newStage.opacityProperty(), 1.0))
            );
            fadeIn.play();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Timeline fadeOut = new Timeline(
                    new KeyFrame(Duration.ZERO,
                    new KeyValue(currentStage.opacityProperty(), 1.0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(currentStage.opacityProperty(), 0.0))
            );
            fadeOut.setOnFinished(e -> currentStage.close());
            fadeOut.play();

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }
}