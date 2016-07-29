package com.protein.negative;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

public class GetProteinFamliyID {
	@SuppressWarnings("static-access")
	public String run(String file) {
		
		String out = "pfamID.txt";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			BufferedWriter bwpfam = new BufferedWriter(new FileWriter(out));
			String line = br.readLine();
			int a = 0;
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
					System.out.println(seq);

					String pfamID = name.substring(name.indexOf('|') + 1);
					pfamID = pfamID.substring(0, pfamID.indexOf('|'));
					
					
					pfamID = getPFAMID(pfamID);
					System.out.println(pfamID); // 打印
					if (pfamID != null) {
						bwpfam.write(pfamID);
						bwpfam.newLine();
						bwpfam.flush();
					} else {
						String site = "http://pfam.xfam.org/protein/" + pfamID + "?output=xml";
						URL url = new URL(site);
						InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
						Thread.currentThread().sleep(100); // 当网速不好的时候
						BufferedReader in = new BufferedReader(isr);
						while (in.ready()) {
							String s = in.readLine();
							while (s.indexOf("<match accession=\"") != -1) {
								s = s.substring(s.indexOf("<match accession=\"") + 1);
								s = s.substring(s.indexOf("PF"));
								bwpfam.write(s.substring(0, s.indexOf('"')));
								System.out.println(s.substring(0, s.indexOf('"'))); // 打印
								bwpfam.newLine();
								bwpfam.flush();
							}
						}
					}

				} else {
					line = br.readLine();
				}
				a++;
				System.out.println("*matching the " + a + " ..."); // 打印
			}

			br.close();

			bwpfam.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		System.out.println("This step is OK.");
		return out;
	}	
	
	public String getPFAMID(String pfamID) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("PfamIDDatabase.txt"));
			
			String line = "";
			while(reader.ready()) {
				line = reader.readLine();
				if (line.contains(pfamID)) {
					line = reader.readLine();
					if (line.substring(0,1).equals("*")) {
						line = line.substring(1);
						reader.close();
						return line;
					} else {
						reader.close();
						return null;
					}
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
