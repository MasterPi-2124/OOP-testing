package nhom14;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainSceneController implements Initializable {
    @FXML
    private HBox parent;
    @FXML
    private VBox side_bar;

    boolean isExpanded = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void expand_sidebar(MouseEvent event) {
        if (isExpanded) {
            side_bar.setPrefWidth(60);
            isExpanded = false;
        } else {
            side_bar.setPrefWidth(200);
            isExpanded = true;
        }
    }
}
