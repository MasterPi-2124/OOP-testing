package graph;

import java.io.*;

public class DFS {

    private static final String pathIn = "graph.inp";
    private static final String pathOut = "graph.out";
    private static int n, m, start, end;
    private static int[][] arrays;

    private static int[] back;
    private static PrintWriter printWriter;

    public static void DFS(int u) {
        int v;
        for (v = 0;  v < n; v++) {
            if(arrays[u][v] == 1 && back[v] == 0) {
                back[v] = u;
                DFS(v);
            }
        }

    }

    public static void main(String[] args) {
        ReadFile();
        for (int i = 0; i < n; i++){
            back[i] = 0;
        }
        back[start] = 1;
        DFS(start);

        try {
            printWriter = new PrintWriter(pathOut, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        printWriter.print(""+end);

        while (start != end) {
            end = back[end];
            printWriter.print("-"+end);
        }
        printWriter.close();

    }

    public static void ReadFile() {
        try {
            FileReader reader = new FileReader(pathIn);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String lineStr = bufferedReader.readLine();

            n = Integer.parseInt(lineStr.split(" ")[0]);
            m = Integer.parseInt(lineStr.split(" ")[1]);
            start = Integer.parseInt(lineStr.split(" ")[2]);
            end = Integer.parseInt(lineStr.split(" ")[3]);

            arrays = new int[n+1][n+1];
            back = new int[n+1];

            int u = 0, v = 0;
            for(int i = 0; i < m; i++) {
                lineStr = bufferedReader.readLine();
                u = Integer.parseInt(lineStr.split(" ")[0]);
                v = Integer.parseInt(lineStr.split(" ")[1]);
                arrays[u][v] = 1;
                arrays[v][u] = 1;
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}