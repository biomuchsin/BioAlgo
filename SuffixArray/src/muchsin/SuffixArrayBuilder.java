package muchsin;

import java.io.IOException;

public interface SuffixArrayBuilder {
	
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
	
}
