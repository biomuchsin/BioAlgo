import muchsin.NaiveSuffixArrayBuilder;
import muchsin.SuffixArrayBuilder;
import muchsin.ManberMyersSuffixArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

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
    public void testSortCompareVirus() {

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
        SuffixArrayBuilder sab = new NaiveSuffixArrayBuilder();
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
        Assert.assertTrue(sab.check(sb.toString(), manbersSA));

    }

}
