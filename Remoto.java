/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.Rectangle;
import java.awt.Robot;
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
    private ServerSocket s;
    private Robot robot;
    private Rectangle r;
    private ObjectOutputStream oos;
    private Socket cl;
    public Remoto(){
        r=new Rectangle(1024,720);
        try {
            s=new ServerSocket(8001);
        } catch (IOException ex) {
            Logger.getLogger(Remoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void connect(){
        while(true){
            try {
                cl=s.accept();
                oos=new ObjectOutputStream(cl.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Remoto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void streamVideo(){
        while(true){
             BufferedImage image = robot.createScreenCapture(r);
             ImageIcon imageIcon = new ImageIcon(image);
             try {
                oos.writeObject(imageIcon);
                oos.flush();
                System.out.println("New screenshot sent");
            } catch (IOException ex) {
               ex.printStackTrace();
            }

        }
    }
}
