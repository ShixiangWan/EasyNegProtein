package com.protein.negative;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class GetNegativeExample {
	public void run(String file) {
		try {
			//BufferedWriter bw = new BufferedWriter(new FileWriter("neg_TATA.fasta"));
			//File f = new File("negfamily_TATA");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			File f = new File("ALLPFAM");
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(list[i]));
				String line = br.readLine();
				String name_last = "", seq_last = "";
				while (br.ready()) {
					if (line.length() != 0 && line.charAt(0) == '>') {
						String name = line;
						StringBuffer sb = new StringBuffer();
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
						String seq = sb.toString();
						if (seq.length() > seq_last.length()) {
							name_last = name;
							seq_last = seq;
						}
					} else {
						line = br.readLine();
					}
				}

				br.close();
				bw.write(name_last);
				bw.newLine();
				bw.write(seq_last);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("Maybe you need crate a folder named: ALLPFAM");
			System.exit(0);
		}
		System.out.println("This step is OK.");
	}
}
