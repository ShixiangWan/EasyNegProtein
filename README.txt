1.Application:
Getting negative protein sequences after getting the raw fasta file.

2.Preparation:
(1)The raw fasta fasta file(e.g. raw_example.fasta);
(2)File folder "ALLPFAM", including all protein famliy (fasta format, prepared).

3.Environment:
Java, Internet(better)

4.Usage:
if the raw fasta file is "raw_example.fasta", the output file is "negative.fata", then command is: 
java -jar protein_negative_sample.jar raw_example.fasta negative.fasta

Author: Shixiang Wan
Date: 2016.06.13

原理：
1. 抽取所有正例的PFAM家族信息
2. 在剩余没有出现正例的PFAM家族中，每个家族选一条最长的序列留下当反例

注意：输入的正例必须是uniport下载的文件，其他文件不可以。


Citation
Li Song, Dapeng Li, Xiangxiang Zeng, Yunfeng Wu, Li Guo, Quan Zou. nDNA-prot: Identification of DNA-binding Proteins Based on Unbalanced Classification. BMC Bioinformatics. 2014, 15:298.

Quan Zou, Zhen Wang, Xinjun Guan, Bin Liu, Yunfeng Wu, Ziyu Lin. An Approach for Identifying Cytokines Based On a Novel Ensemble Classifier. BioMed Research International. 2013, 2013:686090

Xian-Ying Cheng, Wei-Juan Huang, Shi-Chang Hu, Hai-Lei Zhang, Hao Wang, Jing-Xian Zhang, Hong-Huang Lin, Yu-Zong Chen, Quan Zou, Zhi-Liang Ji. A global characterization and identification of multifunctional enzymes. PLoS One. 2012,7(6):e38979