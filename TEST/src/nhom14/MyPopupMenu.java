package nhom14;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPopupMenu;

public class MyPopupMenu extends JPopupMenu {

	private Point point;

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public MyPopupMenu() {
		super();
	}

	public void show(Component invoker, int x, int y) {
		point = new Point(x, y);
		super.show(invoker, x, y);
	}
}
