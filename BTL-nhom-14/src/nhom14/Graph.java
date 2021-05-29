package nhom14;

import java.util.ArrayList;
import javafx.scene.layout.Pane;

public class Graph {

    // Attributes
    private Pane displayPane;
    private ArrayList<Vertex> vertexList;
    private ArrayList<ArrayList<Integer>> EdgeList = new ArrayList<>();
    private ArrayList<Edge> edgeList = new ArrayList<>();

    // constructor
    public Graph(Pane displayPane, int n) {
        this.displayPane = displayPane;
        vertexList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            vertexList.add(new Vertex(i));
        }
        EdgeList = new ArrayList<>() ;
    }

    public Graph(Pane displayPane) {
        this(displayPane,0);
    }

    public int numberVertex() {
        return this.vertexList.size();
    }

    public Vertex getVertex(int i) {
        return vertexList.get(i);
    }

    public ArrayList<Vertex> getVertexList() {
        return vertexList;
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    void createEdge(Vertex u, Vertex v) {
        Edge line = new Edge(u.getLayoutX(), u.getLayoutY(), v.getLayoutX(), v.getLayoutY());
        line.x1Property().bind(u.GetShape().layoutXProperty());
        line.y1Property().bind(u.GetShape().layoutYProperty());
        line.x2Property().bind(v.GetShape().layoutXProperty());
        line.y2Property().bind(v.GetShape().layoutYProperty());

        //u.addAjacentVertex(v);
        ArrayList<Integer> e = new ArrayList<>();
        e.add(u.getID());
        EdgeList.add(e);
        edgeList.add(line);

        displayPane.getChildren().add(line);
    }

    void createEdge(int u, int v) {
        Vertex t1 = null,
               t2 = null;
        for (Vertex i : vertexList) {
            if (i.getID() == u) {
                t1 = i;
            } else if (i.getID() == v) {
                t2 = i;
            }
        }
        createEdge(t1,t2);
    }

    public void addVertex(Vertex v) {
        vertexList.add(v);
    }

    public void addVertex(double x, double y) {
        vertexList.add(new Vertex(numberVertex(), x, y));
    }

    public ArrayList<Vertex> getAdjacentVertexs(int i){
        return vertexList.get(i).getAdjacentNode();
    }

    @Override
    public String toString() {
        String str = "";
        for(int i=0; i<vertexList.size(); i++) {
            str += i + ": [";
            for(Vertex v: vertexList.get(i).getAdjacentNode()) {
                str += v + "  ";
            }
            str += "]\n";
        }
        return str;
    }

    public ArrayList<Integer> runDFS(int s){
        boolean[] visited = new boolean[numberVertex()];
        ArrayList<Integer> dfsTraversal = new ArrayList<>();
        dfs(dfsTraversal, s, visited);
        return dfsTraversal;

    }

    private void dfs(ArrayList<Integer> dfsTraversal, int s, boolean[] visited) {
        dfsTraversal.add(s);
        visited[s] = true;
        for(Vertex v: getAdjacentVertexs(s)) {
            if(!visited[v.getID()]) {
                dfs(dfsTraversal, v.getID(), visited);
            }
        }
    }

    public void setDisable(boolean status) {
        displayPane.setDisable(status);
    }
/*
    public ArrayList<ArrayList<Integer>> getEdgeList() {
        return this.EdgeList;
    }
*/
}
