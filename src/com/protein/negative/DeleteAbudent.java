package com.protein.negative;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class DeleteAbudent {
	public String run(String file) {
		String br1_line1;
		String out = "pure_pfamID.txt";
		
		try {
			BufferedReader br1 = new BufferedReader(
					new FileReader(file));
			BufferedWriter bw = new BufferedWriter(new FileWriter(out, true));

			br1_line1 = br1.readLine();
			bw.write(br1_line1 + "\n");	//只是读取了第一行
			bw.flush();

			while (br1.ready()) {
				br1_line1 = br1.readLine();
				String br2_line;

				BufferedReader br2 = new BufferedReader(new FileReader(out));
				int k = 0;
				int i = 0;
				int n = 0;
				while (br2.ready()) {

					br2_line = br2.readLine();
					i++;

					if (br2_line.length() != br1_line1.length())
						n++;
					k = 0;
					if (br2_line.length() == br1_line1.length()) {
						int j = 0;
						while (j <= (br1_line1.length() - 1)) {
							if (br1_line1.charAt(j) != br2_line.charAt(j))
								k++;
							j++;
						}
						if (k > 0)
							n++; // 超过5个碱基不一样，认为是不一样的序列就写入
					}

				}
				if (n == i) {
					bw.write(br1_line1 + "\n");
				}
				bw.flush();
				br2.close();
			}
			bw.close();
			br1.close();
		} catch (Exception e) {
			System.out.println("error");
		}
		
		System.out.println("This step is OK.");
		return out;
	}
}
