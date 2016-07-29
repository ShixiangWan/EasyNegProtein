package com.protein.negative;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

public class DeletePosFamliyID {
	@SuppressWarnings("unchecked")
	public void run(String file) {
		try {
			// *********************读取正例所在的家族**********************
			//BufferedReader br = new BufferedReader(new FileReader("un_pfamID_TATA.txt"));
			BufferedReader br = new BufferedReader(new FileReader(file));
			@SuppressWarnings("rawtypes")
			Vector posfam = new Vector();
			while (br.ready()) {
				String line = br.readLine().trim();
				posfam.add(line);
			}
			br.close();

			// *************在反例文件夹中删除相应的文件*******************
			//File f = new File("PF_all_TATA");
			File f = new File("ALLPFAM");
			File[] neg = f.listFiles();

			for (int i = 0; i < neg.length; i++) {
				boolean flag = false;
				for (int j = 0; j < posfam.size(); j++) {
					if (posfam.get(j).toString().equals(neg[i].getName().substring(0,neg[i].getName().indexOf('.')))) {
						flag = true;
						break;
					}
				}

				if (flag) {

					System.out.println(neg[i].getName());
					neg[i].delete();
					System.out.println(neg[i].getName());
				}
			}

		} catch (Exception ex) {
			System.out.println(ex.getCause());
			System.out.println("Maybe you need crate a folder named ‘ALLPFAM’！");
			System.exit(0);
		}
		System.out.println("This step is OK.");
	}
}
