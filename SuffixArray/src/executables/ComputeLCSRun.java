package executables;

import muchsin.FastaReader;
import muchsin.ManberMyersSuffixArray;

import java.util.HashMap;

public class ComputeLCSRun {

    public static void main(String args[]) {
        // only for quick build, without argument, require two fasta file in arg1 and arg2

        HashMap<String, String> hhv1Record = FastaReader.parseAsHashmapString(args[0]);
        HashMap<String, String> hhv2Record = FastaReader.parseAsHashmapString(args[1]);

        for(HashMap.Entry<String, String> hhv1: hhv1Record.entrySet()) {
            for(HashMap.Entry<String, String> hhv2: hhv2Record.entrySet()) {

                String hhv1Id = hhv1.getKey();
                String hhv1Sequence = hhv1.getValue();
                String hhv2Id = hhv2.getKey();
                String hhv2Sequence = hhv2.getValue();

                String sequence = hhv1Sequence + ">" + hhv2Sequence + "<";
                ManberMyersSuffixArray mma = new ManberMyersSuffixArray();
                int[] sortedIndex = mma.build(sequence);
                int[] LCP = mma.computeLCPTable();

                int maxValue = 0;
                int maxIdx = 0;

                for(int idx = 1; idx < sequence.length(); idx++) {
                    if(LCP[idx] > maxValue) {
                        if((sortedIndex[idx] < hhv1Sequence.length()) && (sortedIndex[idx-1] > hhv1Sequence.length())) {
                            maxValue = LCP[idx];
                            maxIdx = sortedIndex[idx];
                        }
                    }
                }

                System.out.println("Sequence 1: " + hhv1Id);
                System.out.println("Sequence 2: " + hhv2Id);
                System.out.println("LCS length: " + maxValue);
                System.out.println(hhv1Sequence.substring(maxIdx, maxIdx+maxValue));

            }
        }
    }

}
