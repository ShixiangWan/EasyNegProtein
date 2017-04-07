import java.util.HashSet;
import java.util.List;

/*
* function: extract negative fasta file by positive fasta file
* */
public class MainControl {

    static String allPFAMPath = "ALLPFAM";
    static String proteinIDDatabase = "ProteinIDToPfamIDDatabase.txt";
    //public static String ALLPFAMDatabase = "ALLPFAMIndex.txt";

	public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            System.out.println("Usage: java -jar EasyNegProtein.jar input.fasta output.fasta mode");
            System.out.println("input.fasta: original protein fasta file");
            System.out.println("output.fasta: negative protein fasta file name");
            System.out.println("mode: number 0, no database; if not, number 1.");
            return;
        }

        String fastaFile = args[0];
        String negFile = args[1];
        int isDatabase = 0;
        if (args.length == 3)
            isDatabase = Integer.parseInt(args[2]);

        // 测试参数
        /*String fastaFile = "F:/uniprot-Alzheimer.fasta";
        String negFile = "F:/uniprot-Alzheimer-neg.fasta";
        allPFAMPath = "F:/ALLPFAM";
        proteinIDDatabase = "F:/ProteinIDToPfamIDDatabase.txt";
        int isDatabase = 1;*/

		/*extract PFAM ID by raw fasta file and URL API, maybe repetitive parts exists.*/
		System.out.println(">>1. Getting protein family ID by your fasta file ...");
		List<String> pfamList = new GetProteinFamilyID().fast_run(fastaFile, isDatabase);
		HashSet<String> pfamHashSet = new HashSet<>(pfamList);

		System.out.println(">>2. Getting negative protein family ID by protein family ID ...");
        List<String> negList = new DeletePosFamilyID().run(pfamHashSet, allPFAMPath);

		System.out.println(">>3. Getting final negative protein sequences ...");
		new GetNegativeExample().run(negList, allPFAMPath, negFile);

		System.out.println("Finished!");
	}
}
