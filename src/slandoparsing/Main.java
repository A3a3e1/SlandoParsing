/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slandoparsing;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 *
 * @author A3a3el
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
        
        Notificator notificator = new Notificator();
        Thread thread = new Thread(notificator);
        thread.start();
   
    }

}
