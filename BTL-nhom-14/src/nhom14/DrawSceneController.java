package nhom14;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import javax.swing.*;

public class DrawSceneController extends OutputStream implements Initializable {

    @FXML
    private Stage stage;

    @FXML
    private VBox property_bar;

    @FXML
    private Pane displayPane;

    @FXML
    private ToggleButton addVertex,
                         addEdge,
                         Movable,
                         run,
                         pause;

    @FXML
    private CheckBox isUndirected;

    @FXML
    private ChoiceBox<Integer> startPoint;
    
    @FXML
    private ChoiceBox<String> Algorithm;

    @FXML
    private TableView<Integer> adjacentMatrix;

    @FXML
    private TableColumn<Integer, Integer> vertexColumn;

    @FXML
    private TextArea log;

    @FXML TextArea path;

    private Graph graph;
    private DFS_BFS algo;
    private AllPath allpath;
    
    private boolean isHidden = false,
                    canAddVertex = false,
                    canAddEdge = false,
                    isMovable = false;
    int id = 1;
    Vertex v1,v2;

    //Configuration for Menu bar
    public void toMain(MouseEvent event) throws IOException {
        System.out.println("Redirecting to Main ...");
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
            System.out.println("Opening " + file.getName() + " ...");
            load(file.getName());

        } catch (Exception e) {
            System.out.println("The file can not be opened due to errors.");
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
            System.out.println("Toggle: addVertex = true");
        } else {
            addVertex.setSelected(false);
            canAddVertex = false;
            System.out.println("Toggle: addVertex = false");
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
            System.out.println("Toggle: addEdge = true");
        } else {
            addEdge.setSelected(false);
            canAddEdge = false;
            System.out.println("Toggle: addEdge = false");
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
            System.out.println("Toggle: Movable = true");
        } else {
            Movable.setSelected(false);
            isMovable = false;
            System.out.println("Toggle: Movable = false");
        }
    }

    public void hidePropertyBar(MouseEvent event) {
        if (isHidden) {
            property_bar.setOpacity(1.0);
            isHidden = false;
            System.out.println("Toggle: hiddenProperty = false");
        } else {
            property_bar.setOpacity(0.0);
            isHidden = true;
            System.out.println("Toggle: hiddenProperty = true");
        }
    }

    public void delete(MouseEvent event) {
        while(displayPane.getChildren().size() != 0)
            displayPane.getChildren().remove(0);
        graph = null;
        System.out.println("Deleted. Starting new Scene....\n");
    }

    // Configuration for right bar
    public void toUndirected() {
        if (isUndirected.isSelected()) {
            for (Edge e : graph.getEdgeList()) {
                e.setHeadVisible(false);
            }
            System.out.println("Changed to Directed Graph.");
        } else {
            for (Edge e : graph.getEdgeList()) {
                e.setHeadVisible(true);
            }
            System.out.println("Changed to Undirected Graph.");
        }
    }

    // Algorithm controller
    public void run() {
        if (Algorithm.getValue().equals("Breadth First Search")) {
            if (startPoint.getValue() != null) {
                System.out.println("Running BFS from point " + startPoint.getValue() + " ...\n");
                try {
                    run.setSelected(true);
                    allpath.setStartVertex(startPoint.getValue());
                    allpath.setEndVertex(4);
                    allpath.initialize();
                    allpath.TRY(1);
                    //algo.runBFS(startPoint.getValue());
                } catch (Exception e) {
                    System.out.println("Running BFS failed: " + e.getMessage());
                    run.setSelected(false);
                }
            } else if (startPoint.getValue() == null) {
                System.out.println("Running BFS failed because no start point is chosen.");
                run.setSelected(false);
            }
        } else if (Algorithm.getValue().equals("Depth First Search")) {
            if (startPoint.getValue() != null) {
                System.out.println("Running DFS from point " + startPoint.getValue() + " ...\n");
                try {
                    pause.setSelected(false);
                    algo.runDFS(startPoint.getValue());
                } catch (Exception e) {
                    System.out.println("Running DFS failed: " + e.getMessage());
                    run.setSelected(false);
                }
            } else if (startPoint.getValue() == null) {
                System.out.println("Running DFS failed because no start point is chosen.");
                run.setSelected(false);
            }
        }
    }

    public void runStep(MouseEvent event) {

    }

    public void stop(MouseEvent event) {

    }

    public void pause(MouseEvent event) {

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

        System.out.println("Point " + v.getID() + "(" + event.getX() + "; " + event.getY() + ") is created!");
        startPoint.getItems().add(v.getID());
        algo = new DFS_BFS(graph);
        allpath = new AllPath(graph);
        
        return v.GetShape();
    }

    public Node addVertex(double x, double y) {
        Vertex v = new Vertex(graph.numberVertex(), x, y);
        graph.addVertex(v);
        v.GetShape().setOnMouseDragged(e -> onVertexDragged(e, v));
        v.GetShape().setOnMouseClicked(e -> onVertexClicked(e, v));

        System.out.println("Point " + v.getID() + "(" + x + "; " + y + ") is created!");
        startPoint.getItems().add(v.getID());
        algo = new DFS_BFS(graph);
        allpath = new AllPath(graph);
        
        return v.GetShape();
    }

    public void onVertexClicked(MouseEvent event, Vertex v) {
        if (canAddEdge) {
            if (id == 1) {
                v1 = v;
                id = 2;
            } else if (id == 2) {
                boolean check = true;
                v2 = v;
                if (v2 != v1) {
                    for (Vertex i : v1.getAdjacentVertex()) {
                        if (i.getID() == v2.getID()) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        graph.createEdge(v1, v2);
                        id = 1;
                        System.out.println("Edge from " + v1.getID() + " to " + v2.getID() + " is created!");
                        algo = new DFS_BFS(graph);
                        allpath = new AllPath(graph);
                    } else {
                        System.out.println("Edge from " + v1.getID() + " to " + v2.getID() + " is already created!");
                    }
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


    // Export to Log Text Area
    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                appendText(text);
            }
        });
    }

    @Override
    public void write(int arg0) throws IOException {

    }

    public void appendText(String str) {
        Platform.runLater(() -> log.appendText(str));
    }

    //Initialization code
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OutputStream output = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b,off,len));
            }

            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(output, true));
        Algorithm.getItems().add("Depth First Search");
        Algorithm.getItems().add("Breadth First Search");
        Algorithm.setValue("Depth First Search");
        System.out.println("Start drawing. See Help for more information.");
    }
}