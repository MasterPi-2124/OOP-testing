package nhom14;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainSceneController implements Initializable {
    @FXML
    private HBox parent;
    @FXML
    private VBox side_bar;

    boolean isExpanded = false;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    public void toDraw(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Draw.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);;
        stage.show();
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
