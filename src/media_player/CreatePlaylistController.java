package media_player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author Minhaz Minar
 */
public class CreatePlaylistController implements Initializable {

    @FXML
    public TextField name;
    @FXML
    public TextField type;
    @FXML
    private Button create;


    File f;

    FileOutputStream o;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    public void createButton2(ActionEvent event) {

      
     f = new File("playlist_name.properties");
     Properties p = new Properties();
     
     p.setProperty("name", name.getText());
     p.setProperty("type", type.getText());
     
        try {
            o= new FileOutputStream(f);
            p.store(o, "name");
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        finally{
        
         try {
             o.close();
         } catch (IOException ex) {
         }
        }
    
    
       
      
       ((Node) event.getSource()).getScene().getWindow().hide(); 
       
       

        

    }
}
