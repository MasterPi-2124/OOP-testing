package nhom14;

import java.util.ArrayList;

public class AllPath {
	public AllPath(Graph graph) {
		super();
		this.graph = graph;
	}

	private Graph graph;
    private boolean[] visited;
    //private ArrayList<Integer> Last;
	private ArrayList<Integer> tempPath; // luu tam thoi
    private ArrayList<ArrayList<Integer>> Path = new ArrayList<>(); //luu tat ca duong di
	private int startVertex, endVertex;
    
    
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
		 Path.add(tempPath);
		 for(Integer i: tempPath) {
			 System.out.print(i+"->");
		 }
		 System.out.print("Done");
		 System.out.println();
	}
	
	// duyet ta ca duong di
	public void TRY(int soCanh) {
		if(tempPath.get(soCanh-1) == endVertex) {
			PathFound();
		}else {
			for(Vertex v : graph.getVertex(tempPath.get(soCanh-1)).getAdjacentVertex()){
				 if(visited[v.getID()] == false) {
					 visited[v.getID()] = true;
					 tempPath.add(v.getID());
					 TRY(soCanh+1);
					 visited[v.getID()] = false;
					 tempPath.remove(tempPath.size()-1);
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
