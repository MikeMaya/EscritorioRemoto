/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author MiguelAngel
 */
class VideoHandler extends Thread {

    private String host;
    private Socket cl;
    private Rectangle r;
    private ObjectInputStream ois;
    private JPanel panel;
    private JFrame mainFrame;
    private Toolkit tool;

    public VideoHandler(Socket c, String h) {
        host = h;
        tool = Toolkit.getDefaultToolkit();
        r = new Rectangle(tool.getScreenSize());
        try {
            cl = c;
            ois = new ObjectInputStream(cl.getInputStream());
        } catch (IOException ex) { 
            Logger.getLogger(VideoHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Conexion remota ");
        mainFrame.setSize(tool.getScreenSize());
        //mainFrame.setLayout(new );
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }

    @Override
    public void run() {
        while(true){
            try {
                ImageIcon imageIcon = (ImageIcon) ois.readObject();
                Image image = imageIcon.getImage();
                image = image.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_FAST);
                Graphics graphics = panel.getGraphics();
                graphics.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), panel);
            } catch (IOException ex) {
                Logger.getLogger(VideoHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VideoHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}
