package muchsin;

public class ManberMyersSuffixArray implements SuffixArrayBuilder {

    @Override
    public int[] build(String t) {

        BucketSortSuffixArray bsa = BucketSortSuffixArray.buildFromString(t);

        int[] bucketArray = bsa.getBucketArray();
        int[] reverseBucket = bsa.getReverseBucket();
        boolean[] bucketBounds = bsa.getBucketBounds();

        int numBuckets = bsa.getNumBuckets();

        int strLen = t.length();

        int[] nextIter = new int[strLen];
        boolean[] isMoved = new boolean[strLen];
        int[] bucketIter = new int[strLen];

        nextIter[0] = bucketArray[0];
        while(numBuckets < strLen) {

            int markBucket = 0;
            for(int idx=0; idx < strLen; idx++) {
                if(bucketBounds[idx]) {
                    markBucket++;
                }
                int prevSuffix = bucketArray[idx] - 1;
                if(prevSuffix >= 0) {
                    int prevSuffixPos = reverseBucket[prevSuffix];
                    if(isMoved[prevSuffixPos]) {
                        int moveRight = prevSuffixPos + 1;
                        while (isMoved[moveRight]) {
                            moveRight++;
                        }
                        nextIter[moveRight] = prevSuffix;
                        isMoved[moveRight] = true;
                        bucketIter[moveRight] = markBucket;
                    } else {
                        int moveLeft = prevSuffixPos;
                        while(!(bucketBounds[moveLeft] || isMoved[moveLeft-1])) {
                            moveLeft--;
                        }
                        nextIter[moveLeft] = prevSuffix;
                        isMoved[moveLeft] = true;
                        bucketIter[moveLeft] = markBucket;
                    }
                }
            }

            numBuckets = 0;
            for(int idx = 0; idx < strLen; idx++) {

                boolean isBoundary = (idx==0) || (bucketIter[idx-1] < bucketIter[idx]);
                bucketBounds[idx] |= isBoundary;
                if(bucketBounds[idx]) {
                    numBuckets++;
                }

                bucketArray[idx] = nextIter[idx];
                reverseBucket[bucketArray[idx]] = idx;
                nextIter[idx] = 0;

                isMoved[idx] = false;

                if(idx>=1) {
                    bucketIter[idx-1] = 0;
                }
            }

            bucketIter[strLen-1] = 0;
            nextIter[0] = bucketArray[0];

        }

        return bucketArray;
    }

}
