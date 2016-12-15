package Lazarus_Game;

import Platform.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bravolly Pich.
 */
public class PlayerObj extends GameObj implements Observer {
    int tempX, tempY, lastX, lastY;
    char direction = '~', preDirection = '3';
    int[] keys;
    boolean falling = false;
    boolean jump = false;
    boolean dead = false;
    boolean gameReset = false;
    boolean mapReset = false;

    GameSound sfx;

    BufferedImage[] spriteLeft, spriteRight, spriteJumpLeft, spriteJumpRight, spriteSquished, spriteAfraid;

    public PlayerObj(BufferedImage[] sprite0, BufferedImage[] spriteLeft, BufferedImage[] spriteRight,
                     BufferedImage[] spriteJumpLeft, BufferedImage[] spriteJumpRight,
                     BufferedImage[] spriteSquished, BufferedImage[] spriteAfraid, int[] keys, int speed) {

        super(sprite0);
        this.spriteLeft = spriteLeft;
        this.spriteRight = spriteRight;
        this.spriteJumpLeft = spriteJumpLeft;
        this.spriteJumpRight = spriteJumpRight;
        this.spriteSquished = spriteSquished;
        this.spriteAfraid = spriteAfraid;
        setSpeed(speed);
        this.keys = keys;

    }

    /**
     * Actions to be performed depending on the number from collisionType
     */
    @Override
    public void collisionAction() {
        switch (collisionType) {
            case '0':
                if (falling) {
                    falling = false;
                    jump = false;
                    setY(tempY);
                } else if (!jump) {
                    if (preDirection == '2')
                        setBound(x - 40, y - 40);
                    else if (preDirection == '3')
                        setBound(x + 40, y - 40);
                    jumpWall();
                } else if (jump){
                    jump = false;
                    wallCollision();
                }
                break;
            case '1':
                dead();
                break;
            default:
                break;
        }
        collisionType = '~';
    }

    public void wallCollision() {
        frame = 0;
        setXY(lastX, lastY + 40);
    }

    public void jumpWall() {
        jump = true;
        if (preDirection == '2')
            jumpLeft();
        else if (preDirection == '3')
            jumpRight();
    }

    public void jumpLeft() {
        direction = '0';
        preDirection = '0';
        setX(getX() - speed);

        if (frame > 7)
            frame = 0;

    }

    public void jumpRight() {
        direction = '1';
        preDirection = '1';

        setX(getX() + speed);

        if (frame > 7)
            frame = 0;
    }

    public void moveLeft() {
        direction = '2';
        preDirection = '2';
        setX(getX() - speed);

        if (frame > 7)
            frame = 0;
    }

    public void moveRight() {
        direction = '3';
        preDirection = '3';

        setX(getX() + speed);

        if (frame > 7)
            frame = 0;
    }

    public void fall() {
        tempY = y;
        setY(y + 40);
    }

    public boolean setFalling(boolean b) {
        return falling = b;
    }

    public boolean isFalling() {
        return falling;
    }

    public char getDirection() {
        return preDirection;
    }

    public void setBound(int x, int y) {
        box.x = x;
        box.y = y;
    }

    public void dying() {

    }

    public void dead() {
        dead = true;
    }

    public void notDead() {
        dead = false;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean getGameReset() {
        return gameReset;
    }

    public void setGameReset(boolean r) {
        gameReset = r;
    }

    public boolean getMapReset() {
        return mapReset;
    }

    public void setMapReset(boolean r) {
        mapReset = r;
    }

    public void update() {
        if (!dead) {
            collisionAction();
        }
    }

    @Override
    public void update(Observable o, Object arg){

        GameEvents ge = (GameEvents) arg;
        if (ge.type == 1) {
            KeyEvent e = (KeyEvent) ge.event;
            int keyPressed = e.getKeyCode();

            if (keyPressed == keys[0]) {
                setGameReset(true);
            } else if (keyPressed == keys[1]) {
                setMapReset(true);
            } else if (keyPressed == keys[2]) {

                if (frame == 0 && !falling) {
                    if (visible) {
                        sfx = new GameSound(2, "/Lazarus_Game/Resource/Move.wav");
                        sfx.play();
                        lastX = this.x;
                        lastY = this.y;
                        moveLeft();
                    }
                }
            } else if (keyPressed == keys[3]) {
                if (frame == 0 && !falling) {
                    if (visible) {
                        sfx = new GameSound(2, "/Lazarus_Game/Resource/Move.wav");
                        sfx.play();
                        lastX = this.x;
                        lastY = this.y;
                        moveRight();
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, ImageObserver obs) {
        if (visible) {
            if (dead) {
                if (frame != 10) {
                    g.drawImage(spriteSquished[frame], x, y, obs);
                    frame++;
                } else {
                    frame = 0;
                    setVisible(false);
                }

            } else {
                switch (direction) {
                    case '0':
                        if ((this.x % 40) != 0) {
                            g.drawImage(spriteJumpLeft[frame], x - 20, y - 40, obs);
                            jumpLeft();
                            frame++;
                        } else {
                            frame = 0;
                            direction = '~';
                            setY(y - 40);
                            g.drawImage(sprite[frame], x, y, obs);
                            falling = true;
                        }
                        break;
                    case '1':
                        if ((this.x % 40) != 0) {
                            g.drawImage(spriteJumpRight[frame], x - 20, y - 40, obs);
                            jumpRight();
                            frame++;
                        } else {
                            frame = 0;
                            direction = '~';
                            setY(y - 40);
                            g.drawImage(sprite[frame], x, y, obs);
                            falling = true;
                        }
                        break;
                    case '2':
                        if ((this.x % 40) != 0) {
                            g.drawImage(spriteLeft[frame], x - 20, y - 40, obs);
                            moveLeft();
                            frame++;
                        } else {
                            frame = 0;
                            direction = '~';
                            g.drawImage(sprite[frame], x, y, obs);
                            falling = true;
                        }
                        break;
                    case '3':
                        if ((this.x % 40) != 0) {
                            g.drawImage(spriteRight[frame], x - 20, y - 40, obs);
                            moveRight();
                            frame++;
                        } else {
                            frame = 0;
                            direction = '~';
                            g.drawImage(sprite[frame], x, y, obs);
                            falling = true;
                        }
                        break;
                    default:
                        g.drawImage(sprite[0], x, y, obs);
                        break;
                }

                if (falling) {
                    fall();
                }

            }
        }
    }
}
