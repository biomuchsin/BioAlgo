import muchsin.FastaReader;
import muchsin.ManberMyersSuffixArray;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class SuffixArrayTest {

    @Test
    public void testLCPSimple(){
        String t = "MISSISSIMCPI$";
        ManberMyersSuffixArray mma = new ManberMyersSuffixArray();
        int[] sortedIndex = mma.build(t);
        System.out.println(Arrays.toString(sortedIndex));
        int[] LCP = mma.computeLCPTable();

        for(int i = 0; i < sortedIndex.length; i++){
            System.out.print(sortedIndex[i]);
            System.out.print(": ");
            System.out.print(t.substring(sortedIndex[i]));
            System.out.print("; LCP: ");
            System.out.println(LCP[i]);
        }

        Assert.assertTrue(true);
    }

    @Test
    // now works for >2 strings
    public void testLCSSimple(){
        String a = "MISSISSIPPI>";
        String b = "ISSMIP<";
        String c = "ISPMPI$";
        String t = a + b + c;
        ManberMyersSuffixArray mma = new ManberMyersSuffixArray();
        int[] sortedIndex = mma.build(t);
        System.out.println(Arrays.toString(sortedIndex));
        int[] LCP = mma.computeLCPTable();

        for(int i = 0; i < sortedIndex.length; i++){
            System.out.print(sortedIndex[i]);
            System.out.print(": ");
            System.out.print(t.substring(sortedIndex[i]));
            System.out.print("; LCP: ");
            System.out.println(LCP[i]);
        }

        Assert.assertTrue(true);
    }

    @Test
    public void testLCSVirus() {
        HashMap<String, String> hhv1Record = FastaReader.parseAsHashmapString("testfiles/hhv1.fna");
        HashMap<String, String> hhv2Record = FastaReader.parseAsHashmapString("testfiles/hhv2.fna");

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
                        if(sortedIndex[idx-1] > hhv1Sequence.length()) {
                            maxValue = LCP[idx];
                            maxIdx = sortedIndex[idx];
                        }
                    }
                }

                System.out.println("Sequence 1: " + hhv1Id);
                System.out.println("Sequence 2: " + hhv2Id);
                System.out.println("LCS length: " + maxValue);
                System.out.println(hhv2Sequence.substring(maxIdx, maxIdx+maxValue));

            }
        }
    }

}
