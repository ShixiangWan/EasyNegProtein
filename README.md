# EasyNegProtein
Getting negative protein sequences after getting the raw fasta file.

##Preparation

* The raw fasta fasta file(e.g. raw_example.fasta);
* File folder "ALLPFAM", including all protein famliy (fasta format, prepared).
* File "ProteinIDToPfamIDDatabase", contains the relation between protein ID and PFAM ID.

##Environment

Java, Internet(better)

##Usage
if the raw fasta file is "example.fasta", the output file is "negative.fata", then command is: 

```
java -jar protein_negative_sample.jar example.fasta negative.fasta
```

##Principle

* Extract all positive PFAM information, then choose the longest sequence as negative sequence among the rest of all positive PFAM.
* Your positive sequence is better derived from Uniport Database.

##Upgrade

* 2016-06-13, version 0.1:
  * standalone running
  * internet running
* 2016-12-30, version newer:
  * no temp files
  * smaller database size
  * bug fixes