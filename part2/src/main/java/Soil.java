import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

public class Soil {
	/**
	 * Pass path to file as argument. Outputs if the soil drains or does not train.
	 * @throws IOException file does not exist at path
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			throw new RuntimeException("path is missing");
		}

		try (var file = Files.newBufferedReader(Path.of(args[0]))) {
			if (drains(readFile(file))) {
				System.out.println("Allows water to drain");
			} else {
				System.out.println("Don’t allow water to drain");
			}
		}
	}

	/**
	 * @param file soil data
	 * @return Grid that represents the soil
	 */
	public static Grid readFile(Readable file) {
		var scanner = new Scanner(file);
		var squares = new Vector<Square>();

		int width = 0;

		int row = 0;
		while (scanner.hasNext()) {
			var str = scanner.next();
			if (row == 0) {
				width = str.replaceAll("\\s", "").length();
			}

			for (int i = 0; i < width; i++) {
				switch (str.charAt(i)) {
					case '0' -> squares.add(Square.Hold);
					case '1' -> squares.add(Square.Drain);
					case ' ' | '\t' -> {}
					default -> throw new RuntimeException("invalid character");
				}
			}

			row++;
		}

		return new Grid(squares.toArray(Square[]::new), width);
	}

	/**
	 * Determines if the given soil would drain or not according to the percolation theory.
	 * @return true if the soil would drain
	 */
	public static boolean drains(Grid grid) {
		var topID = grid.width() * grid.width();
		var bottomID = topID + 1;

		var graph = new WeightedPathCompression(grid.width() * grid.width() + 2);
		for (int i = 0; i < grid.width(); i++) {
			graph.union(i, topID);
			graph.union(grid.width() * (grid.width() - 1) + i, bottomID);
		}

		for (var pair : grid) {
			graph.union(pair.a, pair.b);
		}

		return graph.isConnected(topID, bottomID);
	}
}

/**
 * Two connected square IDs.
 */
class Pair {
	public final int a;
	public final int b;

	public Pair(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Pair pair = (Pair) o;
		return a == pair.a && b == pair.b;
	}

	@Override
	public String toString() {
		return "Pair{" +
			"a=" + a +
			", b=" + b +
			'}';
	}
}

enum Square {
	Hold,
	Drain,
}

/**
 * Represents the soil. It can iterate through all draining square pairs.
 */
class Grid implements Iterable<Pair> {
	public Grid(Square[] data, int width) {
		this.data = data;
		this.width = width;
	}

	public int width() {
		return width;
	}

	@Override
	public Iterator<Pair> iterator() {
		return new GridIterator(this);
	}

	protected final Square[] data;
	private final int width;

	static class GridIterator implements Iterator<Pair> {
		GridIterator(Grid grid) {
			this.grid = grid;
			this.index = 0;
			this.vertical = false;
			this.next = null;
		}

		@Override
		public boolean hasNext() {
			// Iterate through horizontal pairs
			if (!vertical) {
				if (grid.width == 0) {
					return false;
				}

				while (index < grid.width * grid.width - 1) {
					if (index % grid.width == grid.width - 1) {
						// Skip extra index at edge
						index += 2;
					} else {
						index++;
					}

					if (
						grid.data[index - 1] == Square.Drain
						&& grid.data[index] == Square.Drain
					) {
						this.next = new Pair(index - 1, index);
						return true;
					}
				}

				index = 0;
				vertical = true;
			}

			// Vertical pairs
			while (index < grid.width * (grid.width - 1)) {
				if (
					grid.data[index] == Square.Drain
					&& grid.data[index + grid.width] == Square.Drain
				) {
					this.next = new Pair(index, index + grid.width);
					index++;
					return true;
				}

				index++;
			}

			this.next = null;
			return false;
		}

		@Override
		public Pair next() {
			if (next == null) {
				throw new NoSuchElementException();
			}

			return next;
		}

		private final Grid grid;
		private int index;
		private boolean vertical;
		@Nullable
		private Pair next;
	}
}