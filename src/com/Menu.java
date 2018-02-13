package com;

import com.graphics.ui.UIActionListener;
import com.graphics.ui.UIButton;
import com.graphics.ui.UIManager;
import com.graphics.ui.UIPanel;
import com.util.Vector2i;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Menu {


    public BufferedImage menuB = null;
    private UIManager ui;

    public void render(Graphics g) {
        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.drawString("NEGER BEGER", Game.getWindowWidth() / 2, 100);

    }

    public void initPic(){
        try {
            ImageReader rdr = ImageIO.getImageReadersByFormatName("PNG").next();
            InputStream is = new FileInputStream("res/pictures/pic.png");
            ImageInputStream imageInput = ImageIO.createImageInputStream(is);
            rdr.setInput(imageInput);
            BufferedImage image = rdr.read(0);
            is.close();
            menuB = image;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Image didnt load");
        }
    }



    public void menuUI() {
        //UI:
        ui = Game.getUIManager();
        UIPanel panel = (UIPanel) new UIPanel(new Vector2i(0, Game.getWindowHeight()), new Vector2i(Game.getWindowWidth(), Game.getBar() * Game.getScale())).setColor(0x4f4f4f);

        ui.addPanel(panel);

        UIButton start = new UIButton(new Vector2i(50, 20), new Vector2i(120, 30), new UIActionListener() {
            public void perform() {
                Game.playerUIFirst = true;
                Game.state = Game.STATE.GAME;
            }
        });
        start.setText("Start");
        panel.addComponent(start);

        UIButton option = new UIButton(new Vector2i(50 * 7, 20), new Vector2i(120, 30), new UIActionListener() {
            public void perform() {
                Game.option = true;
            }
        });
        option.setText("Options");
        panel.addComponent(option);

        UIButton quit = new UIButton(new Vector2i(50 * 12, 20), new Vector2i(120, 30), new UIActionListener() {
            public void perform() {
                System.exit(1);
            }
        });
        quit.setText("Quit");
        panel.addComponent(quit);
    }

    public void menuUIRemove() {
        if (ui != null) ui.removePanel();
    }

    public void optionUI(){
        menuUIRemove();

        UIPanel panel = new UIPanel(new Vector2i(0, 0), new Vector2i(Game.getWindowWidth(), Game.getWindowHeight()));
        UIPanel panelB = (UIPanel) new UIPanel(new Vector2i(0, Game.getWindowHeight()), new Vector2i(Game.getWindowWidth(), Game.getBar() * Game.getScale())).setColor(0x4f4f4f);
        ui.addPanel(panel);
        ui.addPanel(panelB);

        UIButton scaleP = new UIButton(new Vector2i(Game.getWindowWidth()/2, 20), new Vector2i(200, 30), new UIActionListener() {
            public void perform() {
                //Game.scale++;
            }
        });
        scaleP.setText("Scale+");
        panel.addComponent(scaleP);

        UIButton scaleM = new UIButton(new Vector2i(Game.getWindowWidth()/2, 120), new Vector2i(200, 30), new UIActionListener() {
            public void perform() {
                //Game.scale--;
            }
        });
        scaleM.setText("Scale-");
        panel.addComponent(scaleM);

        UIButton soundO = new UIButton(new Vector2i(Game.getWindowWidth()/2, 220), new Vector2i(200, 30), new UIActionListener() {
            public void perform() {
            }
        });
        soundO.setText("Sound ON");
        panel.addComponent(soundO);

        UIButton soundF = new UIButton(new Vector2i(Game.getWindowWidth()/2, 320), new Vector2i(200, 30), new UIActionListener() {
            public void perform() {
            }
        });
        soundF.setText("Sound Off");
        panel.addComponent(soundF);


        UIButton start = new UIButton(new Vector2i(50, 20), new Vector2i(120, 30), new UIActionListener() {
            public void perform() {
                Game.menuUIFirst = true;
                Game.state = Game.STATE.MENU;
            }
        });
        start.setText("Menu");
        panelB.addComponent(start);


        UIButton quit = new UIButton(new Vector2i(50 * 12, 20), new Vector2i(120, 30), new UIActionListener() {
            public void perform() {
                System.exit(1);
            }
        });
        quit.setText("Quit");
        panelB.addComponent(quit);
    }

    public void optionUIRemove(){
        if(ui != null) ui.removePanel();
    }

    public int incScale(){
        return Game.scale++;
    }

}

