/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoremoto;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
            robot=new Robot();
        } catch (IOException | AWTException ex) {
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
                        System.out.println("Key P");
                        robot.keyPress(keycode);
                        break;
                    case 2: //Key Realeased
                        keycode=ois.readInt();
                        System.out.println("Key R");
                        robot.keyRelease(keycode);
                        break;
                    case 3: //Mouse moved
                        x=ois.readInt();
                        y=ois.readInt();
                        System.out.println("Mouse M");
                        robot.mouseMove(x, y);
                        break;
                    case 4: //Mouse pressed
                        keycode=ois.readInt();
                        System.out.println("Mouse P");
                        if(keycode==1) robot.mousePress(InputEvent.BUTTON1_MASK);
                        else if(keycode==2) robot.mousePress(InputEvent.BUTTON2_MASK);
                        else if(keycode==3) robot.mousePress(InputEvent.BUTTON3_MASK);
                        break;
                    case 5: //Mouse realeased
                        keycode=ois.readInt();
                        System.out.println("Mouse R");
                        if(keycode==1) robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        else if(keycode==2) robot.mouseRelease(InputEvent.BUTTON2_MASK);
                        else if(keycode==3) robot.mouseRelease(InputEvent.BUTTON3_MASK);
                        break;
                    case 6:
                        ois.close();
                        cl.close();
                        return;
                }
            } catch (IOException ex) {
                Logger.getLogger(KeyboardHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}
