package Platform;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JApplet;

/**
 * Created by Bravolly Pich.
 */
public class GameWindow extends JApplet implements Runnable {
    public Graphics2D g2;
    public Thread gThread;
    int width, height;
    public GameEvents gameEvents;
    public GameSound sound;

    @Override
    public void start() {

        gThread = new Thread(this);
        gThread.setPriority(Thread.MIN_PRIORITY);
        gThread.start();
    }

    @Override
    public void run() {

        Thread me = Thread.currentThread();
        while (gThread == me) {
            repaint();
            try {
                gThread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * @param name address to image location
     * @return Image of the file
     */
    public Image getSprite(String name) {
        URL url = GameWindow.class.getResource(name);
        Image img = java.awt.Toolkit.getDefaultToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
            System.out.println("Cannot get image file: " + e);
        }
        return img;
    }

    /**
     * @param img Image to be converted
     * @param pixelW width of sprite in pixels
     * @param n number of sprite images
     * @return BufferedImage[] array containing each sprite image
     */
    public BufferedImage[] imgToSprite(Image img, int pixelW, int n) {
        BufferedImage tBImg = convertToBuffered(img);
        BufferedImage[] tBImgAry = convertToSprite(tBImg, pixelW, n);
        return tBImgAry;
    }

    /**
     * @param img Image to be converted
     * @return BufferedImage version
     */
    public BufferedImage convertToBuffered(Image img) {
        int width = img.getWidth(this);
        int height = img.getHeight(this);

        BufferedImage bImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2temp = bImg.createGraphics();

        g2temp.drawImage(img, 0, 0, this);
        g2temp.dispose();

        return bImg;
    }

    /**
     * Converts a image strip into an array of sprite images
     * @param bImg BufferedImage to be converted
     * @param pixelW width of sprite in pixels
     * @param n number of sprite images
     * @return BufferedImage[] array containing each sprite image
     */
    public BufferedImage[] convertToSprite(BufferedImage bImg, int pixelW, int n) {
        BufferedImage[] conv = new BufferedImage[bImg.getTileWidth() / pixelW];
        int width = bImg.getWidth() / n;
        int height = bImg.getHeight();
        int divider = 0;

        for (int i = 0; i < n; i++) {
            conv[i] = bImg.getSubimage(divider, 0, width, height);
            divider += pixelW;
        }

        return conv;
    }

    /**
     * Converts a image strip plane into an array of sprite images
     * @param bImg BufferedImage to be converted
     * @param pixelW width of sprite in pixels
     * @param pixelH height of sprite in pixels
     * @param n number of sprite images per row
     * @return BufferedImage[] array containing each sprite image
     */
    public BufferedImage[] convertToSprite(BufferedImage bImg, int pixelW, int pixelH, int n) {
        BufferedImage[] conv = new BufferedImage[n * (bImg.getHeight() / pixelH)];
        //int width = bImg.getWidth() / n;
        //int height = bImg.getHeight() / pixelH;
        int divider = 0;
        int index = 0;

        for (int j = 0; j < bImg.getHeight(); j += pixelH) {

            for (int i = 0; i < n; i++) {

                conv[index] = bImg.getSubimage(divider, j, pixelW, pixelH);
                divider += pixelW;
                index++;
            }

            divider = 0;
        }

        return conv;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public int getWindowWidth() {
        return width;
    }

    public int getWindowHeight() {
        return height;
    }

    public GameEvents getGameEvents() {
        return this.gameEvents;
    }

    public class KeyControl extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            gameEvents.setValue(e);
        }
    }
}