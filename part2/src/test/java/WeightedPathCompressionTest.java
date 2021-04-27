import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class WeightedPathCompressionTest {
	@Test
	public void find() {
		{
			WeightedPathCompression uf = new WeightedPathCompression(0);
			assertThrows(ArrayIndexOutOfBoundsException.class, () -> uf.find(0));
		}
		{
			WeightedPathCompression uf = new WeightedPathCompression(2);
			uf.union(0, 1);
			assertEquals(uf.find(1), 0);
			assertEquals(uf.find(1), 0);
		}
		{
			WeightedPathCompression uf = new WeightedPathCompression(4);
			uf.union(0, 1);
			uf.union(2, 3);
			uf.union(2, 0);
			assertEquals(uf.find(1), 2);
			assertEquals(uf.find(1), 2);
		}
	}
}
