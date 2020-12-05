package muchsin;

import java.util.Arrays;

public class NaiveSuffixArray implements SuffixArray {

	String query;
	int[] suffix;
	int[] LCP;

	@Override
	public int[] build(String t) {
		Integer[] re = new Integer[t.length()];
		for (int i=0; i<re.length; i++)
			re[i] = i;
		Arrays.sort(re,(a,b)->t.substring(a).compareTo(t.substring(b)));
		int[] re2 = new int[re.length];
		for (int i=0; i<re.length; i++)
			re2[i] = re[i];
		return re2;
	}

	@Override
	public int[] getSuffixArray() {
		return suffix;
	}

	@Override
	public String getString() {
		return query;
	}

	@Override
	public void setLCPTable(int[] lcp) {
		this.LCP = lcp;
	}


}
