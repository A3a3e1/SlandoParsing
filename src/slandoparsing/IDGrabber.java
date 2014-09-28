/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slandoparsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author A3a3el
 */
public class IDGrabber {

    String link;
    String dumpFile = "property.prop";
    Map<String, String> currentMap = new HashMap<>();

    public IDGrabber(String link) {
        this.link = link;
    }

    public Map<String, String> grab() throws MalformedURLException, IOException {

        Document doc = Jsoup.connect(link).get();
        Elements ids = doc.select("#offers_table td > table");
        Map<String, Integer> map = new HashMap<>();
        String line, line2;
        StringBuilder builder;
        int idPosition, counter = 0;
        for (int i = 0; i < ids.size(); i++) {
            builder = new StringBuilder(ids.get(i).className());
            if (builder.indexOf(" ad_") != -1) {
                idPosition = builder.indexOf(" ad_");
                line = builder.substring(idPosition + 4, idPosition + 11);
                map.put(line, counter++);
//                System.out.println(counter + ". " +line);
            }
        }

        Set<String> ss = map.keySet();
        for (String s : ss) {
            Elements ids2 = doc.select("a[href*=" + s + "]");
            StringBuilder sb = new StringBuilder(ids2.get(0).toString());
            int start, end;
            if ((start = sb.indexOf("alt=")) != -1) {
                end = sb.indexOf(" />");
//                System.out.print(s + ". ");
                line2 = sb.substring(start + 5, end - 1);
                currentMap.put(s, line2);
//                System.out.println(line2);
            } else {
                start = sb.indexOf("<span>");
                end = sb.indexOf("</span>");
//                System.out.print(s + ". ");
                line2 = sb.substring(start + 6, end);
                currentMap.put(s, line2);
//                System.out.println(line2);
            }

        }

        return currentMap;
    }

    public Map<String, String> writeToFile() throws FileNotFoundException, IOException {

        File file = new File("property.prop");
        if (!file.exists()) {
            FileOutputStream fosTemp = new FileOutputStream("property.prop");
        }

        FileInputStream inputFis = new FileInputStream("property.prop");
        Properties inputDump = new Properties();
        inputDump.load(inputFis);
        //System.out.println("Now DB contains " + inputDump.size() + " entries.");

        //Copying all the DB from file "properties.prop" into a Map<S, S> allTemp
        Map<String, String> allTemp = new HashMap<>();
        Set<Object> allKeySet = inputDump.keySet();
        for (Object o : allKeySet) {
            allTemp.put((String) o, (String) inputDump.get(o));
        }

        Properties properties = new Properties();
        
        FileOutputStream fos = new FileOutputStream("property.prop");
        
        Map<String, String> newUpdates = new HashMap<>();

        Set<String> currentMapSet = currentMap.keySet();
        for (String s : currentMapSet) {
            if (!allTemp.containsKey(s)) {
                newUpdates.put(s, currentMap.get(s));
                //Now allTemp contains both old and new entries
                allTemp.put(s, currentMap.get(s));
                //properties.put(s, currentMap.get(s));            
            }
        }
        //System.out.println("You have " + newUpdates.size() + " new entries");
        properties.putAll(allTemp);
        properties.store(fos, "property.prop");

        return newUpdates;
           
    }

}
