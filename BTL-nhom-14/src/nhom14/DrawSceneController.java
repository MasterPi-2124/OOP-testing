package nhom14;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DrawSceneController {
    @FXML
    private Stage stage;

    @FXML
    private VBox property_bar;

    @FXML
    private Pane displayPane;

    @FXML
    private ToggleButton addVertex,
                         addEdge,
                         Movable;

    @FXML
    private CheckBox isUndirected;

    @FXML
    private ChoiceBox vertex;

    @FXML
    private TableView<Integer> adjacentMatrix;

    @FXML
    private TableColumn<Integer, Integer> vertexColumn;

    private Graph  graph;
    
    DFS_BFS algo = new DFS_BFS(graph);

    private boolean isHidden = false,
                    canAddVertex = false,
                    canAddEdge = false,
                    isMovable = false;
    Edge edge;
    int id = 1;
    Vertex v1,v2;

    //Configuration for Menu bar
    public void toMain(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Files");
        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Text Files", "*txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Graph Path Finder Files", "*.gph"),
                new FileChooser.ExtensionFilter("All Files","*gph", "*.png", "*.jpg", "*.gif", "*.txt")
        );
        try {
            File file = fileChooser.showOpenDialog(stage);
            load(file.getName());
        } catch (Exception e) {
            System.out.println("errror.");
        }
    }

    void load(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        int n = scan.nextInt();

        graph = new Graph(displayPane);

        for(int i = 0;i < n; i++) {
            double x = scan.nextDouble();
            double y = scan.nextDouble();
            displayPane.getChildren().add(addVertex(x, y));
        }
        int m = scan.nextInt();
        for(int i = 0; i < m; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            graph.createEdge(u,v);
        }
    }

    public void toSave() throws IOException {
        if (graph.numberVertex() > 0) {
            Stage popup_stage = new Stage();
            popup_stage.initModality(Modality.APPLICATION_MODAL);
            popup_stage.setTitle("Save as");
            Parent root = FXMLLoader.load(getClass().getResource("SaveAs.fxml"));
            Scene scene = new Scene(root, 800, 500);
            popup_stage.setScene(scene);
            popup_stage.show();
        }

    }

    //Configuration for left bar
    public void toggleAddVertex() {
        if (!canAddVertex) {
            addVertex.setSelected(true);
            canAddVertex = true;
            addEdge.setSelected(false);
            canAddEdge = false;
            Movable.setSelected(false);
            isMovable = false;
        } else {
            addVertex.setSelected(false);
            canAddVertex = false;
        }
    }

    public void toggleAddEdge() {
        if (!canAddEdge) {
            addVertex.setSelected(false);
            canAddVertex = false;
            addEdge.setSelected(true);
            canAddEdge = true;
            Movable.setSelected(true);
            isMovable = false;
        } else {
            addEdge.setSelected(false);
            canAddEdge = false;
        }
    }

    public void toggleMovable() {
        if (!isMovable) {
            addVertex.setSelected(false);
            canAddVertex = false;
            addEdge.setSelected(false);
            canAddEdge = false;
            Movable.setSelected(true);
            isMovable = true;
        } else {
            Movable.setSelected(false);
            isMovable = false;
        }
    }

    public void hidePropertyBar(MouseEvent event) {
        if (isHidden) {
            property_bar.setOpacity(1.0);
            isHidden = false;
        } else {
            property_bar.setOpacity(0.0);
            isHidden = true;
        }
    }

    public void delete(MouseEvent event) {
        while(displayPane.getChildren().size() != 0)
            displayPane.getChildren().remove(0);
        graph = null;
    }

    // Configuration for right bar
    public void toUndirected() {
        if (isUndirected.isSelected()) {
            for (Edge e : graph.getEdgeList()) {
                e.setHeadVisible(false);
            }
        } else {
            for (Edge e : graph.getEdgeList()) {
                e.setHeadVisible(true);
            }
        }
    }

    public void d() {

    }

    // Mouse Listener
    public void onGraphPressed(MouseEvent event) {
        if (canAddVertex && event.isPrimaryButtonDown()) {
            if (graph == null) graph = new Graph(displayPane);
            displayPane.getChildren().add(addVertex(event));
        }
    }

    public Node addVertex(MouseEvent event) {
            Vertex v = new Vertex(graph.numberVertex(), event.getX(), event.getY());
            graph.addVertex(v);
            v.GetShape().setOnMouseDragged(e -> onVertexDragged(e, v));
            v.GetShape().setOnMouseClicked(e -> onVertexClicked(e, v));
            return v.GetShape();
    }

    public Node addVertex(double x, double y) {
        Vertex v = new Vertex(graph.numberVertex(), x, y);
        graph.addVertex(v);
        System.out.println(graph.toString());
        v.GetShape().setOnMouseDragged(e -> onVertexDragged(e, v));
        v.GetShape().setOnMouseClicked(e -> onVertexClicked(e, v));
        return v.GetShape();
    }

    public void onVertexClicked(MouseEvent event, Vertex v) {
        if (canAddEdge) {
            if (id == 1) {
                v1 = v;
                id = 2;
                System.out.println(v1.getID());
            } else if (id == 2) {
                v2 = v;
                System.out.println(v2.getID());
                if (v2 != v1) {
                    graph.createEdge(v1, v2);
                    id = 1;
                }
            }
        }
    }

    public void onVertexDragged(MouseEvent event, Vertex v) {
        if (event.isPrimaryButtonDown() && !canAddEdge) {
            v.GetShape().setLayoutX(v.GetShape().getLayoutX() + event.getX() + v.GetShape().getTranslateX());
            v.GetShape().setLayoutY(v.GetShape().getLayoutY() + event.getY() + v.GetShape().getTranslateY());
        }
    }

}