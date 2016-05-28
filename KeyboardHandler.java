/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.Robot;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MiguelAngel
 */
public class KeyboardHandler extends Thread{
    private Socket cl;
    private ObjectInputStream ois;
    private Robot robot;
    public KeyboardHandler(Socket c){
        try {
            cl=c;
            ois=new ObjectInputStream(cl.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(KeyboardHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void run(){
        int keycode, x, y, op;
        while(true){
            try {
                op=ois.readInt();
                switch(op){
                    case 1: //Key Pressed
                        keycode=ois.readInt();
                        robot.keyPress(keycode);
                        break;
                    case 2: //Key Realeased
                        keycode=ois.readInt();
                        robot.keyPress(keycode);
                        break;
                    case 3: //Mouse moved
                        x=ois.readInt();
                        y=ois.readInt();
                        robot.mouseMove(x, y);
                        break;
                    case 4: //Mouse pressed
                        keycode=ois.readInt();
                        robot.mousePress(keycode);
                        break;
                    case 5: //Mouse realeased
                        keycode=ois.readInt();
                        robot.mouseRelease(keycode);
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(KeyboardHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
