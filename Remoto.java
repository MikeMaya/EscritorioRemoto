/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author MiguelAngel
 */
public class Remoto {
    private ServerSocket s, s1;
    private VideoStreamer video;
    private KeyboardHandler kb;
    private int port=8001;
    public Remoto() {
        try{
            s = new ServerSocket(port);
            s1= new ServerSocket(port+1);
        } catch (IOException ex) {
            Logger.getLogger(Remoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        try {
            Socket cl = s.accept();
            Socket cl1= s1.accept();
            video=new VideoStreamer(cl);
            video.start();
            kb=new KeyboardHandler(cl1);
            kb.start(); 
            while(kb.isAlive());
            video.detener();
            s.close();
            s1.close();
        } catch (IOException ex) {
            Logger.getLogger(Remoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
