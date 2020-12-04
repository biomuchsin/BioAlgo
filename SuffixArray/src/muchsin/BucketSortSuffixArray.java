package muchsin;

import java.util.HashSet;
import java.util.TreeMap;

public class BucketSortSuffixArray {

    private int[] bucketArray;
    private int[] reverseBucket;
    private boolean[] bucketBounds;
    private int numBuckets;

    private void setBucketArray(int[] bucketArray) {
        this.bucketArray = bucketArray;
    }

    private void setBucketBounds(boolean[] bucketBounds) {
        this.bucketBounds = bucketBounds;
    }

    private void setReverseBucket(int[] reverseBucket) {
        this.reverseBucket = reverseBucket;
    }

    private void setNumBuckets(int numBuckets) {
        this.numBuckets = numBuckets;
    }

    public int[] getBucketArray() {
        return bucketArray;
    }

    public boolean[] getBucketBounds() {
        return bucketBounds;
    }

    public int[] getReverseBucket() {
        return reverseBucket;
    }

    public int getNumBuckets() {
        return numBuckets;
    }

    public static BucketSortSuffixArray buildFromString(String t) {

        TreeMap<Character, HashSet<Integer>> firstChar = new TreeMap<>();

        for(int i=0; i < t.length(); i++) {
            HashSet<Integer> index = firstChar.computeIfAbsent(t.charAt(i), k -> new HashSet<>());
            index.add(i);
        }

        int[] buildBucketArray = new int[t.length()];
        int[] buildReverseBucket = new int[t.length()];
        boolean[] buildBucketBounds = new boolean[t.length()];
        int buildNumBuckets = 0;

        int bIdx = 0;
        for(HashSet<Integer> index: firstChar.values()) {
            buildBucketBounds[bIdx] = true;
            buildNumBuckets++;
            for(int cIdx: index) {
                buildBucketArray[bIdx] = cIdx;
                buildReverseBucket[cIdx] = bIdx;
                bIdx++;
            }
        }

        BucketSortSuffixArray bsa = new BucketSortSuffixArray();
        bsa.setBucketArray(buildBucketArray);
        bsa.setReverseBucket(buildReverseBucket);
        bsa.setBucketBounds(buildBucketBounds);
        bsa.setNumBuckets(buildNumBuckets);

        return bsa;
    }

}
