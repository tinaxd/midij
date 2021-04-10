package work.tinax.midij;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import work.tinax.midij.gui.EventList;
import work.tinax.midij.gui.PianoRoll;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
    	var editor = new SplitPane();
    	var el = new EventList();
    	var pr = new PianoRoll(300, 300);
    	editor.getItems().addAll(el, pr);
    	editor.setDividerPositions(0.5f);
        var scene = new Scene(editor, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}