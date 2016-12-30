package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class GetNegativeExample {
	public void run(List<String> negList, String pfamALLPath, String negFile) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(negFile));
			File file = new File(pfamALLPath);
			File[] list = file.listFiles();
            for (File f : list) {
                if (isNeg(negList, f.toString())) {
				    BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
				    String line = bufferedReader.readLine();
                    String name_last = "", seq_last = "";
                    while (bufferedReader.ready()) {
                        if (line.length() != 0 && line.charAt(0) == '>') {
                            String name = line;
                            StringBuffer stringBuffer = new StringBuffer();
                            line = bufferedReader.readLine();
                            while (bufferedReader.ready() && line.length() == 0)
                                line = bufferedReader.readLine();
                            while (line.length() != 0 && line.charAt(0) != '>') {
                                stringBuffer.append(line);
                                if (bufferedReader.ready()) {
                                    line = bufferedReader.readLine();
                                } else {
                                    break;
                                }
                            }
                            String seq = stringBuffer.toString();
                            if (seq.length() > seq_last.length()) {
                                name_last = name;
                                seq_last = seq;
                            }
                        } else {
                            line = bufferedReader.readLine();
                        }
                    }

                    bufferedReader.close();
                    bufferedWriter.write(name_last);
                    bufferedWriter.newLine();
                    bufferedWriter.write(formatSeq(seq_last));
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
            bufferedWriter.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("Maybe you need crate a folder named: ALLPFAM");
			System.exit(0);
		}
		System.out.println("This step is OK.");
	}

	public boolean isNeg(List<String> negList, String fileName) {
	    for (String neg : negList) {
	        if (fileName.contains(neg)) return true;
        }
        return false;
    }

    public String formatSeq(String seq) {
	    String[] invalidGroup = {"J", "O", "U", "X"};
	    seq = seq.toUpperCase();
        for (String invalid : invalidGroup) {
            seq = seq.replace(invalid, "");
        }
        return seq;
    }
}
