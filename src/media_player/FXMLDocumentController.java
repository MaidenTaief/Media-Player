package media_player;

import com.sun.javafx.image.impl.IntArgb;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static media_player.FplayListController.PlaylistC;

/**
 *
 * @author Minhaz Minar
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public MediaView mv;
    @FXML
    public MediaPlayer mp, mpA;
    @FXML
    public Media media, mediaA;
    @FXML
    public Slider vs, ts;
    @FXML
    double i = 1;
    @FXML
    public Button play, muteB;
    @FXML
    public MenuItem open, recent, PlayList, about;
    File file;
    @FXML
    public Label time, total;
    static int flag = 0, flag1 = 0;
    public ObservableList<File> list = FXCollections.observableArrayList();
    File fa;
    IntegerProperty a = new SimpleIntegerProperty();
    

    int j = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DoubleProperty width = mv.fitWidthProperty();
        DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        a.set(j);
    }

    @FXML
    private void menuOpen(ActionEvent event) throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select a media file");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp4 file", "*.mp4"),
                new FileChooser.ExtensionFilter("mp3 file", "*.mp3"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );

        file = fileChooser.showOpenDialog(null);
//        String extension=file.getAbsolutePath().split("\\.",2)[1];

        if (mp != null && file != null) {

            mp.stop();
        }
        if (file != null) {
            try {
                media = new Media(file.toURI().toURL().toExternalForm());

                mp = new MediaPlayer(media);
                if (file.getAbsolutePath().endsWith("mp3")) {

                    fa = new File("src/audio_visual/Entering The Stronghold - Audio Visual Animation HD! - YouTube.MP4").getParentFile();
                    list.addAll(fa.listFiles());
                    mediaA = new Media(list.get(a.get()).getAbsoluteFile().toURI().toURL().toString());
                    mpA = new MediaPlayer(mediaA);
                    mv.setMediaPlayer(mpA);
                    mpA.play();
                    mpA.setMute(true);
                    flag1 = 1;

                    a.addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            try {
                                mediaA = new Media(list.get(a.get()).getAbsoluteFile().toURI().toURL().toString());
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            mpA = new MediaPlayer(mediaA);
                            mv.setMediaPlayer(mpA);
                            mpA.play();
                            mpA.setMute(true);
                            flag1 = 1;
                        }
                    });
                } else {
                    flag1 = 0;
                    mv.setMediaPlayer(mp);
                }

                flag = 1;
                mp.play();

                vs.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {

                        mp.setVolume(vs.getValue() / 100);
                    }
                });

                mp.currentTimeProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {

                        if (flag1 == 1) {
                            Status status1 = mp.getStatus();
                            Status status2 = mpA.getStatus();
                            if (status1 == Status.PLAYING) {
                                if (mpA.getCurrentTime().greaterThanOrEqualTo(mpA.getTotalDuration())) {
                                    if (a.get() < list.size() - 1) {
                                        a.set(++j);
                                    } else {
                                        j = 0;
                                        a.set(j);
                                    }
                                }
                                if (mp.getCurrentTime().greaterThanOrEqualTo(mp.getTotalDuration()) && status2 == Status.PLAYING) {
                                    mpA.stop();
                                }
                            }
                        }

                        ts.setValue(mp.getCurrentTime().toMillis() / mp.getTotalDuration().toMillis() * 100);

                        if (mp.getTotalDuration().toMinutes() >= mp.getCurrentTime().toMinutes()) {
                            time.setText(String.format("%.2f", mp.getCurrentTime().toMinutes()));
                        }
                        total.setText(toString().format("%.2f", mp.getTotalDuration().toMinutes()));

                        ts.valueProperty().addListener(new InvalidationListener() {
                            @Override
                            public void invalidated(Observable observable) {
                                if (ts.isPressed()) {
                                    double v = ts.getValue() / 100;
                                    mp.seek(mp.getMedia().getDuration().multiply(v));

                                }

                            }
                        });
                    }

                });

            } catch (Exception e) {

            }

        }

    }

    @FXML
    private void playpauseButton(ActionEvent event) {
        Status status = mp.getStatus();
        if (status == Status.PLAYING) {
            if (mp.getCurrentTime().greaterThanOrEqualTo(mp.getTotalDuration())) {

                mp.seek(mp.getStartTime());
                mp.play();

                if (flag1 == 1) {
                    mpA.seek(mpA.getStartTime());
                    mpA.play();
                }

            } else {
                if (flag1 == 1) {
                    mpA.pause();
                }

                mp.pause();

            }
        } else if (status == Status.PAUSED || status == Status.HALTED || status == Status.STOPPED) {
            mp.play();
            if (flag1 == 1) {
                mpA.play();
            }

        }
    }

    @FXML
    private void stopButton(ActionEvent event) {
        mp.stop();
        if (flag1 == 1) {
            mpA.stop();
        }

    }

    @FXML
    private void slowButton(ActionEvent event) {
        if (i > .5) {
            i = i - .5;
        }
        mp.setRate(i);
    }

    @FXML
    private void fastButton(ActionEvent event) {
        i = i + .5;
        mp.setRate(i);
    }

    @FXML
    private void playList(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FplayList.fxml"));

        Scene scene = new Scene(root, 300, 300);
        stage.setTitle("Playlist");

        stage.setScene(scene);

        stage.show();

    }

    @FXML
    private void closeMenu(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void nextB(ActionEvent event) throws MalformedURLException {
        if (flag == 0) {
            PlaylistC.nextP();
        } else {
            mp.seek(mp.getStartTime());
        }

    }

    @FXML
    private void previousB(ActionEvent event) throws MalformedURLException {
        if (flag == 0) {
            PlaylistC.prevP();
        } else {
            mp.seek(mp.getStartTime());
        }
    }

    public void playPlaylist(String s) {

        if (mp != null) {
            mp.stop();
        }
        if (s != null) {
            try {
                media = new Media(s);
                mp = new MediaPlayer(media);
                if (s.endsWith("mp3")){
                
                    fa = new File("src/audio_visual/Entering The Stronghold - Audio Visual Animation HD! - YouTube.MP4").getParentFile();
                    list.addAll(fa.listFiles());
                    mediaA = new Media(list.get(a.get()).getAbsoluteFile().toURI().toURL().toString());
                    mpA = new MediaPlayer(mediaA);
                    mv.setMediaPlayer(mpA);
                    mpA.play();
                    mpA.setMute(true);
                    flag1 = 1;

                    a.addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            try {
                                mediaA = new Media(list.get(a.get()).getAbsoluteFile().toURI().toURL().toString());
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            mpA = new MediaPlayer(mediaA);
                            mv.setMediaPlayer(mpA);
                            mpA.play();
                            mpA.setMute(true);
                            flag1 = 1;
                        }
                    });
                }else {
                    flag1 = 0;
                    mv.setMediaPlayer(mp);
                }
                
               
                flag = 0;
                mp.play();

                vs.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {

                        mp.setVolume(vs.getValue() / 100);
                    }
                });

                mp.currentTimeProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {

                        if (flag1 == 1) {
                            Status status1 = mp.getStatus();
                            Status status2 = mpA.getStatus();
                            if (status1 == Status.PLAYING) {
                                if (mpA.getCurrentTime().greaterThanOrEqualTo(mpA.getTotalDuration())) {
                                    if (a.get() < list.size() - 1) {
                                        a.set(++j);
                                    } else {
                                        j = 0;
                                        a.set(j);
                                    }
                                }
                                if (mp.getCurrentTime().greaterThanOrEqualTo(mp.getTotalDuration()) && status2 == Status.PLAYING) {
                                    mpA.stop();
                                }
                            }
                        }
                        
                        if (ts.getValue() >= 99.5) {

                            try {
                                PlaylistC.nextP();

                            } catch (MalformedURLException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        
                        ts.setValue(mp.getCurrentTime().toMillis() / mp.getTotalDuration().toMillis() * 100);

                         if (mp.getTotalDuration().toMinutes() >= mp.getCurrentTime().toMinutes()) {
                            time.setText(String.format("%.2f", mp.getCurrentTime().toMinutes()));
                        }
                        total.setText(toString().format("%.2f", mp.getTotalDuration().toMinutes()));

                        ts.valueProperty().addListener(new InvalidationListener() {

                            @Override
                            public void invalidated(Observable observable) {

                                if (ts.isPressed()) {
                                    double v = ts.getValue() / 100;
                                    mp.seek(mp.getMedia().getDuration().multiply(v));

                                }

                            }

                        });
                    }

                });

            } catch (Exception e) {
                System.out.println("error in 412");
            }

        }

    }

    @FXML
    private void about(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("About TZMplayer");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    private void audioVisual(ActionEvent event) {

        if(flag1==1)if (a.get() < list.size() - 1) {
            a.set(++j);
        } else {
            j = 0;
            a.set(j);
        }

    }

    @FXML
    private void muteB(ActionEvent event) {
        if (!mp.isMute()) {
            mp.setMute(true);

        } else {
            mp.setMute(false);

        }

    }
}
