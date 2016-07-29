package com.protein.negative;

public class MainControl {
	public static void main(String[] args) {
/*		String fastaFile = "uniprot-TATA-Binding+Protein.fasta";
		String negFile = "neg_TATA.fasta";*/
		String fastaFile = args[0];
		String negFile = args[1];
		if (args.length != 2) {
			System.out.println("Usage: java -jar input.fasta output.fasta");
			System.out.println("input.fasta: original protein fasta file");
			System.out.println("output.fasta: negative protein fasta file name");
			return;
		}
		String fileOut = null;
		
		System.out.println(">>1. Getting protein famliy ID by your fasta file ...");
		fileOut = new GetProteinFamliyID().run(fastaFile);
		if (fileOut == null) {
			System.out.println("ERROR");
			return;
		}
		//fileOut = "pfamID.txt";
		System.out.println(">>2. Deleting repeating protein famliy ID in last step ...");
		fileOut = new DeleteAbudent().run(fileOut);
		if (fileOut == null) {
			System.out.println("ERROR");
			return;
		}
		
		System.out.println(">>3. Deleting positive protein famliy ID in all famliy file (TEMP) ...");
		new DeletePosFamliyID().run(fileOut);
		
		System.out.println(">>4. Getting final negative protein sequence ...");
		new GetNegativeExample().run(negFile);
		
		System.out.println("Finished!");
	}
}
