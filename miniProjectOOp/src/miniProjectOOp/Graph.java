package miniProjectOOp;

import java.util.ArrayList;
import java.util.Stack;

public class Graph extends Thread{

	private int numberPoint;
	private ArrayList<Integer> listPoint;
	private int matrix[][];
	public boolean[] visit;
	private int[] back;
	public ArrayList<Integer> listVisit;
	private ArrayList<ArrayList> listPath;
	Stack<Integer> stack = new Stack<Integer>();

	public void DFS1(int i) {
		visit = new boolean[numberPoint];
		visit[i] = false;
		stack.push(i);
		while (!stack.empty()) {
			i = stack.pop();
			listVisit.add(i);
			visit[i] = true;
			stack.removeElement(i);
			for (int j = numberPoint - 1; j >= 0; j--) {
				if (matrix[i][j] > 0) {
					if (!visit[j]) {
						back[j] = i;
						stack.push(j);
					}
				}
			}
		}
	}
	 // thuật toán dfs step by step với GUI gui là frame, int i là điểm bắt đầu
	public void stepDFS1(GUI gui, int i) throws InterruptedException { 
		currentThread().sleep(1000); // dừng lại 1s
		visit = new boolean[numberPoint]; 
		visit[i] = false; 	// khởi tạo các đỉnh chưa được thăm
		stack.push(i);	// thêm đỉnh đầu tiên vào stack
		while (!stack.empty()) { 	// lặp đến khi stack rỗng
			i = stack.pop();	// lấy đỉnh trên cùng stack
			listVisit.add(i);	// thêm đỉnh này vào danh sách các đỉnh đã thăm
			gui.setPath2();		// vẽ lại đồ thị với đỉnh vừa thăm
			gui.graphicsPanel.repaint(); // vẽ lại đồ thị cho chắc chắn
			gui.pack();			// vẽ lại frame cho chắc chắn
			currentThread().sleep(1000); 	// dừng 1s
			visit[i] = true;	// đánh dấu đỉnh trên đã được thăm
			stack.removeElement(i);	// xóa đỉnh trên khỏi stack
			for (int j = numberPoint - 1; j >= 0; j--) { 	// xét các đỉnh trong đồ thị
				if (!visit[j] && matrix[i][j] > 0) {	// nếu đỉnh tiếp theo chưa được thăm và có cạnh nối với đỉnh vừa rồi
					back[j] = i; // đánh dấu đỉnh i là cha của đỉnh j
					stack.push(j); 	// đưa đỉnh j vào stakc
				}
			}
		}
	}
	
	public void DFS(int i) {
		visit[i] = true;
		listVisit.add(i);
		for (int j = 0; j < listPoint.size(); j++) {
			if (matrix[i][j] > 0) {
				if (!visit[j]) {
					back[j] = i;
					DFS(j);
				}
			}
		}
	}

	public void BFS(int i) {
		ArrayList<Integer> queue = new ArrayList<Integer>();
		visit[i] = true;
		queue.add(i);
		while (queue.size() > 0) {
			i = queue.get(0);
			queue.remove(0);
			listVisit.add(i);
			for (int j = 0; j < listPoint.size(); j++) {
				if (matrix[i][j] > 0) {
					if (!visit[j]) {
						visit[j] = true;
						back[j] = i;
						queue.add(j);
					}
				}
			}
		}
	}

	// --------- init value --------- //
	public void initPath() {
		listPath = new ArrayList<ArrayList>();
	}
	public void initValue() {
		initListPoint();
		initListVisit();
		initMatrix();
	}

	private void initListPoint() {
		listPoint = new ArrayList<Integer>();
		for (int i = 0; i < numberPoint; i++) {
			listPoint.add(i);
		}
	}

	public void initVisit() {
		visit = new boolean[numberPoint];
		back = new int[numberPoint];
	}

	public void initListVisit() {
		listVisit = new ArrayList<Integer>();
		stack = new Stack<Integer>();
	}

	private void initMatrix() {
		matrix = new int[numberPoint][numberPoint];
	}

	public String toStringListPoint(int type) {
		String string = "";
		String temp = ", ";
		ArrayList<Integer> list = listPoint;
		if (type == 1) {
			temp = " -> ";
			list = listVisit;
		}
		for (int i = 0; i < list.size(); i++) {
			string += list.get(i) + 1;
			if (i < list.size() - 1) {
				string += temp;
			}
		}
		return string;
	}

	public void showMatrix() {
		for (int i = 0; i < numberPoint; i++) {
			for (int j = 0; j < numberPoint; j++) {
				System.out.print(String.format("%3d", matrix[i][j]));
			}
			System.out.println();
		}
	}

	// --------- getter - setter --------- //
	
	public int getNumberPoint() {
		return numberPoint;
	}

	public ArrayList<ArrayList> getListPath() {
		return listPath;
	}

	public void setNumberPoint(int numberPoint) {
		this.numberPoint = numberPoint;
	}

	public ArrayList<Integer> getListPoint() {
		return listPoint;
	}

	public void setListPoint(ArrayList<Integer> listPoint) {
		this.listPoint = listPoint;
	}

	public ArrayList<Integer> getListVisit() {
		return listVisit;
	}
	public void setListPath(ArrayList<ArrayList> listPath) {
		this.listPath = listPath;
	}
	public String getStringPath() {
		String string = "Path: ";
		for (int i = 0; i < getListVisit().size(); i++) {
			string += (getListVisit().get(i) + 1) + (i == getListVisit().size() - 1 ? "" : " -> ");
		}
		return string;
	}

	public void setListVisit(ArrayList<Integer> listVisit) {
		this.listVisit = listVisit;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public boolean[] getVisit() {
		return visit;
	}

	public void setVisit(boolean[] visit) {
		this.visit = visit;
	}

	public int[] getBack() {
		return back;
	}

	public void setBack(int[] back) {
		this.back = back;
	}
}
