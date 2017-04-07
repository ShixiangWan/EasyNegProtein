import sun.applet.Main;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetProteinFamilyID {

    private static List<String> pfamList = new ArrayList<>();
    private static int isLocalDatabase = 1;
    // total number: 35869561
    private static List<String> pfamIDDatabaseKey = new ArrayList<>(35869562);
    private static List<String> pfamIDDatabaseVal = new ArrayList<>(35869562);
    //static HashMap<String, String> pfamIDDatabaseMap = new HashMap<String, String>(35869562);

    public static void main(String[] args) throws IOException {
        MainControl.proteinIDDatabase = "F:/ProteinIDToPfamIDDatabase.txt";
        new GetProteinFamilyID().fast_run("F:/uniprot-Alzheimer.fasta", 1);
    }

    List<String> fast_run(String file, int isDatabase) {
        try {
            /*读取fasta源文件，将nameID存入nameIDList*/
            Long start = System.currentTimeMillis();
            String line;
            List<String> nameIDList = new ArrayList<>();
            System.out.print("loading fasta file ... ");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (!line.equals("") && line.charAt(0) == '>') {
                    nameIDList.add(line.split("\\|")[1]);
                }
            }
            bufferedReader.close();
            System.out.println((System.currentTimeMillis() - start) + "ms");

            /*
            * 读取nameID转pfamID数据库，存入pfamIDDatabaseMap
            * 数据库信息类似：
            * Q6GZX3,*PF03003
            * Q197F8,Q197F7,Q6GZX2,Q6GZX1,Q197F5,Q6GZX0,*PF02393
            */
            isLocalDatabase = isDatabase;
            if (isLocalDatabase == 1) {
                System.out.print("loading database ... ");
                bufferedReader = new BufferedReader(new FileReader(MainControl.proteinIDDatabase));
                while(bufferedReader.ready()) {
                    line = bufferedReader.readLine();
                    pfamIDDatabaseKey.add(line.split("\\*")[0]);
                    pfamIDDatabaseVal.add(line.split("\\*")[1]);
                    //pfamIDDatabaseMap.put(line.split("\\*")[0], line.split("\\*")[1]);
                }
                bufferedReader.close();
                System.out.println((System.currentTimeMillis() - start) + "ms");
            }

            /*多线程查找nameID，pfamID，直接搜索数据库或联网*/
            int threadPoolSize = 8;
            for (String ignored : nameIDList) pfamList.add("");
            ExecutorService pool = Executors.newFixedThreadPool(threadPoolSize);
            for (int id = 0; id < nameIDList.size(); id++) {
                SearchThread searchThread = new SearchThread(id, nameIDList.get(id));
                pool.execute(new Thread(searchThread));
            }
            pool.shutdown();
            while (!pool.isTerminated());

        } catch (IOException e) {
            e.printStackTrace();
        }
        pfamIDDatabaseKey = null;
        pfamIDDatabaseVal = null;
        System.gc();
        return pfamList;
    }

    public class SearchThread implements Runnable {
        private int id;
        private String nameID;

        SearchThread(int id, String nameID) {
            this.id = id;
            this.nameID = nameID;
        }

        @Override
        public void run() {
            if (isLocalDatabase == 1) {
                for (int i = 0; i < pfamIDDatabaseKey.size(); i++) {
                    if (pfamIDDatabaseKey.get(i).contains(nameID)) {
                        pfamList.set(id, pfamIDDatabaseVal.get(i));
                        System.out.println("*matching " + id + " sequence(s) ... OK");
                        return; // find out nameID -> pfamID
                    }
                }
            }
            // if not
            try {
                String site = "http://pfam.xfam.org/protein/" + nameID + "?output=xml";
                InputStreamReader isr = new InputStreamReader(new URL(site).openStream(), "UTF-8");
                Thread.sleep(100);
                BufferedReader in = new BufferedReader(isr);
                while (in.ready()) {
                    String s = in.readLine();
                    while (s.contains("<match accession=\"")) {
                        s = s.substring(s.indexOf("<match accession=\"") + 1);
                        s = s.substring(s.indexOf("PF"));
                        s = s.substring(0, s.indexOf('"'));
                        pfamList.set(id, s);
                        System.out.println("*matching " + id +
                                " sequence(s) ... searching on http://pfam.xfam.org/protein/ ... OK");
                        return; // find out nameID -> pfamID
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
