package nhom14;

import java.util.ArrayList;
import javafx.scene.control.Button;

public class Vertex extends Button {
    private int ID;
    private Button shape;
    private ArrayList<Vertex> adjacentVertex;

    //constructor
    public Vertex(int n) {
        super();
        ID = n;
    }
    public Vertex(int n, double x, double y) {
        this.ID = n;
        this.shape = createVertex(x, y, n);
    }

    public Vertex(int n, Button shape) {
        this.ID = n;
        this.shape = shape;
        adjacentVertex = new ArrayList<>();
    }


    private Button createVertex(double x, double y, int n) {
        Button shape = new Button();
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.translateXProperty().bind(shape.widthProperty().divide(-2));
        shape.translateYProperty().bind(shape.heightProperty().divide(-2));
        shape.setPrefWidth(50);
        shape.setPrefHeight(50);
        shape.setText(String.valueOf(n));
        shape.getStyleClass().add("vertex");
        adjacentVertex = new ArrayList<>();
        return shape;
    }

    int getID() {
        return this.ID;
    }
    void setID(int iD) {
        this.ID = iD;
    }
    ArrayList<Vertex> getAdjacentVertex() {
        return adjacentVertex;
    }

    void setAdjacentNode(ArrayList<Vertex> adjacentVertex) {
        this.adjacentVertex = adjacentVertex;
    }

    void addAdjacentVertex(Vertex v) {
        adjacentVertex.add(v);
    }
    public Button GetShape() {
        return shape;
    }
}
