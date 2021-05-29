package nhom14;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DFS_BFS {
    private Graph graph;
    private boolean visited[];
    private Timeline visualizer;
    private boolean isRunning = false;

    public DFS_BFS(Graph g){
        graph = g;
    }

    public Timeline getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(Timeline visualizer) {
        this.visualizer = visualizer;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void runBFS(int startNode){
        isRunning = true;
        Queue<Integer> queue = new LinkedList<>();
        visited = new boolean[graph.numberVertex()];

        graph.setDisable(true);

        queue.add(startNode);
        queue.add(startNode);
        visited[startNode] = true;
        graph.getVertex(startNode).GetShape().setTextFill(Color.ORANGE);

        KeyFrame bfsKeyFrame = new KeyFrame(Duration.seconds(1), e->{
            if(queue.size()>1){
                //graph.getVertex(queue.poll()).GetShape().setStrokeWidth(0);
                int currentNode = queue.peek();
                graph.getVertex(currentNode).GetShape().setTextFill(Color.DEEPPINK);
                //graph.getVertex(currentNode).getShape().setStrokeWidth(5);

                for(Vertex v: graph.getAdjacentVertices(currentNode)){
                    if(!visited[v.getID()]){
                        visited[v.getID()] = true;
                        queue.add(v.getID());
                        v.getShape().setFill(Color.ORANGE);
                    }
                }
            }
            else{
                stop();
                isRunning = false;
                //graph.getVertex(queue.poll()).getShape().setStrokeWidth(0);
            }
        });
        visualizer = new Timeline(bfsKeyFrame);
        visualizer.setCycleCount(Animation.INDEFINITE);
        visualizer.setAutoReverse(false);
        visualizer.play();

    }

    public void runDFS(int startNode){
        isRunning = true;
        Stack<Integer> stack = new Stack<>();
        visited = new boolean[graph.numberVertex()];

        graph.setDisable(true);
        stack.push(startNode);

        KeyFrame dfsKeyFrame = new KeyFrame(Duration.seconds(1), e->{
            if(!stack.isEmpty()){
                int currentNode = stack.peek();
                visited[currentNode] = true;
                
                graph.getVertex(currentNode).GetShape().setTextFill(Color.DEEPPINK);
                //graph.getVertex(currentNode).GetShape();

                if(stack.size() > 1){
                    graph.getVertex(stack.get(stack.size()-2)).GetShape();
                }

                int i;
                for(i=0;i < graph.getAdjacentVertices(currentNode).size();i++){
                    Vertex v = graph.getAdjacentVertices(currentNode).get(i);
                    if(!visited[v.getID()]){
                        stack.push(v.getID());
                        graph.getVertex(currentNode).GetShape().setTextFill(Color.DARKORANGE);
                        break;
                    }
                }
                if(i == graph.getAdjacentVertices(currentNode).size()){
                    int node = stack.pop();
                    graph.getVertex(node).GetShape().setTextFill(Color.GREY);
                    //graph.getVertex(node).getShape().setStrokeWidth(0);
                }
            }
            else{
                stop();

            }
        });
        visualizer = new Timeline(dfsKeyFrame);
        visualizer.setCycleCount(Animation.INDEFINITE);

        visualizer.play();
    }

    void play(){
        if(!isRunning)
        {
            isRunning = true;
            visualizer.play();
        }
    }

    void stop(){
        if(isRunning){
            graph.setDisable(false);
            graph.resetVertexsColor();
            visualizer.stop();
            isRunning = false;
        }
    }

    void pause(){
        if(isRunning){
            isRunning = false;
            visualizer.pause();
        }
    }
}
