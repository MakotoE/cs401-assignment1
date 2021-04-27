public class Soil {
	public static boolean drains(int width, Pair[] connections) {
		// id 0 = top; id 1 = bottom

		WeightedPathCompression graph = new WeightedPathCompression(width * width + 2);
		for (int i = 0; i < width; i++) {
			graph.union(coordinate_to_id(width, i, 0), 0);
			graph.union(coordinate_to_id(width, i, width - 1), 1);
		}

		for (Pair pair : connections) {
			graph.union(pair.a, pair.b);
		}

		return graph.isConnected(0, 1);
	}

	public static int coordinate_to_id(int width, int x, int y) {
		return y * width + x + 2;
	}
}

class Pair {
	public final int a;
	public final int b;

	public Pair(int a, int b) {
		this.a = a;
		this.b = b;
	}
}