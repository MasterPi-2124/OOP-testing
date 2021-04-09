package Picture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static javax.swing.text.StyleConstants.setBackground;

public class buffedimage extends BufferedImage {

    public buffedimage(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    public void paint(Graphics g) {
        Image img = createImageWithText();
        g.drawImage(img,30,30,null);
    }



    private  Image createImageWithText() {
        BufferedImage bufferedImage = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        g.drawString("www.tutorialspoint.com", 20,20);
        g.drawString("www.tutorialspoint.com", 20,40);
        g.drawString("www.tutorialspoint.com", 20,60);
        g.drawString("www.tutorialspoint.com", 20,80);
        g.drawString("www.tutorialspoint.com", 20,100);

        return bufferedImage;
    }

    public static void main(String[] args) {
        BufferedImage img = new buffedimage(1800,1800,BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.drawString("1",37,34);
        g.drawOval(30,20,20,20);
        g.setColor(Color.RED);
        g.drawLine(33,23,145,123);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,3000,3000);

        File f = null;
        try
        {
            f = new File("nocolor.png");
            ImageIO.write(img,"png",f);
        }
        catch( IOException e)
        {
            System.out.println(e);
        }
    }
}
