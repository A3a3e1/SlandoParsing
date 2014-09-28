/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slandoparsing;

import sms.center.Smsc;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author A3a3el
 */
public class UpdChecker {

    String link = "http://kiev.ko.slando.ua/elektronika/";

    public Map<String, String> check() throws IOException {

        IDGrabber grabber = new IDGrabber(link);
        grabber.grab();
        Map<String, String> updates = grabber.writeToFile();

        if (updates.isEmpty()) {
            System.out.println("Scanning......");
        } else {
            System.out.println(updates.size() + " new entries!");
        }

        return updates;

    }

}
