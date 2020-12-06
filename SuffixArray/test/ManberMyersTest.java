import muchsin.FastaReader;
import muchsin.NaiveSuffixArray;
import muchsin.SuffixArray;
import muchsin.ManberMyersSuffixArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class ManberMyersTest {

    @Test
    public void testSortSimple() {
        String t = "ACBABCDDF$";
        ManberMyersSuffixArray mma = new ManberMyersSuffixArray();
        int[] sortedIndex = mma.build(t);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < sortedIndex.length; i++){
            sb.append(sortedIndex[i]);
            sb.append(": ");
            sb.append(t.substring(sortedIndex[i]));
            sb.append('\n');
        }
        System.out.print(sb);

        Assert.assertTrue(true);

    }

    @Test
    public void testSortVirus() {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("testfiles/hhv1.fna")))) {

            String line;
            while ((line=br.readLine())!=null) {
                if (line.startsWith(">")) {
                    if (sb.length()>0) {
                        System.err.println("WARNING: More than one sequence in fasta file, ignoring all but the first!");
                        break;
                    }
                }
                else
                    sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.append('$');

        long startTime = System.currentTimeMillis();
        SuffixArray sab = new NaiveSuffixArray();
        int[] sa = sab.build(sb.toString());
        long endTime = System.currentTimeMillis();
        System.out.print("Naive implementation: ");
        System.out.println(endTime-startTime);

        startTime = System.currentTimeMillis();
        ManberMyersSuffixArray mma = new ManberMyersSuffixArray();
        int[] manbersSA = mma.build(sb.toString());
        endTime = System.currentTimeMillis();
        System.out.print("Manber Myers algorithm: ");
        System.out.println(endTime - startTime);

        Assert.assertTrue(sab.check(sb.toString(), sa));
        Assert.assertTrue(mma.check(sb.toString(), manbersSA));

    }

    @Test
    public void testSortUseFastaReader() {

        HashMap<String, String> fastaRecord = FastaReader.parseAsHashmapString("testfiles/hhv1.fna");
        for(HashMap.Entry<String, String> record: fastaRecord.entrySet()) {
            String recordId = record.getKey();
            String sequence = record.getValue();

            sequence += "$";

            System.out.println("Processing: " + recordId);
            long startTime = System.currentTimeMillis();
            ManberMyersSuffixArray mma = new ManberMyersSuffixArray();
            int[] manbersSA = mma.build(sequence);
            long endTime = System.currentTimeMillis();
            System.out.print("Manber Myers algorithm: ");
            System.out.println(endTime - startTime);

            Assert.assertTrue(mma.check(sequence, manbersSA));

        }

    }

}
