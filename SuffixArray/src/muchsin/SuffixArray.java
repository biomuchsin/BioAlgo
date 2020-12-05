package muchsin;

import java.io.IOException;

public interface SuffixArray {
	
	int[] build(String t);
	
	default boolean check(String t, int[] sa) {
		boolean re = true;
		for (int i=1; i<t.length(); i++) {
			if (t.substring(sa[i-1]).compareTo(t.substring(sa[i]))>0) {
				System.err.println("ERROR: Not sorted here:");
				try {
					output(System.err,t,sa,i-1,i,50);
				} catch (IOException e) {
				}
				re = false;
			}
		}
		return re;
	}
	

	default void output(Appendable out, String t, int[] sa, int start, int end) throws IOException {
		for (int i=Math.max(0, start); i<Math.min(sa.length, end); i++)
			out.append(String.format("%d\t%d\n", i, sa[i]));
	}

	default void output(Appendable out, String t, int[] sa, int start, int end, int suffLen) throws IOException {
		for (int i=Math.max(0, start); i<Math.min(sa.length, end); i++)
			out.append(String.format("%d\t%d\t%s\n", i, sa[i], t.substring(sa[i],Math.min(sa[i]+suffLen,t.length()))));
	}

	default int[] computeLCPTable() {

		String query = getString();
		int[] suffix = getSuffixArray();
		int[] LCP = new int[suffix.length];

		int[] reversedSA = new int[suffix.length];
		for(int idx=0; idx < suffix.length; idx++) {
			reversedSA[suffix[idx]] = idx;
		}

		int numMatch = 0;
		for(int idx = 0; idx < suffix.length-1; idx++) {

			int compareIdx = suffix[reversedSA[idx]-1];

			if(numMatch == 0) {
				for(int pos = 0; pos < suffix.length-idx-1; pos++) {
					if (query.charAt(idx+pos) == query.charAt(compareIdx+pos)) {
						numMatch++;
					} else if (compareIdx+pos >= suffix.length-1) {
						break;
					} else {
						break;
					}
				}
			} else {
				numMatch--;
			}

			LCP[reversedSA[idx]] = numMatch;
		}
		LCP[0] = -1;
		setLCPTable(LCP);

		return LCP;

	}

	int[] getSuffixArray();
	String getString();
	void setLCPTable(int[] lcp);
	
}
