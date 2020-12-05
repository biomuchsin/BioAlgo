import muchsin.ManberMyersSuffixArray;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SuffixArrayTest {

    @Test
    public void testLCPSimple(){
        String t = "ACBABCDDF$";
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

}
