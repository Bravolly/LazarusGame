package Lazarus_Game;

import Platform.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Bravolly Pich.
 */
public class LazarusWorld extends GameWindow {
    private BufferedImage bimg;
    private int w = 656, h = 520;

    private static HashMap<String, Image> sprites;

    private BufferedImage tempImg;
    private ArrayList<PlayerObj>  playerAry = new ArrayList<>();
    private ArrayList<BoxObj> boxAry = new ArrayList<>();
    private ArrayList<ButtonObj> stopAry = new ArrayList<>();
    private int level = 1;
    private boolean showTitle = true;

    public BufferedImage[] LazaSprtStand, LazaSprtAfraid, LazaSprtSquished, LazaSprtLeft, LazaSprtRight, LazaSprtJumpLeft,
            LazaSprtJumpRight, WallSprt, CardSprt, MetalSprt, StoneSprt, WoodSprt, StopSprt;
    public int[] keys = {KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
    public int speed = 5;
    public GameSound sfx;

    @Override
    public void init() {
        setWidth(w);
        setHeight(h);
        setFocusable(true);
        setBackground(Color.darkGray);

        sprites = new HashMap<String, Image>();

        try {
            sound = new GameSound(1, "/Lazarus_Game/Resource/Music.mid");
            sound.play();

            loadSprites();
            tempImg = convertToBuffered(sprites.get("LazaStand"));
            LazaSprtStand = convertToSprite(tempImg, 40, 1);
            tempImg = convertToBuffered(sprites.get("LazaAfraid"));
            LazaSprtAfraid = convertToSprite(tempImg, 40, 10);
            tempImg = convertToBuffered(sprites.get("LazaSquished"));
            LazaSprtSquished = convertToSprite(tempImg, 40, 11);
            tempImg = convertToBuffered(sprites.get("LazaLeft"));
            LazaSprtLeft = convertToSprite(tempImg, 80, 7);
            tempImg = convertToBuffered(sprites.get("LazaRight"));
            LazaSprtRight = convertToSprite(tempImg, 80, 7);
            tempImg = convertToBuffered(sprites.get("LazaJumpLeft"));
            LazaSprtJumpLeft = convertToSprite(tempImg, 80, 7);
            tempImg = convertToBuffered(sprites.get("LazaJumpRight"));
            LazaSprtJumpRight = convertToSprite(tempImg, 80, 7);
            tempImg = convertToBuffered(sprites.get("Wall"));
            WallSprt = convertToSprite(tempImg, 40, 1);
            tempImg = convertToBuffered(sprites.get("CardBox"));
            CardSprt = convertToSprite(tempImg, 40, 1);
            tempImg = convertToBuffered(sprites.get("MetalBox"));
            MetalSprt = convertToSprite(tempImg, 40, 1);
            tempImg = convertToBuffered(sprites.get("StoneBox"));
            StoneSprt = convertToSprite(tempImg, 40, 1);
            tempImg = convertToBuffered(sprites.get("WoodBox"));
            WoodSprt = convertToSprite(tempImg, 40, 1);
            tempImg = convertToBuffered(sprites.get("Button"));
            StopSprt = convertToSprite(tempImg, 40, 1);

            playerAry.add(new PlayerObj(LazaSprtStand,LazaSprtLeft,LazaSprtRight,LazaSprtJumpLeft,LazaSprtJumpRight,
                    LazaSprtSquished,LazaSprtAfraid,keys,speed));

            loadMap("/Lazarus_Game/Resource/MapStart.txt", 40, 40);

            randomBox();

            BufferedImage[][] BoxType = {CardSprt, WoodSprt, StoneSprt, MetalSprt};

            int rand = ThreadLocalRandom.current().nextInt(0, 4);
            boxAry.add(new BoxObj(BoxType[rand], playerAry.get(0).getX(), 0, speed, (char)(rand + 49), true));

            gameEvents = new GameEvents();
            gameEvents.addObserver(playerAry.get(0));
            KeyControl key = new KeyControl();
            addKeyListener(key);
        } catch (Exception e) {
            System.out.println("Error - Incorrect file name: " + e);
        }
    }

    /*Functions for loading image resources*/
    private void loadSprites() {

        sprites.put("title", getSprite("/Lazarus_Game/Resource/Title.png"));
        sprites.put("background", getSprite("/Lazarus_Game/Resource/Background.png"));
        sprites.put("LazaStand", getSprite("/Lazarus_Game/Resource/Lazarus_stand.png"));
        sprites.put("LazaAfraid", getSprite("/Lazarus_Game/Resource/Lazarus_afraid_strip10.png"));
        sprites.put("LazaLeft", getSprite("/Lazarus_Game/Resource/Lazarus_left_strip7.png"));
        sprites.put("LazaRight", getSprite("/Lazarus_Game/Resource/Lazarus_right_strip7.png"));
        sprites.put("LazaJumpLeft", getSprite("/Lazarus_Game/Resource/Lazarus_jump_left_strip7.png"));
        sprites.put("LazaJumpRight", getSprite("/Lazarus_Game/Resource/Lazarus_jump_right_strip7.png"));
        sprites.put("LazaSquished", getSprite("/Lazarus_Game/Resource/Lazarus_squished_strip11.png"));
        sprites.put("CardBox", getSprite("/Lazarus_Game/Resource/CardBox.png"));
        sprites.put("MetalBox", getSprite("/Lazarus_Game/Resource/MetalBox.png"));
        sprites.put("StoneBox", getSprite("/Lazarus_Game/Resource/StoneBox.png"));
        sprites.put("WoodBox", getSprite("/Lazarus_Game/Resource/WoodBox.png"));
        sprites.put("Wall", getSprite("/Lazarus_Game/Resource/Wall.png"));
        sprites.put("Button", getSprite("/Lazarus_Game/Resource/Button.png"));

    }

    private void isGameReset() {
        if (playerAry.get(0).getGameReset()) {
            level = 1;
            playerAry.get(0).setGameReset(false);
            playerAry.get(0).notDead();
            playerAry.get(0).setVisible(true);
            boxAry.clear();
            stopAry.clear();

            try {
                loadMap("/Lazarus_Game/Resource/MapStart.txt", 40, 40);
            } catch (Exception e) {
                System.out.println("Error - Incorrect file name: " + e);
            }

            randomBox();

            BufferedImage[][] BoxType = {CardSprt, WoodSprt, StoneSprt, MetalSprt};

            int rand = ThreadLocalRandom.current().nextInt(0, 4);
            boxAry.add(new BoxObj(BoxType[rand], playerAry.get(0).getX(), 0, speed, (char)(rand + 49), true));

        } else if (playerAry.get(0).getMapReset() && level > 1) {
            playerAry.get(0).setMapReset(false);
            playerAry.get(0).notDead();
            playerAry.get(0).setVisible(true);
            boxAry.clear();
            stopAry.clear();

            try {
                loadMap("/Lazarus_Game/Resource/lvl" + (level-1) + ".txt", 40, 40);
            } catch (Exception e) {
                System.out.println("Error - Incorrect file name: " + e);
            }

            randomBox();

            BufferedImage[][] BoxType = {CardSprt, WoodSprt, StoneSprt, MetalSprt};

            int rand = ThreadLocalRandom.current().nextInt(0, 4);
            boxAry.add(new BoxObj(BoxType[rand], playerAry.get(0).getX(), 0, speed, (char)(rand + 49), true));
        }
    }

    public void randomBox() {
        BufferedImage[][] BoxType = {CardSprt, WoodSprt, StoneSprt, MetalSprt};

        int rand = ThreadLocalRandom.current().nextInt(0, 4);
        boxAry.add(new BoxObj(BoxType[rand], 0, 440, speed, (char)(rand + 49), false)); //0,440
    }

    public void dropBox() {
        if (!playerAry.get(0).isDead()) {
            if (!boxAry.get(boxAry.size() - 2).isFalling()) {
                boxAry.get(boxAry.size() - 1).setXY(playerAry.get(0).getX() - playerAry.get(0).getX() % 40, 0);
                boxAry.get(boxAry.size() - 1).setFalling(true);
                randomBox();
            }
        }
    }

    public boolean checkCollision(GameObj obj1, GameObj obj2, char type) {

        if (obj1.box.intersects(obj2.box)) {/*
            System.out.println("obj1:\n" + obj1.getX() + " , " + obj1.getY());
            System.out.println("obj2:\n" + obj2.getX() + " , " + obj2.getY());*/
            obj1.setCollision(type);

            return true;
        }

        return false;
    }

    public void lazaStopCheck() {
        if (!playerAry.get(0).isDead()) {
            for (int i = 0; i < stopAry.size(); i++) {
                if (checkCollision(playerAry.get(0), stopAry.get(i), '~')) {
                    boxAry.clear();
                    stopAry.clear();

                    try {
                        loadMap("/Lazarus_Game/Resource/lvl" + level + ".txt", 40, 40);
                    } catch (Exception e) {
                        System.out.println("Error - Incorrect file name: " + e);
                    }

                    randomBox();

                    BufferedImage[][] BoxType = {CardSprt, WoodSprt, StoneSprt, MetalSprt};

                    int rand = ThreadLocalRandom.current().nextInt(0, 4);
                    boxAry.add(new BoxObj(BoxType[rand], playerAry.get(0).getX(), 0, speed, (char)(rand + 49), true));
                    level++;
                }
            }
        }
    }

    public void lazBoxCheck() {
        if (!playerAry.get(0).isDead()) {
            for (int i = 0; i < boxAry.size(); i++) {
                if (boxAry.get(i).getVisible()) {
                    if (checkCollision(playerAry.get(0), boxAry.get(i), '0')) {
                        playerAry.get(0).update();
                    }
                }
            }
        }
    }

    public void boxBoxCheck() {
        for (int i = 0; i < boxAry.size(); i++) {
            for (int j = 0; j < boxAry.size(); j++) {
                if (j != i && boxAry.get(j).getVisible()) {
                    if (boxAry.get(i).isFalling()) {
                        if (boxAry.get(j).getType() > boxAry.get(i).getType() || boxAry.get(j).getType() == '0' ||
                                boxAry.get(i).getType() == boxAry.get(j).getType()) {
                            if (checkCollision(boxAry.get(i), boxAry.get(j), '0')) {
                                sfx = new GameSound(2, "/Lazarus_Game/Resource/Wall.wav");
                                sfx.play();
                                boxAry.get(i).update();

                                if (showTitle) {
                                    showTitle = false;
                                }
                            }
                        } else if (checkCollision(boxAry.get(i), boxAry.get(j), '~')){
                            sfx = new GameSound(2, "/Lazarus_Game/Resource/Crush.wav");
                            sfx.play();
                            boxAry.get(j).setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void boxLazaCheck() {
        if (!playerAry.get(0).isDead()) {
            for (int i = 0; i < boxAry.size(); i++) {
                if (boxAry.get(i).isFalling() && !playerAry.get(0).isDead()) {
                    if (checkCollision(playerAry.get(0), boxAry.get(i), '1')) {
                        sfx = new GameSound(2, "/Lazarus_Game/Resource/Squished.wav");
                        sfx.play();
                        playerAry.get(0).update();
                    }
                }
            }
        }
    }

    public void loadMap(String fileName, int tileX, int tileY) throws Exception {
        int x = 0;
        int y = 0;

        try {
            InputStream in = getClass().getResourceAsStream(fileName);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
            int b = bReader.read();

            while (b > -1) {

                while (b != '\n') {

                    switch (b) {
                        case ' ':
                            x += tileX;
                            break;
                        case 's':
                            stopAry.add(new ButtonObj(StopSprt, x, y));
                            x += tileX;
                            break;
                        case 'x':
                            boxAry.add(new BoxObj(WallSprt, x, y, speed, '0', false));
                            x += tileX;
                            break;
                        case 'z':
                            playerAry.get(0).setXY(x,y);
                            x += tileX;
                            break;
                        default:
                            break;
                    }

                    b = bReader.read();
                }

                b = bReader.read();
                x = 0;
                y += tileY;

                if (b == ' ') {
                    b = bReader.read();
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading from map: " + e);
        }
    }

    public void drawWall() {
        for (int i = 0; i < boxAry.size(); i++) {
            boxAry.get(i).draw(g2, this);
        }
    }

    public void drawStop() {
        for (int i = 0; i < stopAry.size(); i++) {
            stopAry.get(i).draw(g2, this);
        }
    }

    public void drawBG(){
        g2.drawImage(sprites.get("background"), 0, 0, this);
    }

    public void winGame() {
        g2.drawImage(sprites.get("title"), 70, 90, this);
    }

    /**
     * Check updates for all objects and draw
     */
    public void draw() {
        drawBG();
        if (level > 3) {
            winGame();
        } else {
            isGameReset();
            lazaStopCheck();
            boxLazaCheck();
            lazBoxCheck();
            boxBoxCheck();
            dropBox();

            drawWall();
            drawStop();
            playerAry.get(0).draw(g2, this);
            if (showTitle) {
                g2.drawImage(sprites.get("title"), 70, 0, this);
            }
        }
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2temp;
        Dimension windowSize = getSize();
        bimg = (BufferedImage) createImage(w, h);
        g2 = bimg.createGraphics();

        draw();
        g2.dispose();

        g.drawImage(bimg, 0, 0, this);
    }

    public static void main(String argv[]) {
        final LazarusWorld game = new LazarusWorld();
        game.init();

        JFrame f = new JFrame("Lazarus Pit");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", game);

        f.setSize(game.getWindowWidth(), game.getWindowHeight());
        f.setResizable(true);
        f.setVisible(true);

        game.start();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
