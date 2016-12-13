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
    int tempX, tempY;
    char direction = '~', preDirection = '3';
    int[] keys;
    boolean dead = false;
    GameSound sfx;

    BufferedImage[] spriteLeft;
    BufferedImage[] spriteRight;
    BufferedImage[] spriteJumpLeft;
    BufferedImage[] spriteJumpRight;
    BufferedImage[] spriteSquished;
    BufferedImage[] spriteAfraid;

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
                //wallCollision();
                jumpWall();
            default:
                break;
        }
        collisionType = '~';
    }

    public void wallCollision() {

        setXY(tempX, tempY);
    }

    public void jumpWall() {
        if (preDirection == '2')
            jumpLeft();
        else if (preDirection == '3')
            jumpRight();
    }

    public void jumpLeft() {
        direction = '0';
        preDirection = '2';
        setX(getX() - speed);

        if (frame > 7)
            frame = 0;

    }

    public void jumpRight() {
        direction = '1';
        preDirection = '3';

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

        /*if (frame < 7) {
            //frame++;
            setX(getX() - speed);
        } else {
            frame = 0;
            setX(getX() - speed);
        }*/
    }

    public void moveRight() {
        direction = '3';
        preDirection = '3';

        setX(getX() + speed);

        if (frame > 7)
            frame = 0;

        /*if (frame < 7) {
            //frame++;
            setX(getX() + speed);
        } else {
            frame = 0;
            setX(getX() + speed);
        }*/
    }

    public char getDirection() {
        return preDirection;
    }

    public void dead() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
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

            if (!dead) {
                if (keyPressed == keys[0]) {

                } else if (keyPressed == keys[1]) {

                } else if (keyPressed == keys[2]) {

                    if (frame == 0) {
                        if (visible) {
                            sfx = new GameSound(2, "/Lazarus_Game/Resource/Move.wav");
                            sfx.play();
                            tempX = this.x;
                            tempY = this.y;
                            moveLeft();
                        }
                    }
                } else if (keyPressed == keys[3]) {
                    if (frame == 0) {
                        if (visible) {
                            sfx = new GameSound(2, "/Lazarus_Game/Resource/Move.wav");
                            sfx.play();
                            tempX = this.x;
                            tempY = this.y;
                            moveRight();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, ImageObserver obs) {
        if (dead) {
        } else if (visible) {

            switch (direction) {
                case '0':
                    if ((this.x % 40) != 0) {
                        g.drawImage(spriteJumpLeft[frame],x-20,y-40,obs);
                        jumpLeft();
                        frame++;
                    } else {
                        frame = 0;
                        direction = '~';
                        setY(y - 40);
                        g.drawImage(sprite[frame],x,y,obs);
                    }
                    break;
                case '1':
                    if ((this.x % 40) != 0) {
                        g.drawImage(spriteJumpRight[frame],x-20,y-40,obs);
                        jumpRight();
                        frame++;
                    } else {
                        frame = 0;
                        direction = '~';
                        setY(y - 40);
                        g.drawImage(sprite[frame],x,y,obs);
                    }
                    break;
                case '2':
                    if ((this.x % 40) != 0) {
                        g.drawImage(spriteLeft[frame],x-20,y-40,obs);
                        moveLeft();
                        frame++;
                    } else {
                        frame = 0;
                        direction = '~';
                        g.drawImage(sprite[frame],x,y,obs);
                    }
                    break;
                case '3':
                    if ((this.x % 40) != 0) {
                        g.drawImage(spriteRight[frame],x-20,y-40,obs);
                        moveRight();
                        frame++;
                    } else {
                        frame = 0;
                        direction = '~';
                        g.drawImage(sprite[frame],x,y,obs);
                    }
                    break;
                default:
                    g.drawImage(sprite[0], x, y, obs);
                    break;
            }
        }
    }
}
