/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author MiguelAngel
 */
class VideoHandler extends Thread {
    private boolean ejecutar=true;
    private String host;
    private Socket cl;
    private Rectangle r;
    private ObjectInputStream ois;
    private JPanel panel;
    private Toolkit tool;
    private int wR;
    private int hR;
    public VideoHandler(Socket c, String h, JPanel p) {
        panel=p;
        host = h;
        tool = Toolkit.getDefaultToolkit();
        r = new Rectangle(tool.getScreenSize());
        try {
            cl = c;
            ois = new ObjectInputStream(cl.getInputStream());
            wR=ois.readInt();
            hR=ois.readInt();
        } catch (IOException ex) { 
            Logger.getLogger(VideoHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getW(){
        return wR;
    }
    public int getH(){
        return hR;
    }
    
    public void detener(){
        try {
            ejecutar=false;
            ois.close();
            cl.close();
        } catch (IOException ex) {
            Logger.getLogger(VideoHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        while(ejecutar){
            try {
                ImageIcon imageIcon = (ImageIcon) ois.readObject();
                int x=ois.readInt();
                int y=ois.readInt();
                Image image = imageIcon.getImage();
                image = image.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_FAST);
                Graphics graphics = panel.getGraphics();
                graphics.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), panel);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(VideoHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}
