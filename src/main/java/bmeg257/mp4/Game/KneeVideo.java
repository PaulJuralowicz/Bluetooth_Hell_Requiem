package main.java.bmeg257.mp4.Game;


import java.io.File;
import java.util.concurrent.Delayed;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class KneeVideo extends Application{
    MediaPlayer player;
    MediaView viewer;
    StackPane root;
    public static void playVideo(String[] args) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        File f = new File("local/demo.mp4");


        //Converts media to string URL
        Media media = new Media(f.toURI().toURL().toString());
        player = new MediaPlayer(media);
        viewer = new MediaView(player);

        if(viewer == null) {
            viewer = new MediaView(player);
        }
        viewer.setMediaPlayer(player);

        //change width and height to fit video

        DoubleProperty width = viewer.fitWidthProperty();
        DoubleProperty height = viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
        viewer.setPreserveRatio(true);


        root = new StackPane();
        root.getChildren().add(viewer);

        //set the Scene
        Scene scenes = new Scene(root,500, 500, Color.BLACK);
        stage.setScene(scenes);
        stage.setTitle("preview");
        stage.setFullScreen(false);
        stage.show();
        player.play();
        PauseTransition delay = new PauseTransition(Duration.seconds(50));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }

    public void playHipEx() throws Exception{

    }
}