//Version 1.2 (10-9-2016)
package Stan.Lib.ReplaceWrappers;

public class RW {

	private final String from;
	private final Object to;

	public RW(String from, Object to) {
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public Object getTo() {
		return to;
	}

	@Override
	public String toString() {
		return "RW [from=" + from + ", to=" + to + "]";
	}

	public static RW[] ConcatRW(RW[] a, RW... b) {
		int aLen = a.length;
		int bLen = b.length;
		RW[] c = new RW[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

}
