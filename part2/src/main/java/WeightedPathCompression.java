public class WeightedPathCompression extends WeightedQuickUnion {
	public WeightedPathCompression(int n) {
		super(n);
	}

	@Override
	public int find(int p) {
		id[p] = super.find(p);
		return id[p];
	}
}
