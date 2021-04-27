import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class SoilTest {
	@RunWith(Parameterized.class)
	public static class readFile {
		@Parameterized.Parameters
		public static Collection<Object> data() {
			return Arrays.asList(new Object[][]{
				{
					"",
					new Square[]{},
				},
				{
					"0",
					new Square[]{Square.Hold},
				},
				{
					"1",
					new Square[]{Square.Drain},
				},
				{
					"10\n01",
					new Square[]{Square.Drain, Square.Hold, Square.Hold, Square.Drain},
				},
				{
					"1 0\n0 1",
					new Square[]{Square.Drain, Square.Hold, Square.Hold, Square.Drain},
				},
			});
		}

		@Parameterized.Parameter
		public String text;

		@Parameterized.Parameter(1)
		public Square[] expected;

		@Test
		public void test() {
			assertArrayEquals(expected, Soil.readFile(new StringReader(text)).data);
		}
	}

	@RunWith(Parameterized.class)
	public static class drains {
		@Parameterized.Parameters
		public static Collection<Object> data() {
			return Arrays.asList(new Object[][] {
				{ // 0
					new Square[][]{},
					false,
				},
				{ // 1
					new Square[][]{
						{Square.Hold},
					},
					true,
				},
				{ // 2
					new Square[][]{
						{Square.Hold, Square.Hold},
						{Square.Hold, Square.Hold},
					},
					false,
				},
				{ // 3
					new Square[][]{
						{Square.Drain, Square.Drain},
						{Square.Hold, Square.Hold},
					},
					false,
				},
				{ // 4
					new Square[][]{
						{Square.Drain, Square.Hold},
						{Square.Drain, Square.Hold},
					},
					true,
				},
				{ // 5
					new Square[][]{
						{Square.Hold, Square.Hold, Square.Hold},
						{Square.Hold, Square.Hold, Square.Hold},
						{Square.Hold, Square.Hold, Square.Hold},
					},
					false,
				},
				{ // 6
					new Square[][]{
						{Square.Drain, Square.Drain, Square.Hold},
						{Square.Drain, Square.Hold, Square.Hold},
						{Square.Drain, Square.Hold, Square.Hold},
					},
					true,
				},
			});
		}

		@Parameterized.Parameter
		public Square[][] squares;

		@Parameterized.Parameter(1)
		public boolean expected;

		@Test
		public void test() {
			Square[] flattened = Arrays.stream(squares)
				.flatMap(Arrays::stream)
				.toArray(Square[]::new);

			assertEquals(expected, Soil.drains(new Grid(flattened, squares.length)));
		}
	}

	@RunWith(Parameterized.class)
	public static class GridTest {
		@Parameterized.Parameters
		public static Collection<Object> data() {
			return Arrays.asList(new Object[][]{
				{
					new Square[][] {},
					new Pair[] {},
				},
				{
					new Square[][] {
						{Square.Drain},
					},
					new Pair[] {},
				},
				{
					new Square[][] {
						{Square.Hold, Square.Hold},
						{Square.Hold, Square.Hold},
					},
					new Pair[] {},
				},
				{
					new Square[][] {
						{Square.Drain, Square.Hold},
						{Square.Hold, Square.Hold},
					},
					new Pair[] {},
				},
				{
					new Square[][] {
						{Square.Drain, Square.Hold},
						{Square.Hold, Square.Drain},
					},
					new Pair[] {},
				},
				{
					new Square[][] {
						{Square.Drain, Square.Drain},
						{Square.Hold, Square.Hold},
					},
					new Pair[] {
						new Pair(0, 1),
					},
				},
				{
					new Square[][] {
						{Square.Drain, Square.Drain},
						{Square.Drain, Square.Drain},
					},
					new Pair[] {
						new Pair(0, 1),
						new Pair(2, 3),
						new Pair(0, 2),
						new Pair(1, 3),
					},
				},
				{
					new Square[][] {
						{Square.Drain, Square.Drain, Square.Drain},
						{Square.Drain, Square.Drain, Square.Drain},
						{Square.Drain, Square.Drain, Square.Drain},
					},
					new Pair[] {
						new Pair(0, 1),
						new Pair(1, 2),
						new Pair(3, 4),
						new Pair(4, 5),
						new Pair(6, 7),
						new Pair(7, 8),
						new Pair(0, 3),
						new Pair(1, 4),
						new Pair(2, 5),
						new Pair(3, 6),
						new Pair(4, 7),
						new Pair(5, 8),
					},
				},
			});
		}

		@Parameterized.Parameter
		public Square[][] squares;

		@Parameterized.Parameter(1)
		public Pair[] expected;

		@Test
		public void test() {
			Square[] flattened = Arrays.stream(squares)
				.flatMap(Arrays::stream)
				.toArray(Square[]::new);

			var result = new Vector<Pair>();
			for (Pair pair : new Grid(flattened, squares.length)) {
				result.add(pair);
			}

			assertArrayEquals(expected, result.toArray(Pair[]::new));
		}
	}
}
