package main.java.bmeg257.mp4.Game;


import java.io.File;

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

public class HipexVideo extends Application{

    public static void playVideo(String[] args) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        File f = new File("local/hipex.mp4");


        //Converts media to string URL
        Media media = new Media(f.toURI().toURL().toString());
        MediaPlayer player = new   MediaPlayer(media);
        MediaView viewer = new MediaView(player);

        if(viewer == null) {
            viewer = new MediaView(player);
        }
        viewer.setMediaPlayer(player);

        //change width and height to fit video
        /*
        DoubleProperty width = viewer.fitWidthProperty();
        DoubleProperty height = viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
        viewer.setPreserveRatio(true); */


        StackPane root = new StackPane();
        root.getChildren().add(viewer);

        //set the Scene
        Scene scenes = new Scene(root,1920, 1080, Color.BLACK);
        stage.setScene(scenes);
        stage.setTitle("preview");
        stage.setFullScreen(true);
        stage.show();
        player.play();
        PauseTransition delay = new PauseTransition(Duration.seconds(23));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }
}