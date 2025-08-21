package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private SimpleClient client;

    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        client = SimpleClient.getClient();
        client.openConnection();

        scene = new Scene(loadFXML("LogInPrim"), 600, 500);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        EventBus.getDefault().unregister(this);
        super.stop();
    }

    @Subscribe
    public void onWarningEvent(WarningEvent event) {
        System.out.println("happened in APP");
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText(String.format(
                    "Message: %s%nTimestamp: %s",
                    event.getWarning().getWarningText(),
                    event.getWarning().getIssuedAt().toString()
            ));
            alert.show();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
