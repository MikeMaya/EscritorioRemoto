/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Diego
 */
public class VideoStreamer extends Thread {

    private boolean ejecutar = true;
    private Robot robot;
    private Rectangle r;
    private ObjectOutputStream oos;
    private Socket cl;
    private Toolkit tool;

    public VideoStreamer(Socket c) {
        try {
            tool = Toolkit.getDefaultToolkit();
            r = new Rectangle(tool.getScreenSize());
            robot = new Robot();
            cl = c;
            oos = new ObjectOutputStream(cl.getOutputStream());
            oos.writeInt((int) tool.getScreenSize().getWidth());
            oos.flush();
            oos.writeInt((int) tool.getScreenSize().getHeight());
            oos.flush();
        } catch (AWTException | IOException ex) {
            Logger.getLogger(VideoStreamer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void detener() {
        ejecutar = false;
    }

    @Override
    public void run() {
        while (ejecutar) {
            BufferedImage image = robot.createScreenCapture(r);
            ImageIcon imageIcon = new ImageIcon(image);
            try {
                oos.writeObject(imageIcon);
                oos.flush();
                oos.writeInt(MouseInfo.getPointerInfo().getLocation().x);
                oos.flush();
                oos.writeInt(MouseInfo.getPointerInfo().getLocation().y);
                oos.flush();
            } catch(SocketException se){
                System.out.println("CONEXION TERMINADA");
                break;
            } catch (IOException ex) {
                Logger.getLogger(VideoStreamer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
