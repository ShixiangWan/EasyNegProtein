# EasyNegProtein
Getting negative protein sequences after getting the raw fasta file.

## Preparation

* The raw fasta fasta file(e.g. raw_example.fasta);
* File folder "ALLPFAM", including all protein famliy (fasta format, prepared).
* File "ProteinIDToPfamIDDatabase", contains the relation between protein ID and PFAM ID.

## Environment

Java, Internet(better)

## Usage
Suppose that the raw fasta file is "example.fasta", the output file is "negative.fata".

if the 'ProteinIDToPfamIDDatabase' doesn't exist, then:
```
java -jar EasyNegProtein.jar example.fasta negative.fasta
```

if the 'ProteinIDToPfamIDDatabase' exists, then:
```
java -jar -Xmx8000m EasyNegProtein.jar example.fasta negative.fasta 1
```
## Principle

* Extract all positive PFAM information, then choose the longest sequence as negative sequence among the rest of all positive PFAM.
* Your positive sequence is better derived from Uniport Database.

## Upgrade

* 2016-06-13, version 0.10:
  * standalone running
  * internet running
* 2016-12-30, version 0.11:
  * no temp files
  * smaller database size
  * bug fixes
* 2017-04-07, version 0.12:
  * multiple threads
  * add 'mode' option