package Picture;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import static javax.imageio.ImageIO.write;

public class DisplayGraphics extends Canvas {
    public void paint(Graphics g) {
//        g.drawString("Hello", 40, 40);
        setBackground(Color.WHITE);
//        g.fillRect(130, 30, 100, 80);
//        g.drawOval(30, 130, 50, 60);
//        g.fillOval(130, 130, 50, 60);
//        g.drawArc(30, 200, 40, 50, 90, 60);
//        g.fillArc(30, 130, 40, 50, 180, 40);
        setForeground(Color.black);
        g.drawString("ahgdga",10,10);
        g.setColor(Color.orange);
        g.drawOval(20,31,30,30);
    }

    private void write(Graphics g, String png, File f) {
        g.drawString("nguyen",10,10);
        g.setColor(Color.orange);
        g.drawOval(20,31,30,30);
    }

    public static void main(String[] args) {
        DisplayGraphics m = new DisplayGraphics();
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(400, 300);
        f.setVisible(true);
        File k = null;
    }
}
