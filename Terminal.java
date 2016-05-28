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

    public Terminal() {
        //Do something i guess
    }

    public void connect() {
        try {
            getRemoto();
            Socket s=new Socket(host,port);
            video=new VideoHandler(s, host);
            video.start();
            System.out.println("CONEXION ESTABLECIDA");
        } catch (IOException ex) {
            Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getRemoto() {
        System.out.println("Ingresa la direccion a la cual se conectara");
        host = sc.nextLine();
        System.out.println("Ingresa el puerto");
        port = sc.nextInt();
    }
}
