/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slandoparsing;

import sms.center.Smsc;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author A3a3el
 */
public class Notificator implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                UpdChecker checker = new UpdChecker();
                Map<String, String> updates = checker.check();

                if (updates.size() > 0) {
                    
                    //SMS sending quantity of new entries at slando.ua
                    Smsc sms = new Smsc();
                    //Output to the mobile phone:
//                    sms.send_sms("380952069486", "You got " + updates.size() + " new ads at slando.ua!", 1, "", "", 0, "", "");
                    //Output to the screen:
                    System.out.println("You got " + updates.size() + " new ads at slando.ua!");
                }

                Thread.sleep(60000);

            } catch (IOException ex) {
                Logger.getLogger(Notificator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Notificator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
