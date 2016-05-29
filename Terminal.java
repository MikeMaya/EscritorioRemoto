/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author MiguelAngel
 */

//Este es el cliente, el que se conecta

public class Terminal {

    private String host;
    private int port;
    private Scanner sc = new Scanner(System.in);
    private VideoHandler video;
    private KeyboardStreamer kb;
    private JPanel panel;
    private JFrame mainFrame;
    private Toolkit tool;
    public Terminal(String host) {
        this.host=host;
        port=8001;
    }
    public void connect() {
        try {
            //getRemoto();
            tool=Toolkit.getDefaultToolkit();
            Socket s=new Socket(host,port);
            Socket s1=new Socket(host,port+1);
            prepareGUI();
            video=new VideoHandler(s, host,panel);
            kb=new KeyboardStreamer(s1,panel,video.getW(),video.getH());
            video.start();
            kb.start();
            System.out.println("CONEXION ESTABLECIDA");
        } catch (IOException ex) {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void prepareGUI() {
        mainFrame = new JFrame("Conexion remota ");
        mainFrame.setSize(1280,720);
        //mainFrame.setLayout(new );
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }
    public void getRemoto() {
        System.out.println("Ingresa la direccion a la cual se conectara");
        host = sc.nextLine();
        System.out.println("Ingresa el puerto");
        port = sc.nextInt();
    }
    public void detener(){
        video.detener();
        kb.detener();
        video.stop();
        kb.stop();
        mainFrame.setVisible(false);
    }
}
