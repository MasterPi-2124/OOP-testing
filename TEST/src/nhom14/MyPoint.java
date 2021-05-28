package nhom14;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class MyPoint{
	private Ellipse2D.Float el = new Ellipse2D.Float();
	private Point p = new Point();

	final int r = 15;

	public void drawIndex(Graphics2D g, int index, Color colorIndex) { 
		g.setColor(colorIndex);
		Font currentFont = g.getFont();
		Font newFont = g.getFont().deriveFont(g.getFont().getSize() * 1.5F);
		g.setFont(newFont);
		int stringLen = (int) g.getFontMetrics()
				.getStringBounds(String.valueOf(index), g).getWidth();
		int stringHeight = (int) g.getFontMetrics()
				.getStringBounds(String.valueOf(index), g).getHeight();
		int startX = -stringLen / 2;
		int startY = stringHeight / 2;
		g.drawString(String.valueOf(index), startX + (int) p.x, (int) p.y
				+ startY);
		g.setFont(currentFont);
	}

	public void drawCost(Graphics2D g, String cost, Color colorCostResult) {
		g.setColor(colorCostResult);
		Font currentFont = g.getFont();
		Font newFont = g.getFont().deriveFont(g.getFont().getSize() * 1.5F);
		g.setFont(newFont);
		g.drawString(String.valueOf(cost), (int) p.x - r / 5, (int) p.y - r);
		g.setFont(currentFont);
	}

	public void drawPoint(Graphics2D g, int index, Color colorPoint) {
		//g.setColor(new Color((int)(Math.random() * 0x1000000)));
		g.setColor(Color.black);
		Ellipse2D.Float neww = new Ellipse2D.Float((int) el.getCenterX() - 19, (int) el.getCenterY() - 19, 38, 38);
		g.fill(neww);
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
