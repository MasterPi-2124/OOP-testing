package GDF;

import java.util.ArrayList;

public class AllPath {
    public AllPath(Graph graph) {
        super();
        this.graph = graph;
    }

    private Graph graph;
    private boolean[] visited;
    private ArrayList<Integer> tempPath; // luu tam thoi
    private ArrayList<ArrayList<Integer>> Path = new ArrayList<>(); //luu tat ca duong di
    private int startVertex, endVertex;
    private StringBuffer path = new StringBuffer("");

    public ArrayList<ArrayList<Integer>> getPath() {
        return Path;
    }

    public void initialize() {
        visited = new boolean[graph.numberVertex()];
        tempPath = new ArrayList<>();
        for(int i = 0; i<graph.numberVertex(); i++)
            visited[i] = false;
        visited[startVertex] = true;
        tempPath.add(startVertex);
    }

    public void setPath(ArrayList<ArrayList<Integer>> path) {
        Path = path;
    }

    public void PathFound() {
        if(tempPath.size() > 1 && tempPath.get(tempPath.size() - 1) == endVertex) {
            Path.add(tempPath);
            path.append("[").append(Path.size()).append("]: ");
            for (Integer i : tempPath) {
                path.append(i).append(" -> ");
            }
            path.delete(path.length() - 4, path.length());
            path.append("\n");
        }
    }

    public String getPathLog() {
        return String.valueOf(path);
    }

    public void setPathLog(String str) {
        this.path = new StringBuffer(str);
    }

    // duyet ta ca duong di
    public void TRY(int soCanh) {
        if (tempPath.get(soCanh-1) == endVertex || soCanh > graph.getEdgeList().size()) {
            PathFound();
        } else {
            if (graph.getVertex(tempPath.get(soCanh-1)).getAdjacentVertex().size() == 0) {
                PathFound();
            } else {
                for (Vertex v : graph.getVertex(tempPath.get(soCanh - 1)).getAdjacentVertex()) {
                    if (visited[v.getID()] == false) {
                        visited[v.getID()] = true;
                        tempPath.add(v.getID());
                        TRY(soCanh + 1);
                        visited[v.getID()] = false;
                        tempPath.remove(tempPath.size() - 1);
                    }
                }
            }
        }
    }

    public int getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(int startVertex) {
        this.startVertex = startVertex;
    }

    public int getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(int endVertex) {
        this.endVertex = endVertex;
    }


}
