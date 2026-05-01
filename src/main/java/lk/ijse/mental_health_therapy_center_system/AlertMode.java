package lk.ijse.mental_health_therapy_center_system;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AlertMode {
    private static Notifications base(String title, String msg) {
        return Notifications.create()
                .title(title)
                .text(msg)
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(3))
                .darkStyle();
    }

    public static void error(String msg) {
        Notifications n = base("Error", msg);
        n.getStyleClass().add("notification-bar");
        n.getStyleClass().add("error");
        n.darkStyle();
        n.showError();
    }

    public static void warning(String msg) {
        Notifications n = base("Warning", msg);
        n.getStyleClass().add("notification-bar");
        n.getStyleClass().add("warning");
        n.showWarning();
    }

    public static void info(String msg) {
        Notifications n = base("Info", msg);
        n.getStyleClass().add("notification-bar");
        n.getStyleClass().add("info");
        n.showInformation();
    }
}
