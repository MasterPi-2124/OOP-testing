package GDF;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ExportSceneController {
    private Stage stage;

    public void savetoImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Files");
        fileChooser.setInitialFileName("graph-" + LocalTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        try {
            File file = fileChooser.showSaveDialog(stage);
        } catch (Exception e) {

        }
    }

    public void savetoGPH(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Files");
        fileChooser.setInitialFileName("graph-" + LocalTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Graph Path Finder Files", "*.gph"));

        try {
            File file = fileChooser.showSaveDialog(stage);
        } catch (Exception e) {

        }
    }
}
