/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author MiguelAngel
 */
public class KeyboardStreamer extends Thread {

    private Socket cl;
    private JPanel panel;
    private ObjectOutputStream oos;

    public KeyboardStreamer(Socket s, JPanel p) {
        try {
            cl = s;
            oos = new ObjectOutputStream(cl.getOutputStream());
            panel = p;
        } catch (IOException ex) {
            Logger.getLogger(KeyboardStreamer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //Nothing at all
            }

            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    synchronized (oos) {
                        oos.writeInt(1);
                        oos.flush();
                        oos.writeInt(e.getKeyCode());
                        oos.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(KeyboardStreamer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    oos.writeInt(2);
                    oos.flush();
                    oos.writeInt(e.getKeyCode());
                    oos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(KeyboardStreamer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    synchronized (oos) {
                        oos.writeInt(3);
                        oos.flush();
                        oos.writeInt(e.getX());
                        oos.writeInt(e.getY());
                        oos.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(KeyboardStreamer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }//Nothing yet
        });
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    synchronized (oos) {
                        oos.writeInt(4);
                        oos.flush();
                        oos.writeInt(e.getButton());
                        oos.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(KeyboardStreamer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    synchronized (oos) {
                        oos.writeInt(5);
                        oos.flush();
                        oos.writeInt(e.getButton());
                        oos.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(KeyboardStreamer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }// Nothing yet

            @Override
            public void mouseExited(MouseEvent e) {
            }// Nothing yet

            @Override
            public void mouseClicked(MouseEvent e) {
            } // Nothing yet
        });

    }
}
