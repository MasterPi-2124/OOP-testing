package miniProjectOOp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;


public class MyPoint {

	private final Color colorIndex = Color.black;
	private final Color color[] = { Color.white, Color.yellow, Color.green,
			Color.red };

	private final int r = 15;
	private Ellipse2D.Double l;
	private Point center = new Point();
	private int type = 0;
	private int indexPoint;

	public MyPoint(double x, double y, int indexPoint) {
		l = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);
		this.center.x = (int) (x);
		this.center.y = (int) (y);
		this.indexPoint = indexPoint;
		System.out.println(l.x + " * " + l.y);
	}

	public void draw(Graphics2D g) {
		g.setColor(color[type]);
		g.fill(l);

		g.setColor(colorIndex);
		int stringLen = (int) g.getFontMetrics()
				.getStringBounds(String.valueOf(indexPoint), g).getWidth();
		int stringHeight = (int) g.getFontMetrics()
				.getStringBounds(String.valueOf(indexPoint), g).getHeight();
		int startX = -stringLen / 2;
		int startY = stringHeight / 2;
		g.drawString(String.valueOf(indexPoint), startX + (int) center.x,
				(int) center.y + startY);
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	
	private static final long serialVersionUID = 1L;
	private Ellipse2D.Float el = new Ellipse2D.Float();
	private Point p = new Point();
	public void drawIndex(Graphics2D g, int index, Color colorIndex) {
		g.setColor(colorIndex);
		int stringLen = (int) g.getFontMetrics()
				.getStringBounds(String.valueOf(index), g).getWidth();
		int stringHeight = (int) g.getFontMetrics()
				.getStringBounds(String.valueOf(index), g).getHeight();
		int startX = -stringLen / 2;
		int startY = stringHeight / 2;
		g.drawString(String.valueOf(index), startX + (int) p.x, (int) p.y
				+ startY);
	}

	public void drawCost(Graphics2D g, String cost, Color colorCostResult) {
		g.setColor(colorCostResult);
		g.drawString(String.valueOf(cost), (int) p.x - r / 5, (int) p.y - r);
	}

	public void drawPoint(Graphics2D g, int index, Color colorPoint) {
		g.setColor(colorPoint);
		g.fill(el);
	}

	public void drawIndexCost(Graphics2D g, int index, String cost,
			Color colorIndex, Color colorCostResult) {
		drawIndex(g, index, colorIndex);
		drawCost(g, cost, colorCostResult);
	}

	public void drawResult(Graphics2D g, int index, Color colorPoint,
			Color colorIndex, String cost, Color colorCostResult) {
		drawPoint(g, index, colorPoint);
		drawIndex(g, index, colorIndex);
		drawCost(g, cost, colorCostResult);
	}

	public void draw(Graphics2D g, int index, Color colorPoint, Color colorIndex) {
		drawPoint(g, index, colorPoint);
		drawIndex(g, index, colorIndex);
	}

	public Ellipse2D.Float getEl() {
		return el;
	}

	public void setEl(Ellipse2D.Float el) {
		this.el = el;
		this.p.x = (int) (el.x + r);
		this.p.y = (int) (el.y + r);
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public MyPoint(Ellipse2D.Float el) {
		super();
		setEl(el);
	}

}
