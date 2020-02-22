package media_player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * FXML Controller class
 *
 * @author Minhaz Minar
 */
public class FplayListController implements Initializable {

    @FXML
    private Button Create;
    @FXML
    public ListView<String> PlayList_Name;

    File f;
    Properties p;
    FileInputStream fi;

    static PlaylistController PlaylistC;
    public ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        PlayList_Name.setItems(list);

    }

    @FXML
    public void createButton(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("CreatePlaylist.fxml").openStream());
        Scene scene = new Scene(root);
        stage.setTitle("Create");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);

        stage.showAndWait();
       

        f = new File("playlist_name.properties");
        p = new Properties();
        fi = new FileInputStream(f);
        p.load(fi);
        list.add(p.getProperty("name"));

        fi.close();
    }

    @FXML
    public void deleteButton(ActionEvent event) throws IOException {
         list.remove(PlayList_Name.getSelectionModel().getSelectedIndex());
    }
    @FXML
    public void openButton(ActionEvent event) throws IOException{
        
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("playlist.fxml").openStream());
        Scene scene = new Scene(root);
        PlaylistC = (PlaylistController)loader.getController();
        stage.setScene(scene);
        stage.setTitle(list.get(PlayList_Name.getSelectionModel().getSelectedIndex()));
        
        stage.show();
//        ((Node) event.getSource()).getScene().getWindow().hide(); 
    }
}
