/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author MiguelAngel
 */
//Este es el cliente, el que se conecta
public class Terminal {
    private Socket cl;
    private String host;
    private Rectangle r;
    private Robot robot;
    private int port;
    private Scanner sc= new Scanner(System.in);
    private ObjectInputStream ois;
    private JPanel panel;
    public Terminal(){
        //Do something i guess
        r=new Rectangle(1024,720);
    }
    public void connect(){
        try {
            getRemoto();
            cl=new Socket(host,port);
            ois=new ObjectInputStream(cl.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
        panel = new JPanel(new BorderLayout());
        System.out.println("CONEXION ESTABLECIDA");
    }
    public void getRemoto(){
        System.out.println("Ingresa la direccion a la cual se conectara");
        host=sc.nextLine();
        System.out.println("Ingresa el puerto");
        port=sc.nextInt();
    }
    public void getVideo(){
        while(true){
            try {
                ImageIcon imageIcon = (ImageIcon) ois.readObject();
                Image image = imageIcon.getImage();
                image = image.getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_FAST);
                Graphics graphics = panel.getGraphics();
                graphics.drawImage(image, 0, 0, panel.getWidth(),panel.getHeight(),panel);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        }
    }
}
