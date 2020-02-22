package media_player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Media_Player extends Application {
    static FXMLDocumentController mainc;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("FXMLDocument.fxml").openStream());
        mainc = (FXMLDocumentController)loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setTitle("TZMplayer version: 1.0");
        
        stage.setScene(scene);
       
        stage.show();
    }

        public static void main(String[] args) {
        launch(args);
    }
    
}
