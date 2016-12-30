package main;

import java.util.HashSet;
import java.util.List;

/*
* function: extract negative fasta file by positive fasta file
* */
public class MainControl {
	public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar EasyNegProtein.jar input.fasta output.fasta");
            System.out.println("input.fasta: original protein fasta file");
            System.out.println("output.fasta: negative protein fasta file name");
            return;
        }

        String fastaFile = args[0];
        String negFile = args[1];

		/*extract PFAM ID by raw fasta file and URL API, maybe repetitive parts exists.*/
		System.out.println(">>1. Getting protein family ID by your fasta file ...");
		List<String> pfamList = new GetProteinFamilyID().run(fastaFile, "ProteinIDToPfamIDDatabase.txt");
		HashSet<String> pfamHashSet = new HashSet<>(pfamList);

        /**/
		System.out.println(">>2. Getting negative protein family ID by protein family ID ...");
        List<String> negList = new DeletePosFamilyID().run(pfamHashSet, "ALLPFAM");

		System.out.println(">>3. Getting final negative protein sequences ...");
		new GetNegativeExample().run(negList, "ALLPFAM", negFile);

		System.out.println("Finished!");
	}
}
