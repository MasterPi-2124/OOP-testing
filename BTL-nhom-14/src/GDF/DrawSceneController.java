package GDF;

import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
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
                         run;

    @FXML
    private CheckBox isUndirected;

    @FXML
    private ChoiceBox<Integer> startPoint,
                               endPoint;

    @FXML
    private ChoiceBox<String> Algorithm;

    @FXML
    private TableView<Integer> adjacentMatrix;

    @FXML
    private TableColumn<Integer, Integer> vertexColumn;

    @FXML
    private TextArea log;

    @FXML
    private HBox delete;

    @FXML
    private ListView<String> pathList;

    private AllPath allPath;
    private Graph directedGraph,
                  undirectedGraph;
    private DFS_BFS algo;
    private boolean isHidden = false,
                    canAddVertex = false,
                    canAddEdge = false,
                    isMovable = false,
                    isRunning = false,
                    isPaused = false;
    int id = 1;
    Vertex v1,v2;
    String path;

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
                new FileChooser.ExtensionFilter("Graph Path Finder Files", "*.gph"),
                new FileChooser.ExtensionFilter("All Files","*gph", "*.txt")
        );
        try {
            File file = fileChooser.showOpenDialog(stage);
            System.out.println("Opening " + file.getName() + " ...");
            load(file.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("The file can not be opened due to errors.");
            e.printStackTrace();
        }
    }

    public void load(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        int n = scan.nextInt();

        delete();
        directedGraph = new Graph(displayPane);

        for(int i = 0;i < n; i++) {
            double x = scan.nextDouble();
            double y = scan.nextDouble();
            displayPane.getChildren().add(addVertex(x, y));
        }
        int m = scan.nextInt();
        for(int i = 0; i < m; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            directedGraph.createEdge(u,v);
        }
    }

    public void toPNG() throws IOException {
        if (directedGraph != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Files");
            fileChooser.setInitialFileName("graph-" + LocalTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss")) + ".png");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.bmp", "*.jpg", "*.gif"));

            File file = fileChooser.showSaveDialog(stage);

            WritableImage writableImage = new WritableImage((int) displayPane.getWidth(), (int) displayPane.getHeight());
            displayPane.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
            System.out.println("Exported to PNG successfully!\nDirectory: " + file.getAbsolutePath());
        } else {
            System.out.println("Ngu loz a, ko co gi ma doi di xuat anh???");
        }
    }

    public void toGPH() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Files");
        fileChooser.setInitialFileName("graph-" + LocalTime.now().format(DateTimeFormatter.ofPattern("hh-mm-ss")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Graph Path Finder Files", "*.gph"));

        File file = fileChooser.showSaveDialog(stage);
        // CODE HERE
    }

    public void toHelpAndAbout() throws IOException {
        Stage popup_stage = new Stage();
        popup_stage.initModality(Modality.APPLICATION_MODAL);
        popup_stage.setTitle("Save as");
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        Scene scene = new Scene(root, 1000, 600);
        popup_stage.setScene(scene);
        popup_stage.show();
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
            Movable.setSelected(false);
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

    public void hidePropertyBar() {
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

    public void delete() {
        while(displayPane.getChildren().size() != 0)
            displayPane.getChildren().remove(0);
        directedGraph = null;
        while (startPoint.getItems().size() > 0) {
            startPoint.getItems().remove(0);
            endPoint.getItems().remove(0);
        }
        v1 = null;
        id = 1;
        v2 = null;
        while (pathList.getItems().size() > 0) {
            pathList.getItems().remove(0);
        }
        System.out.println("Deleted. Starting new Scene....\n");
    }

    // Configuration for right bar
    public void toUndirected() {
        if (isUndirected.isSelected()) {
            for (Edge e : directedGraph.getEdgeList()) {
                e.setHeadVisible(false);
            }
            System.out.println("Changed to Directed Graph.");
        } else {
            for (Edge e : directedGraph.getEdgeList()) {
                e.setHeadVisible(true);
            }
            System.out.println("Changed to Undirected Graph.");
        }
    }

    // Algorithm controller
    public void setOption() {
        Algorithm.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Algorithm.setValue(Algorithm.getItems().get(newValue.intValue()));
                if (Algorithm.getValue().equals("Find All Paths")) {
                    endPoint.setDisable(false);
                } else {
                    endPoint.setDisable(true);
                }
            }
        });
    }

    public void showPath() {
        directedGraph.resetVerticesColor();
        pathList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        pathList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                path = newValue;
            }
        });
        System.out.println(path);
        String[] temp = path.split(" -> ");
        if(path != null) {
            for (int i = 1; i < temp.length - 1; i++) {
                directedGraph.getVertex(Integer.parseInt(temp[i])).GetShape().setStyle("-fx-background-color: #ff6ebe; -fx-text-fill: white;");
            }
            directedGraph.getVertex(Integer.parseInt(temp[0])).GetShape().setStyle("-fx-background-color: #63ff8e; -fx-text-fill: white;");
            directedGraph.getVertex(Integer.parseInt(temp[temp.length - 1])).GetShape().setStyle("-fx-background-color: red; -fx-text-fill: white;");
        }
    }

    public void run() {
        if (isRunning) {
            run.setSelected(true);
        } else {
            if (Algorithm.getValue().equals("Breadth First Search")) {
                if (startPoint.getValue() != null) {
                    System.out.println("Running BFS from point " + startPoint.getValue() + " ...\n");
                    try {
                        isRunning = true;
                        run.setSelected(true);
                        Algorithm.setDisable(true);
                        startPoint.setDisable(true);
                        addEdge.setDisable(true);
                        addVertex.setDisable(true);
                        delete.setDisable(true);
                        Movable.setDisable(true);

                        algo.runBFS(startPoint.getValue());

                    } catch (Exception e) {
                        System.out.println("Running BFS failed: " + e.getMessage());
                        isRunning = false;
                        run.setSelected(false);
                        Algorithm.setDisable(false);
                        startPoint.setDisable(false);
                        addEdge.setDisable(false);
                        addVertex.setDisable(false);
                        delete.setDisable(false);
                    }
                } else if (startPoint.getValue() == null) {
                    System.out.println("Running BFS failed because no start point is chosen.");
                    run.setSelected(false);
                }
            } else if (Algorithm.getValue().equals("Depth First Search")) {
                if (startPoint.getValue() != null) {
                    System.out.println("Running DFS from point " + startPoint.getValue() + " ...\n");
                    try {
                        isRunning = true;
                        run.setSelected(true);
                        Algorithm.setDisable(true);
                        startPoint.setDisable(true);
                        addEdge.setDisable(true);
                        addVertex.setDisable(true);
                        delete.setDisable(true);

                        algo.runDFS(startPoint.getValue());

                    } catch (Exception e) {
                        System.out.println("Running DFS failed: " + e.getMessage());
                        isRunning = false;
                        run.setSelected(false);
                        Algorithm.setDisable(false);
                        startPoint.setDisable(false);
                        addEdge.setDisable(false);
                        addVertex.setDisable(false);
                        delete.setDisable(false);
                    }
                } else if (startPoint.getValue() == null) {
                    System.out.println("Running DFS failed because no start point is chosen.");
                    run.setSelected(false);
                }
            } else if (Algorithm.getValue().equals("Find All Paths")) {
                run.setSelected(false);
                if (startPoint.getValue() != null && endPoint.getValue() != null) {
                    System.out.println("Paths from point " + startPoint.getValue() + " to point " + endPoint.getValue() + ":");
                    allPath.setPathLog();
                    while (pathList.getItems().size() > 0) {
                        pathList.getItems().remove(0);
                    }
                    allPath.setPath(new ArrayList<>());
                    allPath.setStartVertex(startPoint.getValue());
                    allPath.setEndVertex(endPoint.getValue());
                    allPath.initialize();
                    allPath.TRY(1);
                    if (allPath.getPathLog().size() == 0) allPath.getPathLog().add("No paths are found.");
                    for (String i : allPath.getPathLog()) {
                        pathList.getItems().add(i);
                    }
                    System.out.println("Done.");
                } else {
                    System.out.println("Can not find paths because no point is chosen.");
                }
            }
        }
    }

    public void runStep() {
        if (isHidden) {
            displayPane.setOpacity(1);
            isHidden = false;
        } else {
            displayPane.setOpacity(0);
            isHidden = true;
        }
    }

    public void stop(MouseEvent event) {
        if (isRunning) {
            algo.stop();
            isRunning = false;
            run.setSelected(false);
            Algorithm.setDisable(false);
            startPoint.setDisable(false);
            addEdge.setDisable(false);
            addVertex.setDisable(false);
            delete.setDisable(false);
            Movable.setDisable(false);
        } else {
            System.out.println("con chua chay thi pause kieu loz gi?");
        }

    }

    // Mouse Listener
    public void onGraphPressed(MouseEvent event) {
        if (canAddVertex && event.isPrimaryButtonDown()) {
            if (directedGraph == null) directedGraph = new Graph(displayPane);
            displayPane.getChildren().add(addVertex(event));
        } else if (event.isSecondaryButtonDown()) {
            if (v1 != null) {
                v1 = null;
                id = 1;
            }
        }
    }

    public Node addVertex(MouseEvent event) {
        Vertex v = new Vertex(directedGraph.numberVertex(), event.getX(), event.getY());
        directedGraph.addVertex(v);
        v.GetShape().setOnMouseDragged(e -> onVertexDragged(e, v));
        v.GetShape().setOnMouseClicked(e -> onVertexClicked(e, v));

        System.out.println("Point " + v.getID() + "(" + event.getX() + "; " + event.getY() + ") is created!");
        startPoint.getItems().add(v.getID());
        endPoint.getItems().add(v.getID());
        algo = new DFS_BFS(directedGraph);
        allPath = new AllPath(directedGraph);
        return v.GetShape();
    }

    public Node addVertex(double x, double y) {
        Vertex v = new Vertex(directedGraph.numberVertex(), x, y);
        directedGraph.addVertex(v);
        v.GetShape().setOnMouseDragged(e -> onVertexDragged(e, v));
        v.GetShape().setOnMouseClicked(e -> onVertexClicked(e, v));

        System.out.println("Point " + v.getID() + "(" + x + "; " + y + ") is created!");
        startPoint.getItems().add(v.getID());
        algo = new DFS_BFS(directedGraph);
        allPath = new AllPath(directedGraph);
        return v.GetShape();
    }

    public void onVertexClicked(MouseEvent event, Vertex v) {
        if (canAddEdge) {
            if (id == 1) {
                v1 = v;
                id = 2;
                System.out.println("Point " + v1.getID() + " is clicked.");
            } else if (id == 2) {
                boolean check = true;
                v2 = v;
                System.out.println("Point " + v2.getID() + " is clicked.");
                if (v2 != v1) {
                    for (Vertex i : v1.getAdjacentVertices()) {
                        if (i.getID() == v2.getID()) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        directedGraph.createEdge(v1, v2);
                        id = 1;
                        algo = new DFS_BFS(directedGraph);
                        allPath = new AllPath(directedGraph);
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
        Algorithm.getItems().add("Find All Paths");
        Algorithm.setValue("Depth First Search");
        System.out.println("Start drawing. See Help for more information.");
        run.setSelected(false);
        endPoint.setDisable(true);
    }
}