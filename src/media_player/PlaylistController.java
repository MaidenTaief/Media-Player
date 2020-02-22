package media_player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static media_player.Media_Player.mainc;

/**
 * FXML Controller class
 *
 * @author Minhaz Minar
 */
public class PlaylistController implements Initializable {

    @FXML
    ListView<String> pl;
    List<File> selectedfiles;
    int slc = 0, n = -1, flag = 0;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void selectButton(ActionEvent event) throws IOException {

        FileChooser fc = new FileChooser();
        fc.setTitle("select media files");

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp4 file", "*.mp4"),
                new FileChooser.ExtensionFilter("mp3 file", "*.mp3"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        selectedfiles = fc.showOpenMultipleDialog(null);
        if (selectedfiles != null) {
            for (int i = 0; i < selectedfiles.size(); i++) {

                pl.getItems().add(selectedfiles.get(i).getName());

            }

        }
    }

    @FXML
    public void playButton(ActionEvent event) throws MalformedURLException, InterruptedException, IOException {

        slc = pl.getSelectionModel().getSelectedIndex();
        n = slc;
        mainc.playPlaylist(selectedfiles.get(slc).toURI().toURL().toString());

    }

    public void nextP() throws MalformedURLException {

        
        if (n < selectedfiles.size()-1) {
            n++;
            
            mainc.playPlaylist(selectedfiles.get(n).toURI().toURL().toString());
            pl.getSelectionModel().selectIndices(n);
        }
    }

    public void prevP() throws MalformedURLException {

        if (n > 0) {
            n--;

            mainc.playPlaylist(selectedfiles.get(n).toURI().toURL().toString());
            pl.getSelectionModel().selectIndices(n);
        }
    }
}
