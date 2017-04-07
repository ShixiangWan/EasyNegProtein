import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DeletePosFamilyID {

    public List<String> run(HashSet<String> pfamHashSet, String ALLPFAMDatabase) {
        try {
            File file = new File(ALLPFAMDatabase);
            String[] fileList = file.list();
            List<String> negList = new ArrayList<>();
            for (String fileString: fileList) {
                boolean flag = false;
                fileString = fileString.substring(0, fileString.indexOf("."));
                for (String posString : pfamHashSet) {
                    if (fileString.equals(posString)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    negList.add(fileString);
                }
            }
            System.out.println("This step is OK.");
            return negList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
