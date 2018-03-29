
public class Probability {
	static int likely() {
		return likely(.4);
	}

	public static int likely(double factor) {
		int out = 0;
		double rand = Math.random();
		double curCompare = factor;
		while (rand < curCompare) {
			curCompare *= factor;
			out++;
		}
		return out;
	}
	
	// returns true if there are none a's in the array b
	public static boolean containsNone(String[] a, String[] b) {
		if (b == null)
			return false;// Not Sure
		for (String x : a) {
			for (String y : b)
				if (x.equals(y) || ("-" + x).equals(y)) {
					return false;
				}
		}
		return true;
	}
}
