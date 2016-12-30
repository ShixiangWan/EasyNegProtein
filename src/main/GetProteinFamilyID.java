package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetProteinFamilyID {

    public List<String> run(String file, String databasePath) {

        List<String> pfamList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            int a = 0;
            while (br.ready()) {
                System.out.print("*matching " + (++a) + " sequence(s) ...");
                if (line.length() != 0 && line.charAt(0) == '>') {
                    String name = line;
                    StringBuilder sb = new StringBuilder();
                    line = br.readLine();
                    while (br.ready() && line.length() == 0)
                        line = br.readLine();
                    while (line.length() != 0 && line.charAt(0) != '>') {
                        sb.append(line);
                        if (br.ready()) {
                            line = br.readLine();
                        } else {
                            break;
                        }
                    }

                    String nameID = name.substring(name.indexOf('|') + 1);
                    nameID = nameID.substring(0, nameID.indexOf('|'));

                    String pfamID = getPFAMID(nameID, databasePath);
                    if (pfamID != null) {
                        pfamList.add(pfamID);
                    } else {
                        String site = "http://pfam.xfam.org/protein/" + nameID + "?output=xml";
                        InputStreamReader isr = new InputStreamReader(new URL(site).openStream(), "UTF-8");
                        Thread.sleep(100);
                        BufferedReader in = new BufferedReader(isr);
                        while (in.ready()) {
                            String s = in.readLine();
                            while (s.contains("<match accession=\"")) {
                                s = s.substring(s.indexOf("<match accession=\"") + 1);
                                s = s.substring(s.indexOf("PF"));
                                pfamID = s.substring(0, s.indexOf('"'));
                                pfamList.add(pfamID);
                            }
                        }
                    }
                } else {
                    line = br.readLine();
                }
                System.out.println("OK");
            }
            br.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        System.out.println("This step is OK.");
        return pfamList;
    }

    public String getPFAMID(String nameID, String databasePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(databasePath));

            String line;
            while(reader.ready()) {
                line = reader.readLine();
                if (line.contains(nameID)) {
                    line = line.split("\\*")[1];
                    return line;
//                    line = reader.readLine();
//                    if (line.substring(0,1).equals("*")) {
//                        line = line.substring(1);
//                        reader.close();
//                        return line;
//                    } else {
//                        reader.close();
//                        return null;
//                    }
                }
            }
            reader.close();
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("There is an error: "+e);
        }

        return null;
    }
}
